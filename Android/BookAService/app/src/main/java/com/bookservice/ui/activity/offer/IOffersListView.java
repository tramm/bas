package com.bookservice.ui.activity.offer;

import com.bookservice.data.model.offers.OfferListReponse;

public interface IOffersListView {

    public void onSuccessOfferList(OfferListReponse offerListReponse);
    public void onError(String message);


}
