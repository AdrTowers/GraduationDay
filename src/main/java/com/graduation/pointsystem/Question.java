package com.graduation.pointsystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduation.client.GameClient;
import com.graduation.utils.Grade;
import com.graduation.utils.Prompter;
import com.graduation.utils.SoundEffects;
import com.graduation.utils.readMap;
import org.jsoup.Jsoup;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Question {
    public static final Map<String, Integer> categories =
            Map.of("maths", 19, "history", 23, "geography", 22, "sports", 21, "general knowledge", 9
                    , "computers", 18);
    private static final Map<Grade, String> difficulties = Map.of(Grade.FRESHMAN, "easy", Grade.SOPHOMORE, "easy", Grade.JUNIOR, "medium",
            Grade.SENIOR, "hard");
    public static int cheatCounter = 0;
    private static QuestionDetail currentQuestion = null;
    private static Map<Character, String> currentAnswer = null;
    private static List<String> correct = new ArrayList<>(List.of("Congrats!", "Nice job!", "Correct!", "Way to go!", "Keep it going!", "Nailed it!"));
    private File questionJson = new File("Banner/question.json");
    private ObjectMapper mapper = new ObjectMapper();

    public static QuestionDetail getCurrentQuestion() {
        return currentQuestion;
    }


    public static Map<Character, String> getCurrentAnswer() {
        return currentAnswer;
    }


    //public static boolean isHacked = false;

    private String getRandomElement(List<String> list) {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }
    List<QuestionDetail> getQuestions(String type, Grade grade) throws JsonProcessingException, ExecutionException, InterruptedException {
        //testing level
        //System.out.println("level=" + difficulties.get(grade));
        //make a client object
        HttpClient client = HttpClient.newHttpClient();
        //create a request object
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://opentdb.com/api.php?amount=5&category=" + categories.get(type.toLowerCase()) +
                        "&difficulty=" + difficulties.get(grade)))
                .build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        String text = response.thenApply(HttpResponse::body).join();
        ObjectMapper questions = new ObjectMapper();
        //mapping the rest api json to POJO QuestionTemplate class
        QuestionTemplate category = questions.readValue(text, QuestionTemplate.class);
        List<QuestionDetail> lists = category.getResults();
        //return the question details as a list
        return lists;
    }

    public int generateQuestions(String type, Grade level) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        QuestionParser textparser = mapper.readValue(questionJson, QuestionParser.class);
        if (type.isBlank()) {
            return -1;
        } else {
            Prompter.clearScreen();
            //System.out.println(GameClient.getPlayer());
            System.out.println(readMap.convertedMap());
            List<QuestionDetail> samples = null;
            try {
                samples = getQuestions(type, level);
            } catch (ExecutionException | JsonProcessingException | InterruptedException ex) {
                ex.printStackTrace();
            }
            int counter = 0;
            //loop through the questions

            for (QuestionDetail sample : samples) {
                //assign the current question to currentQuestion class variable
                currentQuestion = sample;
                cheatCounter = 0;
                Map<Character, String> possible_answers = new LinkedHashMap<>();
                System.out.println(Jsoup.parse(sample.getQuestion()).text());
                List<String> answers = new ArrayList<>();
                answers.add(sample.getCorrect_answer());
                String correctAnswerKey = sample.getCorrect_answer();
                char correctAnswerChar = correctAnswerKey.charAt(0);
                char correctAnswerValue;
                for (Object incorrect : sample.getIncorrect_answers()) {
                    answers.add(incorrect.toString());
                }
                for (int i = 0; i < samples.size(); i++) {
                    //this accounts for all the true/false questions from the API
                    if(answers.size() == 2) {
                        for (String possible_answer : answers) {
                            possible_answers.put('A', "True");
                            possible_answers.put('B', "False");
                        }
                    }
                    else{
                        Collections.shuffle(answers);
                        char option = 'A';
                        for (String possible_answer : answers) {
                            //stripping the answer of any html tags
                            possible_answers.put(option++, Jsoup.parse(possible_answer).text());
                        }
                    }
                }

                //assign the current set of answers to the class variable currentAnswer
                currentAnswer = possible_answers;
                for (Map.Entry<Character, String> options : possible_answers.entrySet()) {
                    System.out.println(options.getKey() + ") " + options.getValue());
                }
                //get user response
                String userChoice = GameClient.getPrompter().prompt(":>").trim().toUpperCase();
                if (userChoice.matches(textparser.getQuit()))  { //quit
                    return 0;
                }

                char chosen = userChoice.charAt(0);
                //acceptable answer logic for multiple choice questions
                if (answers.size() == 4) {
                    while (userChoice.compareTo("") == 0 || !possible_answers.containsKey(userChoice.toUpperCase().charAt(0))) {
                        System.out.println(textparser.getOptions() + Arrays.toString(possible_answers.keySet().toArray(new Character[0])));
                        userChoice = GameClient.getPrompter().prompt(":>").trim().toUpperCase();
                        if (userChoice.matches(textparser.getQuit())) {
                            return 0;
                        }
                    }
                    if (possible_answers.get(chosen).compareTo(Jsoup.parse(sample.getCorrect_answer()).text()) == 0) {
                        System.out.println(getRandomElement(correct));
                        SoundEffects.correctAnswer();   // Plays 'positive' sound effect
                        counter += 1;
                        System.out.println(counter + textparser.getOutof() + samples.size() + textparser.getQuestions());
                    }
                    else {
                        System.out.println(textparser.getIncorrect() + sample.getCorrect_answer());
                        SoundEffects.incorrectAnswer(); // Plays 'negative' sound effect
                        System.out.println(counter + textparser.getOutof() + samples.size() + textparser.getQuestions());

                    }
                }
                //acceptable answer logic for True False questions
                if (answers.size() == 2) {
                    while (userChoice.compareTo("") == 0 || !possible_answers.containsKey(userChoice.toUpperCase().charAt(0))) {
                        if (userChoice.equals("TRUE") || userChoice.equals("FALSE")) {
                            break;
                        }
                        else if (userChoice.length() == 1 && chosen == 'F') {
                            break;
                        }
                        else if(userChoice.length() == 1 && chosen == 'T') {
                            break;
                        }
                        System.out.println(textparser.getOptions() + "[A, B, True, False, T, or F]");

                        userChoice = GameClient.getPrompter().prompt(":>").trim().toUpperCase();
                        if (userChoice.matches(textparser.getQuit())) {
                            return 0;
                        }
                    }

                    if (correctAnswerKey.equals("True")) {
                        correctAnswerValue = 'A';
                    }
                    else {
                        correctAnswerValue = 'B';
                    }

                    if(userChoice.equals(correctAnswerKey.toUpperCase())) {
                        System.out.println(getRandomElement(correct));
                        SoundEffects.correctAnswer();   // Plays 'positive' sound effect
                        counter += 1;
                        System.out.println(counter + textparser.getOutof() + samples.size() + textparser.getQuestions());
                    }

                    else if (correctAnswerValue == chosen) {
                        System.out.println(getRandomElement(correct));
                        SoundEffects.correctAnswer();   // Plays 'positive' sound effect
                        counter += 1;
                        System.out.println(counter + textparser.getOutof() + samples.size() + textparser.getQuestions());
                    }
                    else if(String.valueOf(correctAnswerChar).equals(String.valueOf(chosen))) {
                        System.out.println(getRandomElement(correct));
                        SoundEffects.correctAnswer();   // Plays 'positive' sound effect
                        counter += 1;
                        System.out.println(counter + textparser.getOutof() + samples.size() + textparser.getQuestions());
                    }
                    else {
                        System.out.println(textparser.getIncorrect() + sample.getCorrect_answer());
                        SoundEffects.incorrectAnswer(); // Plays 'negative' sound effect
                        System.out.println(counter + textparser.getOutof() + samples.size() + textparser.getQuestions());
                    }
                }
                counter = counter - cheatCounter;
            }
            return counter;
        }
    }
}
