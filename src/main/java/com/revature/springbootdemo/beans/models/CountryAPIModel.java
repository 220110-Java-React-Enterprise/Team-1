package com.revature.springbootdemo.beans.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryAPIModel {
    String gdp;
    String unemployment;
    String homicide_rate;

    public CountryAPIModel() {
    }

    public CountryAPIModel(String gdp, String unemployment, String homicide_rate) {
        this.gdp = gdp;
        this.unemployment = unemployment;
        this.homicide_rate = homicide_rate;
    }

    public String getGdp() {
        return gdp;
    }

    public void setGdp(String gdp) {
        this.gdp = gdp;
    }

    public String getUnemployment() {
        return unemployment;
    }

    public void setUnemployment(String unemployment) {
        this.unemployment = unemployment;
    }

    public String getHomicide_rate() {
        return homicide_rate;
    }

    public void setHomicide_rate(String homicide_rate) {
        this.homicide_rate = homicide_rate;
    }

    @Override
    public String toString() {
        return "CountryAPIModel{" +
                "gdp='" + gdp + '\'' +
                ", unemployment='" + unemployment + '\'' +
                ", homicide_rate='" + homicide_rate + '\'' +
                '}';
    }
}
