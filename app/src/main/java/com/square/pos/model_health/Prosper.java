package com.square.pos.model_health;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Prosper {

@SerializedName("success")
@Expose
private String success;
@SerializedName("msg")
@Expose
private String msg;
@SerializedName("quoteid")
@Expose
private String quoteid;
@SerializedName("ProsperName")
@Expose
private String prosperName;
@SerializedName("ProsperEmail")
@Expose
private String prosperEmail;
@SerializedName("ProsperMobile")
@Expose
private String prosperMobile;
@SerializedName("ProsperAddress1")
@Expose
private String prosperAddress1;
@SerializedName("ProsperAddress2")
@Expose
private String prosperAddress2;
@SerializedName("ProsperCity")
@Expose
private String prosperCity;
@SerializedName("ProsperAreaId")
@Expose
private String prosperAreaId;

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

public String getQuoteid() {
return quoteid;
}

public void setQuoteid(String quoteid) {
this.quoteid = quoteid;
}

public String getProsperName() {
return prosperName;
}

public void setProsperName(String prosperName) {
this.prosperName = prosperName;
}

public String getProsperEmail() {
return prosperEmail;
}

public void setProsperEmail(String prosperEmail) {
this.prosperEmail = prosperEmail;
}

public String getProsperMobile() {
return prosperMobile;
}

public void setProsperMobile(String prosperMobile) {
this.prosperMobile = prosperMobile;
}

public String getProsperAddress1() {
return prosperAddress1;
}

public void setProsperAddress1(String prosperAddress1) {
this.prosperAddress1 = prosperAddress1;
}

public String getProsperAddress2() {
return prosperAddress2;
}

public void setProsperAddress2(String prosperAddress2) {
this.prosperAddress2 = prosperAddress2;
}

public String getProsperCity() {
return prosperCity;
}

public void setProsperCity(String prosperCity) {
this.prosperCity = prosperCity;
}

public String getProsperAreaId() {
return prosperAreaId;
}

public void setProsperAreaId(String prosperAreaId) {
this.prosperAreaId = prosperAreaId;
}

}