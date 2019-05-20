package com.bookservice.ui.activity.bookings;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bookservice.R;
import com.bookservice.adapter.BookingHistoryAdapter;
import com.bookservice.data.model.bookinghistory.Booking;
import com.bookservice.data.model.bookinghistory.BookingHistoryResponse;
import com.bookservice.ui.base.BsBaseActivity;
import com.bookservice.utils.view.BsToast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookingHistoryActivity extends BsBaseActivity implements IBookingHistoryView {

    @BindView(R.id.recycler_View)
    RecyclerView recyclerView;
    BookingHistoryPresenter bookingHistoryPresenter;
    Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_list);
        ButterKnife.bind(this);
        activity = BookingHistoryActivity.this;

        showTile("Booking History");
        showWhiteStatusBar(Color.WHITE);
        showBackButton();

        bookingHistoryPresenter = new BookingHistoryPresenter(this);
        bookingHistoryPresenter.getBookings(activity);
    }

    @Override
    public void onSuccessBookingHistory(BookingHistoryResponse bookingHistoryResponse) {

        final List<Booking> bookings = bookingHistoryResponse.getBookings();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);

        if (bookings == null || bookings.isEmpty() ) {

        }else{
            BookingHistoryAdapter bookingHistoryAdapter = new BookingHistoryAdapter(bookings, this);
            recyclerView.setAdapter(bookingHistoryAdapter);
        }

    }

    @Override
    public void onError(String message) {
        BsToast.showLong(activity, message);
    }
}
