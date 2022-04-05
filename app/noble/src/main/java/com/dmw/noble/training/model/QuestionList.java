package com.dmw.noble.training.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuestionList {
    @SerializedName("question_data")
    @Expose
    private List<QuestionDatum> questionData = null;
    @SerializedName("pass_mark")
    @Expose
    private String passMark;
    @SerializedName("total_mark")
    @Expose
    private String totalMark;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;

    public List<QuestionDatum> getQuestionData() {
        return questionData;
    }

    public void setQuestionData(List<QuestionDatum> questionData) {
        this.questionData = questionData;
    }

    public String getPassMark() {
        return passMark;
    }

    public void setPassMark(String passMark) {
        this.passMark = passMark;
    }

    public String getTotalMark() {
        return totalMark;
    }

    public void setTotalMark(String totalMark) {
        this.totalMark = totalMark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}