package com.dmw.noble.model_life;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnualIncomeList {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("annualIncome")
@Expose
private List<AnualIncome> anualIncome = null;

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

public List<AnualIncome> getAnualIncome() {
return anualIncome;
}

public void setAnualIncome(List<AnualIncome> anualIncome) {
this.anualIncome = anualIncome;
}

}