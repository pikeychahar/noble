package com.dmw.noble.model_health;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OccupationList {

@SerializedName("OccupationId")
@Expose
private String occupationId;
@SerializedName("OccupationName")
@Expose
private String occupationName;

public String getOccupationId() {
return occupationId;
}

public void setOccupationId(String occupationId) {
this.occupationId = occupationId;
}

public String getOccupationName() {
return occupationName;
}

public void setOccupationName(String occupationName) {
this.occupationName = occupationName;
}

}