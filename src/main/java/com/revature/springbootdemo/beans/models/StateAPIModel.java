package com.revature.springbootdemo.beans.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StateAPIModel {
    String state;
    String name;
    String country;
    String latitude;
    String longitude;

    public StateAPIModel() {
    }

    public StateAPIModel(String state, String name, String country, String latitude, String longitude) {
        this.state = state;
        this.name = name;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "StateAPIModel{" +
                "state='" + state + '\'' +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
