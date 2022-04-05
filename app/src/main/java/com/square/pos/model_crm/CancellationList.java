package com.square.pos.model_crm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CancellationList {
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
    private List<Cancellation> data = null;

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

    public List<Cancellation> getData() {
        return data;
    }

    public void setData(List<Cancellation> data) {
        this.data = data;
    }
}