package com.dmw.noble.model_crm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StateData {

    @SerializedName("SrNo")
    @Expose
    private Integer srNo;
    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("isSelected")
    @Expose
    private Boolean isSelected;
    @SerializedName("Agent_Id")
    @Expose
    private String agentId;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("TDS")
    @Expose
    private Integer tds;
    @SerializedName("Total_Payout_Amount")
    @Expose
    private Integer totalPayoutAmount;
    @SerializedName("Total_TDS_Amount")
    @Expose
    private Integer totalTDSAmount;
    @SerializedName("Total_Payout_Net_Amount")
    @Expose
    private Integer totalPayoutNetAmount;
    @SerializedName("Pending")
    @Expose
    private String pending;
    @SerializedName("Approved")
    @Expose
    private String approved;
    @SerializedName("Rejected")
    @Expose
    private String rejected;
    @SerializedName("TotalFiles")
    @Expose
    private Integer totalFiles;
    @SerializedName("Group_Id")
    @Expose
    private String groupId;
    @SerializedName("UTR_No")
    @Expose
    private String uTRNo;
    @SerializedName("UTR_Update_Date")
    @Expose
    private String uTRUpdateDate;
    @SerializedName("Add_Stamp")
    @Expose
    private String addStamp;

    public Integer getSrNo() {
        return srNo;
    }

    public void setSrNo(Integer srNo) {
        this.srNo = srNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTds() {
        return tds;
    }

    public void setTds(Integer tds) {
        this.tds = tds;
    }

    public Integer getTotalPayoutAmount() {
        return totalPayoutAmount;
    }

    public void setTotalPayoutAmount(Integer totalPayoutAmount) {
        this.totalPayoutAmount = totalPayoutAmount;
    }

    public Integer getTotalTDSAmount() {
        return totalTDSAmount;
    }

    public void setTotalTDSAmount(Integer totalTDSAmount) {
        this.totalTDSAmount = totalTDSAmount;
    }

    public Integer getTotalPayoutNetAmount() {
        return totalPayoutNetAmount;
    }

    public void setTotalPayoutNetAmount(Integer totalPayoutNetAmount) {
        this.totalPayoutNetAmount = totalPayoutNetAmount;
    }

    public String getPending() {
        return pending;
    }

    public void setPending(String pending) {
        this.pending = pending;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public String getRejected() {
        return rejected;
    }

    public void setRejected(String rejected) {
        this.rejected = rejected;
    }

    public Integer getTotalFiles() {
        return totalFiles;
    }

    public void setTotalFiles(Integer totalFiles) {
        this.totalFiles = totalFiles;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getUTRNo() {
        return uTRNo;
    }

    public void setUTRNo(String uTRNo) {
        this.uTRNo = uTRNo;
    }

    public String getUTRUpdateDate() {
        return uTRUpdateDate;
    }

    public void setUTRUpdateDate(String uTRUpdateDate) {
        this.uTRUpdateDate = uTRUpdateDate;
    }

    public String getAddStamp() {
        return addStamp;
    }

    public void setAddStamp(String addStamp) {
        this.addStamp = addStamp;
    }
}
