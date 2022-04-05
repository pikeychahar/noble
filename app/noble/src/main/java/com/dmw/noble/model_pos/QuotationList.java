package com.dmw.noble.model_pos;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuotationList {

@SerializedName("policy")
@Expose
private List<Quotation> policy = null;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("message")
@Expose
private String message;

public List<Quotation> getPolicy() {
return policy;
}

public void setPolicy(List<Quotation> policy) {
this.policy = policy;
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

}