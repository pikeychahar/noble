package com.dmw.noble.model_pos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeList {

@SerializedName("id")
@Expose
private String id;
@SerializedName("name")
@Expose
private String name;
@SerializedName("employee_id")
@Expose
private String employeeId;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getEmployeeId() {
return employeeId;
}

public void setEmployeeId(String employeeId) {
this.employeeId = employeeId;
}

}