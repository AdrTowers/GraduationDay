package com.graduation.client;

import com.graduation.utils.Prompter;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Scanner;

public class GraduationDayApplicationClient {
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        System.out.println("\nGRADUATION DAY");
        System.out.println( "Team Members:\n" +
                " Hongyi Qu\n" +
                " Jauric Flowers\n" +
                " Pierre Gober\n" +
                " Stephen Yeboah\n\n");

        TitleScreen.displayInstructions();

        Prompter prompter = new Prompter(new Scanner(System.in));
        GameClient game = new GameClient(prompter);
        game.initialize();
    }
}


