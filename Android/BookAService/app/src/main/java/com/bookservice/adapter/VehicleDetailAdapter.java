package com.bookservice.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bookservice.R;
import com.bookservice.data.model.vehicle.UserVehicle;
import com.bookservice.event.EditVehicleEvent;
import com.bookservice.ui.base.BaseApplication;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class VehicleDetailAdapter extends RecyclerView.Adapter<VehicleDetailAdapter.VehicleDetailAdapterItemViewHolder> {

    private List<UserVehicle> arrayList;
    private Activity activity;
    boolean mIsFrom;

    public VehicleDetailAdapter(List<UserVehicle> details, Activity activity, boolean isFrom) {
        this.arrayList = details;
        this.activity = activity;
        this.mIsFrom = isFrom;
    }

    @NonNull
    @Override
    public VehicleDetailAdapter.VehicleDetailAdapterItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_vehicle_detail, viewGroup, false);
        return new VehicleDetailAdapter.VehicleDetailAdapterItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VehicleDetailAdapterItemViewHolder itemViewHolder, final int i) {

        final UserVehicle vehicle = arrayList.get(i);
        if (vehicle != null) {
            final String manufacturer = vehicle.getManufacturer().getName();
            final String model = vehicle.getModel().getName();
            String brand = manufacturer + " " + model;
            itemViewHolder.mBrand.setText(brand);

            itemViewHolder.mRegNo.setText(vehicle.getRegistrationNumber());
            final Integer year = vehicle.getYear();
            if (year != null) {
                itemViewHolder.mYear.setText(String.valueOf(year));
            }

            itemViewHolder.mType.setText(vehicle.getTag());

        }

        itemViewHolder.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseApplication.getEventBus().post(new EditVehicleEvent(vehicle));
            }
        });

        itemViewHolder.llParentVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIsFrom) {
                    Intent intent = new Intent();
                    intent.putExtra("vehicle", vehicle);
                    activity.setResult(RESULT_OK, intent);
                    activity.finish();
                }
            }
        });

        if (mIsFrom) {
            itemViewHolder.rb.setVisibility(View.VISIBLE);
        }
        itemViewHolder.rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIsFrom) {
                    Intent intent = new Intent();
                    intent.putExtra("vehicle", vehicle);
                    activity.setResult(RESULT_OK, intent);
                    activity.finish();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class VehicleDetailAdapterItemViewHolder extends RecyclerView.ViewHolder {

        TextView mBrand;
        TextView mRegNo;
        TextView mYear;
        TextView mType;
        TextView mEdit;
        LinearLayout llParentVehicle;
        RadioButton rb;

        public VehicleDetailAdapterItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mBrand = (TextView) itemView.findViewById(R.id.brand);
            mType = (TextView) itemView.findViewById(R.id.type);
            mRegNo = (TextView) itemView.findViewById(R.id.reg_no);
            mYear = (TextView) itemView.findViewById(R.id.year);
            mEdit = (TextView) itemView.findViewById(R.id.edit);
            llParentVehicle = (LinearLayout) itemView.findViewById(R.id.ll_parent_vehicle);
            rb = (RadioButton) itemView.findViewById(R.id.radioButton);
        }
    }
}