package com.dmw.noble.model_crm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InsurerNewList {

@SerializedName("Status")
@Expose
private Boolean status;
@SerializedName("Message")
@Expose
private String message;
@SerializedName("Ins_Compaines")
@Expose
private List<InsCompany> insCompaines = null;

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

public List<InsCompany> getInsCompaines() {
return insCompaines;
}

public void setInsCompaines(List<InsCompany> insCompaines) {
this.insCompaines = insCompaines;
}

}