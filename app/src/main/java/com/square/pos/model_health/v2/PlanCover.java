package com.square.pos.model_health.v2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlanCover {

@SerializedName("covers")
@Expose
private List<com.square.pos.model_health.v2.Cover> covers = null;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("message")
@Expose
private String message;

public List<com.square.pos.model_health.v2.Cover> getCovers() {
return covers;
}

public void setCovers(List<com.square.pos.model_health.v2.Cover> covers) {
this.covers = covers;
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