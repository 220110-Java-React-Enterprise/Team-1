package com.revature.springbootdemo.DAOs;//package DAOs;

import com.revature.springbootdemo.Annotations.Entity;
import com.revature.springbootdemo.Annotations.PrimaryKey;
import com.revature.springbootdemo.Annotations.Property;

@Entity(tableName = "Location")
public class LocationDAO {

    //Fields
    @PrimaryKey( autoIncrement =  true)
    @Property(fieldName = "Location_ID")
    private Integer LocationID = 9;

    @Property(fieldName = "city")
    private String city;

    @Property(fieldName = "country")
    private String country;

    @Property(fieldName = "state")
    private String state;


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



    public LocationDAO()
    {

    }



    public LocationDAO(int Location_ID, String city, String country, String state)
    {
        setLocationID(Location_ID);
        setCity(city);
        setCountry(country);
        setState(state);
    }

}

