package com.dmw.noble.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SbiMisd {

@SerializedName("sbiData")
@Expose
private List<SbiData> sbiData = null;
@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;

public List<SbiData> getSbiData() {
return sbiData;
}

public void setSbiData(List<SbiData> sbiData) {
this.sbiData = sbiData;
}

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

}