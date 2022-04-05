package com.dmw.noble.model_health.bajaj;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City {

@SerializedName("code")
@Expose
private String code;
@SerializedName("name")
@Expose
private String name;
@SerializedName("pincode")
@Expose
private String pincode;

public String getCode() {
return code;
}

public void setCode(String code) {
this.code = code;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getPincode() {
return pincode;
}

public void setPincode(String pincode) {
this.pincode = pincode;
}

}