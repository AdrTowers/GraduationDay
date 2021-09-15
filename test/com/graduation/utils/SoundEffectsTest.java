package com.graduation.utils;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;



public class SoundEffectsTest {

    @Test
    public void correctAnswer_filePath_isCorrect(){
        File file = new File("Audio/quickwin.wav");
        String path = file.getAbsolutePath();
        assertTrue(path.endsWith("Audio\\quickwin.wav"));
    }

    @Test
    public void incorrectAnswer_filePath_isCorrect(){
        File file = new File("Audio/fail.wav");
        String path = file.getAbsolutePath();
        assertTrue(path.endsWith("Audio\\fail.wav"));
    }

    @Test
    public void moveToNextGrade_filePath_isCorrect(){
        File file = new File("Audio/Levelup.wav");
        String path = file.getAbsolutePath();
        assertTrue(path.endsWith("Audio\\Levelup.wav"));
    }

    @Test
    public void moveToNextGrade_filePath_isNull(){
        File file = new File("");
        assertFalse(true);
    }

}