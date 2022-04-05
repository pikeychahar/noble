package com.square.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FuelType {

@SerializedName("fuel_type")
@Expose
private String fuelType;

public String getFuelType() {
return fuelType;
}

public void setFuelType(String fuelType) {
this.fuelType = fuelType;
}

}