package com.dmw.noble.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Prahalad Chahar on 2019-06-25.
 */
public class VehicleData {
    @SerializedName("vehicle_num")
    @Expose
    private String vehicleNum;
    @SerializedName("ownerName")
    @Expose
    private String ownerName;
    @SerializedName("rto")
    @Expose
    private String rto;
    @SerializedName("car")
    @Expose
    private VehicleInfo car;
    @SerializedName("ownerAdd")
    @Expose
    private Object ownerAdd;
    @SerializedName("registDate")
    @Expose
    private String registDate;
    @SerializedName("vehicleClass")
    @Expose
    private String vehicleClass;
    @SerializedName("fuelType")
    @Expose
    private String fuelType;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("cc")
    @Expose
    private String cc;
    @SerializedName("ownerNum")
    @Expose
    private String ownerNum;
    @SerializedName("engineNo")
    @Expose
    private String engineNo;
    @SerializedName("chasisNo")
    @Expose
    private String chasisNo;
    @SerializedName("meta")
    @Expose
    private String meta;

    public String getVehicleNum() {
        return vehicleNum;
    }

    public void setVehicleNum(String vehicleNum) {
        this.vehicleNum = vehicleNum;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getRto() {
        return rto;
    }

    public void setRto(String rto) {
        this.rto = rto;
    }

    public VehicleInfo getCar() {
        return car;
    }

    public void setCar(VehicleInfo car) {
        this.car = car;
    }

    public Object getOwnerAdd() {
        return ownerAdd;
    }

    public void setOwnerAdd(Object ownerAdd) {
        this.ownerAdd = ownerAdd;
    }

    public String getRegistDate() {
        return registDate;
    }

    public void setRegistDate(String registDate) {
        this.registDate = registDate;
    }

    public String getVehicleClass() {
        return vehicleClass;
    }

    public void setVehicleClass(String vehicleClass) {
        this.vehicleClass = vehicleClass;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getOwnerNum() {
        return ownerNum;
    }

    public void setOwnerNum(String ownerNum) {
        this.ownerNum = ownerNum;
    }

    public String getEngineNo() {
        return engineNo;
    }

    public void setEngineNo(String engineNo) {
        this.engineNo = engineNo;
    }

    public String getChasisNo() {
        return chasisNo;
    }

    public void setChasisNo(String chasisNo) {
        this.chasisNo = chasisNo;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }
}
