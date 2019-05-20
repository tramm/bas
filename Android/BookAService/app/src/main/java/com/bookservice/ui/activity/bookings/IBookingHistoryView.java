package com.bookservice.ui.activity.bookings;

import com.bookservice.data.model.bookinghistory.BookingHistoryResponse;

public interface IBookingHistoryView {

    public void onSuccessBookingHistory(BookingHistoryResponse bookingHistoryResponse);
    public void onError(String message);


}
