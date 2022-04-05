package com.square.pos.training.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Module3 implements Serializable {


    @SerializedName("spent_time")
    @Expose
    private String spentTime;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("module")
    @Expose
    private String module;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("content")
    @Expose
    private String content;

    public String getSpentTime() {
        return spentTime;
    }

    public void setSpentTime(String spentTime) {
        this.spentTime = spentTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}