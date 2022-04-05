package com.square.pos.model_pos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class State {

@SerializedName("state_name")
@Expose
private String stateName;
@SerializedName("id")
@Expose
private String id;

public String getStateName() {
return stateName;
}

public void setStateName(String stateName) {
this.stateName = stateName;
}

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

}