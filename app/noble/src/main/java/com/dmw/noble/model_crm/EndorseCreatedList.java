package com.dmw.noble.model_crm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EndorseCreatedList {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("draw")
    @Expose
    private Integer draw;
    @SerializedName("recordsTotal")
    @Expose
    private Integer recordsTotal;
    @SerializedName("recordsFiltered")
    @Expose
    private Integer recordsFiltered;
    @SerializedName("TotalFiles")
    @Expose
    private String totalFiles;
    @SerializedName("data")
    @Expose
    private List<EndorsementCreatedData> data = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getDraw() {
        return draw;
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

    public String getTotalFiles() {
        return totalFiles;
    }

    public void setTotalFiles(String totalFiles) {
        this.totalFiles = totalFiles;
    }

    public List<EndorsementCreatedData> getData() {
        return data;
    }

    public void setData(List<EndorsementCreatedData> data) {
        this.data = data;
    }

}