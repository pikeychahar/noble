package com.dmw.noble.model_travel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Prahalad Chahar on 22/02/22.
 */

public class TravelData {

    @SerializedName("qid")
    @Expose
    private String qid;

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }
}