package com.example.magicplacefinder.modelTests;

import com.example.magicplacefinder.R;
import com.example.magicplacefinder.models.LatLng;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

//Arrange
//Act
//Assert

public class LatLngObjTest {
    @Test
    void isLatLngEqual_identicalProperties_returnTrue() {
        LatLng latLng1 = new LatLng(5.67, 120.32);
        LatLng latLng2 = new LatLng(5.67, 120.32);

        assertEquals(latLng1, latLng2);
    }

    @Test
    void isLatLngEqual_differentProperties_returnFalse() {
        LatLng latLng1 = new LatLng(4.67, 120.32);
        LatLng latLng2 = new LatLng(5.67, 140.32);

        assertNotEquals(latLng1, latLng2);
    }

    @Test
    void isLatLngToStringOfTypeString_returnTrue() {
        LatLng latLng1 = new LatLng(new Random().nextDouble(), new Random().nextDouble());
        Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
        Matcher hasSpecial = special.matcher(latLng1.toString());
        assertFalse(hasSpecial.find());
    }
}