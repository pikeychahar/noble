package com.dmw.noble.model_life;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SumList {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("suminsured")
@Expose
private List<Suminsured> suminsured = null;

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

public List<Suminsured> getSuminsured() {
return suminsured;
}

public void setSuminsured(List<Suminsured> suminsured) {
this.suminsured = suminsured;
}

}