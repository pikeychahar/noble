package com.square.pos.model_crm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("MainClassName")
    @Expose
    private String mainClassName;
    @SerializedName("ClassName")
    @Expose
    private String className;
    @SerializedName("DateTimeClassName")
    @Expose
    private String dateTimeClassName;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Photo")
    @Expose
    private String photo;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Is_Attachement")
    @Expose
    private String isAttachement;
    @SerializedName("Attachment_Name")
    @Expose
    private String attachmentName;
    @SerializedName("Attachment_Url")
    @Expose
    private String attachmentUrl;
    @SerializedName("DateTime")
    @Expose
    private String dateTime;

    public String getMainClassName() {
        return mainClassName;
    }

    public void setMainClassName(String mainClassName) {
        this.mainClassName = mainClassName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDateTimeClassName() {
        return dateTimeClassName;
    }

    public void setDateTimeClassName(String dateTimeClassName) {
        this.dateTimeClassName = dateTimeClassName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIsAttachement() {
        return isAttachement;
    }

    public void setIsAttachement(String isAttachement) {
        this.isAttachement = isAttachement;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

}