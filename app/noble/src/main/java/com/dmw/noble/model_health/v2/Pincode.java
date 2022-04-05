package com.dmw.noble.model_health.v2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pincode {

@SerializedName("pincode")
@Expose
private String pincode;
@SerializedName("id")
@Expose
private String id;
@SerializedName("city")
@Expose
private String city;

public String getPincode() {
return pincode;
}

public void setPincode(String pincode) {
this.pincode = pincode;
}

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getCity() {
return city;
}

public void setCity(String city) {
this.city = city;
}

}