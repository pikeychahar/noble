package com.dmw.noble.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentTrack {

@SerializedName("full_name")
@Expose
private String fullName;
@SerializedName("email")
@Expose
private String email;
@SerializedName("current_datetime")
@Expose
private String currentDatetime;
@SerializedName("url_paymentpage")
@Expose
private String urlPaymentpage;
@SerializedName("source")
@Expose
private String source;
@SerializedName("payment_time")
@Expose
private String paymentTime;
@SerializedName("payment_status")
@Expose
private String paymentStatus;
@SerializedName("status")
@Expose
private String status;
@SerializedName("policyDocument")
@Expose
private String policyDocument;

public String getFullName() {
return fullName;
}

public void setFullName(String fullName) {
this.fullName = fullName;
}

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

public String getCurrentDatetime() {
return currentDatetime;
}

public void setCurrentDatetime(String currentDatetime) {
this.currentDatetime = currentDatetime;
}

public String getUrlPaymentpage() {
return urlPaymentpage;
}

public void setUrlPaymentpage(String urlPaymentpage) {
this.urlPaymentpage = urlPaymentpage;
}

public String getSource() {
return source;
}

public void setSource(String source) {
this.source = source;
}

public String getPaymentTime() {
return paymentTime;
}

public void setPaymentTime(String paymentTime) {
this.paymentTime = paymentTime;
}

public String getPaymentStatus() {
return paymentStatus;
}

public void setPaymentStatus(String paymentStatus) {
this.paymentStatus = paymentStatus;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getPolicyDocument() {
return policyDocument;
}

public void setPolicyDocument(String policyDocument) {
this.policyDocument = policyDocument;
}

}