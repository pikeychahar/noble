package com.square.pos.model_travel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Prahalad Chahar on 22/02/22.
 */

public class Country {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("countryName")
    @Expose
    private String countryName;
    @SerializedName("countryDigitCoverage")
    @Expose
    private String countryDigitCoverage;
    @SerializedName("priroty")
    @Expose
    private String priroty;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryDigitCoverage() {
        return countryDigitCoverage;
    }

    public void setCountryDigitCoverage(String countryDigitCoverage) {
        this.countryDigitCoverage = countryDigitCoverage;
    }

    public String getPriroty() {
        return priroty;
    }

    public void setPriroty(String priroty) {
        this.priroty = priroty;
    }

}