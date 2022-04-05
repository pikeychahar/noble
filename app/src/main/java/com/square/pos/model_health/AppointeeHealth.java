package com.square.pos.model_health;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AppointeeHealth {

@SerializedName("appointeeList")
@Expose
private List<AppointeeList> appointeeList = null;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("message")
@Expose
private String message;

public List<AppointeeList> getAppointeeList() {
return appointeeList;
}

public void setAppointeeList(List<AppointeeList> appointeeList) {
this.appointeeList = appointeeList;
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