package com.graduation.elements;

public class Bully {
    private static Bully instance = null;
    private String name;
    private int health;
    private boolean presence;

    private Bully() {

    }

    public static Bully getInstance() {
        if (instance == null) {
            instance = new Bully();
        }
        return instance;
    }

    public String getName() {
        return name;
    }

    public String setName(String name) {
        return this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public int setHealth(int health) {
        return this.health = health;
    }

    public boolean getPresence() {
        return presence;
    }

    public void setPresence(boolean newPresence) {
         presence = newPresence;
    }
}