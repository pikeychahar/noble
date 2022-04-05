package com.square.pos.model_health;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

@SerializedName("error")
@Expose
private String error;

public String getError() {
return error;
}

public void setError(String error) {
this.error = error;
}

}