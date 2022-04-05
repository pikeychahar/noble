package com.square.pos.model_health.v2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HospitalCity {

@SerializedName("city")
@Expose
private String city;

public String getCity() {
return city;
}

public void setCity(String city) {
this.city = city;
}

}