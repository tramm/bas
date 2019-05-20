
package com.bookservice.data.model.offers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OfferListReponse {

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("offerList")
    @Expose
    private List<OfferList> offerList = null;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<OfferList> getOfferList() {
        return offerList;
    }

    public void setOfferList(List<OfferList> offerList) {
        this.offerList = offerList;
    }

}
