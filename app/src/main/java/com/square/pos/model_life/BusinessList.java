package com.square.pos.model_life;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BusinessList {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("businessBelongs")
@Expose
private List<BusinessBelong> businessBelongs = null;

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

public List<BusinessBelong> getBusinessBelongs() {
return businessBelongs;
}

public void setBusinessBelongs(List<BusinessBelong> businessBelongs) {
this.businessBelongs = businessBelongs;
}

}