package com.square.pos.model_pos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AgentDocuments {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("id")
    @Expose
    private Object id;
    @SerializedName("pancard_image")
    @Expose
    private String pancardImage;
    @SerializedName("aadharcard_image")
    @Expose
    private String aadharcardImage;
    @SerializedName("aadharcard_image_back")
    @Expose
    private String aadharcardImageBack;
    @SerializedName("qualification_image")
    @Expose
    private String qualificationImage;
    @SerializedName("cheque_image")
    @Expose
    private String chequeImage;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("signature_image")
    @Expose
    private String signatureImage;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getPancardImage() {
        return pancardImage;
    }

    public void setPancardImage(String pancardImage) {
        this.pancardImage = pancardImage;
    }

    public String getAadharcardImage() {
        return aadharcardImage;
    }

    public void setAadharcardImage(String aadharcardImage) {
        this.aadharcardImage = aadharcardImage;
    }

    public String getAadharcardImageBack() {
        return aadharcardImageBack;
    }

    public void setAadharcardImageBack(String aadharcardImageBack) {
        this.aadharcardImageBack = aadharcardImageBack;
    }

    public String getQualificationImage() {
        return qualificationImage;
    }

    public void setQualificationImage(String qualificationImage) {
        this.qualificationImage = qualificationImage;
    }

    public String getChequeImage() {
        return chequeImage;
    }

    public void setChequeImage(String chequeImage) {
        this.chequeImage = chequeImage;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSignatureImage() {
        return signatureImage;
    }

    public void setSignatureImage(String signatureImage) {
        this.signatureImage = signatureImage;
    }

}