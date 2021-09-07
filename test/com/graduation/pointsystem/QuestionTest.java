package com.graduation.pointsystem;

import com.graduation.utils.Grade;
import org.junit.Test;

import static org.junit.Assert.*;

public class QuestionTest {

    @Test
    public void testGenerateQuestions() {
        assertEquals(-1,new Question().generateQuestions("", Grade.FRESHMAN));

    }
}