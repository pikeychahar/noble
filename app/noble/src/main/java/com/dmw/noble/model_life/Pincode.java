package com.dmw.noble.model_life;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pincode {

@SerializedName("city_id")
@Expose
private String cityId;
@SerializedName("state_id")
@Expose
private String stateId;
@SerializedName("pincode")
@Expose
private String pincode;
@SerializedName("city")
@Expose
private String city;
@SerializedName("state")
@Expose
private String state;

public String getCityId() {
return cityId;
}

public void setCityId(String cityId) {
this.cityId = cityId;
}

public String getStateId() {
return stateId;
}

public void setStateId(String stateId) {
this.stateId = stateId;
}

public String getPincode() {
return pincode;
}

public void setPincode(String pincode) {
this.pincode = pincode;
}

public String getCity() {
return city;
}

public void setCity(String city) {
this.city = city;
}

public String getState() {
return state;
}

public void setState(String state) {
this.state = state;
}

}