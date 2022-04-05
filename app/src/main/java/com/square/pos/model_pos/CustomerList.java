package com.square.pos.model_pos;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerList {

@SerializedName("customer")
@Expose
private List<Customer> customer = null;
@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;

public List<Customer> getCustomer() {
return customer;
}

public void setCustomer(List<Customer> customer) {
this.customer = customer;
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