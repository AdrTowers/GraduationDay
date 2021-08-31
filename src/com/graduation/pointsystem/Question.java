package com.graduation.pointsystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduation.utils.Grade;
import org.jsoup.Jsoup;
import com.graduation.utils.Prompter;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Question {
    public Question(Prompter prompter) {
        this.prompter = prompter;
    }

    private Prompter prompter;
    public static final Map<String, Integer> categories =
            Map.of("maths", 19, "history", 23, "geography", 22, "sports", 21, "general knowledge", 9
                    , "computers", 18);
    private static final Map<Grade, String> difficulties = Map.of(Grade.FRESHMAN, "easy", Grade.SOPHOMORE, "easy", Grade.JUNIOR, "medium",
            Grade.SENIOR,"hard");

    private List<QuestionDetail> getQuestions(String type,Grade grade) throws JsonProcessingException, ExecutionException, InterruptedException {
        //testing level
        System.out.println("level="+difficulties.get(grade));
        //make a client object
        HttpClient client = HttpClient.newHttpClient();
        //create a request object
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://opentdb.com/api.php?amount=5&category=" + categories.get(type.toLowerCase()) +
                        "&difficulty="+difficulties.get(grade)))
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

    public int generateQuestions(String type,Grade level) {
        if (type.equals("")) {
            return -1;
        }
        List<QuestionDetail> samples = null;
        try {
            samples = getQuestions(type,level);
        } catch (ExecutionException | JsonProcessingException | InterruptedException ex) {
            ex.printStackTrace();
        }
        int counter = 0;
        for (QuestionDetail sample : samples) {
            //create a new set of answers
            Map<Character, String> possible_answers = new LinkedHashMap<>();
            System.out.println(Jsoup.parse(sample.getQuestion()).text());
            List<String> answers = new ArrayList<>();
            answers.add(sample.getCorrect_answer());
            for (Object incorrect : sample.getIncorrect_answers()) {
                answers.add(incorrect.toString());
            }
            //randomize the possible answers
            Collections.shuffle(answers);
            char option = 'A';
            for (String possible_answer : answers) {
                //stripping the answer of any html tags
                possible_answers.put(option++, Jsoup.parse(possible_answer).text());
            }

            for (Map.Entry<Character, String> options : possible_answers.entrySet()) {
                System.out.println(options.getKey() + ") " + options.getValue());
            }
            String userChoice = prompter.prompt(":>").trim().toUpperCase();
            char chosen = ' ';
            while (userChoice.compareTo("") == 0 || !possible_answers.keySet().contains(userChoice.toUpperCase().charAt(0))) {
                System.out.println("You can choose from these options: " + Arrays.toString(possible_answers.keySet().toArray(new Character[0])));
                userChoice = prompter.prompt(":>").trim().toUpperCase();
            }
            chosen = userChoice.charAt(0);
            if (possible_answers.get(chosen).compareTo(Jsoup.parse(sample.getCorrect_answer()).text()) == 0) {
                System.out.println("correct");
                counter += 1;
            } else {
                System.out.println("Incorrect");
            }
            System.out.println();
        }
        return counter;
    }

}