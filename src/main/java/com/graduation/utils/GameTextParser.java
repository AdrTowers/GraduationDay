package com.graduation.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduation.client.GameClient;
import com.graduation.elements.Player;
import com.graduation.pointsystem.PointSystem;
import com.graduation.pointsystem.Question;
import org.jsoup.Jsoup;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Random;

import com.graduation.view.ViewWindow;

public class GameTextParser {

    private String inputText;
    private ViewWindow viewWindow;
    private File prompterJson = new File("Banner/prompter.json");
    private ObjectMapper mapper = new ObjectMapper();
    private GameClient gameClient;
    PrompterParser textparser = mapper.readValue(prompterJson, PrompterParser.class);


    // Constructor
    public GameTextParser() throws IOException {
    }

    public void init(){
        viewWindow = ViewWindow.getInstance();
    }
    // Setters and Getters
    public String getInputText() {
        return inputText;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    // Business Methods
    public String parseText(String text) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        init();
        String response;

        response = text;
        if (response.matches("s")) {
            //add function to show player status
            System.out.println(GameClient.getPlayer().getGrade().toString());
            System.out.println(readMap.convertedMap());
            String subjectList = textparser.getSubejects();
            for (String subject : Player.getSubjectTaken()) {
                subjectList += subject + "; ";
            }
            System.out.println(subjectList);
            System.out.println(" ");
            //blank line
            //give player a helpful message

            //display the current question to remind the user to answer it
            if (Question.getCurrentQuestion() != null) {
                viewWindow.updateInputPanel(Jsoup.parse(Question.getCurrentQuestion().getQuestion()).text());
                for (Map.Entry<Character, String> options : Question.getCurrentAnswer().entrySet()) {
                    viewWindow.updateInputPanel(options.getKey() + ") " + options.getValue());
                }
            }
        }
        else if (response.matches("help|h")) {
            viewWindow.updateInputPanel("<html>"+
                    "<p> " +  "***************************************************************" +
                    "<p> " +  textparser.getHelp()+ "</p>" +
                    "<p> " +  textparser.getGoNSEW()+ "</p>" +
                    "<p> " + textparser.getLookDisplay()+ "</p>" +
                    "<p> " + textparser.getCheat()+ "</p>" +
                    "<p> " + textparser.getGetItem()+ "</p>" +
                    "<p> " + textparser.getS()+ "</p>" +
                    "<p> " + textparser.getQ()+ "</p>"+
                    "<p> " + textparser.getAnswerQuesitons()+ "</p>" +
                    "<p> " + textparser.getMultipleChoice()+ "</p>" +
                    "<p> " + textparser.getForTF()+ "</p>"+
                    "<p> " + "***************************************************************"+ "</p>"+
                    "</html>");
//            System.out.println("\n***************************************************************");
//            System.out.println(textparser.getHelp() +
//                    textparser.getGoNSEW() +
//                    textparser.getLookDisplay() +
//                    textparser.getCheat() +
//                    textparser.getGetItem() +
//                    textparser.getS() +
//                    textparser.getQ());
//            System.out.println(
//                    textparser.getAnswerQuesitons() +
//                            textparser.getMultipleChoice() +
//                            textparser.getForTF());
//            //blank line
//            System.out.println("***************************************************************");
//            System.out.println();
            //quit the game by inputting Q/q
        }
        else if (response.matches("q")) {
            viewWindow.updateInputPanel(textparser.getSave());
            response = "";
            if (response.matches("yes|y")) {
                saveCurrentState();
            }
            System.exit(0);

        }
        else if (response.matches("look")) {
            viewWindow.updateInputPanel(textparser.getLook());
            GameClient.getLevelDetails("desc");
        }
        else if (response.matches("cheat")) {
            //if random integer between 1-10 is even then the user will get the question wrong
            if (((getRandomNumber(10) % 2) == 0)) {
                viewWindow.updateInputPanel(textparser.getCaught());
                Question.cheatCounter++;

            } else {
                viewWindow.updateInputPanel(Question.getCurrentQuestion().getCorrect_answer());
            }
            //hacking a room
        }
        else if (response.matches("hack")) {
            //get the current room
            hackClass();
            return "quit";
        }
        else if (response.matches("quit")) {
            //get the current room
            return "quit";
        }
        else {
            return response;
        }
        return "";
    }

    public static int getRandomNumber(int n) {
        Random rand = new Random();
        return rand.nextInt(n) + 1;
    }

    private void hackClass() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        String currentLocation = PointSystem.currentPlayer.getLocation().toLowerCase();
        //check if the current room is not a non-subject room
        if (!PointSystem.getNotSubject().contains(currentLocation)) {
            //check if the list of subject taken contains the current room
            if (PointSystem.currentPlayer.getSubjectTaken().contains(currentLocation)) {
                System.out.println(textparser.getTaken() + currentLocation);
            } else {
                PointSystem.currentPlayer.getSubjectTaken().add(currentLocation);
                //default 2.4 GPA if you hack
                PointSystem.currentPlayer.setCredit(new PointSystem().getCumulativeScore(3, PointSystem.currentPlayer.getSubjectTaken().size()));
                PointSystem.changePlayerGrade(PointSystem.currentPlayer);
            }


        }
    }

    public static void clearScreen() {
        String os = System.getProperty("os.name").toLowerCase();
        ProcessBuilder process = (os.contains("windows")) ?
                new ProcessBuilder("cmd", "/c", "cls") :
                new ProcessBuilder("clear");
        try {
            process.inheritIO().start().waitFor();
        } catch (InterruptedException | IOException ignored) {
            System.out.println(ignored);
        }
    }

    private void saveCurrentState() {
        ObjectMapper save = new ObjectMapper();
        try {
            save.writeValue(new File("storage.txt"), save.writeValueAsString(PointSystem.currentPlayer));
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}