package com.dmw.noble.model_health.v2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HospitalPincode {

@SerializedName("pincode")
@Expose
private String pincode;

public String getPincode() {
return pincode;
}

public void setPincode(String pincode) {
this.pincode = pincode;
}

}