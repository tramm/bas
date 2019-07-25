package com.bookservice.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bookservice.R;
import com.bookservice.data.model.bookinghistory.Booking;
import com.bookservice.data.model.bookinghistory.Offer;
import com.bookservice.data.model.bookinghistory.Partner;
import com.bookservice.data.model.bookinghistory.Vehicle;

import java.util.List;

public class BookingHistoryAdapter extends RecyclerView.Adapter<BookingHistoryAdapter.OfferListAdapterItemViewHolder> {

    private List<Booking> arrayList;
    private Activity activity;
    String mUrl;

    public BookingHistoryAdapter(List<Booking> details, Activity activity) {
        this.arrayList = details;
        this.activity = activity;
    }

    @NonNull
    @Override
    public BookingHistoryAdapter.OfferListAdapterItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_booking_history_list, viewGroup, false);
        return new BookingHistoryAdapter.OfferListAdapterItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BookingHistoryAdapter.OfferListAdapterItemViewHolder itemViewHolder, final int i) {
        final String dateOfService = arrayList.get(i).getDateOfService();
        itemViewHolder.mDateOfService.setText(dateOfService);

        final Partner partner = arrayList.get(i).getPartner();
        if (partner != null) {
            final String name = partner.getName();
            final String mobile = partner.getMobile() + ", " + partner.getEmail();
            itemViewHolder.mName.setText(name);
            itemViewHolder.mMobile.setText(mobile);
        } else {
            itemViewHolder.mName.setVisibility(View.GONE);
            itemViewHolder.mMobile.setVisibility(View.GONE);
        }

        final Vehicle vehicle = arrayList.get(i).getVehicle().get(0);
        if (vehicle != null) {
            final String brand = vehicle.getManufacturer().getName() + ", " + vehicle.getModel().getName();
            itemViewHolder.mBrand.setText(brand);
        } else {
            itemViewHolder.mBrand.setVisibility(View.GONE);
        }

        final Offer offer = arrayList.get(i).getOffer();
        if (offer != null) {
            final String offerDescription = offer.getName() + ", " + offer.getDescription();
            itemViewHolder.mOffer.setText(offerDescription);
        } else {
            itemViewHolder.mOffer.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class OfferListAdapterItemViewHolder extends RecyclerView.ViewHolder {

        TextView mName;
        TextView mMobile;
        TextView mBrand;
        TextView mOffer;
        TextView mDateOfService;

        public OfferListAdapterItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.name);
            mMobile = (TextView) itemView.findViewById(R.id.mobile);
            mBrand = (TextView) itemView.findViewById(R.id.brand);
            mOffer = (TextView) itemView.findViewById(R.id.offer);
            mDateOfService = (TextView) itemView.findViewById(R.id.date_of_service);
        }
    }
}

