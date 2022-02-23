package com.revature.springbootdemo.beans.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResultModel {
    String country;
    String temp;
    String feels_like;
    String humidity;
    String min_temp;
    String max_temp;
    String wind_speed;
    String gdp;
    String unemployment;
    String homicide_rate;

    public SearchResultModel() {
    }

    public SearchResultModel(String country, String temp, String feels_like, String humidity, String min_temp, String max_temp, String wind_speed, String gdp, String unemployment, String homicide_rate) {
        this.country = country;
        this.temp = temp;
        this.feels_like = feels_like;
        this.humidity = humidity;
        this.min_temp = min_temp;
        this.max_temp = max_temp;
        this.wind_speed = wind_speed;
        this.gdp = gdp;
        this.unemployment = unemployment;
        this.homicide_rate = homicide_rate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getFeels_like() {
        return feels_like;
    }

    public void setFeels_like(String feels_like) {
        this.feels_like = feels_like;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getMin_temp() {
        return min_temp;
    }

    public void setMin_temp(String min_temp) {
        this.min_temp = min_temp;
    }

    public String getMax_temp() {
        return max_temp;
    }

    public void setMax_temp(String max_temp) {
        this.max_temp = max_temp;
    }

    public String getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(String wind_speed) {
        this.wind_speed = wind_speed;
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
        return "{" +
                "country='" + country + '\'' +
                ", temp='" + temp + '\'' +
                ", feels_like='" + feels_like + '\'' +
                ", humidity='" + humidity + '\'' +
                ", min_temp='" + min_temp + '\'' +
                ", max_temp='" + max_temp + '\'' +
                ", wind_speed='" + wind_speed + '\'' +
                ", gdp='" + gdp + '\'' +
                ", unemployment='" + unemployment + '\'' +
                ", homicide_rate='" + homicide_rate + '\'' +
                '}';
    }
}
