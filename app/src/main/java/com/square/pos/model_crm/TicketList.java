package com.square.pos.model_crm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TicketList {

@SerializedName("Status")
@Expose
private Boolean status;
@SerializedName("TicketList")
@Expose
private List<Ticket> ticketList = null;

public Boolean getStatus() {
return status;
}

public void setStatus(Boolean status) {
this.status = status;
}

public List<Ticket> getTicketList() {
return ticketList;
}

public void setTicketList(List<Ticket> ticketList) {
this.ticketList = ticketList;
}

}