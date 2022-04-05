package com.dmw.noble.model_life;

import java.util.ArrayList;

/**
 * Created by Prahalad Chahar on 27/09/21.
 */
public  class LifePremiumQuote {
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
    public String cancerCare;
    public String criticalIllness;
    public String accidentDeathCover;
    public String personalAccident;

    public ArrayList<String> getArrayCover() {
        return arrayCover;
    }

    public void setArrayCover(ArrayList<String> arrayCover) {
        this.arrayCover = arrayCover;
    }

    public ArrayList<String> arrayCover = new ArrayList<>();

    public String getCancerCare() {
        return cancerCare;
    }

    public void setCancerCare(String cancerCare) {
        this.cancerCare = cancerCare;
    }

    public String getCriticalIllness() {
        return criticalIllness;
    }

    public void setCriticalIllness(String criticalIllness) {
        this.criticalIllness = criticalIllness;
    }

    public String getAccidentDeathCover() {
        return accidentDeathCover;
    }

    public void setAccidentDeathCover(String accidentDeathCover) {
        this.accidentDeathCover = accidentDeathCover;
    }

    public String getPersonalAccident() {
        return personalAccident;
    }

    public void setPersonalAccident(String personalAccident) {
        this.personalAccident = personalAccident;
    }

    public String getIncomeBenefit() {
        return incomeBenefit;
    }

    public void setIncomeBenefit(String incomeBenefit) {
        this.incomeBenefit = incomeBenefit;
    }

    public String incomeBenefit;

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
