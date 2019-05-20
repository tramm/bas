package com.bookservice.ui.activity.offer;

import android.app.Activity;

import com.bookservice.data.model.offers.OfferListReponse;
import com.bookservice.network.AsyncTaskConnection;
import com.bookservice.network.IConnectionListener;
import com.bookservice.network.Result;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import static com.bookservice.constants.Constants.CATEGORY_ID;
import static com.bookservice.constants.Constants.POST;
import static com.bookservice.constants.ConstantsUrl.CATEGORY_OFFERS_LIST;

public class OfferListPresenter implements IOfferListPresenter {

    IOffersListView view;

    public OfferListPresenter(IOffersListView iOffersListView) {
        view = iOffersListView;
    }

    @Override
    public void getOfferList(Activity activity, String catId) {

        String json = "";
        JSONObject postDataParams = new JSONObject();
        try {
            postDataParams.put(CATEGORY_ID, catId);
            json = postDataParams.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(CATEGORY_OFFERS_LIST, activity, json,
                POST, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    OfferListReponse offerListReponse = gson.fromJson(jsonObject.toString(), OfferListReponse.class);
                    view.onSuccessOfferList(offerListReponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFail(Result result) {
                view.onError("Something went wrong, Please try after sometime");
            }

            @Override
            public void onNetworkFail(String message) {
                view.onError(message);
            }
        });
        asyncTaskConnection.execute();
    }
}
