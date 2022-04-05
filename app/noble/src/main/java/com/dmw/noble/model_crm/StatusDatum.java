package com.dmw.noble.model_crm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusDatum {

@SerializedName("Timestamp")
@Expose
private String timestamp;
@SerializedName("Status")
@Expose
private String status;

public String getTimestamp() {
return timestamp;
}

public void setTimestamp(String timestamp) {
this.timestamp = timestamp;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

}