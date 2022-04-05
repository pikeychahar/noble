package com.dmw.noble.model_health;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HealthPaymentLink {

@SerializedName("success")
@Expose
private String success;
@SerializedName("msg")
@Expose
private String msg;
@SerializedName("quoteid")
@Expose
private String quoteid;
@SerializedName("ref_id")
@Expose
private String refId;
@SerializedName("redirect_url")
@Expose
private String redirectUrl;

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

public String getRefId() {
return refId;
}

public void setRefId(String refId) {
this.refId = refId;
}

public String getRedirectUrl() {
return redirectUrl;
}

public void setRedirectUrl(String redirectUrl) {
this.redirectUrl = redirectUrl;
}

}