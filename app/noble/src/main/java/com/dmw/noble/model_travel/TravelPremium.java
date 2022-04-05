package com.dmw.noble.model_travel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TravelPremium {

    @SerializedName("data")
    @Expose
    private TravelPremiumData data;

    public TravelPremiumData getData() {
        return data;
    }

    public void setData(TravelPremiumData data) {
        this.data = data;
    }

}