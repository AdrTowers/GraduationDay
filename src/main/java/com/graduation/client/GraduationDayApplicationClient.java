package com.graduation.client;

import com.graduation.utils.GameTextParser;
import com.graduation.utils.Prompter;
import com.graduation.view.ViewWindow;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class GraduationDayApplicationClient {
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {

//        System.out.println();
//        System.out.println(readTXT("introtext"));
//        System.out.println();
//
//        TitleScreen.displayInstructions();

        ViewWindow.getInstance();
        //Prompter prompter = new Prompter(new Scanner(System.in));
        GameClient game = new GameClient();
        //game.initialize();
    }


}


