package com.revature.springbootdemo.beans.models;//package DAOs;


import javax.persistence.*;

@Entity(name = "location_model")
public class LocationModel {

    //Fields
    @Id
    @Column(name = "location_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer LocationID;


    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "state")
    private String state;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "population")
    private int population;

    @Column(name = "is_capital")
    private boolean is_capital;


    //Getters and Setters
    public Integer getLocationID() {
        return LocationID;
    }

    public void setLocationID(Integer locationID) {
        LocationID = locationID;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }



    public LocationModel()
    {

    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public boolean isIs_capital() {
        return is_capital;
    }

    public void setIs_capital(boolean is_capital) {
        this.is_capital = is_capital;
    }

    public LocationModel(String city, String country, String state,
                         double latitude, double longitude, int population, boolean is_capital)
    {
        this.LocationID = LocationID;
        this.city = city;
        this.country = country;
        this.state = state;
        this.latitude = latitude;
        this.longitude = longitude;
        this.population = population;
        this.is_capital = is_capital;
    }

}

