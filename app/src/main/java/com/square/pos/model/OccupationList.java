package com.square.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OccupationList {

@SerializedName("occupation")
@Expose
private List<Occupation> occupation = null;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("message")
@Expose
private String message;

public List<Occupation> getOccupation() {
return occupation;
}

public void setOccupation(List<Occupation> occupation) {
this.occupation = occupation;
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