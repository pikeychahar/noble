package com.dmw.noble.model.master;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenderStatus {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("gender")
@Expose
private List<Gender> gender = null;

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public List<Gender> getGender() {
return gender;
}

public void setGender(List<Gender> gender) {
this.gender = gender;
}

}