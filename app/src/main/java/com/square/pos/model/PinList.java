package com.square.pos.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PinList {

@SerializedName("pincode")
@Expose
private List<Pincode> pincode = null;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("message")
@Expose
private String message;

public List<Pincode> getPincode() {
return pincode;
}

public void setPincode(List<Pincode> pincode) {
this.pincode = pincode;
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