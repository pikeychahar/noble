package com.dmw.noble.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

@SerializedName("Id")
@Expose
private String id;
@SerializedName("InsType")
@Expose
private String insType;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getInsType() {
return insType;
}

public void setInsType(String insType) {
this.insType = insType;
}

}