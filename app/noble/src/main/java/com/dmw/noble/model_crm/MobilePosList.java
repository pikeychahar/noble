package com.dmw.noble.model_crm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MobilePosList {

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
    @SerializedName("data")
    @Expose
    private List<MobilePos> data = null;

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

    public List<MobilePos> getData() {
        return data;
    }

    public void setData(List<MobilePos> data) {
        this.data = data;
    }

}