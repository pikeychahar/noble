package com.square.pos.model_travel;

import java.util.ArrayList;

/**
 * Created by Prahalad Chahar on 22/02/22.
 */
public  class TravelPremiumQuote {
    public String planName;
    public String imgPath;
    public String extra;
    public int gross;
    public int serviceTax;
    public String company;
    public int premium;
    public int ageYears;
    public int maxAge;
    public String sumAssured;

    public ArrayList<String> getArrayCover() {
        return arrayCover;
    }

    public void setArrayCover(ArrayList<String> arrayCover) {
        this.arrayCover = arrayCover;
    }

    public ArrayList<String> arrayCover = new ArrayList<>();

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
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

    public int getGross() {
        return gross;
    }

    public void setGross(int gross) {
        this.gross = gross;
    }

    public int getServiceTax() {
        return serviceTax;
    }

    public void setServiceTax(int serviceTax) {
        this.serviceTax = serviceTax;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getPremium() {
        return premium;
    }

    public void setPremium(int premium) {
        this.premium = premium;
    }

    public String getSumAssured() {
        return sumAssured;
    }

    public void setSumAssured(String sumAssured) {
        this.sumAssured = sumAssured;
    }

    public String getPlanTenure() {
        return planTenure;
    }

    public void setPlanTenure(String planTenure) {
        this.planTenure = planTenure;
    }

    public String planTenure;

    public int getAgeYears() {
        return ageYears;
    }

    public void setAgeYears(int ageYears) {
        this.ageYears = ageYears;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }
}
