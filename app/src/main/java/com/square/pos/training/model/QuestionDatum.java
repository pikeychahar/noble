package com.square.pos.training.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionDatum {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("option1")
    @Expose
    private String option1;
    @SerializedName("option2")
    @Expose
    private String option2;
    @SerializedName("option3")
    @Expose
    private String option3;
    @SerializedName("option4")
    @Expose
    private String option4;
    @SerializedName("answer")
    @Expose
    private String answer;
    @SerializedName("mark")
    @Expose
    private String mark;
    @SerializedName("pass_mark")
    @Expose
    private String passMark;
    @SerializedName("set_no")
    @Expose
    private String setNo;
    @SerializedName("attempt")
    @Expose
    private String attempt;
    @SerializedName("mark_get")
    @Expose
    private String markGet;
    @SerializedName("user_answer")
    @Expose
    private String userAnswer;
    @SerializedName("option_no")
    @Expose
    private Integer optionNo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getPassMark() {
        return passMark;
    }

    public void setPassMark(String passMark) {
        this.passMark = passMark;
    }

    public String getSetNo() {
        return setNo;
    }

    public void setSetNo(String setNo) {
        this.setNo = setNo;
    }

    public String getAttempt() {
        return attempt;
    }

    public void setAttempt(String attempt) {
        this.attempt = attempt;
    }

    public String getMarkGet() {
        return markGet;
    }

    public void setMarkGet(String markGet) {
        this.markGet = markGet;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public Integer getOptionNo() {
        return optionNo;
    }

    public void setOptionNo(Integer optionNo) {
        this.optionNo = optionNo;
    }

}