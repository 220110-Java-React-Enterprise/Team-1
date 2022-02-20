package com.revature.springbootdemo.beans.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CityAPIModel {
    private String name;
    private String latitude;
    private String longitude;
    private String country;
    private String population;
    private String is_capital;

    public CityAPIModel() {
    }

    public CityAPIModel(String name, String latitude, String longitude, String country, String population, String is_capital) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.population = population;
        this.is_capital = is_capital;
    }

    @Override
    public String toString() {
        return "LocationAPIModel{" +
                "name='" + name + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", country='" + country + '\'' +
                ", population='" + population + '\'' +
                ", is_capital='" + is_capital + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude() {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getIs_capital() {
        return is_capital;
    }

    public void setIs_capital(String is_capital) {
        this.is_capital = is_capital;
    }
}
