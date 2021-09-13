package com.graduation.utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class SoundEffects {

    /**
     * Plays 'positive' sound when user gets a correct answer
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @throws LineUnavailableException
     */
    public static void correctAnswer() throws IOException, UnsupportedAudioFileException, LineUnavailableException {

        Scanner scanner = new Scanner(System.in);
        File file = new File("Audio/quickwin.wav");
        AudioInputStream audioInput = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInput);
//        FloatControl decrease = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
//        decrease.setValue(-10);
        clip.start();
    }

    /**
     * Plays 'negative' sound when user gets an incorrect answer
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @throws LineUnavailableException
     */
    public static void incorrectAnswer() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        Scanner scanner = new Scanner(System.in);
        File file = new File("Audio/fail.wav");
        AudioInputStream audioInput = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInput);

        clip.start();
    }

    /**
     * Plays a 'congratulatory' sound when user passes into the next grade
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @throws LineUnavailableException
     */
    public static void movingToNextGrade() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        Scanner scanner = new Scanner(System.in);
        File file = new File("Audio/Levelup.wav");
        AudioInputStream audioInput = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInput);
        //increaseVolume(clip, vol);
        clip.start();
    }

    public static void increaseVolume(Clip clip, Double vol) throws LineUnavailableException {
        FloatControl decrease = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        decrease.setValue(-10);
    }
}