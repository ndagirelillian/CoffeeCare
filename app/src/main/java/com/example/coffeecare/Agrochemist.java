package com.example.coffeecare;

public class Agrochemist {
    private String agrochemistId;
    private String name;
    private String email;
    private double latitude;
    private double longitude;

    public Agrochemist() {
        // Default constructor required for Firebase Realtime Database
    }

    public Agrochemist(String agrochemistId, String name, String email, double latitude, double longitude) {
        this.agrochemistId = agrochemistId;
        this.name = name;
        this.email = email;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getAgrochemistId() {
        return agrochemistId;
    }

    public void setAgrochemistId(String agrochemistId) {
        this.agrochemistId = agrochemistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
