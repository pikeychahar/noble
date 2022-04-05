package com.dmw.noble.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Occupation {

@SerializedName("id")
@Expose
private String id;
@SerializedName("occupation")
@Expose
private String occupation;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getOccupation() {
return occupation;
}

public void setOccupation(String occupation) {
this.occupation = occupation;
}

}
