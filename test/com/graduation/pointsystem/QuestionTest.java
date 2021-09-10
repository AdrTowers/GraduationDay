package com.graduation.pointsystem;

import com.graduation.utils.Grade;
import org.junit.Test;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

import static org.junit.Assert.*;

public class QuestionTest {

    @Test
    public void testGenerateQuestions() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        assertEquals(-1,new Question().generateQuestions("", Grade.FRESHMAN));

    }
}