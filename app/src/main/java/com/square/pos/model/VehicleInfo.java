package com.square.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Prahalad Chahar on 2019-06-25.
 */
public class VehicleInfo {
    @SerializedName("imageUrl")
    @Expose
    private Object imageUrl;
    @SerializedName("modelName")
    @Expose
    private String modelName;
    @SerializedName("price")
    @Expose
    private Object price;
    @SerializedName("companyName")
    @Expose
    private String companyName;
    @SerializedName("key1")
    @Expose
    private Object key1;
    @SerializedName("key2")
    @Expose
    private Object key2;
    @SerializedName("varients")
    @Expose
    private Object varients;
    @SerializedName("varientCode")
    @Expose
    private Object varientCode;
    @SerializedName("id")
    @Expose
    private Integer id;

    public Object getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Object imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Object getPrice() {
        return price;
    }

    public void setPrice(Object price) {
        this.price = price;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Object getKey1() {
        return key1;
    }

    public void setKey1(Object key1) {
        this.key1 = key1;
    }

    public Object getKey2() {
        return key2;
    }

    public void setKey2(Object key2) {
        this.key2 = key2;
    }

    public Object getVarients() {
        return varients;
    }

    public void setVarients(Object varients) {
        this.varients = varients;
    }

    public Object getVarientCode() {
        return varientCode;
    }

    public void setVarientCode(Object varientCode) {
        this.varientCode = varientCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
