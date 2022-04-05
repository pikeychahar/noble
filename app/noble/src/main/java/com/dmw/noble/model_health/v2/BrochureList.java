package com.dmw.noble.model_health.v2;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BrochureList {

@SerializedName("Brochure")
@Expose
private List<Brochure> brochure = null;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("message")
@Expose
private String message;

public List<Brochure> getBrochure() {
return brochure;
}

public void setBrochure(List<Brochure> brochure) {
this.brochure = brochure;
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