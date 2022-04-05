package com.square.pos.model_crm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateClaim {

    @SerializedName("Status")
    @Expose
    private Boolean status;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Claim_Id")
    @Expose
    private Integer claimId;

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

    public Integer getClaimId() {
        return claimId;
    }

    public void setClaimId(Integer claimId) {
        this.claimId = claimId;
    }
}
