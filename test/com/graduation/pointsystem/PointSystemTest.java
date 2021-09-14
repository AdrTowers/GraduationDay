package com.graduation.pointsystem;

import com.graduation.elements.Player;
import com.graduation.utils.Grade;
import org.junit.Before;
import org.junit.Test;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static com.graduation.pointsystem.PointSystem.updateReportCard;
import static org.junit.Assert.*;

public class PointSystemTest {

    @Test
    public void shouldCreateReportCard() {
        Player player = new Player("test name", 4.0, 100, Grade.FRESHMAN, "cafeteria");
        String report = player.getCredit() + " " + player.getGrade();
        updateReportCard(report, player);
        String data = "";
        data = readFromFile(data);
        assertEquals(report, data);
    }

    @Test
    public void shouldAppendToReportCardWhenNewGrade() throws IOException {
        Player player = new Player("test name", 4.0, 100, Grade.FRESHMAN, "cafeteria");
        String data = "";

        // Check freshman year
        String freshmanReport = player.getCredit() + " " + player.getGrade();
        updateReportCard(freshmanReport, player);
        data = readFromFile(data); // call read from file for freshman
        assertEquals(freshmanReport, data);

        // Check sophomore year
        player.setGrade(Grade.SOPHOMORE);
        player.setCredit(2.4);
        String sophmoreReport = player.getCredit() + " " + player.getGrade();
        updateReportCard(sophmoreReport, player);
        data = readFromFile(data); // call read from file for sophomore
        assertEquals(sophmoreReport, data);

        // Check junior year
        player.setGrade(Grade.JUNIOR);
        player.setCredit(2.4);
        String juniorReport = player.getCredit() + " " + player.getGrade();
        updateReportCard(juniorReport, player);
        data = readFromFile(data); // call read from file for junior
        assertEquals(juniorReport, data);

        // Check senior year
        player.setGrade(Grade.SENIOR);
        player.setCredit(2.4);
        String seniorReport = player.getCredit() + " " + player.getGrade();
        updateReportCard(seniorReport, player);
        data = readFromFile(data); // call read from file for senior
        assertEquals(seniorReport, data);

        int lines = checkLines();
        // Check that updating the report card four times produces four lines of text
        assertEquals(4, lines);
    }

    /**
     * Checks the number of lines in a file
     * @return an int of the number of lines
     */
    private int checkLines() {
        int lines = 0;
        Path path = Paths.get("report_card.txt");
        try {
            lines = (int) Files.lines(path).count(); // the number of lines in the file
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return lines;
    }


    /**
     * Reads data from a file
     * @param data String value of data
     * @return a String value from the file
     */
    private String readFromFile(String data) {
        try {
            File reportCard = new File("report_card.txt");
            Scanner scanner = new Scanner(reportCard);
            while(scanner.hasNextLine()) {
                data = scanner.nextLine();

            }
            scanner.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Error: ");
            ex.printStackTrace();
        }
        return data;
    }

}