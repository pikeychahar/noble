package com.dmw.noble.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Rto implements Serializable {

    @SerializedName("rto_id")
    @Expose
    private String rtoId;
    @SerializedName("rto")
    @Expose
    private String rto;
    @SerializedName("Region_Name")
    @Expose
    private String regionName;

    public String getRtoId() {
        return rtoId;
    }

    public void setRtoId(String rtoId) {
        this.rtoId = rtoId;
    }

    public String getRto() {
        return rto;
    }

    public void setRto(String rto) {
        this.rto = rto;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

}