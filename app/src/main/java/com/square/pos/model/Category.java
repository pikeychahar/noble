package com.square.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {

@SerializedName("code")
@Expose
private String code;
@SerializedName("name")
@Expose
private String name;
@SerializedName("logo")
@Expose
private String logo;
@SerializedName("type")
@Expose
private String type;

public String getCode() {
return code;
}

public void setCode(String code) {
this.code = code;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getLogo() {
return logo;
}

public void setLogo(String logo) {
this.logo = logo;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

}