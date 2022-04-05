package com.square.pos.model_health.v2;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HospitalStateList {

@SerializedName("hospitals_state")
@Expose
private List<HospitalsState> hospitalsState = null;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("message")
@Expose
private String message;

public List<HospitalsState> getHospitalsState() {
return hospitalsState;
}

public void setHospitalsState(List<HospitalsState> hospitalsState) {
this.hospitalsState = hospitalsState;
}

public Integer getStatus() {
return status;
}

public void setStatus(Integer status) {
this.status = status;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

}