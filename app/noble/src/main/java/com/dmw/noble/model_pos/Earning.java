package com.dmw.noble.model_pos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Earning {

@SerializedName("quotation_id")
@Expose
private String quotationId;
@SerializedName("od_percent")
@Expose
private String odPercent;
@SerializedName("tp_percent")
@Expose
private String tpPercent;
@SerializedName("od_com_amount")
@Expose
private String odComAmount;
@SerializedName("tp_com_amount")
@Expose
private String tpComAmount;
@SerializedName("total_amount")
@Expose
private String totalAmount;
@SerializedName("utr_no")
@Expose
private String utrNo;
@SerializedName("date_time")
@Expose
private String dateTime;
@SerializedName("status")
@Expose
private String status;

public String getQuotationId() {
return quotationId;
}

public void setQuotationId(String quotationId) {
this.quotationId = quotationId;
}

public String getOdPercent() {
return odPercent;
}

public void setOdPercent(String odPercent) {
this.odPercent = odPercent;
}

public String getTpPercent() {
return tpPercent;
}

public void setTpPercent(String tpPercent) {
this.tpPercent = tpPercent;
}

public String getOdComAmount() {
return odComAmount;
}

public void setOdComAmount(String odComAmount) {
this.odComAmount = odComAmount;
}

public String getTpComAmount() {
return tpComAmount;
}

public void setTpComAmount(String tpComAmount) {
this.tpComAmount = tpComAmount;
}

public String getTotalAmount() {
return totalAmount;
}

public void setTotalAmount(String totalAmount) {
this.totalAmount = totalAmount;
}

public String getUtrNo() {
return utrNo;
}

public void setUtrNo(String utrNo) {
this.utrNo = utrNo;
}

public String getDateTime() {
return dateTime;
}

public void setDateTime(String dateTime) {
this.dateTime = dateTime;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

}