package com.example.coffeecare;

public class Agrochemist {
    private String chemicalname;
    private String description;
    private String industry;
    private String agrochemistId;
    private String name;
    private String email;
    private double latitude;
    private double longitude;

    public Agrochemist(String agrochemistId, String name, String email, double latitude, double longitude) {
        // Default constructor required for Firebase Realtime Database
    }

    public Agrochemist(String chemicalname, String description, String industry,
                       String agrochemistId, String name, String email,
                       double latitude, double longitude) {
        this.chemicalname = chemicalname;
        this.description = description;
        this.industry = industry;
        this.agrochemistId = agrochemistId;
        this.name = name;
        this.email = email;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Agrochemist(String agrochemicalname, String agrodescription, String agroindustry) {
    }

    public String getChemicalname() {
        return chemicalname;
    }

    public String getDescription() {
        return description;
    }

    public String getIndustry() {
        return industry;
    }

    public String getAgrochemistId() {
        return agrochemistId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setChemicalname(String chemicalname) {
        this.chemicalname = chemicalname;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public void setAgrochemistId(String agrochemistId) {
        this.agrochemistId = agrochemistId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}


//package com.example.coffeecare;
//
//
//public class Agrochemist {
//    private String chemicalname;
//    private String description;
//    private String industry;
//    private String agrochemistId;
//    private String name;
//    private String email;
//    private double latitude;
//    private double longitude;
//
//
//    public Agrochemist(String agrochemistId, String name, String email) {
//    }
//
//    public Agrochemist(String chemicalname, String description, String industry,
//                       String agrochemistId, String name, String email, double latitude, double longitude) {
//        this.chemicalname = chemicalname;
//        this.description = description;
//        this.industry = industry;
//        this.agrochemistId = agrochemistId;
//        this.name = name;
//        this.email = email;
//        this.latitude = latitude;
//        this.longitude = longitude;
//    }
//
//    public Agrochemist(String agrochemistId, String name, String email, double latitude, double longitude) {
//    }
//
//    public Agrochemist(String name, String email, double latitude, double longitude) {
//    }
//
//
//    public String getChemicalname() {
//        return chemicalname;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public String getIndustry() {
//        return industry;
//    }
//
//    public String getAgrochemistId() {
//        return agrochemistId;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public double getLatitude() {
//        return latitude;
//    }
//
//    public double getLongitude() {
//        return longitude;
//    }
//
//    public void setChemicalname(String chemicalname) {
//        this.chemicalname = chemicalname;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public void setIndustry(String industry) {
//        this.industry = industry;
//    }
//
//    public void setAgrochemistId(String agrochemistId) {
//        this.agrochemistId = agrochemistId;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public void setLatitude(double latitude) {
//        this.latitude = latitude;
//    }
//
//    public void setLongitude(double longitude) {
//        this.longitude = longitude;
//    }
//}