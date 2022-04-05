package com.dmw.noble.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Account {

@SerializedName("account_types")
@Expose
private List<AccountType> accountTypes = null;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("message")
@Expose
private String message;

public List<AccountType> getAccountTypes() {
return accountTypes;
}

public void setAccountTypes(List<AccountType> accountTypes) {
this.accountTypes = accountTypes;
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
