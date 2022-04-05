package com.dmw.noble.model_crm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RaiseTicket {

@SerializedName("Status")
@Expose
private Boolean status;
@SerializedName("Errors")
@Expose
private Integer errors;
@SerializedName("Message")
@Expose
private String message;

public Boolean getStatus() {
return status;
}

public void setStatus(Boolean status) {
this.status = status;
}

public Integer getErrors() {
return errors;
}

public void setErrors(Integer errors) {
this.errors = errors;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

}