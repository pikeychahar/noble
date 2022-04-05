package com.dmw.noble.model_pos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Customer {

@SerializedName("id")
@Expose
private String id;
@SerializedName("name")
@Expose
private String name;
@SerializedName("mobile")
@Expose
private String mobile;
@SerializedName("city")
@Expose
private String city;
@SerializedName("last_modified_date")
@Expose
private String lastModifiedDate;
@SerializedName("pos_status")
@Expose
private String posStatus;

    public String getPosId() {
        return posId;
    }

    public void setPosId(String posId) {
        this.posId = posId;
    }

    public String getRejectedReason() {
        return rejectedReason;
    }

    public void setRejectedReason(String rejectedReason) {
        this.rejectedReason = rejectedReason;
    }

    @SerializedName("pos_id")
@Expose
private String posId;
@SerializedName("rejected_reason")
@Expose
private String rejectedReason;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getMobile() {
return mobile;
}

public void setMobile(String mobile) {
this.mobile = mobile;
}

public String getCity() {
return city;
}

public void setCity(String city) {
this.city = city;
}

public String getLastModifiedDate() {
return lastModifiedDate;
}

public void setLastModifiedDate(String lastModifiedDate) {
this.lastModifiedDate = lastModifiedDate;
}

public String getPosStatus() {
return posStatus;
}

public void setPosStatus(String posStatus) {
this.posStatus = posStatus;
}

}