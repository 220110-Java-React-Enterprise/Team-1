package com.revature.springbootdemo.beans.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResultModel {
    String country;

    public SearchResultModel() {
    }

    public SearchResultModel(String name) {
        this.country = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
