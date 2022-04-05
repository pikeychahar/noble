package com.dmw.noble.model_crm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VehicleData {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("data")
    @Expose
    private List<Vehicle> data = null;

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

    public List<Vehicle> getData() {
        return data;
    }

    public void setData(List<Vehicle> data) {
        this.data = data;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}