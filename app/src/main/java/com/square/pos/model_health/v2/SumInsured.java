package com.square.pos.model_health.v2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SumInsured {

@SerializedName("id")
@Expose
private String id;
@SerializedName("suminsured")
@Expose
private String suminsured;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getSuminsured() {
return suminsured;
}

public void setSuminsured(String suminsured) {
this.suminsured = suminsured;
}

}