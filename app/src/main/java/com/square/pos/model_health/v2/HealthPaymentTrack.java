package com.square.pos.model_health.v2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HealthPaymentTrack {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("payment_status")
@Expose
private String paymentStatus;
@SerializedName("url")
@Expose
private String url;

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public String getPaymentStatus() {
return paymentStatus;
}

public void setPaymentStatus(String paymentStatus) {
this.paymentStatus = paymentStatus;
}

public String getUrl() {
return url;
}

public void setUrl(String url) {
this.url = url;
}

}