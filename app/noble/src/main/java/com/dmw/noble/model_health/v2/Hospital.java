package com.dmw.noble.model_health.v2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hospital {

@SerializedName("company_name")
@Expose
private String companyName;
@SerializedName("hospital_name")
@Expose
private String hospitalName;
@SerializedName("hospital_address")
@Expose
private String hospitalAddress;
@SerializedName("pincode")
@Expose
private String pincode;
@SerializedName("tpa")
@Expose
private String tpa;

public String getCompanyName() {
return companyName;
}

public void setCompanyName(String companyName) {
this.companyName = companyName;
}

public String getHospitalName() {
return hospitalName;
}

public void setHospitalName(String hospitalName) {
this.hospitalName = hospitalName;
}

public String getHospitalAddress() {
return hospitalAddress;
}

public void setHospitalAddress(String hospitalAddress) {
this.hospitalAddress = hospitalAddress;
}

public String getPincode() {
return pincode;
}

public void setPincode(String pincode) {
this.pincode = pincode;
}

public String getTpa() {
return tpa;
}

public void setTpa(String tpa) {
this.tpa = tpa;
}

}