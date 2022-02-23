package com.revature.springbootdemo.beans.models;


import javax.persistence.*;

@Entity(name = "Location_model")
public class LocationModel {

    //  Fields
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



    public LocationModel() {      //  no arg Constructor
    }

    public LocationModel(int Location_ID, String city, String country, String state) {
        setLocationID(Location_ID);
        setCity(city);
        setCountry(country);
        setState(state);
    }

    //  Getters and Setters

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

}

