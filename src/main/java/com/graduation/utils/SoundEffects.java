package com.graduation.utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundEffects {
    private static int volume;
    private static SoundEffects instance = null;

    public static SoundEffects getInstance(){
        if (instance == null){
            instance = new SoundEffects();
        }
        return instance;
    }

    // CONSTRUCTOR
    private SoundEffects() {
        this.volume = 0;
    }

    public static void setVolume(int volume) {
        SoundEffects.volume = volume;
    }

    public static int getVolume() {
        return volume;
    }

    /**
     * Locates 'positive' sound path file
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @throws LineUnavailableException
     */
    public static void correctAnswer() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        File file = new File("Audio/quickwin.wav");
        playAudio(file, volume);
    }

    /**
     * Locates 'negative' sound path file
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @throws LineUnavailableException
     */
    public static void incorrectAnswer() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File("Audio/fail.wav");
        playAudio(file, volume);
    }

    /**
     * Locates 'congratulatory' sound path file
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @throws LineUnavailableException
     */
    public static void movingToNextGrade() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File("Audio/Levelup.wav");
        playAudio(file, volume);
    }

    /**
     * Plays audio file and sets volume
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @throws LineUnavailableException
     */
    public static void playAudio(File audioFile, int volume) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            FloatControl gains = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gains.setValue(volume);
            clip.start();
    }
}