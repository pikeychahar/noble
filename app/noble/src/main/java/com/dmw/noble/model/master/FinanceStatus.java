package com.dmw.noble.model.master;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FinanceStatus {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("insurer")
@Expose
private List<Insurer> insurer = null;

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public List<Insurer> getInsurer() {
return insurer;
}

public void setInsurer(List<Insurer> insurer) {
this.insurer = insurer;
}

}