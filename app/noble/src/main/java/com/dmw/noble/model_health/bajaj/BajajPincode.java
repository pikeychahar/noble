package com.dmw.noble.model_health.bajaj;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BajajPincode {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("city")
@Expose
private List<City> city = null;

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

public List<City> getCity() {
return city;
}

public void setCity(List<City> city) {
this.city = city;
}

}