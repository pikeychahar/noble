package com.dmw.noble.model_health.v2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Occupation {

@SerializedName("code")
@Expose
private String code;
@SerializedName("name")
@Expose
private String name;

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

}