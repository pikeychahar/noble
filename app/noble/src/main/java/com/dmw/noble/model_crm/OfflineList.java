package com.dmw.noble.model_crm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OfflineList {

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
    private List<OfflineData> data = null;
    @SerializedName("FilterData")
    @Expose
    private List<OfflineData> filterData = null;

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

    public List<OfflineData> getData() {
        return data;
    }

    public void setData(List<OfflineData> data) {
        this.data = data;
    }

    public List<OfflineData> getFilterData() {
        return filterData;
    }

    public void setFilterData(List<OfflineData> filterData) {
        this.filterData = filterData;
    }

}
