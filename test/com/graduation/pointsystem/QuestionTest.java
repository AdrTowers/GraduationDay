package com.graduation.pointsystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduation.utils.Grade;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
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



    @After
    public void tearDown() {
        System.setOut(standardOut);
    }
}