package com.graduation.pointsystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduation.client.GameClient;
import com.graduation.elements.Bully;
import com.graduation.elements.Player;
import com.graduation.utils.Grade;
import com.graduation.utils.SoundEffects;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PointSystem {

    private static SoundEffects soundEffects = SoundEffects.getInstance();
    private File pointSystemJson = new File("Banner/pointsystem.json");
    private ObjectMapper mapper = new ObjectMapper();
    private static File staticPointSystemJson = new File("Banner/pointsystem.json");
    private static ObjectMapper mapperStatic = new ObjectMapper();
    PointSystemParser textparser = mapper.readValue(pointSystemJson, PointSystemParser.class);
    static PointSystemParser staticParser;
    private static Bully bully = Bully.getInstance();
    private static List<String> notSubject = new ArrayList<>(Arrays.asList("gym", "cafeteria", "hallway"));
    private static final int GRADE = 4;
    private static double player_total_grade = 0;
    private static final List<String> core = new ArrayList<>(Arrays.asList("math", "computers", "geography", "history"));
    private static List<String> elective = new ArrayList<>(Arrays.asList("mythology"));
    private static boolean isNewLevel = false;
    public static Player player = Player.getInstance();

    static {
        try {
            staticParser = mapperStatic.readValue(staticPointSystemJson, PointSystemParser.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PointSystem() throws IOException {
    }

    public static List<String> getNotSubject() {
        return notSubject;
    }

    private double getScore(int correct) {
        double current_class = 0;
        current_class += ((correct / (double) 5) * GRADE);
        return current_class;
    }

    public double getCumulativeScore(int eachScore, int numberOfSubjects) {
       // System.out.println("Counter = " + numberOfSubjects);
        player_total_grade += (getScore(eachScore));
        return Double.parseDouble(new DecimalFormat(textparser.getFormat()).format(player_total_grade / (double) numberOfSubjects));
    }

    public static void teacherQuestions(String subject, Grade level) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if (!player.getSubjectTaken().contains(subject)) {
            Question questions = new Question();
            if(player.getSubjectTaken().size()==0){
                isNewLevel = false;
            }
            PointSystem pointSystem = new PointSystem();
            int score = 0;
            if (!notSubject.contains(subject.toLowerCase())) {
                score = questions.generateQuestions(subject, level);
                if (score == -1) {
                    System.out.println(subject+ staticParser.getRequired());
                }
                else if(score==0){
                    GameClient.continueJourney(isNewLevel);
                }
                else {
                    while (pointSystem.getScore(score) < 2) {
                        System.out.println(staticParser.getScore() + pointSystem.getScore(score) + staticParser.getLessThan2() + subject + staticParser.getAgain());
                        System.out.println();
                        score = questions.generateQuestions(subject, level);
                    }
                    //add the passed subject to the list of taken subject
                    player.getSubjectTaken().add(subject);
                    //set the player's GPA
                    player.setCredit(pointSystem.getCumulativeScore(score, player.getSubjectTaken().size()));
                    System.out.println(staticParser.getGpa() + player.getCredit());
                    //determine if the player has meet the criteria to change its level
                    //from freshman->sophomore->junior->senior
                    //based on a gpa greater than or equal to 2.0 and having taken all the core
                    //subjects i.e. math,computers,history and geography
                    //reset the taken subject list
                    changePlayerGrade();
                    System.out.println(staticParser.getGrade() + player.getGrade() + "\n");
                }
            }

        } else {
            System.out.println(staticParser.getPassed() + subject);
            //GameClient.continueJourney(isNewLevel);
        }

        if(!player.getGrade().equals(Grade.GRADUATE)) {
            GameClient.continueJourney(isNewLevel);
            //see the class list
            // System.out.println(Arrays.toString(player.getSubjectTaken().toArray(new String[0])));
        }

    }

    /**
     * Decides whether the player meets the criteria to move to the next grade
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public static void changePlayerGrade() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        //Step 1: Determine if we can go to the next grade level
        if (player.getSubjectTaken().containsAll(core) && player.getCredit() >= 2.0) {
            moveToNextGrade();
        } else if (player.getSubjectTaken().size() == 4 && player.getSubjectTaken().containsAll(elective) && player.getCredit() >= 2.0){
            moveToNextGrade();
        }
    }

    /**
     * Moves the player to the following grade
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    private static void moveToNextGrade() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        // Writes GPA and grade to report_card.txt
        updateReportCard(player.getCredit() + " " + player.getGrade(), player);
        //display a congratulation message on moving to the next grade
        System.out.println(staticParser.getCongratulations() + player.getGrade() + staticParser.getYear());
        // Access SoundEffects
        soundEffects.movingToNextGrade();   // Plays 'congratulatory' sound effect
        isNewLevel = true;
        switch (player.getGrade()) {
            case FRESHMAN:
                player.setGrade(Grade.SOPHOMORE);
                break;
            case SOPHOMORE:
                player.setGrade(Grade.JUNIOR);
                break;
            case JUNIOR:
                player.setGrade(Grade.SENIOR);
                break;
            case SENIOR:
                player.setGrade(Grade.GRADUATE);
                break;
        }
        if(!player.getGrade().equals(Grade.GRADUATE)) {
            //Step 2: Clear the subjects that we passed from the player
            player.getSubjectTaken().clear();
            //Step 3: Get the first location of the next level
            player.setLocation(GameClient.getFirstLocation());
            //reset the GPA for the new level to zero
            player_total_grade = 0;
            //Step 4: Toggle the bully
            bully.setPresence(true);
            bully.setHealth(100);
        }
    }

    /**
     * Creates and updates a report card after passing each grade
     * @param report String containing the GPA and current grade
     */
    public static void updateReportCard(String report, Player player) {
        try {
            File file = new File("report_card.txt");
            // Check if the user is currently a Freshman and if the file exists
            // If the file already exists, delete the file
            if (file.exists() && player.getGrade().equals(Grade.FRESHMAN)) {
                file.delete();
            }
            // If the file does not exist, create a new file
            if(!file.exists()) {
                file.createNewFile();
            }

            // Append the file on each report creation
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            // Write the report
            bw.write(report +"\n");
            bw.close();
        } catch (IOException ex) {
            System.out.println("Exception occurred: ");
            ex.printStackTrace();
        }
    }
}
