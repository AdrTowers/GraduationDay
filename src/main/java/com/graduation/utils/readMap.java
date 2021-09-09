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
            setupMapPlayerAndItemLocations(mapCode);

            newMap = String.format(map, mapCode.get("Gym"), mapCode.get("Geography"),
                    mapCode.get("Player") ,mapCode.get("Hallway"), mapCode.get("Grade"),
                    mapCode.get("Maths"), mapCode.get("Cafeteria"), mapCode.get("History"),
                    mapCode.get("Credit"), mapCode.get("Computers"));

        }
        else if (Player.getGrade() == Grade.SOPHOMORE){
            setupMapPlayerAndItemLocations(mapCode);

            newMap = String.format(map, mapCode.get("Gym"), mapCode.get("Geography"),
                    mapCode.get("Player"), mapCode.get("History"),mapCode.get("Hallway"),
                    mapCode.get("Credit"), mapCode.get("Cafeteria"), mapCode.get("Maths"),
                    mapCode.get("Computers"));
        }
        else if (Player.getGrade() == Grade.JUNIOR){
            setupMapPlayerAndItemLocations(mapCode);

            newMap = String.format(map, mapCode.get("Computers"), mapCode.get("Player"),
                    mapCode.get("Hallway"),mapCode.get("Gym"), mapCode.get("Credit"),
                    mapCode.get("Cafeteria"), mapCode.get("Maths"), mapCode.get("Geography"),
                    mapCode.get("History"));
        }
        else if (Player.getGrade() == Grade.SENIOR){
            setupMapPlayerAndItemLocations(mapCode);

            newMap = String.format(map, mapCode.get("Computers"), mapCode.get("Player"),
                    mapCode.get("History"), mapCode.get("Gym"), mapCode.get("Grade"),
                    mapCode.get("Hallway"), mapCode.get("Geography"), mapCode.get("Credit"),
                    mapCode.get("Cafeteria"), mapCode.get("Maths"));
        }
        return newMap;
    }

    private static void setupMapPlayerAndItemLocations(HashMap<String, String> mapCode) {
        map = importTXT("Banner/map-" + GameClient.getPlayer().getGrade().toString() + ".txt");
        mapCode.replace(GameClient.getPlayer().getLocation(), "\uD83C\uDF93");
        mapCode.replace("Cafeteria", "\uD83C\uDF92");
        mapCode.replace("Hallway", "\uD83C\uDF92");
        mapCode.replace("Gym", "\uD83C\uDF92");

        if (GameClient.getPlayer().getLocation().equals("Cafeteria")) {
            mapCode.replace("Cafeteria", "\uD83C\uDF93");
        } else if (GameClient.getPlayer().getLocation().equals("Hallway")) {
            mapCode.replace("Cafeteria", "\uD83C\uDF93");
        } else if (GameClient.getPlayer().getLocation().equals("Gym")) {
            mapCode.replace("Cafeteria", "\uD83C\uDF93");
        }
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