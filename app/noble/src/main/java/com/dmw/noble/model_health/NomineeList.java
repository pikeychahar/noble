package com.dmw.noble.model_health;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NomineeList {

@SerializedName("id")
@Expose
private String id;
@SerializedName("nominee")
@Expose
private String nominee;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getNominee() {
return nominee;
}

public void setNominee(String nominee) {
this.nominee = nominee;
}

}