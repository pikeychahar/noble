package com.square.pos.model_travel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Prahalad Chahar on 22/02/22.
 */
public class TravelQuote {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private TravelData data;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TravelData getData() {
        return data;
    }

    public void setData(TravelData data) {
        this.data = data;
    }

}