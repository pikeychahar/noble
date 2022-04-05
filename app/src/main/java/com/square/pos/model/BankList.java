package com.square.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BankList {

@SerializedName("bank_id")
@Expose
private String bankId;
@SerializedName("bank_name")
@Expose
private String bankName;

public String getBankId() {
return bankId;
}

public void setBankId(String bankId) {
this.bankId = bankId;
}

public String getBankName() {
return bankName;
}

public void setBankName(String bankName) {
this.bankName = bankName;
}

}