package com.example.coffeecare;

public class User {
    private String chemicalname;
    private String description;
    private String industry;


    public User() {

    }

    public User(String chemicalname, String description, String industry) {
        this.chemicalname = chemicalname;
        this.description = description;
        this.industry = industry;
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
}
