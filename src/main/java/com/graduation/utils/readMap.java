package com.graduation.utils;

import com.graduation.client.GameClient;
import com.graduation.elements.Player;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class readMap {

    private static String map;
    private static Player player = Player.getInstance();
    public static String convertedMap() {
        String newMap = null;
        String healthHearts = null;
        HashMap<String, String> mapCode = new HashMap<>();
        mapCode.put("Gym", "  ");
        mapCode.put("Geography", "  ");
        mapCode.put("Player", GameClient.getPlayer().getName());
        mapCode.put("Mythology", "  ");
        mapCode.put("Hallway", "  ");
        mapCode.put("Grade", String.valueOf(player.getGrade()));
        mapCode.put("Math", "  ");
        mapCode.put("Credit", String.valueOf(GameClient.getPlayer().getCredit()));
        mapCode.put("Cafeteria", "  ");
        mapCode.put("History", "  ");
        mapCode.put("Credit", String.valueOf(GameClient.getPlayer().getCredit()));
        mapCode.put("Computers", "  ");


        if (player.getGrade() == Grade.FRESHMAN) {
            healthHearts = determineHealthHearts();
            mapCode.put("Health", healthHearts);
            map = importTXT("Banner/map-" + GameClient.getPlayer().getGrade().toString() + ".txt");
            mapCode.replace(GameClient.getPlayer().getLocation(), "\uD83C\uDF93");

            newMap = String.format(map, mapCode.get("Gym"), mapCode.get("Geography"),
                    mapCode.get("Player"), mapCode.get("Mythology"), mapCode.get("Hallway"), mapCode.get("Grade"),
                    mapCode.get("Math"), mapCode.get("Cafeteria"), mapCode.get("History"),
                    mapCode.get("Credit"), mapCode.get("Computers"), mapCode.get("Health"));

        } else if (player.getGrade() == Grade.SOPHOMORE) {
            healthHearts = determineHealthHearts();
            mapCode.put("Health", healthHearts);
            map = importTXT("Banner/map-" + GameClient.getPlayer().getGrade().toString() + ".txt");
            mapCode.replace(GameClient.getPlayer().getLocation(), "\uD83C\uDF93");
            newMap = String.format(map, mapCode.get("Gym"), mapCode.get("Geography"),
                    mapCode.get("Player"), mapCode.get("History"), mapCode.get("Hallway"),
                    mapCode.get("Grade"), mapCode.get("Cafeteria"), mapCode.get("Math"),
                    mapCode.get("Credit"), mapCode.get("Mythology"), mapCode.get("Computers"),
                    mapCode.get("Health"));
        } else if (player.getGrade() == Grade.JUNIOR) {
            healthHearts = determineHealthHearts();
            mapCode.put("Health", healthHearts);
            map = importTXT("Banner/map-" + GameClient.getPlayer().getGrade().toString() + ".txt");
            mapCode.replace(GameClient.getPlayer().getLocation(), "\uD83C\uDF93");
            newMap = String.format(map, mapCode.get("Mythology"), mapCode.get("Computers"), mapCode.get("Player"),
                    mapCode.get("Hallway"), mapCode.get("Gym"), mapCode.get("Grade"),
                    mapCode.get("Cafeteria"), mapCode.get("Credit"), mapCode.get("Math"),
                    mapCode.get("Health"), mapCode.get("Geography"), mapCode.get("History"));
        } else if (player.getGrade() == Grade.SENIOR) {
            healthHearts = determineHealthHearts();
            mapCode.put("Health", healthHearts);
            map = importTXT("Banner/map-" + GameClient.getPlayer().getGrade().toString() + ".txt");
            mapCode.replace(GameClient.getPlayer().getLocation(), "\uD83C\uDF93");
            newMap = String.format(map, mapCode.get("Computers"), mapCode.get("Player"),
                    mapCode.get("History"), mapCode.get("Gym"), mapCode.get("Grade"),
                    mapCode.get("Hallway"), mapCode.get("Geography"), mapCode.get("Credit"),
                    mapCode.get("Mythology"), mapCode.get("Cafeteria"), mapCode.get("Health"), mapCode.get("Math"));
        }

        return newMap;
    }

    public static String importTXT(String path) {
        String result = null;
        try {
            result = Files.readString(Path.of(path));
        } catch (IOException e) {
        }
        return result;
    }

    public static String determineHealthHearts() {
        String result = null;
        if(player.getHealth() >=67 && player.getHealth() <= 100) {
            result = "\u2665 \u2665 \u2665"; //3 full hearts
        }
        else if(player.getHealth() >=34 && player.getHealth() <= 66) {
            result = "\u2665 \u2665 \u2661"; //2 full hearts followed by one empty heart
        }
        else if(player.getHealth() >=1 && player.getHealth() <= 33) {
            result = "\u2665 \u2661 \u2661"; //1 full heart followed by two empty hearts
        }
        else {
            result = "\u2661 \u2661 \u2661"; //3 empty hearts
        }
        return result;
    }
}