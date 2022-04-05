package com.dmw.noble.model_crm.policy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("verticalData")
    @Expose
    private List<VerticalDatum> verticalData = null;
    @SerializedName("zoneData")
    @Expose
    private List<ZoneDatum> zoneData = null;
    @SerializedName("regionData")
    @Expose
    private List<RegionDatum> regionData = null;
    @SerializedName("SR_Session_Year")
    @Expose
    private List<SRSessionYear> sRSessionYear = null;
    @SerializedName("LobData")
    @Expose
    private List<LobDatum> lobData = null;
    @SerializedName("EarningStatusData")
    @Expose
    private List<Object> earningStatusData = null;
    @SerializedName("LoginScope")
    @Expose
    private List<Object> loginScope = null;

    public List<VerticalDatum> getVerticalData() {
        return verticalData;
    }

    public void setVerticalData(List<VerticalDatum> verticalData) {
        this.verticalData = verticalData;
    }

    public List<ZoneDatum> getZoneData() {
        return zoneData;
    }

    public void setZoneData(List<ZoneDatum> zoneData) {
        this.zoneData = zoneData;
    }

    public List<RegionDatum> getRegionData() {
        return regionData;
    }

    public void setRegionData(List<RegionDatum> regionData) {
        this.regionData = regionData;
    }

    public List<SRSessionYear> getSRSessionYear() {
        return sRSessionYear;
    }

    public void setSRSessionYear(List<SRSessionYear> sRSessionYear) {
        this.sRSessionYear = sRSessionYear;
    }

    public List<LobDatum> getLobData() {
        return lobData;
    }

    public void setLobData(List<LobDatum> lobData) {
        this.lobData = lobData;
    }

    public List<Object> getEarningStatusData() {
        return earningStatusData;
    }

    public void setEarningStatusData(List<Object> earningStatusData) {
        this.earningStatusData = earningStatusData;
    }

    public List<Object> getLoginScope() {
        return loginScope;
    }

    public void setLoginScope(List<Object> loginScope) {
        this.loginScope = loginScope;
    }

}