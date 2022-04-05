package com.dmw.noble.model_pos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginData {

@SerializedName("Id")
@Expose
private String id;
@SerializedName("Name")
@Expose
private String name;
@SerializedName("Code")
@Expose
private String code;
@SerializedName("Type")
@Expose
private String type;
@SerializedName("otp")
@Expose
private String otp;
@SerializedName("login_token")
@Expose
private String loginToken;
@SerializedName("pos_status")
@Expose
private String posStatus;
@SerializedName("pos_type")
@Expose
private String posType;
@SerializedName("Life_Training_Status")
@Expose
private String lifeTrainingStatus;
@SerializedName("SessionExpired")
@Expose
private Integer sessionExpired;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getCode() {
return code;
}

public void setCode(String code) {
this.code = code;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public String getOtp() {
return otp;
}

public void setOtp(String otp) {
this.otp = otp;
}

public String getLoginToken() {
return loginToken;
}

public void setLoginToken(String loginToken) {
this.loginToken = loginToken;
}

public String getPosStatus() {
return posStatus;
}

public void setPosStatus(String posStatus) {
this.posStatus = posStatus;
}

public String getPosType() {
return posType;
}

public void setPosType(String posType) {
this.posType = posType;
}

public String getLifeTrainingStatus() {
return lifeTrainingStatus;
}

public void setLifeTrainingStatus(String lifeTrainingStatus) {
this.lifeTrainingStatus = lifeTrainingStatus;
}

public Integer getSessionExpired() {
return sessionExpired;
}

public void setSessionExpired(Integer sessionExpired) {
this.sessionExpired = sessionExpired;
}

}