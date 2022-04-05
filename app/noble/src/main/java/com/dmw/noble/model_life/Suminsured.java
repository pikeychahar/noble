package com.dmw.noble.model_life;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Suminsured {

@SerializedName("suminsured")
@Expose
private String suminsured;

public String getSuminsured() {
return suminsured;
}

public void setSuminsured(String suminsured) {
this.suminsured = suminsured;
}

}