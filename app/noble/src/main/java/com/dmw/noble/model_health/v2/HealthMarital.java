package com.dmw.noble.model_health.v2;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HealthMarital {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("marital_status")
@Expose
private List<MaritalStatus> maritalStatus = null;

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

public List<MaritalStatus> getMaritalStatus() {
return maritalStatus;
}

public void setMaritalStatus(List<MaritalStatus> maritalStatus) {
this.maritalStatus = maritalStatus;
}

}