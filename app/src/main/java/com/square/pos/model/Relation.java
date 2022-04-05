package com.square.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Relation {

@SerializedName("id")
@Expose
private String id;
@SerializedName("relation")
@Expose
private String relation;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getRelation() {
return relation;
}

public void setRelation(String relation) {
this.relation = relation;
}

}