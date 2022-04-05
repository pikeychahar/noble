package com.dmw.noble.model_pos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Employee {

@SerializedName("employeeList")
@Expose
private List<EmployeeList> employeeList = null;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("message")
@Expose
private String message;

public List<EmployeeList> getEmployeeList() {
return employeeList;
}

public void setEmployeeList(List<EmployeeList> employeeList) {
this.employeeList = employeeList;
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