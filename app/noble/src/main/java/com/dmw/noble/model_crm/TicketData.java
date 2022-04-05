package com.dmw.noble.model_crm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TicketData {

    @SerializedName("SrNo")
    @Expose
    private Integer srNo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("Ticket_Id")
    @Expose
    private String ticketId;
    @SerializedName("Completed_Timestamp")
    @Expose
    private String completedTimestamp;
    @SerializedName("Ticket_Creator")
    @Expose
    private String ticketCreator;
    @SerializedName("Ticket_Manager")
    @Expose
    private String ticketManager;
    @SerializedName("TicketType")
    @Expose
    private String ticketType;

    public String getTicketTypeDes() {
        return ticketTypeDes;
    }

    public void setTicketTypeDes(String ticketTypeDes) {
        this.ticketTypeDes = ticketTypeDes;
    }

    @SerializedName("ticket_type")
    @Expose
    private String ticketTypeDes;
    @SerializedName("Quotation_Id")
    @Expose
    private String quotationId;
    @SerializedName("Status_Data")
    @Expose
    private List<StatusDatum> statusData = null;
    @SerializedName("Add_Stamp")
    @Expose
    private String addStamp;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("CurrentUser_Id")
    @Expose
    private String currentUserId;

    public Integer getSrNo() {
        return srNo;
    }

    public void setSrNo(Integer srNo) {
        this.srNo = srNo;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getCompletedTimestamp() {
        return completedTimestamp;
    }

    public void setCompletedTimestamp(String completedTimestamp) {
        this.completedTimestamp = completedTimestamp;
    }

    public String getTicketCreator() {
        return ticketCreator;
    }

    public void setTicketCreator(String ticketCreator) {
        this.ticketCreator = ticketCreator;
    }

    public String getTicketManager() {
        return ticketManager;
    }

    public void setTicketManager(String ticketManager) {
        this.ticketManager = ticketManager;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public String getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(String quotationId) {
        this.quotationId = quotationId;
    }

    public List<StatusDatum> getStatusData() {
        return statusData;
    }

    public void setStatusData(List<StatusDatum> statusData) {
        this.statusData = statusData;
    }

    public String getAddStamp() {
        return addStamp;
    }

    public void setAddStamp(String addStamp) {
        this.addStamp = addStamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

}