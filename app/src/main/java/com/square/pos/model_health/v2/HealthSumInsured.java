package com.square.pos.model_health.v2;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HealthSumInsured {

@SerializedName("status")
@Expose
private String status;
@SerializedName("msg")
@Expose
private String msg;
@SerializedName("sumInsured")
@Expose
private List<SumInsured> sumInsured = null;

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getMsg() {
return msg;
}

public void setMsg(String msg) {
this.msg = msg;
}

public List<SumInsured> getSumInsured() {
return sumInsured;
}

public void setSumInsured(List<SumInsured> sumInsured) {
this.sumInsured = sumInsured;
}

}