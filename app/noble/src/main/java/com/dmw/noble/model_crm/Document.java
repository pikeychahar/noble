package com.dmw.noble.model_crm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Document {

    @SerializedName("Upload_By")
    @Expose
    private String uploadBy;
    @SerializedName("Download_Url")
    @Expose
    private String downloadUrl;
    @SerializedName("Document_Type")
    @Expose
    private String documentType;
    @SerializedName("Upload_Date")
    @Expose
    private String uploadDate;

    public String getUploadBy() {
        return uploadBy;
    }

    public void setUploadBy(String uploadBy) {
        this.uploadBy = uploadBy;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

}