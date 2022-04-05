package com.dmw.noble.model_health.v2;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HealthApointee {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("appointee")
@Expose
private List<Apointee> apointee = null;

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

public List<Apointee> getApointee() {
return apointee;
}

public void setApointee(List<Apointee> apointee) {
this.apointee = apointee;
}

}