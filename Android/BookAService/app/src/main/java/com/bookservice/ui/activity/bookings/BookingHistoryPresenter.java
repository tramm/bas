package com.bookservice.ui.activity.bookings;

import android.app.Activity;

import com.bookservice.data.model.bookinghistory.BookingHistoryResponse;
import com.bookservice.network.AsyncTaskConnection;
import com.bookservice.network.IConnectionListener;
import com.bookservice.network.Result;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import static com.bookservice.constants.Constants.GET;
import static com.bookservice.constants.ConstantsUrl.BOOKINGS_HISTORY;

public class BookingHistoryPresenter implements IBookingHistoryPresenter {

    IBookingHistoryView view;

    public BookingHistoryPresenter(IBookingHistoryView iBookingHistoryView) {
        view = iBookingHistoryView;
    }

    @Override
    public void getBookings(Activity activity) {
        AsyncTaskConnection asyncTaskConnection = new AsyncTaskConnection(BOOKINGS_HISTORY,
                activity, GET, new IConnectionListener() {
            @Override
            public void onSuccess(Result result) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result.getResponse());
                    Gson gson = new Gson();
                    BookingHistoryResponse bookingHistoryResponse = gson.fromJson(jsonObject.toString(), BookingHistoryResponse.class);
                    view.onSuccessBookingHistory(bookingHistoryResponse);
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
