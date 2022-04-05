package com.dmw.noble.model_pos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileRequest {

@SerializedName("success")
@Expose
private String success;
@SerializedName("msg")
@Expose
private String msg;
@SerializedName("id")
@Expose
private String id;
@SerializedName("fields")
@Expose
private String fields;
@SerializedName("valuess")
@Expose
private String valuess;

public String getSuccess() {
return success;
}

public void setSuccess(String success) {
this.success = success;
}

public String getMsg() {
return msg;
}

public void setMsg(String msg) {
this.msg = msg;
}

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getFields() {
return fields;
}

public void setFields(String fields) {
this.fields = fields;
}

public String getValuess() {
return valuess;
}

public void setValuess(String valuess) {
this.valuess = valuess;
}

}