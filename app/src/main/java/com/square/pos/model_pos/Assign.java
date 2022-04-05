package com.square.pos.model_pos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Assign {

@SerializedName("assignList")
@Expose
private List<AssignList> assignList = null;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("message")
@Expose
private String message;

public List<AssignList> getAssignList() {
return assignList;
}

public void setAssignList(List<AssignList> assignList) {
this.assignList = assignList;
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