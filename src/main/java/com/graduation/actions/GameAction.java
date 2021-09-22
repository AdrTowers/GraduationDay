package com.graduation.actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduation.client.GameClient;
import com.graduation.utils.Prompter;
import com.graduation.utils.readMap;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class GameAction {
    private static Scanner action = new Scanner(System.in);
    private static File gameActionJson = new File("Banner/gameaction.json");
    private static ObjectMapper mapper = new ObjectMapper();
    static GameActionParser textparser;

    static {
        try {
            textparser = mapper.readValue(gameActionJson, GameActionParser.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void getAction() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        Prompter.clearScreen();
        //System.out.println(GameClient.getPlayer());
        System.out.println(readMap.convertedMap());
        String move = GameClient.getPrompter().prompt(textparser.getEnterMove());
        if (move == null || move.isBlank()) {
            System.out.println(textparser.getInvalidMove());
            getAction();
        } else {
            String[] moveArray = move.toLowerCase().split(" ");

            // ArrayIndexOutOfBoundsException
            switch (moveArray[0]) {
                case "go":
                    if (moveArray[1].equals(textparser.getNorth()) || moveArray[1].equals(textparser.getSouth()) || moveArray[1].equals(textparser.getEast()) || moveArray[1].equals(textparser.getWest())) {
                        GameClient.nextLocation(moveArray[1]);
                    } else {
                        System.out.println(textparser.getValidDirection());
                        getAction();
                    }
                    break;
                case "get":
                    //Calls the method to initiate the item sequence
                    try {
                        GameClient.getLevelDetails(textparser.getItemLevel());
                    } catch (NullPointerException e){
                        System.out.println(textparser.getItem());
                    }

                    break;
                default:
                    System.out.println(textparser.getInvalidMove());
                    getAction();
                    break;
            }
        }


    }
}