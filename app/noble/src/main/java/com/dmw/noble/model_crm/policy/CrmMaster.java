package com.dmw.noble.model_crm.policy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CrmMaster {

    @SerializedName("Status")
    @Expose
    private Boolean status;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private com.dmw.noble.model_crm.policy.Data data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public com.dmw.noble.model_crm.policy.Data getData() {
        return data;
    }

    public void setData(com.dmw.noble.model_crm.policy.Data data) {
        this.data = data;
    }

}