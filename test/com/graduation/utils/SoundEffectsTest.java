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

    @Test(expected = IllegalArgumentException.class)
    public void volume_IsLowerThan_MinBoundary() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        int volume = -81;
        File file = new File("Audio/Levelup.wav");
        AudioInputStream audioInput = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInput);
        FloatControl gains = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gains.setValue(volume);
        clip.start();

        assertEquals(-81, SoundEffects.getVolume());
    }

    @Test(expected = IllegalArgumentException.class)
    public void volume_IsHigherThan_MaxBoundary() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        int volume = 7;
        File file = new File("Audio/Levelup.wav");
        AudioInputStream audioInput = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInput);
        FloatControl gains = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gains.setValue(volume);
        clip.start();

        assertEquals(7, SoundEffects.getVolume());
    }

}