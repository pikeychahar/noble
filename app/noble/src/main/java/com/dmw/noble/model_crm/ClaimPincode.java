package com.dmw.noble.model_crm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClaimPincode {

    @SerializedName("Status")
    @Expose
    private Boolean status;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("State_ID_PK")
    @Expose
    private String stateIDPK;
    @SerializedName("State_Name")
    @Expose
    private String stateName;
    @SerializedName("District_ID_PK")
    @Expose
    private String districtIDPK;
    @SerializedName("District_Name")
    @Expose
    private String districtName;
    @SerializedName("City_or_Village_ID_PK")
    @Expose
    private String cityOrVillageIDPK;
    @SerializedName("City_or_Village_Name")
    @Expose
    private String cityOrVillageName;
    @SerializedName("Area_ID_PK")
    @Expose
    private String areaIDPK;
    @SerializedName("Area_Name")
    @Expose
    private String areaName;
    @SerializedName("Pin_code")
    @Expose
    private String pinCode;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStateIDPK() {
        return stateIDPK;
    }

    public void setStateIDPK(String stateIDPK) {
        this.stateIDPK = stateIDPK;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getDistrictIDPK() {
        return districtIDPK;
    }

    public void setDistrictIDPK(String districtIDPK) {
        this.districtIDPK = districtIDPK;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getCityOrVillageIDPK() {
        return cityOrVillageIDPK;
    }

    public void setCityOrVillageIDPK(String cityOrVillageIDPK) {
        this.cityOrVillageIDPK = cityOrVillageIDPK;
    }

    public String getCityOrVillageName() {
        return cityOrVillageName;
    }

    public void setCityOrVillageName(String cityOrVillageName) {
        this.cityOrVillageName = cityOrVillageName;
    }

    public String getAreaIDPK() {
        return areaIDPK;
    }

    public void setAreaIDPK(String areaIDPK) {
        this.areaIDPK = areaIDPK;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

}