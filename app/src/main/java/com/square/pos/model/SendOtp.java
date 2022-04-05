package com.square.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendOtp {

@SerializedName("status")
@Expose
private Integer status;
@SerializedName("msg")
@Expose
private String msg;
@SerializedName("otp")
@Expose
private Integer otp;

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

public Integer getOtp() {
return otp;
}

public void setOtp(Integer otp) {
this.otp = otp;
}

}