package com.ISA.OnlyBunsBackend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RabbitCareLocationDTO {

    private Integer id;
    private String name;
    private String city;
    private String country;
    private double latitude;
    private double longitude;

    public RabbitCareLocationDTO() {}

    // Constructor with @JsonCreator and @JsonProperty for deserialization
    @JsonCreator
    public RabbitCareLocationDTO(
            @JsonProperty("id") Integer id,
            @JsonProperty("name") String name,
            @JsonProperty("city") String city,
            @JsonProperty("country") String country,
            @JsonProperty("latitude") double latitude,
            @JsonProperty("longitude") double longitude
    ) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getteri i setteri

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
