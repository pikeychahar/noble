package com.dmw.noble.model_health.compare;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Compare {

@SerializedName("features")
@Expose
private List<Feature> features = null;
@SerializedName("companies")
@Expose
private List<Company> companies = null;
@SerializedName("msg")
@Expose
private String msg;
@SerializedName("success")
@Expose
private String success;

public List<Feature> getFeatures() {
return features;
}

public void setFeatures(List<Feature> features) {
this.features = features;
}

public List<Company> getCompanies() {
return companies;
}

public void setCompanies(List<Company> companies) {
this.companies = companies;
}

public String getMsg() {
return msg;
}

public void setMsg(String msg) {
this.msg = msg;
}

public String getSuccess() {
return success;
}

public void setSuccess(String success) {
this.success = success;
}

}