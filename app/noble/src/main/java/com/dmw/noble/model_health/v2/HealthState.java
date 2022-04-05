package com.dmw.noble.model_health.v2;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HealthState {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("state")
@Expose
private List<State> state = null;

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

public List<State> getState() {
return state;
}

public void setState(List<State> state) {
this.state = state;
}

}