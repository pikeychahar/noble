package com.square.pos.model_crm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReqData {

@SerializedName("CID")
@Expose
private String cid;
@SerializedName("cancelId")
@Expose
private String cancelId;
@SerializedName("SrId")
@Expose
private String srId;
@SerializedName("login_type")
@Expose
private String loginType;
@SerializedName("login_id")
@Expose
private String loginId;
@SerializedName("alternatePolicy")
@Expose
private String alternatePolicy;
@SerializedName("customerLetter")
@Expose
private String customerLetter;
@SerializedName("cancelCheque")
@Expose
private String cancelCheque;
@SerializedName("sucessDoc")
@Expose
private String sucessDoc;
@SerializedName("remarks")
@Expose
private String remarks;
@SerializedName("insert_date")
@Expose
private String insertDate;
@SerializedName("assignedToEmp")
@Expose
private String assignedToEmp;
@SerializedName("status")
@Expose
private String status;
@SerializedName("inProcessRemarks")
@Expose
private String inProcessRemarks;
@SerializedName("completeRemarks")
@Expose
private String completeRemarks;
@SerializedName("rejectRemarks")
@Expose
private String rejectRemarks;

public String getCid() {
return cid;
}

public void setCid(String cid) {
this.cid = cid;
}

public String getCancelId() {
return cancelId;
}

public void setCancelId(String cancelId) {
this.cancelId = cancelId;
}

public String getSrId() {
return srId;
}

public void setSrId(String srId) {
this.srId = srId;
}

public String getLoginType() {
return loginType;
}

public void setLoginType(String loginType) {
this.loginType = loginType;
}

public String getLoginId() {
return loginId;
}

public void setLoginId(String loginId) {
this.loginId = loginId;
}

public String getAlternatePolicy() {
return alternatePolicy;
}

public void setAlternatePolicy(String alternatePolicy) {
this.alternatePolicy = alternatePolicy;
}

public String getCustomerLetter() {
return customerLetter;
}

public void setCustomerLetter(String customerLetter) {
this.customerLetter = customerLetter;
}

public String getCancelCheque() {
return cancelCheque;
}

public void setCancelCheque(String cancelCheque) {
this.cancelCheque = cancelCheque;
}

public String getSucessDoc() {
return sucessDoc;
}

public void setSucessDoc(String sucessDoc) {
this.sucessDoc = sucessDoc;
}

public String getRemarks() {
return remarks;
}

public void setRemarks(String remarks) {
this.remarks = remarks;
}

public String getInsertDate() {
return insertDate;
}

public void setInsertDate(String insertDate) {
this.insertDate = insertDate;
}

public String getAssignedToEmp() {
return assignedToEmp;
}

public void setAssignedToEmp(String assignedToEmp) {
this.assignedToEmp = assignedToEmp;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getInProcessRemarks() {
return inProcessRemarks;
}

public void setInProcessRemarks(String inProcessRemarks) {
this.inProcessRemarks = inProcessRemarks;
}

public String getCompleteRemarks() {
return completeRemarks;
}

public void setCompleteRemarks(String completeRemarks) {
this.completeRemarks = completeRemarks;
}

public String getRejectRemarks() {
return rejectRemarks;
}

public void setRejectRemarks(String rejectRemarks) {
this.rejectRemarks = rejectRemarks;
}

}