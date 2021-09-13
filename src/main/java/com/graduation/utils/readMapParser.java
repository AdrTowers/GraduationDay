package com.graduation.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
class readMapParser {

    private String Gym;
    private String Geography;
    private String Player;
    private String Hallway;
    private String Grade;
    private String Maths;
    private String Credit;
    private String Cafeteria;
    private String History;
    private String Computers;
    private String Health;

    public String getGym() {
        return Gym;
    }

    public String getGeography() {
        return Geography;
    }

    public String getPlayer() {
        return Player;
    }

    public String getHallway() {
        return Hallway;
    }

    public String getGrade() {
        return Grade;
    }

    public String getMaths() {
        return Maths;
    }

    public String getCredit() {
        return Credit;
    }

    public String getCafeteria() {
        return Cafeteria;
    }

    public String getHistory() {
        return History;
    }

    public String getComputers() {
        return Computers;
    }

    public String getHealth() {
        return Health;
    }
}