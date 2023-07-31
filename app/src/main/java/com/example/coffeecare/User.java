package com.example.coffeecare;

public class User {
    private String chemicalname;
    private String description;
    private String industry;
    private  String userid;



    public User(String chemicalname, String description, String industry, String userid) {
        this.chemicalname = chemicalname;
        this.description = description;
        this.industry = industry;
        this.userid = userid;
    }

    public User() {
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

    public String getUserid() {
        return userid;
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

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
