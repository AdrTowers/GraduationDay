package com.graduation.client;

import com.graduation.elements.Player;
import com.graduation.utils.Grade;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameClientTest {

    @Test
    public void shouldChangeHealthIfInGym() {
        Player player = new Player("test", 2.4, 50, Grade.FRESHMAN, "Cafeteria");
        player.setLocation("Gym");
        GameClient.updateHealthWhenInGym(player.getLocation());
        assertEquals(100, player.getHealth());
    }

    @Test
    public void shouldNotChangeHealthIfUpdateMethodNotCalled() {
        Player player = new Player("test", 2.4, 50, Grade.FRESHMAN, "Cafeteria");
        player.setLocation("Gym");
        assertEquals(50, player.getHealth());
    }

    @Test
    public void shouldNotChangeHealthIfInNotInGym() {
        Player player = new Player("test", 2.4, 50, Grade.FRESHMAN, "Cafeteria");
        player.setLocation("Maths");
        GameClient.updateHealthWhenInGym(player.getLocation());
        assertEquals(50, player.getHealth());
    }

}