package com.square.pos.model_health;

/**
 * Created by Prahalad Chahar on 21/04/20.
 */
public class HealthPremiumPojo {

    public String planName;
    public String planType;
    public String imgPath;
    public String extra;
    public Float gross;
    public Float serviceTax;
    public String company;
    public Float premium;
    public String sumAssured;
    public String planTenure;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String appKey;

    public String getFeaturedPlan() {
        return featuredPlan;
    }

    public void setFeaturedPlan(String featuredPlan) {
        this.featuredPlan = featuredPlan;
    }

    public String featuredPlan;

    public String getFeaturedSubPlan() {
        return featuredSubPlan;
    }

    public void setFeaturedSubPlan(String featuredSubPlan) {
        this.featuredSubPlan = featuredSubPlan;
    }

    public String featuredSubPlan;

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public String planCode;

    public String getPlanTenure() {
        return planTenure;
    }

    public void setPlanTenure(String planTenure) {
        this.planTenure = planTenure;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Float getGross() {
        return gross;
    }

    public void setGross(Float gross) {
        this.gross = gross;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSumAssured() {
        return sumAssured;
    }

    public void setSumAssured(String sumAssured) {
        this.sumAssured = sumAssured;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Float getPremium() {
        return premium;
    }

    public void setPremium(Float premium) {
        this.premium = premium;
    }

    public Float getServiceTax() {
        return serviceTax;
    }

    public void setServiceTax(Float serviceTax) {
        this.serviceTax = serviceTax;
    }

}
