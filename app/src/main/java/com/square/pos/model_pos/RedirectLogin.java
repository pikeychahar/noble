package com.square.pos.model_pos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RedirectLogin {

@SerializedName("status")
@Expose
private Integer status;
@SerializedName("msg")
@Expose
private String msg;
@SerializedName("login_type")
@Expose
private String loginType;
@SerializedName("PrimeStatus")
@Expose
private String primeStatus;
@SerializedName("pos_type")
@Expose
private String posType;
@SerializedName("GemsStatus")
@Expose
private String gemsStatus;
@SerializedName("token")
@Expose
private String token;
@SerializedName("data")
@Expose
private LoginData data;
@SerializedName("SessionExpired_State")
@Expose
private String sessionExpiredState;

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

public String getLoginType() {
return loginType;
}

public void setLoginType(String loginType) {
this.loginType = loginType;
}

public String getPrimeStatus() {
return primeStatus;
}

public void setPrimeStatus(String primeStatus) {
this.primeStatus = primeStatus;
}

public String getPosType() {
return posType;
}

public void setPosType(String posType) {
this.posType = posType;
}

public String getGemsStatus() {
return gemsStatus;
}

public void setGemsStatus(String gemsStatus) {
this.gemsStatus = gemsStatus;
}

public String getToken() {
return token;
}

public void setToken(String token) {
this.token = token;
}

public LoginData getData() {
return data;
}

public void setData(LoginData data) {
this.data = data;
}

public String getSessionExpiredState() {
return sessionExpiredState;
}

public void setSessionExpiredState(String sessionExpiredState) {
this.sessionExpiredState = sessionExpiredState;
}

}