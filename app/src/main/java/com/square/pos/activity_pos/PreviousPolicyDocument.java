package com.square.pos.activity_pos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PreviousPolicyDocument implements Serializable {

@SerializedName("image")
@Expose
private String image;

public String getImage() {
return image;
}

public void setImage(String image) {
this.image = image;
}

}