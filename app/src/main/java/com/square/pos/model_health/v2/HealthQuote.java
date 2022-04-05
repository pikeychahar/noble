package com.square.pos.model_health.v2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HealthQuote {

@SerializedName("status")
@Expose
private String status;
@SerializedName("msg")
@Expose
private String msg;
@SerializedName("qid")
@Expose
private String qid;

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

public String getQid() {
return qid;
}

public void setQid(String qid) {
this.qid = qid;
}

}