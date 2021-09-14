package com.graduation.utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundEffects {
    private static int mute = -80;

    /**
     * Locates 'positive' sound path file
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @throws LineUnavailableException
     */
    public static void correctAnswer(boolean isMuted) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        File file = new File("Audio/quickwin.wav");
        playAudio(file, isMuted);
    }

    /**
     * Locates 'negative' sound path file
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @throws LineUnavailableException
     */
    public static void incorrectAnswer(boolean isMuted) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File("Audio/fail.wav");
        playAudio(file, isMuted);
    }

    /**
     * Locates 'congratulatory' sound path file
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @throws LineUnavailableException
     */
    public static void movingToNextGrade(boolean isMuted) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File("Audio/Levelup.wav");
        playAudio(file, isMuted);
    }

    /**
     * Plays audio file
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @throws LineUnavailableException
     */
    public static void playAudio(File audioFile, boolean isMuted) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        if (!isMuted){
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);

            clip.start();
            System.out.println("Volume un-muted.");
        } else {
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            FloatControl gains = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gains.setValue(mute);
            clip.start();
            System.out.println("Volume muted");
        }
    }

    /**
     * Toggles mute and un-mute volume options
     * @param vol
     */
    public static void toggleMute(String vol) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if (vol.equals("mute")){
            System.out.println("ToggleVolume - mute :" + vol);
            correctAnswer(true);
            incorrectAnswer(true);
            movingToNextGrade(true);
        } else if (vol.equals("unmute")){
            System.out.println("ToggleVolume - unmute :" + vol);
            correctAnswer(false);
            incorrectAnswer(false);
            movingToNextGrade(false);
        } else {
            System.out.println("Unable to do that.");
        }
    }
}