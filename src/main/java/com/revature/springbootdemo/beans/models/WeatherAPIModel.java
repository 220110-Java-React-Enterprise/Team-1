package com.revature.springbootdemo.beans.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherAPIModel {

    // Declare variables
    Integer cloud_pct;
    Integer temp;
    Integer feels_like;
    Integer humidity;
    Integer min_temp;
    Integer max_temp;
    Float wind_speed;
    Integer wind_degrees;

    public WeatherAPIModel() {      // no arg Constructor
    }

    public WeatherAPIModel(Integer cloud_pct, Integer temp, Integer feels_like, Integer humidity,
                           Integer min_temp, Integer max_temp, Float wind_speed, Integer wind_degrees) {

        this.cloud_pct = cloud_pct;
        this.temp = temp;
        this.feels_like = feels_like;
        this.humidity = humidity;
        this.min_temp = min_temp;
        this.max_temp = max_temp;
        this.wind_speed = wind_speed;
        this.wind_degrees = wind_degrees;

    }

    public Integer getCloud_pct() {
        return cloud_pct;
    }

    public void setCloud_pct(Integer cloud_pct) {
        this.cloud_pct = cloud_pct;
    }


    // Getters and Setters

    public Integer getTemp() {
        return temp;
    }

    public void setTemp(Integer temp) {
        this.temp = temp;
    }

    public Integer getFeels_like() {
        return feels_like;
    }

    public void setFeels_like(Integer feels_like) {
        this.feels_like = feels_like;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Integer getMin_temp() {
        return min_temp;
    }

    public void setMin_temp(Integer min_temp) {
        this.min_temp = min_temp;
    }

    public Integer getMax_temp() {
        return max_temp;
    }

    public void setMax_temp(Integer max_temp) {
        this.max_temp = max_temp;
    }

    public Float getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(Float wind_speed) {
        this.wind_speed = wind_speed;
    }

    public Integer getWind_degrees() {
        return wind_degrees;
    }

    public void setWind_degrees(Integer wind_degrees) {
        this.wind_degrees = wind_degrees;
    }

    @Override
    public String toString() {   // Return the JSON
        return "{" +
                "\"cloud_pct\":" + cloud_pct +
                ", \"temp\":" + temp +
                ", \"feels_like\":" + feels_like +
                ", \"humidity\":" + humidity +
                ", \"min_temp\":" + min_temp +
                ", \"max_temp\":" + max_temp +
                ", \"wind_speed\":" + wind_speed +
                ", \"wind_degrees\":" + wind_degrees +
                '}';
    }
}
