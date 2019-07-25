package com.bookservice.ui.activity.offer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.bookservice.R;
import com.bookservice.adapter.OfferListAdapter;
import com.bookservice.data.model.offers.OfferList;
import com.bookservice.data.model.offers.OfferListReponse;
import com.bookservice.ui.base.BsBaseActivity;
import com.bookservice.utils.view.BsToast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bookservice.constants.Constants.EXTRA_CAT_ID;
import static com.bookservice.constants.Constants.EXTRA_NAME;

public class OfferListActivity extends BsBaseActivity implements IOffersListView{

    @BindView(R.id.recycler_View)
    RecyclerView recyclerView;
    String strCategory;
    String strCategoryId;
    OfferListPresenter offerListPresenter;
    Activity activity;
    @BindView(R.id.ll_no_list)
    LinearLayout llNoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_list);
        ButterKnife.bind(this);
        activity = OfferListActivity.this;
        offerListPresenter = new OfferListPresenter(this);


        final Intent intent = getIntent();
        if (intent != null) {
            strCategory = intent.getStringExtra(EXTRA_NAME);
            strCategoryId = intent.getStringExtra(EXTRA_CAT_ID);
            showTile(strCategory);
        }
        showWhiteStatusBar(Color.WHITE);
        showBackButton();

        offerListPresenter.getOfferList(activity, strCategoryId);
    }

    @Override
    public void onSuccessOfferList(OfferListReponse offerListReponse) {

        final String url = offerListReponse.getUrl();
        final List<OfferList> offerList = offerListReponse.getOfferList();

        if (offerList.isEmpty() || offerList == null) {
            llNoList.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            llNoList.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        GridLayoutManager  gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        OfferListAdapter offerListAdapter = new OfferListAdapter(offerList, this, url);
        recyclerView.setAdapter(offerListAdapter);
    }

    @Override
    public void onError(String message) {
        BsToast.showLong(activity, message);
    }

}
