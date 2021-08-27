package com.graduation.client;

import com.graduation.utils.Prompter;

import java.util.Scanner;

class GraduationDayApplicationClient {
    public static void main(String[] args) {
        System.out.println("\nGRADUATION DAY");
        System.out.println( "Team Members:\n" +
                " Hongyi Qu\n" +
                " Jauric Flowers\n" +
                " Pierre Gober\n" +
                " Stephen Yeboah\n\n");

        Prompter prompter = new Prompter(new Scanner(System.in));
        GameClient game = new GameClient(prompter);
        game.initialize();

    }
}


