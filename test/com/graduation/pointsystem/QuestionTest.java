package com.graduation.pointsystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduation.utils.Grade;
import com.graduation.pointsystem.Question;
import org.jsoup.Jsoup;
import org.junit.Before;
import org.junit.Test;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import static org.junit.Assert.*;

public class QuestionTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();


    public QuestionTest() throws IOException {
    }

    @Before
    public void setUp() throws IOException {
        System.setOut(new PrintStream(outputStreamCaptor));

    }

    @Test //Testing the Jackson library to read the json file and send it to a class of getters.
    public void getSystemOutPrints_whenInvokePrintln_thenOutputCaptorSuccess() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        File questionJson = new File("Banner/question.json");
        ObjectMapper mapper = new ObjectMapper();
        QuestionParser textparser = mapper.readValue(questionJson, QuestionParser.class);

        System.out.println(textparser.getIncorrect());
        Assert.assertEquals("Incorrect: The correct answer is", outputStreamCaptor.toString()
                .trim());
    }

    @Test
    public void testGenerateQuestions() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        assertEquals(-1,new Question().generateQuestions("", Grade.FRESHMAN));

    }

    @Test
    public void optionA_shouldAlwaysReturnTrue() throws UnsupportedAudioFileException, IOException, LineUnavailableException, ExecutionException, InterruptedException {
        List<QuestionDetail> samples = null;
        samples = new Question().getQuestions("", Grade.FRESHMAN);
        new Question().generateQuestions("", Grade.FRESHMAN);
        for (QuestionDetail sample : samples) {
            Map<Character, String> possible_answers = new LinkedHashMap<>();
            System.out.println(Jsoup.parse(sample.getQuestion()).text());
            List<String> answers = new ArrayList<>();
            answers.add(sample.getCorrect_answer());
            char option = 'A';
            for (String possible_answer : answers) {

                possible_answers.put(option++, Jsoup.parse(possible_answer).text());
            }
            for (Map.Entry<Character, String> options : possible_answers.entrySet()) {
                if (answers.size() == 2 && options.getKey() == 'A') {
                    assertEquals(options.getValue(), "True");
                }
            }

        }
    }

    @Test
    public void optionB_shouldAlwaysReturnFalse() throws UnsupportedAudioFileException, IOException, LineUnavailableException, ExecutionException, InterruptedException {
        List<QuestionDetail> samples = null;
        samples = new Question().getQuestions("", Grade.FRESHMAN);
        new Question().generateQuestions("", Grade.FRESHMAN);
        for (QuestionDetail sample : samples) {
            Map<Character, String> possible_answers = new LinkedHashMap<>();
            System.out.println(Jsoup.parse(sample.getQuestion()).text());
            List<String> answers = new ArrayList<>();
            answers.add(sample.getCorrect_answer());
            char option = 'A';
            for (String possible_answer : answers) {

                possible_answers.put(option++, Jsoup.parse(possible_answer).text());
            }
            for (Map.Entry<Character, String> options : possible_answers.entrySet()) {
                if (answers.size() == 2 && options.getKey() == 'B') {
                    assertEquals(options.getValue(), "False");
                }
            }

        }
    }
}