package com.revature.springbootdemo.beans.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherAPIModel {
    String cloud_pct;
    String temp;
    String feels_like;
    String humidity;
    String min_temp;
    String max_temp;
    String wind_speed;
    String wind_degrees;

    public WeatherAPIModel() {
    }

    public WeatherAPIModel(String cloud_pct, String temp,
                           String feels_like, String humidity,
                           String min_temp, String max_temp,
                           String wind_speed, String wind_degrees) {
        this.cloud_pct = cloud_pct;
        this.temp = temp;
        this.feels_like = feels_like;
        this.humidity = humidity;
        this.min_temp = min_temp;
        this.max_temp = max_temp;
        this.wind_speed = wind_speed;
        this.wind_degrees = wind_degrees;
    }

    public String getCloud_pct() {
        return cloud_pct;
    }

    public void setCloud_pct(String cloud_pct) {
        this.cloud_pct = cloud_pct;
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

    public String getWind_degrees() {
        return wind_degrees;
    }

    public void setWind_degrees(String wind_degrees) {
        this.wind_degrees = wind_degrees;
    }

    @Override
    public String toString() {
        return "WeatherAPIModel{" +
                "cloud_pct='" + cloud_pct + '\'' +
                ", temp='" + temp + '\'' +
                ", feels_like='" + feels_like + '\'' +
                ", humidity='" + humidity + '\'' +
                ", min_temp='" + min_temp + '\'' +
                ", max_temp='" + max_temp + '\'' +
                ", wind_speed='" + wind_speed + '\'' +
                ", wind_degrees='" + wind_degrees + '\'' +
                '}';
    }
}
