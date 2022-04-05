package com.square.pos.model.master;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NomineeStatus {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("nominee_relation")
@Expose
private List<NomineeRelation> nomineeRelation = null;

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

public List<NomineeRelation> getNomineeRelation() {
return nomineeRelation;
}

public void setNomineeRelation(List<NomineeRelation> nomineeRelation) {
this.nomineeRelation = nomineeRelation;
}

}