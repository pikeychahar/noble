package com.dmw.noble.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VehicleDetail {

@SerializedName("errors")
@Expose
private Object errors;
@SerializedName("data")
@Expose
private VehicleData data;

public Object getErrors() {
return errors;
}

public void setErrors(Object errors) {
this.errors = errors;
}

public VehicleData getData() {
return data;
}

public void setData(VehicleData data) {
this.data = data;
}

}