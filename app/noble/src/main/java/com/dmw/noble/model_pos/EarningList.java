package com.dmw.noble.model_pos;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EarningList {

@SerializedName("earnings")
@Expose
private List<Earning> earnings = null;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("totalEarning")
@Expose
private String totalEarning;

public List<Earning> getEarnings() {
return earnings;
}

public void setEarnings(List<Earning> earnings) {
this.earnings = earnings;
}

public Integer getStatus() {
return status;
}

public void setStatus(Integer status) {
this.status = status;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public String getTotalEarning() {
return totalEarning;
}

public void setTotalEarning(String totalEarning) {
this.totalEarning = totalEarning;
}

}