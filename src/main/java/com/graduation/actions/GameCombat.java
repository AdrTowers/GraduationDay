package com.graduation.actions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduation.client.GameClient;
import com.graduation.elements.Bully;
import com.graduation.elements.Player;
import com.graduation.utils.Prompter;
import com.graduation.utils.readMap;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class GameCombat {

    private static Bully bully = Bully.getInstance();
    private static Player player = Player.getInstance();
    private static Scanner action = new Scanner(System.in);
    private static final ObjectMapper mapper = new ObjectMapper();
    private static int bullyHitPoints = (int)(Math.random() * 35);
    private static JsonNode data;

    private static File gameCombatJson = new File("Banner/gamecombat.json");
    private static ObjectMapper mapper1 = new ObjectMapper();
    static GameCombatParser textparser;

    static {
        try {
            textparser = mapper1.readValue(gameCombatJson, GameCombatParser.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void initializeCombatScene() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        Prompter.clearScreen();
        //System.out.println(GameClient.getPlayer());
        System.out.println(readMap.convertedMap());
        System.out.println(textparser.getStars());
        System.out.println(bully.getName() + textparser.getSpotted());
        fight();

    }

    public static void fight() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if (player.getHealth() <= 0) {
            //Step 1: You get a report card stolen
            String reportCard = player.getSubjectTaken().remove(0);             //remove first entry, store the string for later
            player.setSubjectTaken(player.getSubjectTaken());                         //pass back the List to the method
            //Step 2: Dialogue for the user
            System.out.println(textparser.getStole()+ reportCard + textparser.getAgain());
            //Step 3: Remove the bully for this level
            bully.setPresence(false);

        }else{
            Prompter.clearScreen();
            //System.out.println(GameClient.getPlayer());
            System.out.println(readMap.convertedMap());
            System.out.println(textparser.getStars());

            System.out.println(textparser.getHealthPoints() + player.getHealth());
            System.out.println(textparser.getBullyHealth() + bully.getHealth());
            System.out.println(textparser.getStars());
            System.out.println(textparser.getCommands());
            System.out.println(textparser.getStars());
            System.out.println(player.getName() + textparser.getBookbag());
            for (String item : player.getInventory()) {
                System.out.println(item);
            }
            System.out.println(textparser.getStars());

            System.out.println(textparser.getEnterMove());
            String move = action.nextLine();
            String[] moveArray = move.toLowerCase().split(" ");

            switch (moveArray[0]) {
                case "run":
                    //Step 1: RNG to find out if you can run successfully -- you have a 12% chance of NOT escaping
                    if((int)(Math.random() * 100) >= 12){
                        System.out.println(textparser.getRunAway());
                        GameAction.getAction();
                    }else{
                        //Massive damage if caught trying to run
                        int rightHook = player.getHealth() - 50 ;
                        player.setHealth(rightHook);
                        System.out.println(textparser.getNotLucky());
                        fight();
                    }
                    break;
                case "fight":
                    //Step 1: RNG for number with just your fists && Negate the points from the Bully's health-- allows for a one hit kill
                    int kick = (int)(Math.random() * 100);
                    bully.setHealth(bully.getHealth() - kick);
                    //Step 2: Conditional dialogue
                    if(kick > 80) {
                        System.out.println(textparser.getKicked() + kick + textparser.getDamage());
                    }else if(kick > 50){
                        System.out.println(textparser.getHurt() + kick + textparser.getToTheBully());
                    }else {
                        System.out.println(textparser.getWow() + kick + textparser.getToTheBully());
                    }
                    //Step 3: Recursion if needed
                    if(bully.getHealth() <= 0){
                        System.out.println(textparser.getDefeated() + bully.getName() + textparser.getHooray());
                        GameAction.getAction();
                    }else{
                        //Bully's turn;
                        bullyAttack();
                        fight();
                    }
                    break;
                case "use":
                    if (moveArray.length == 2 ){
                        try{
                            if (player.getInventory().contains(moveArray[1])) {
                                //Step 1: remove the item
                                player.getInventory().remove(moveArray[1]);
                                //Step 2: RNG for result -- 90% of a good ending, 10% chance the item has no effect
                                if((Math.random() * 100) > 10){
                                    //Step 2a: Print the item desc
                                    getItemDesc(moveArray[1]);
                                    //***Bully defeated*
                                    bully.setHealth(0);
                                    //Toggle the bully presence var
                                    bully.setPresence(false);
                                    //Get the next action
                                    GameAction.getAction();
                                }else{
                                    //Step 2b: Bully was unaffected by the item
                                    System.out.println(bully.getName() + textparser.getUnaffected() + moveArray[1]);
                                    // Bully HIT3 you
                                    bullyAttack();
                                }
                                //Step 4: Snap to the Bully response if still health points left
                                if(bully.getHealth() > 0){
                                    //System.out.println(textparser.getHit());
                                    bullyAttack();
                                }
                            } else {
                                System.out.println(textparser.getYouDont() + moveArray[1] + textparser.getInYourBag());
                                fight();
                            }
                        }catch(ArrayIndexOutOfBoundsException e){
                            System.out.println(textparser.getNotYourBag());
                            fight();
                        }
                    } else if (moveArray.length == 3){
                        String moveArr = moveArray[1] + " " + moveArray[2];
                        try{
                            if (player.getInventory().contains(moveArr)) {
                                //Step 1: remove the item
                                player.getInventory().remove(moveArr);
                                //Step 2: RNG for result -- 90% of a good ending, 10% chance the item has no effect
                                if((Math.random() * 100) > 10){
                                    //Step 2a: Print the item desc
                                    getItemDesc(moveArr);
                                    //***Bully defeated*
                                    bully.setHealth(0);
                                    //Toggle the bully presence var
                                    bully.setPresence(false);
                                    //Get the next action
                                    GameAction.getAction();
                                }else{
                                    //Step 2b: Bully was unaffected by the item
                                    System.out.println(bully.getName() + textparser.getUnaffected() + moveArray[1]);
                                }
                                //Step 4: Snap to the Bully response if still health points left
                                if(bully.getHealth() > 0){
                                    System.out.println(textparser.getHit());
                                    bullyAttack();
                                }
                            } else {
                                System.out.println(textparser.getYouDont() + moveArr + textparser.getInYourBag());
                                fight();
                            }
                        }catch(ArrayIndexOutOfBoundsException e){
                            System.out.println(textparser.getNotYourBag());
                            fight();
                        }
                    }
                    break;
                default:
                    System.out.println(textparser.getInvalidMove());
                    fight();
                    break;
            }
        }
    }

    private static void getItemDesc(String item) {
        try {
            data = mapper.readTree(Files.readAllBytes(Paths.get("Banner/items.json")));
            JsonNode filteredData = data.get(String.valueOf(item));
            System.out.println(filteredData.asText());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void bullyAttack(){
        //Step 1: Set the player health minus the bully strike
        int punch = player.getHealth() - bullyHitPoints ;
        player.setHealth(punch);
        //Step 2: Conditional dialogue
        if(bullyHitPoints > 50) {
            System.out.println(textparser.getMassiveDamage() + bullyHitPoints + textparser.getToYou());
        }else if(bullyHitPoints > 25){
            System.out.println(textparser.getPunch() + bullyHitPoints + textparser.getToYou());
        }else{
            System.out.println(textparser.getDidntHurtMuch() + bullyHitPoints + textparser.getToYou());
        }
    }
}