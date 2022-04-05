package com.square.pos.model_life;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LifePremium {

@SerializedName("status")
@Expose
private String status;
@SerializedName("msg")
@Expose
private String msg;
@SerializedName("qid")
@Expose
private String qid;
@SerializedName("company")
@Expose
private String company;
@SerializedName("result")
@Expose
private List<Result> result = null;

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

public String getCompany() {
return company;
}

public void setCompany(String company) {
this.company = company;
}

public List<Result> getResult() {
return result;
}

public void setResult(List<Result> result) {
this.result = result;
}

}