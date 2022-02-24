package com.revature.springbootdemo.beans.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SessionModel {

    String email;
    String password;
    Integer location_id;

    public SessionModel() {
    }

    public SessionModel(String email, String password, Integer ID, Integer location_id) {
        this.email = email;
        this.password = password;
        this.location_id = location_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public Integer getLocation_id() {
        return location_id;
    }

    public void setLocation_id(Integer location_id) {
        this.location_id = location_id;
    }

    @Override
    public String toString() {
        return "SessionModel{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", location_id=" + location_id +
                '}';
    }
}
