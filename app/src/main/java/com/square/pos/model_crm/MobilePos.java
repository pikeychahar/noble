package com.square.pos.model_crm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MobilePos {

@SerializedName("SrNo")
@Expose
private Integer srNo;
@SerializedName("Creator")
@Expose
private String creator;
@SerializedName("Manager")
@Expose
private String manager;
@SerializedName("Ticket_Manager")
@Expose
private String ticketManager;
@SerializedName("Mobile")
@Expose
private String mobile;
@SerializedName("Assigner_Id")
@Expose
private String assignerId;
@SerializedName("Id")
@Expose
private String id;
@SerializedName("Add_Stamp")
@Expose
private String addStamp;
@SerializedName("Update_Stamp")
@Expose
private String updateStamp;
@SerializedName("Status")
@Expose
private String status;

public Integer getSrNo() {
return srNo;
}

public void setSrNo(Integer srNo) {
this.srNo = srNo;
}

public String getCreator() {
return creator;
}

public void setCreator(String creator) {
this.creator = creator;
}

public String getManager() {
return manager;
}

public void setManager(String manager) {
this.manager = manager;
}

public String getTicketManager() {
return ticketManager;
}

public void setTicketManager(String ticketManager) {
this.ticketManager = ticketManager;
}

public String getMobile() {
return mobile;
}

public void setMobile(String mobile) {
this.mobile = mobile;
}

public String getAssignerId() {
return assignerId;
}

public void setAssignerId(String assignerId) {
this.assignerId = assignerId;
}

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getAddStamp() {
return addStamp;
}

public void setAddStamp(String addStamp) {
this.addStamp = addStamp;
}

public String getUpdateStamp() {
return updateStamp;
}

public void setUpdateStamp(String updateStamp) {
this.updateStamp = updateStamp;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

}