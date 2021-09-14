package com.graduation.client;

import com.graduation.utils.Prompter;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class GraduationDayApplicationClient {
    private static String banner;
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        System.out.println();
        System.out.println(readTXT("introtext"));
        System.out.println();

        TitleScreen.displayInstructions();

        Prompter prompter = new Prompter(new Scanner(System.in));
        GameClient game = new GameClient(prompter);
        game.initialize();
    }

    public static String readTXT(String name) {

        try {
            banner = Files.readString(Path.of("Banner/" + name + ".txt"));
        } catch (IOException e) {

        }
        return banner;
    }
}


