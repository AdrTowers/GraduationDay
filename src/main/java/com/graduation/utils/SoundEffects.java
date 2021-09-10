package com.graduation.utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class SoundEffects {

    public static void correctAnswer() throws IOException, UnsupportedAudioFileException, LineUnavailableException {

        Scanner scanner = new Scanner(System.in);
        File file = new File("Audio/quickwin.wav");
        AudioInputStream audioInput = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInput);

        clip.start();
    }

    public static void incorrectAnswer() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        Scanner scanner = new Scanner(System.in);
        File file = new File("Audio/fail.wav");
        AudioInputStream audioInput = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInput);

        clip.start();
    }

    public static void movingToNextGrade() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        Scanner scanner = new Scanner(System.in);
        File file = new File("Audio/Levelup.wav");
        AudioInputStream audioInput = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInput);

        clip.start();
    }

//    public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException{
//
//                Scanner scanner = new Scanner(System.in);
//        File file = new File("Audio/spellwave.wav");
//        AudioInputStream audioInput = AudioSystem.getAudioInputStream(file);
//        Clip clip = AudioSystem.getClip();
//        clip.open(audioInput);
//
//        clip.start();
//        String response = scanner.next();
//    }
}