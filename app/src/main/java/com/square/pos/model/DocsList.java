package com.square.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DocsList {

    @SerializedName("DocsWallet")
    @Expose
    private List<DocsWallet> docsWallet = null;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<DocsWallet> getDocsWallet() {
        return docsWallet;
    }

    public void setDocsWallet(List<DocsWallet> docsWallet) {
        this.docsWallet = docsWallet;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}