package com.square.pos.model_health;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Area {

@SerializedName("areaID")
@Expose
private Integer areaID;
@SerializedName("areaName")
@Expose
private String areaName;

public Integer getAreaID() {
return areaID;
}

public void setAreaID(Integer areaID) {
this.areaID = areaID;
}

public String getAreaName() {
return areaName;
}

public void setAreaName(String areaName) {
this.areaName = areaName;
}

}