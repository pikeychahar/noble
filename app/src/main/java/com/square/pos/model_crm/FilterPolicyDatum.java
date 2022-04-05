package com.square.pos.model_crm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FilterPolicyDatum {

    @SerializedName("LOB")
    @Expose
    private String lob;
    @SerializedName("TotalFiles")
    @Expose
    private String totalFiles;
    @SerializedName("TotalPremium")
    @Expose
    private String totalPremium;

    public String getLob() {
        return lob;
    }

    public void setLob(String lob) {
        this.lob = lob;
    }

    public String getTotalFiles() {
        return totalFiles;
    }

    public void setTotalFiles(String totalFiles) {
        this.totalFiles = totalFiles;
    }

    public String getTotalPremium() {
        return totalPremium;
    }

    public void setTotalPremium(String totalPremium) {
        this.totalPremium = totalPremium;
    }

}