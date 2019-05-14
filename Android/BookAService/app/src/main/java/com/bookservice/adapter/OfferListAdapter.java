package com.bookservice.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bookservice.R;
import com.bookservice.data.model.offers.OfferList;
import com.bookservice.ui.activity.offer.OfferDetailActivity;
import com.bookservice.utils.ImageLoad;

import java.util.List;

import static com.bookservice.constants.Constants.EXTRA_DESCRIPTION;
import static com.bookservice.constants.Constants.EXTRA_NAME;
import static com.bookservice.constants.Constants.EXTRA_OFFER_ID;
import static com.bookservice.constants.Constants.EXTRA_SERVICE_CENTER_ID;
import static com.bookservice.constants.Constants.EXTRA_SERVICE_CENTER_NAME;

public class OfferListAdapter extends RecyclerView.Adapter<OfferListAdapter.OfferListAdapterItemViewHolder> {

    private List<OfferList> arrayList;
    private Activity activity;
    String mUrl;

    public OfferListAdapter(List<OfferList> details, Activity activity, String url) {
        this.arrayList = details;
        this.activity = activity;
    }

    @NonNull
    @Override
    public OfferListAdapter.OfferListAdapterItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_offers_list, viewGroup, false);
        return new OfferListAdapter.OfferListAdapterItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OfferListAdapter.OfferListAdapterItemViewHolder itemViewHolder, final int i) {
        final String name = arrayList.get(i).getName();
        final String description = arrayList.get(i).getDescription();
        final String serviceCenterId = arrayList.get(i).getServiceCenterId();
        final String serviceCenterName = arrayList.get(i).getServiceCenterName();
        final String url = arrayList.get(i).getUrl();
        final String id = arrayList.get(i).getId();

        itemViewHolder.mName.setText(name);
        itemViewHolder.mDescription.setText(description);
        ImageLoad.loadImage(mUrl + url, itemViewHolder.imageBg);


        itemViewHolder.flBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, OfferDetailActivity.class);
                intent.putExtra(EXTRA_NAME, name);
                intent.putExtra(EXTRA_DESCRIPTION, description);
                intent.putExtra(EXTRA_SERVICE_CENTER_ID, serviceCenterId);
                intent.putExtra(EXTRA_SERVICE_CENTER_NAME, serviceCenterName);
                intent.putExtra(EXTRA_OFFER_ID, id);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class OfferListAdapterItemViewHolder extends RecyclerView.ViewHolder {

        TextView mName;
        TextView mDescription;
        ImageView imageBg;
        FrameLayout flBg;

        public OfferListAdapterItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.name);
            mDescription = (TextView) itemView.findViewById(R.id.description);
            imageBg = (ImageView) itemView.findViewById(R.id.image_bg);
            flBg = (FrameLayout) itemView.findViewById(R.id.fl_bg);
        }
    }
}

