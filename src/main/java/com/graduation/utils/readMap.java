package com.graduation.utils;

import com.graduation.client.GameClient;
import com.graduation.elements.Player;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class readMap {

    private static String map;

    public static String convertedMap() {
        String newMap = null;
        HashMap<String, String> mapCode = new HashMap<>();
        mapCode.put("Gym", "  ");
        mapCode.put("Geography", "  ");
        mapCode.put("Player", GameClient.getPlayer().getName());
        mapCode.put("Hallway", "  ");
        mapCode.put("Grade", String.valueOf(Player.getGrade()));
        mapCode.put("Maths", "  ");
        mapCode.put("Credit", String.valueOf(GameClient.getPlayer().getCredit()));
        mapCode.put("Cafeteria", "  ");
        mapCode.put("History", "  ");
        mapCode.put("Computers", "  ");

        if (Player.getGrade() == Grade.FRESHMAN) {
            map = importTXT("Banner/map-" + GameClient.getPlayer().getGrade().toString() + ".txt");
            mapCode.replace(GameClient.getPlayer().getLocation(), "\uD83C\uDF93");

            newMap = String.format(map, mapCode.get("Gym"), mapCode.get("Geography"),
                    mapCode.get("Player") ,mapCode.get("Hallway"), mapCode.get("Grade"),
                    mapCode.get("Maths"), mapCode.get("Cafeteria"), mapCode.get("History"),
                    mapCode.get("Credit"), mapCode.get("Computers"));

        } else if (Player.getGrade() == Grade.SOPHOMORE) {
            map = importTXT("Banner/map-" + GameClient.getPlayer().getGrade().toString() + ".txt");
            mapCode.replace(GameClient.getPlayer().getLocation(), "\uD83C\uDF93");
            newMap = String.format(map, mapCode.get("Gym"), mapCode.get("Geography"),
                    mapCode.get("Player"), mapCode.get("History"), mapCode.get("Hallway"),
                    mapCode.get("Grade"), mapCode.get("Cafeteria"), mapCode.get("Maths"),
                    mapCode.get("Credit"), mapCode.get("Computers"));
        } else if (Player.getGrade() == Grade.JUNIOR) {
            map = importTXT("Banner/map-" + GameClient.getPlayer().getGrade().toString() + ".txt");
            mapCode.replace(GameClient.getPlayer().getLocation(), "\uD83C\uDF93");
            newMap = String.format(map, mapCode.get("Computers"), mapCode.get("Player"),
                    mapCode.get("Hallway"), mapCode.get("Gym"), mapCode.get("Grade"),
                    mapCode.get("Cafeteria"), mapCode.get("Credit"), mapCode.get("Maths"),
                    mapCode.get("Geography"), mapCode.get("History"));
        } else if (Player.getGrade() == Grade.SENIOR) {
            map = importTXT("Banner/map-" + GameClient.getPlayer().getGrade().toString() + ".txt");
            mapCode.replace(GameClient.getPlayer().getLocation(), "\uD83C\uDF93");
            newMap = String.format(map, mapCode.get("Computers"), mapCode.get("Player"),
                    mapCode.get("History"), mapCode.get("Gym"), mapCode.get("Grade"),
                    mapCode.get("Hallway"), mapCode.get("Geography"), mapCode.get("Credit"),
                    mapCode.get("Cafeteria"), mapCode.get("Maths"));
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
}