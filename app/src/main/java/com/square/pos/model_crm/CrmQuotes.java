package com.square.pos.model_crm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CrmQuotes {

@SerializedName("status")
@Expose
private Boolean status;
@SerializedName("msg")
@Expose
private String msg;
@SerializedName("data")
@Expose
private List<QuoteData> data = null;

public Boolean getStatus() {
return status;
}

public void setStatus(Boolean status) {
this.status = status;
}

public String getMsg() {
return msg;
}

public void setMsg(String msg) {
this.msg = msg;
}

public List<QuoteData> getData() {
return data;
}

public void setData(List<QuoteData> data) {
this.data = data;
}

}

