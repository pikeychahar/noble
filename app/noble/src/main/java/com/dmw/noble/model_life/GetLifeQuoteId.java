package com.dmw.noble.model_life;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetLifeQuoteId {

@SerializedName("success")
@Expose
private String success;
@SerializedName("message")
@Expose
private String message;
@SerializedName("q_id")
@Expose
private String qId;

public String getSuccess() {
return success;
}

public void setSuccess(String success) {
this.success = success;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public String getqId() {
return qId;
}

public void setqId(String qId) {
this.qId = qId;
}

}