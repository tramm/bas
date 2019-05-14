package com.bookservice.utils;

import android.widget.ImageView;

import com.bookservice.R;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


public class ImageLoad {

    public static void loadImage(final String imageUrl,
                                 final ImageView imageView) {
        Picasso.get()
                .load(imageUrl)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.h5)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(imageView);
    }


    public static void loadImageDrawable(int image, ImageView imageView) {
        Picasso.get()
                .load(image)
                .fit()
                .placeholder(R.drawable.h5)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(imageView);
    }

    public static void loadImageDeals(final String imageUrl,
                                 final ImageView imageView) {
        Picasso.get()
                .load(imageUrl)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.offer1)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(imageView);
    }
    public static void loadImageCategory(final String imageUrl,
                                      final ImageView imageView) {
        Picasso.get()
                .load(imageUrl)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.wash)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(imageView);
    }

}
