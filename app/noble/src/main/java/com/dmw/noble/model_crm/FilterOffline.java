package com.dmw.noble.model_crm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FilterOffline {

@SerializedName("Id")
@Expose
private String id;
@SerializedName("quote_status")
@Expose
private String quoteStatus;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getQuoteStatus() {
return quoteStatus;
}

public void setQuoteStatus(String quoteStatus) {
this.quoteStatus = quoteStatus;
}

}
