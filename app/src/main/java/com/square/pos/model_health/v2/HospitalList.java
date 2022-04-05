package com.square.pos.model_health.v2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HospitalList {

@SerializedName("hospitals")
@Expose
private List<com.square.pos.model_health.v2.Hospital> hospitals = null;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("message")
@Expose
private String message;

public List<com.square.pos.model_health.v2.Hospital> getHospitals() {
return hospitals;
}

public void setHospitals(List<com.square.pos.model_health.v2.Hospital> hospitals) {
this.hospitals = hospitals;
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