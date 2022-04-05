package com.dmw.noble.model_crm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Claim implements Serializable {
    @SerializedName("SrNo")
    @Expose
    private Integer srNo;
    @SerializedName("LOB")
    @Expose
    private String lob;
    @SerializedName("TypeName")
    @Expose
    private String typeName;
    @SerializedName("PolicyNo")
    @Expose
    private String policyNo;
    @SerializedName("CustomerName")
    @Expose
    private String customerName;
    @SerializedName("CustomerMobile")
    @Expose
    private String customerMobile;
    @SerializedName("DownloadUrl")
    @Expose
    private String downloadUrl;
    @SerializedName("Company")
    @Expose
    private String company;
    @SerializedName("Vehicle_No")
    @Expose
    private String vehicleNo;
    @SerializedName("Policy_Type")
    @Expose
    private String policyType;
    @SerializedName("Posting_Status_Web")
    @Expose
    private String postingStatusWeb;
    @SerializedName("SR_No")
    @Expose
    private String sRNo;
    @SerializedName("MakeModel")
    @Expose
    private String makeModel;
    @SerializedName("SrID")
    @Expose
    private String srID;
    @SerializedName("CompanyId")
    @Expose
    private String companyId;
    @SerializedName("BookingDate")
    @Expose
    private String bookingDate;
    @SerializedName("IssuedDate")
    @Expose
    private String issuedDate;
    @SerializedName("NetPremium")
    @Expose
    private String netPremium;
    @SerializedName("GrossPremium")
    @Expose
    private String grossPremium;
    @SerializedName("Payout")
    @Expose
    private String payout;
    @SerializedName("Web_Agent_Payout_OD_Amount")
    @Expose
    private String webAgentPayoutODAmount;
    @SerializedName("Web_Agent_Payout_TP_Amount")
    @Expose
    private String webAgentPayoutTPAmount;

    public Integer getSrNo() {
        return srNo;
    }

    public void setSrNo(Integer srNo) {
        this.srNo = srNo;
    }

    public String getLob() {
        return lob;
    }

    public void setLob(String lob) {
        this.lob = lob;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getPolicyType() {
        return policyType;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }

    public String getPostingStatusWeb() {
        return postingStatusWeb;
    }

    public void setPostingStatusWeb(String postingStatusWeb) {
        this.postingStatusWeb = postingStatusWeb;
    }

    public String getSRNo() {
        return sRNo;
    }

    public void setSRNo(String sRNo) {
        this.sRNo = sRNo;
    }

    public String getMakeModel() {
        return makeModel;
    }

    public void setMakeModel(String makeModel) {
        this.makeModel = makeModel;
    }

    public String getSrID() {
        return srID;
    }

    public void setSrID(String srID) {
        this.srID = srID;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(String issuedDate) {
        this.issuedDate = issuedDate;
    }

    public String getNetPremium() {
        return netPremium;
    }

    public void setNetPremium(String netPremium) {
        this.netPremium = netPremium;
    }

    public String getGrossPremium() {
        return grossPremium;
    }

    public void setGrossPremium(String grossPremium) {
        this.grossPremium = grossPremium;
    }

    public String getPayout() {
        return payout;
    }

    public void setPayout(String payout) {
        this.payout = payout;
    }

    public String getWebAgentPayoutODAmount() {
        return webAgentPayoutODAmount;
    }

    public void setWebAgentPayoutODAmount(String webAgentPayoutODAmount) {
        this.webAgentPayoutODAmount = webAgentPayoutODAmount;
    }

    public String getWebAgentPayoutTPAmount() {
        return webAgentPayoutTPAmount;
    }

    public void setWebAgentPayoutTPAmount(String webAgentPayoutTPAmount) {
        this.webAgentPayoutTPAmount = webAgentPayoutTPAmount;
    }
}