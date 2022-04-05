package com.dmw.noble.model_crm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailedPolicyList {

    @SerializedName("draw")
    @Expose
    private Integer draw;
    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("recordsTotal")
    @Expose
    private Integer recordsTotal;
    @SerializedName("recordsFiltered")
    @Expose
    private Integer recordsFiltered;
    @SerializedName("data")
    @Expose
    private List<PolicyData> data = null;
    @SerializedName("TotalEarning")
    @Expose
    private String totalEarning;
    @SerializedName("TotalFiles")
    @Expose
    private String totalFiles;
    @SerializedName("TotalPremium")
    @Expose
    private String totalPremium;
    @SerializedName("FilterPolicyData")
    @Expose
    private List<FilterPolicyDatum> filterPolicyData = null;
    @SerializedName("FilterToTalPolicyAndPremium")
    @Expose
    private List<FilterToTalPolicyAndPremium> filterToTalPolicyAndPremium = null;

    public Integer getDraw() {
        return draw;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Integer recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public Integer getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(Integer recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<PolicyData> getData() {
        return data;
    }

    public void setData(List<PolicyData> data) {
        this.data = data;
    }

    public String getTotalEarning() {
        return totalEarning;
    }

    public void setTotalEarning(String totalEarning) {
        this.totalEarning = totalEarning;
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

    public List<FilterPolicyDatum> getFilterPolicyData() {
        return filterPolicyData;
    }

    public void setFilterPolicyData(List<FilterPolicyDatum> filterPolicyData) {
        this.filterPolicyData = filterPolicyData;
    }

    public List<FilterToTalPolicyAndPremium> getFilterToTalPolicyAndPremium() {
        return filterToTalPolicyAndPremium;
    }

    public void setFilterToTalPolicyAndPremium(List<FilterToTalPolicyAndPremium> filterToTalPolicyAndPremium) {
        this.filterToTalPolicyAndPremium = filterToTalPolicyAndPremium;
    }

}