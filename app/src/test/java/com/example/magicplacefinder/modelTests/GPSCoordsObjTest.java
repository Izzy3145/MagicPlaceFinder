package com.example.magicplacefinder.modelTests;

import com.example.magicplacefinder.models.GPSCoords;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

//Arrange
//Act
//Assert

public class GPSCoordsObjTest {
    @Test
    void isLatLngEqual_identicalProperties_returnTrue() {
        GPSCoords GPSCoords1 = new GPSCoords(5.67, 120.32);
        GPSCoords GPSCoords2 = new GPSCoords(5.67, 120.32);

        assertEquals(GPSCoords1, GPSCoords2);
    }

    @Test
    void isLatLngEqual_differentProperties_returnFalse() {
        GPSCoords GPSCoords1 = new GPSCoords(4.67, 120.32);
        GPSCoords GPSCoords2 = new GPSCoords(5.67, 140.32);

        assertNotEquals(GPSCoords1, GPSCoords2);
    }

    @Test
    void isLatLngToStringOfTypeString_returnTrue() {
        GPSCoords GPSCoords1 = new GPSCoords(new Random().nextDouble(), new Random().nextDouble());
        Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
        Matcher hasSpecial = special.matcher(GPSCoords1.toString());
        assertFalse(hasSpecial.find());
    }
}