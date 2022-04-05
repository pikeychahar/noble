package com.square.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PosterData {

@SerializedName("SrNo")
@Expose
private Integer srNo;
@SerializedName("Id")
@Expose
private String id;
@SerializedName("Title")
@Expose
private String title;
@SerializedName("Image")
@Expose
private String image;
@SerializedName("Status")
@Expose
private String status;
@SerializedName("Date")
@Expose
private String date;

public Integer getSrNo() {
return srNo;
}

public void setSrNo(Integer srNo) {
this.srNo = srNo;
}

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getTitle() {
return title;
}

public void setTitle(String title) {
this.title = title;
}

public String getImage() {
return image;
}

public void setImage(String image) {
this.image = image;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getDate() {
return date;
}

public void setDate(String date) {
this.date = date;
}

}