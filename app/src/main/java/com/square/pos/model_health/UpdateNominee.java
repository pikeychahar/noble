package com.square.pos.model_health;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateNominee {

@SerializedName("success")
@Expose
private String success;
@SerializedName("msg")
@Expose
private String msg;
@SerializedName("quoteid")
@Expose
private String quoteid;
@SerializedName("NomineeName")
@Expose
private String nomineeName;
@SerializedName("NomineeAge")
@Expose
private String nomineeAge;
@SerializedName("NomineeRelationship")
@Expose
private String nomineeRelationship;
@SerializedName("AppointeeName")
@Expose
private String appointeeName;
@SerializedName("AppointeeAge")
@Expose
private String appointeeAge;
@SerializedName("AppointeeRelationship")
@Expose
private String appointeeRelationship;

public String getSuccess() {
return success;
}

public void setSuccess(String success) {
this.success = success;
}

public String getMsg() {
return msg;
}

public void setMsg(String msg) {
this.msg = msg;
}

public String getQuoteid() {
return quoteid;
}

public void setQuoteid(String quoteid) {
this.quoteid = quoteid;
}

public String getNomineeName() {
return nomineeName;
}

public void setNomineeName(String nomineeName) {
this.nomineeName = nomineeName;
}

public String getNomineeAge() {
return nomineeAge;
}

public void setNomineeAge(String nomineeAge) {
this.nomineeAge = nomineeAge;
}

public String getNomineeRelationship() {
return nomineeRelationship;
}

public void setNomineeRelationship(String nomineeRelationship) {
this.nomineeRelationship = nomineeRelationship;
}

public String getAppointeeName() {
return appointeeName;
}

public void setAppointeeName(String appointeeName) {
this.appointeeName = appointeeName;
}

public String getAppointeeAge() {
return appointeeAge;
}

public void setAppointeeAge(String appointeeAge) {
this.appointeeAge = appointeeAge;
}

public String getAppointeeRelationship() {
return appointeeRelationship;
}

public void setAppointeeRelationship(String appointeeRelationship) {
this.appointeeRelationship = appointeeRelationship;
}

}