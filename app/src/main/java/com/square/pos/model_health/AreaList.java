package com.square.pos.model_health;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AreaList {

@SerializedName("area")
@Expose
private List<Area> area = null;

public List<Area> getArea() {
return area;
}

public void setArea(List<Area> area) {
this.area = area;
}

}