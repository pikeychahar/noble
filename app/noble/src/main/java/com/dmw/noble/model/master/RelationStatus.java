package com.dmw.noble.model.master;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RelationStatus {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("relation")
@Expose
private List<Relation> relation = null;

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

public List<Relation> getRelation() {
return relation;
}

public void setRelation(List<Relation> relation) {
this.relation = relation;
}

}