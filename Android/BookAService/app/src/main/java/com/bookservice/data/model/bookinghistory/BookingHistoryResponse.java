
package com.bookservice.data.model.bookinghistory;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingHistoryResponse {

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("bookings")
    @Expose
    private List<Booking> bookings = null;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

}
