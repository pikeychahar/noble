package com.square.pos.model_health.v2;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HospitalPincodeList {

@SerializedName("Hospital_pincode")
@Expose
private List<HospitalPincode> hospitalPincode = null;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("message")
@Expose
private String message;

public List<HospitalPincode> getHospitalPincode() {
return hospitalPincode;
}

public void setHospitalPincode(List<HospitalPincode> hospitalPincode) {
this.hospitalPincode = hospitalPincode;
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