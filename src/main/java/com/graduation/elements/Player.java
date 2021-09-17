package com.graduation.elements;

import com.graduation.utils.Grade;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private  static Player instance = null;
    private  Grade grade;
    private  String name;
    private  double credit;
    private  String location;
    private  int health;
    private  List<String> inventory = new ArrayList<>();
    private  List<String> subjectTaken = new ArrayList<>();

    // Constructor
    private Player(){
    }
    // Singleton setup
    public static Player getInstance(){
        if ( instance == null){
            instance = new Player();
        }
        return instance;
    }

    public int getTotalSubject() {
        return totalSubject;
    }

    public void setTotalSubject(int totalSubject) {
        this.totalSubject = totalSubject;
    }

    private int totalSubject = 0;

    public  List<String> getSubjectTaken() {
        return subjectTaken;
    }

    public  void setSubjectTaken(List<String> newSubjectTaken) {
        subjectTaken = newSubjectTaken;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public void setInventory(List<String> inventory) {
        this.inventory = inventory;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public  int setHealth(int health) {
        return this.health = health;
    }

    public  int getHealth() {
        return health;
    }

    public  Grade getGrade() {
        return grade;
    }

    public  String getLocation() {
        return location;
    }

    public  double getCredit() {
        return credit;
    }

    public void setName(String name) {
        this.name = name;
    }

    public  String getName() {
        return name;
    }

    public  List<String> getInventory() {
        return inventory;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                " name= " + getName() +
                " , health= " + health +
                " , credit= " + getCredit() +
                '}';
    }
}
