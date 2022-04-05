package com.square.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MessageData {

    @SerializedName("Sender_Id")
    @Expose
    private String senderId;
    @SerializedName("Receiver_Id")
    @Expose
    private String receiverId;
    @SerializedName("Claim_Id")
    @Expose
    private String claimId;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Type")
    @Expose
    private String type;

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getClaimId() {
        return claimId;
    }

    public void setClaimId(String claimId) {
        this.claimId = claimId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}