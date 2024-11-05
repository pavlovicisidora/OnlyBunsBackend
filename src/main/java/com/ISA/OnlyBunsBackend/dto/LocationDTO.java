package com.ISA.OnlyBunsBackend.dto;

import com.ISA.OnlyBunsBackend.model.Location;

public class LocationDTO {
    private int id;
    private double longitude;
    private double latitude;
    private String country;
    private String city;

    public LocationDTO() {
    }

    public LocationDTO(int id, double longitude, double latitude, String country, String city) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.country = country;
        this.city = city;
    }

    public LocationDTO(Location location) {
        id = location.getId();
        longitude = location.getLongitude();
        latitude = location.getLatitude();
        country = location.getCountry();
        city = location.getCity();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
