package com.square.pos.model_crm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.square.pos.model.MessageData;

public class ChatMessage {

@SerializedName("Message")
@Expose
private MessageData message;

public MessageData getMessage() {
return message;
}

public void setMessage(MessageData message) {
this.message = message;
}

}