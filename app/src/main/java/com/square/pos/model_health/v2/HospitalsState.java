package com.square.pos.model_health.v2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HospitalsState {

@SerializedName("state")
@Expose
private String state;

public String getState() {
return state;
}

public void setState(String state) {
this.state = state;
}

}