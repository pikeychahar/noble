package com.square.pos.model_health;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityList {

@SerializedName("state_name")
@Expose
private String stateName;
@SerializedName("city")
@Expose
private List<City> city = null;

public String getStateName() {
return stateName;
}

public void setStateName(String stateName) {
this.stateName = stateName;
}

public List<City> getCity() {
return city;
}

public void setCity(List<City> city) {
this.city = city;
}

}