package com.dmw.noble.model_health;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HelathOccupation {

@SerializedName("occupationList")
@Expose
private List<OccupationList> occupationList = null;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("message")
@Expose
private String message;

public List<OccupationList> getOccupationList() {
return occupationList;
}

public void setOccupationList(List<OccupationList> occupationList) {
this.occupationList = occupationList;
}

public Integer getStatus() {
return status;
}

public void setStatus(Integer status) {
this.status = status;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

}