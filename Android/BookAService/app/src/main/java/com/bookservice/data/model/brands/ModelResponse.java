
package com.bookservice.data.model.brands;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelResponse {

    @SerializedName("modelList")
    @Expose
    private List<Brand> brands = null;

    public List<Brand> getBrands() {
        return brands;
    }

    public void setBrands(List<Brand> brands) {
        this.brands = brands;
    }

}
