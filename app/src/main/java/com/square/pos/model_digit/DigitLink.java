package com.square.pos.model_digit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DigitLink {

@SerializedName("link")
@Expose
private String link;
@SerializedName("status")
@Expose
private String status;
@SerializedName("msg")
@Expose
private String msg;

public String getLink() {
return link;
}

public void setLink(String link) {
this.link = link;
}

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

}