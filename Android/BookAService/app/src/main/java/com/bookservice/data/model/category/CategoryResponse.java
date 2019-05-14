
package com.bookservice.data.model.category;

import java.util.List;

public class CategoryResponse {

    private String url;
    private List<Category> categories = null;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

}
