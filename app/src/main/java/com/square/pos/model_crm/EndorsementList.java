package com.square.pos.model_crm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EndorsementList {

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
    private Integer totalFiles;
    @SerializedName("data")
    @Expose
    private List<EndorseData> data = null;

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

    public Integer getTotalFiles() {
        return totalFiles;
    }

    public void setTotalFiles(Integer totalFiles) {
        this.totalFiles = totalFiles;
    }

    public List<EndorseData> getData() {
        return data;
    }

    public void setData(List<EndorseData> data) {
        this.data = data;
    }

}