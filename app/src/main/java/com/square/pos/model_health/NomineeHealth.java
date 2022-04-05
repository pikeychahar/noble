package com.square.pos.model_health;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NomineeHealth {

@SerializedName("nomineeList")
@Expose
private List<NomineeList> nomineeList = null;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("message")
@Expose
private String message;

public List<NomineeList> getNomineeList() {
return nomineeList;
}

public void setNomineeList(List<NomineeList> nomineeList) {
this.nomineeList = nomineeList;
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