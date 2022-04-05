package com.dmw.noble.model_crm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StatementList {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("draw")
    @Expose
    private Integer draw;
    @SerializedName("recordsTotal")
    @Expose
    private Integer recordsTotal;
    @SerializedName("recordsFiltered")
    @Expose
    private Integer recordsFiltered;
    @SerializedName("totalPremium")
    @Expose
    private Integer totalPremium;
    @SerializedName("data")
    @Expose
    private List<StateData> data = null;
    @SerializedName("SumValueNet")
    @Expose
    private Double sumValueNet;
    @SerializedName("SQL_Where")
    @Expose
    private String sQLWhere;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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

    public Integer getTotalPremium() {
        return totalPremium;
    }

    public void setTotalPremium(Integer totalPremium) {
        this.totalPremium = totalPremium;
    }

    public List<StateData> getData() {
        return data;
    }

    public void setData(List<StateData> data) {
        this.data = data;
    }

    public Double getSumValueNet() {
        return sumValueNet;
    }

    public void setSumValueNet(Double sumValueNet) {
        this.sumValueNet = sumValueNet;
    }

    public String getSQLWhere() {
        return sQLWhere;
    }

    public void setSQLWhere(String sQLWhere) {
        this.sQLWhere = sQLWhere;
    }
}