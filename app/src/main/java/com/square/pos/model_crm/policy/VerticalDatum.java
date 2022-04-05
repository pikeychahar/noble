package com.square.pos.model_crm.policy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerticalDatum {

    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("Name")
    @Expose
    private String name;

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

}