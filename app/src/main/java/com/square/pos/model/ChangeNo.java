package com.square.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangeNo {

@SerializedName("success")
@Expose
private String success;
@SerializedName("msg")
@Expose
private String msg;
@SerializedName("mobile_no")
@Expose
private String mobileNo;
@SerializedName("quotation_id")
@Expose
private String quotationId;

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

public String getMobileNo() {
return mobileNo;
}

public void setMobileNo(String mobileNo) {
this.mobileNo = mobileNo;
}

public String getQuotationId() {
return quotationId;
}

public void setQuotationId(String quotationId) {
this.quotationId = quotationId;
}

}