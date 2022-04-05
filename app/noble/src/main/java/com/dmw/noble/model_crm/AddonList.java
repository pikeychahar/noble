package com.dmw.noble.model_crm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddonList {

@SerializedName("Status")
@Expose
private Boolean status;
@SerializedName("Message")
@Expose
private String message;
@SerializedName("Addon")
@Expose
private List<Addon> addon = null;

public Boolean getStatus() {
return status;
}

public void setStatus(Boolean status) {
this.status = status;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public List<Addon> getAddon() {
return addon;
}

public void setAddon(List<Addon> addon) {
this.addon = addon;
}

}