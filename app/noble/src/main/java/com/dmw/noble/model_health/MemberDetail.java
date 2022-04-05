package com.dmw.noble.model_health;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MemberDetail {

@SerializedName("success")
@Expose
private String success;
@SerializedName("msg")
@Expose
private String msg;
@SerializedName("quoteid")
@Expose
private String quoteid;
@SerializedName("selfData")
@Expose
private String selfData;
@SerializedName("spouseData")
@Expose
private String spouseData;
@SerializedName("sonData")
@Expose
private String sonData;
@SerializedName("daughterData")
@Expose
private String daughterData;
@SerializedName("fatherData")
@Expose
private String fatherData;
@SerializedName("motherData")
@Expose
private String motherData;

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

public String getSelfData() {
return selfData;
}

public void setSelfData(String selfData) {
this.selfData = selfData;
}

public String getSpouseData() {
return spouseData;
}

public void setSpouseData(String spouseData) {
this.spouseData = spouseData;
}

public String getSonData() {
return sonData;
}

public void setSonData(String sonData) {
this.sonData = sonData;
}

public String getDaughterData() {
return daughterData;
}

public void setDaughterData(String daughterData) {
this.daughterData = daughterData;
}

public String getFatherData() {
return fatherData;
}

public void setFatherData(String fatherData) {
this.fatherData = fatherData;
}

public String getMotherData() {
return motherData;
}

public void setMotherData(String motherData) {
this.motherData = motherData;
}

}