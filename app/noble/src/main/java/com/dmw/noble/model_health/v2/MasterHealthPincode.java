package com.dmw.noble.model_health.v2;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterHealthPincode {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("pincodes")
    @Expose
    private List<Pincode> pincodes = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Pincode> getPincodes() {
        return pincodes;
    }

    public void setPincodes(List<Pincode> pincodes) {
        this.pincodes = pincodes;
    }

}