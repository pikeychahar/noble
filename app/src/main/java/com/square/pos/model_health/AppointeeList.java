package com.square.pos.model_health;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppointeeList {

@SerializedName("id")
@Expose
private String id;
@SerializedName("appointee")
@Expose
private String appointee;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getAppointee() {
return appointee;
}

public void setAppointee(String appointee) {
this.appointee = appointee;
}

}