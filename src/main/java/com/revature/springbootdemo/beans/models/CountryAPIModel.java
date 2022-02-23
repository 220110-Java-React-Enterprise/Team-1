package com.revature.springbootdemo.beans.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryAPIModel {
    String name;
    Double gdp;
    Float unemployment;
    Float homicide_rate;

    public CountryAPIModel() {
    }

    public CountryAPIModel(String name, Double gdp, Float unemployment, Float homicide_rate) {
        this.name = name;
        this.gdp = gdp;
        this.unemployment = unemployment;
        this.homicide_rate = homicide_rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getGdp() {
        return gdp;
    }

    public void setGdp(Double gdp) {
        this.gdp = gdp;
    }

    public Float getUnemployment() {
        return unemployment;
    }

    public void setUnemployment(Float unemployment) {
        this.unemployment = unemployment;
    }

    public Float getHomicide_rate() {
        return homicide_rate;
    }

    public void setHomicide_rate(Float homicide_rate) {
        this.homicide_rate = homicide_rate;
    }

    @Override
    public String toString() {
        return "{" +
                "\"name\":\"" + name + '\"' +
                ", \"gdp\":" + gdp +
                ", \"unemployment\":" + unemployment +
                ", \"homicide_rate\":" + homicide_rate +
                '}';
    }
}
