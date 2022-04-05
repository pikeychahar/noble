package com.square.pos.training.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Module implements Serializable {

    @SerializedName("module1")
    @Expose
    private List<Module1> module1 = null;
    @SerializedName("module2")
    @Expose
    private List<Module2> module2 = null;
    @SerializedName("module3")
    @Expose
    private List<Module3> module3 = null;
    @SerializedName("module4")
    @Expose
    private List<Module4> module4 = null;

    public List<Module1> getModule1() {
        return module1;
    }

    public void setModule1(List<Module1> module1) {
        this.module1 = module1;
    }

    public List<Module2> getModule2() {
        return module2;
    }

    public void setModule2(List<Module2> module2) {
        this.module2 = module2;
    }

    public List<Module3> getModule3() {
        return module3;
    }

    public void setModule3(List<Module3> module3) {
        this.module3 = module3;
    }

    public List<Module4> getModule4() {
        return module4;
    }

    public void setModule4(List<Module4> module4) {
        this.module4 = module4;
    }

    public static class Module2 implements Serializable {

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
}