package com.graduation.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduation.actions.GameAction;
import com.graduation.actions.GameCombat;
import com.graduation.elements.Bully;
import com.graduation.elements.Player;
import com.graduation.pointsystem.PointSystem;
import com.graduation.utils.Grade;
import com.graduation.utils.Prompter;
import com.graduation.utils.SoundEffects;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameClient {
    private static Prompter prompter;
    private static Player player;
    private static Bully bully;
    private static final ObjectMapper mapper = new ObjectMapper();
    private static JsonNode data;
    private static JsonNode prevRoom;
    private static final List<String> notSubject = new ArrayList<>(Arrays.asList("gym", "cafeteria", "hallway"));

    private File gameClientJson = new File("Banner/gameclient.json");
    private ObjectMapper mapper1 = new ObjectMapper();
    private static File staticGameClientJson = new File("Banner/gameclient.json");
    private static ObjectMapper mapperStatic = new ObjectMapper();
    GameClientParser textparser = mapper1.readValue(gameClientJson, GameClientParser.class);
    static GameClientParser staticParser;

    static {
        try {
            staticParser = mapperStatic.readValue(staticGameClientJson, GameClientParser.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GameClient(Prompter prompter) throws IOException {
        this.prompter = prompter;
    }
    public void initialize() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        setPlayer();
        setBully();

        //Step 1 -- Generate the location info from the json
        getLevelDetails("desc");

        //Step 2a -- Some conditional seeing if its is a subject
        if(player.getLocation().equals("cafeteria") || player.getLocation().equals("gym") || player.getLocation().equals("hallway")){
            continueJourney(false);
        }else{
            //Step 2b -- Call method to initialize the question sequence
            PointSystem.teacherQuestions(player.getLocation().toLowerCase(), player.getGrade());
        }


    }

    public static void nextLocation(String location) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        //Grab the previous and read the location according to direction within it's JSON properties
        if(!player.getGrade().equals(Grade.GRADUATE)) {
            try{
                String nextLoc = prevRoom.get(location).textValue();
                System.out.println(nextLoc);
                player.setLocation(nextLoc);
                getLevelDetails("desc");
                updateHealthWhenInGym(nextLoc);


                //Determine if it's a subject room
                if(!notSubject.contains(nextLoc.toLowerCase())){
                    PointSystem.teacherQuestions(player.getLocation().toLowerCase(), player.getGrade());
                }else {
                    //Step 1: random number generator to see if a bully will engage in combat
                    int combat = (int) (Math.random() * 100);
                    //You have a 50% chance of a bully not being there.
                    if (combat >= 60) {
                        System.out.println(staticParser.getUhoh() + bully.getName() + staticParser.getIshere());
                        //Engage in combat
                        bully.setHealth(100);
                        GameCombat.initializeCombatScene();
                    } else {
                        continueJourney(false);
                    }
                }
                //Catch if the direction is null
            }catch(NullPointerException e){
                System.out.println(staticParser.getDifferent());
                GameAction.getAction();
            }
        }

    }

    /**
     * Updates Health if the player is located in the gym
     * @param location the current location of the player
     */
    public static void updateHealthWhenInGym(String location) {
        if(location.equals("Gym")) {
            System.out.println("Your health has now increased!");
            player.setHealth(100);
        }
    }

    public static void getLevelDetails(String key) throws LineUnavailableException, UnsupportedAudioFileException {
        try{
            data = mapper.readTree(Files.readAllBytes(Paths.get("Banner/rooms.json")));
            prevRoom = getLastRoom(data, player.getLocation(), player.getGrade());
            JsonNode filteredData = getDetails(data, player.getLocation(), player.getGrade(), key);
            if(key.equals("item")){
                //If the room does have an item check if player already has it!
                if(player.getInventory().contains(filteredData.asText())){
                    //View to tell the user that they grabbed the room item already
                    System.out.println(staticParser.getNoitems() + filteredData + "\n");
                    continueJourney(false);
                }else{
                    //Method to add the item to the player's Book Bag
                    List<String> items = player.getInventory();
                    items.add(filteredData.textValue());
                    System.out.println(staticParser.getSuccessfull() + filteredData + staticParser.getBackpack());
                    continueJourney(false);
                }
            }else{
                System.out.println(filteredData);
            }
        }catch(IOException e){

            System.out.println(e);
        }
    }

    //Method to initialize the action to move
    public static void continueJourney(boolean val) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        //Have a conditional that switch when it's a new level
        if(player.getGrade().equals(Grade.GRADUATE)) {
            playerDidGraduate();
        } else {
            if(val){
                getLevelDetails("desc");
                PointSystem.teacherQuestions(player.getLocation().toLowerCase(), player.getGrade());
            }else{
                System.out.println(staticParser.getNextmove());
                GameAction.getAction();
            }
        }

    }

    private static void playerDidGraduate() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        System.out.println("\n### Grade Report ###");
        System.out.println("GPA Year");
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("report_card.txt"));
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("See you next year in...COLLEGE DAY!");
    }

    //Gets the description of the current room
    private static JsonNode getDetails(JsonNode node, String location, Grade grade, String key) {
         return node.get(String.valueOf(grade)).get(location).get(key);
    }

    //Assigns value to the prevRoom. Assists with keeping track of the location of player
    private static JsonNode getLastRoom(JsonNode node, String location, Grade grade) {
         return node.get(String.valueOf(grade)).get(location);
    }

    public static String getFirstLocation(){
        try{
            //Step 1: Read our JSON file
            data = mapper.readTree(Files.readAllBytes(Paths.get("Banner/rooms.json")));
            //Step 2: Access to my level
            String node = String.valueOf(data.get(String.valueOf(player.getGrade())));
            //Step 3: Spilt to get my location string
            String strNew = node.replace("{\"", "");
            String[] arrOfStr = strNew.split("\"", 2);
            //Step 4: Send back the first part which is the init location for the level
           return arrOfStr[0];
        }catch(IOException e){
            System.out.println(staticParser.getWrong() + e);
            return "Computers"; //default value
        }
    }

    //Initialize the bully
    public void setBully() {
        bully = Bully.getInstance();
        while (true){
            String bullyName = prompter.prompt(textparser.getPlease(), textparser.getHole());
            try{
                if (bullyName == null || bullyName.isBlank()){
                    System.out.println(textparser.getNameError());
                } else {
                    bully.setName(bullyName);
                    System.out.println(textparser.getBullyName() + bullyName + "\n");
                    break;
                }
            } catch (StringIndexOutOfBoundsException e){
                System.out.println(textparser.getNameError());
            }
        }
        bully.setHealth(100);
        bully.setPresence(true);
    }

    //Initialize the player as a FRESHMAN aka first level
    public void setPlayer() {
        player = Player.getInstance();
        while (true){
            String userName = prompter.prompt(textparser.getEntername(), textparser.getTrashcan());
            try{
                if (userName == null || userName.isBlank()){
                    System.out.println(textparser.getNameError());
                } else {
                    player.setName(userName);
                    System.out.println(textparser.getYourName() + userName + "\n");
                    break;
                }
            } catch (StringIndexOutOfBoundsException e){
                System.out.println(textparser.getNameError());
            }
        }
        player.setCredit(0);
        player.setHealth(100);
        player.setGrade(Grade.FRESHMAN);
        player.setLocation("Computers");
    }

    public static Player getPlayer() {
        return player;
    }

    public static Prompter getPrompter() {
        return prompter;
    }
}
