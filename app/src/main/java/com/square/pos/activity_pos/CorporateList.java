package com.square.pos.activity_pos;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CorporateList {

@SerializedName("CorporateInsuranceType")
@Expose
private List<CorporateInsuranceType> corporateInsuranceType = null;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("message")
@Expose
private String message;

public List<CorporateInsuranceType> getCorporateInsuranceType() {
return corporateInsuranceType;
}

public void setCorporateInsuranceType(List<CorporateInsuranceType> corporateInsuranceType) {
this.corporateInsuranceType = corporateInsuranceType;
}

public Integer getStatus() {
return status;
}

public void setStatus(Integer status) {
this.status = status;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

}