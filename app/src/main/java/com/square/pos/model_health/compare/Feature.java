package com.square.pos.model_health.compare;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Feature {

@SerializedName("feature")
@Expose
private String feature;
@SerializedName("company1")
@Expose
private String company1;
@SerializedName("company2")
@Expose
private String company2;
@SerializedName("company3")
@Expose
private String company3;

public String getFeature() {
return feature;
}

public void setFeature(String feature) {
this.feature = feature;
}

public String getCompany1() {
return company1;
}

public void setCompany1(String company1) {
this.company1 = company1;
}

public String getCompany2() {
return company2;
}

public void setCompany2(String company2) {
this.company2 = company2;
}

public String getCompany3() {
return company3;
}

public void setCompany3(String company3) {
this.company3 = company3;
}

}