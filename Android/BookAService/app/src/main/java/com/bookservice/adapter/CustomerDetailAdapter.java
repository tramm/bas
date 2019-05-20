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
import com.bookservice.data.model.user.User;
import com.bookservice.event.EditUserEvent;
import com.bookservice.ui.base.BaseApplication;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class CustomerDetailAdapter extends RecyclerView.Adapter<CustomerDetailAdapter.CustomerDetailAdapterItemViewHolder> {

    private List<User> arrayList;
    private Activity activity;
    boolean mIsFrom;

    public CustomerDetailAdapter(List<User> details, Activity activity, boolean isFrom) {
        this.arrayList = details;
        this.activity = activity;
        this.mIsFrom = isFrom;
    }

    @NonNull
    @Override
    public CustomerDetailAdapter.CustomerDetailAdapterItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_customer_detail, viewGroup, false);
        return new CustomerDetailAdapter.CustomerDetailAdapterItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomerDetailAdapter.CustomerDetailAdapterItemViewHolder itemViewHolder, final int i) {
        final User user = arrayList.get(i);
        itemViewHolder.mName.setText(user.getName());
        itemViewHolder.mType.setText(user.getTag());
        itemViewHolder.mEmail.setText(user.getEmail());
        itemViewHolder.mMobile.setText(user.getMobile());
        itemViewHolder.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseApplication.getEventBus().post(new EditUserEvent(user));
            }
        });


        itemViewHolder.llParentUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIsFrom) {
                    Intent intent = new Intent();
                    intent.putExtra("user", user);
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
                    intent.putExtra("user", user);
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

    public class CustomerDetailAdapterItemViewHolder extends RecyclerView.ViewHolder {

        TextView mName;
        TextView mType;
        TextView mEmail;
        TextView mMobile;
        TextView mEdit;
        LinearLayout llParentUser;
        RadioButton rb;

        public CustomerDetailAdapterItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.name);
            mType = (TextView) itemView.findViewById(R.id.type);
            mEmail = (TextView) itemView.findViewById(R.id.email);
            mMobile = (TextView) itemView.findViewById(R.id.mobile);
            mEdit = (TextView) itemView.findViewById(R.id.edit);
            llParentUser = (LinearLayout) itemView.findViewById(R.id.ll_parent_user);
            rb = (RadioButton) itemView.findViewById(R.id.radioButton);

        }
    }
}

