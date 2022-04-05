package com.dmw.noble.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountType {

@SerializedName("account_types_id")
@Expose
private String accountTypesId;
@SerializedName("account_types_name")
@Expose
private String accountTypesName;

public String getAccountTypesId() {
return accountTypesId;
}

public void setAccountTypesId(String accountTypesId) {
this.accountTypesId = accountTypesId;
}

public String getAccountTypesName() {
return accountTypesName;
}

public void setAccountTypesName(String accountTypesName) {
this.accountTypesName = accountTypesName;
}

}