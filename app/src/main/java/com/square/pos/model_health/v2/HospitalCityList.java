package com.square.pos.model_health.v2;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HospitalCityList {

@SerializedName("Hospital_city")
@Expose
private List<HospitalCity> hospitalCity = null;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("message")
@Expose
private String message;

public List<HospitalCity> getHospitalCity() {
return hospitalCity;
}

public void setHospitalCity(List<HospitalCity> hospitalCity) {
this.hospitalCity = hospitalCity;
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