package com.bookservice.ui.activity.home;

import android.app.Activity;

import com.bookservice.data.model.category.CategoryResponse;
import com.bookservice.data.model.offers.GetOffersResponse;
import com.bookservice.network.AsyncTaskConnection;
import com.bookservice.network.IConnectionListener;
import com.bookservice.network.Result;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import static com.bookservice.constants.Constants.GET;
import static com.bookservice.constants.ConstantsUrl.CATEGORIES;
import static com.bookservice.constants.ConstantsUrl.OFFERS;

public class HomePresenter implements IHomePresenter {

    IHomeView view;

    public HomePresenter(IHomeView iHomeView) {
        view = iHomeView;
    }

    @Override
    public void getOffers(Activity activity) {

       /* GetOffersResponse getOffersResponse = new GetOffersResponse();
        getOffersResponse.setUrl("");

        List<Offer> offers = new ArrayList<>();
        Offer offer = new Offer();
        offer.setDescription("Car wash 25% off");
        offer.setName("Car wash");
        offers.add(offer);

        Offer offer1 = new Offer();
        offer1.setDescription("40% off on Body work");
        offer1.setName("Body Work");
        offers.add(offer1);
        getOffersResponse.setOffers(offers);
        view.onSuccessOffers(getOffersResponse);*/

        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(OFFERS, activity, GET, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    GetOffersResponse getOffersResponse = gson.fromJson(jsonObject.toString(), GetOffersResponse.class);
                    view.onSuccessOffers(getOffersResponse);
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

    @Override
    public void getCategories(Activity activity) {
      /*  CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setUrl("");


        List<Category> categories = new ArrayList<>();
        Category category = new Category();
        category.setDescription("Body Work");
        category.setName("Body Work");
        category.setUrl(R.drawable.bodywork);
        categories.add(category);

        Category category1 = new Category();
        category1.setDescription("General Service");
        category1.setName("General Service");
        category1.setUrl(R.drawable.regular);
        categories.add(category1);

        Category category2 = new Category();
        category2.setDescription("Painting");
        category2.setName("Painting");
        category2.setUrl(R.drawable.panting);
        categories.add(category2);

        Category category3 = new Category();
        category3.setDescription("Repair");
        category3.setName("Repair");
        category3.setUrl(R.drawable.repair);
        categories.add(category3);

        categoryResponse.setCategories(categories);
        view.onSuccessCategory(categoryResponse);*/

        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(CATEGORIES, activity, GET, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    CategoryResponse categoryResponse = gson.fromJson(jsonObject.toString(), CategoryResponse.class);
                    view.onSuccessCategory(categoryResponse);
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
