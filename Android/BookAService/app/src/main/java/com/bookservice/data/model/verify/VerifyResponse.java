
package com.bookservice.data.model.verify;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyResponse {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("auth")
    @Expose
    private Boolean auth;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("key")
    @Expose
    private String key;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getAuth() {
        return auth;
    }

    public void setAuth(Boolean auth) {
        this.auth = auth;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
