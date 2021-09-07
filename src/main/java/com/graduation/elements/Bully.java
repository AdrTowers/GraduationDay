package com.graduation.elements;

public class Bully {
    private static String name;
    private static int health;
    private static boolean presence;

    public Bully(String name, int health, boolean presence){
        this.name = name;
        this.health = health;
        this.presence = presence;
    }
    public static String getName() {
        return name;
    }

    public static int getHealth() {
        return health;
    }

    public static void setHealth(int health) {
        Bully.health = health;
    }

    public boolean getPresence() {
        return presence;
    }

    public static void setPresence(boolean newPresence) {
         presence = newPresence;
    }
}