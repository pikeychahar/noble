package com.dmw.noble.model_health.v2;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HealthSalutation {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("salutation")
@Expose
private List<Salutation> salutation = null;

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

public List<Salutation> getSalutation() {
return salutation;
}

public void setSalutation(List<Salutation> salutation) {
this.salutation = salutation;
}

}