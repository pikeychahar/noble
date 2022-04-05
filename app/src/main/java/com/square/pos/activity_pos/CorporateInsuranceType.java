package com.square.pos.activity_pos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CorporateInsuranceType {

@SerializedName("id")
@Expose
private String id;
@SerializedName("InsuranceType")
@Expose
private String insuranceType;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getInsuranceType() {
return insuranceType;
}

public void setInsuranceType(String insuranceType) {
this.insuranceType = insuranceType;
}

}