package com.dmw.noble.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RtoLocation {

@SerializedName("rto")
@Expose
private List<Rto> rto = null;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("message")
@Expose
private String message;

public List<Rto> getRto() {
return rto;
}

public void setRto(List<Rto> rto) {
this.rto = rto;
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