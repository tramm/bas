package com.bookservice.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bookservice.R;
import com.bookservice.data.model.category.Category;
import com.bookservice.ui.activity.offer.OfferListActivity;
import com.bookservice.utils.ImageLoad;

import java.util.ArrayList;
import java.util.List;

import static com.bookservice.constants.Constants.EXTRA_CAT_ID;
import static com.bookservice.constants.Constants.EXTRA_NAME;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private List<Category> data = new ArrayList<>();
    Activity mActivity;
    String mUrl;

    public CategoryAdapter(Activity activity, List<Category> data, String url) {
        this.data = data;
        this.mActivity = activity;
        this.mUrl = url;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vertical_single_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        ImageLoad.loadImageCategory(mUrl + data.get(position).getUrl(), holder.image);

        holder.txtTitle.setText(data.get(position).getName());
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, OfferListActivity.class);
                intent.putExtra(EXTRA_NAME, data.get(position).getName());
                intent.putExtra(EXTRA_CAT_ID, data.get(position).getId());
                mActivity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView txtTitle;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            txtTitle = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
