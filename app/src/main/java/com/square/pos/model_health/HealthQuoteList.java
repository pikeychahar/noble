package com.square.pos.model_health;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HealthQuoteList {

@SerializedName("health_quote")
@Expose
private List<HealthQuote> healthQuote = null;
@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;

public List<HealthQuote> getHealthQuote() {
return healthQuote;
}

public void setHealthQuote(List<HealthQuote> healthQuote) {
this.healthQuote = healthQuote;
}

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

}