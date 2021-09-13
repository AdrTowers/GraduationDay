package com.graduation.utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundEffects {
    private static int mute = -100;
    private static int unmute = 0;

    /**
     * Locates 'positive' sound path file
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @throws LineUnavailableException
     */
    public static void correctAnswer() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        File file = new File("Audio/quickwin.wav");
        playAudio(file);
    }

    /**
     * Locates 'negative' sound path file
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @throws LineUnavailableException
     */
    public static void incorrectAnswer() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File("Audio/fail.wav");
        playAudio(file);
    }

    /**
     * Locates 'congratulatory' sound path file
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @throws LineUnavailableException
     */
    public static void movingToNextGrade() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File("Audio/Levelup.wav");
        playAudio(file);
    }

    /**
     * Plays audio file
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @throws LineUnavailableException
     */
    public static void playAudio(File audioFile) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        AudioInputStream audioInput = AudioSystem.getAudioInputStream(audioFile);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInput);

        clip.start();
    }

    /**
     * Toggles mute and un-mute volume options
     * @param vol
     */
    public static void toggleMute(String vol){
        if (vol.equals("mute")){
            System.out.println("ToggleVolume - mute :" + vol);

        } else if (vol.equals("unmute")){
            System.out.println("ToggleVolume - unmute :" + vol);
        } else {
            System.out.println("Unable to do that.");
        }
    }
}