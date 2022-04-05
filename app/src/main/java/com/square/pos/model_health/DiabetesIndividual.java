package com.square.pos.model_health;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DiabetesIndividual {

@SerializedName("premium")
@Expose
private Integer premium;
@SerializedName("serviceTax")
@Expose
private Integer serviceTax;
@SerializedName("totalPremium")
@Expose
private Integer totalPremium;

public Integer getPremium() {
return premium;
}

public void setPremium(Integer premium) {
this.premium = premium;
}

public Integer getServiceTax() {
return serviceTax;
}

public void setServiceTax(Integer serviceTax) {
this.serviceTax = serviceTax;
}

public Integer getTotalPremium() {
return totalPremium;
}

public void setTotalPremium(Integer totalPremium) {
this.totalPremium = totalPremium;
}

}