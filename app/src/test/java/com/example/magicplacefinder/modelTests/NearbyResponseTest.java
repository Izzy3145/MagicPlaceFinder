package com.example.magicplacefinder.modelTests;

import com.example.magicplacefinder.BaseTest;
import com.example.magicplacefinder.models.responses.NearbyResponse;
import com.example.magicplacefinder.models.responses.PlaceIdentifier;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class NearbyResponseTest extends BaseTest {

    @Test
    void isNearbyResponseEqual_identicalProperties_returnTrue() {
        List<PlaceIdentifier> resultsList = new ArrayList<>();
        resultsList.add(result1);
        resultsList.add(result2);
        NearbyResponse nearby1 = new NearbyResponse(resultsList);
        NearbyResponse nearby2 = new NearbyResponse(resultsList);

        assertEquals(nearby1, nearby2);
    }

    @Test
    void isNearbyEqual_differentProperties_returnFalse() {
        PlaceIdentifier diffresult2 = new PlaceIdentifier("id2", "name2", "diffplaceID2");
        List<PlaceIdentifier> resultsList1 = new ArrayList<>();
        resultsList1.add(result1);
        resultsList1.add(result2);
        List<PlaceIdentifier> resultsList2 = new ArrayList<>();
        resultsList2.add(result1);
        resultsList2.add(result2);
        resultsList2.add(diffresult2);

        NearbyResponse nearby1 = new NearbyResponse(resultsList1);
        NearbyResponse nearby2 = new NearbyResponse(resultsList2);

        assertNotEquals(nearby1, nearby2);
    }

    @Test
    void isNearbyEqual_differentSizeList_returnFalse() {
        PlaceIdentifier result3 = new PlaceIdentifier("id3", "name3", "placeID3");
        List<PlaceIdentifier> resultsList1 = new ArrayList<>();
        List<PlaceIdentifier> resultsList2 = new ArrayList<>();
        resultsList1.add(result1);
        resultsList1.add(result2);
        resultsList2.add(result1);
        resultsList2.add(result2);
        resultsList2.add(result3);

        NearbyResponse nearby1 = new NearbyResponse(resultsList1);
        NearbyResponse nearby2 = new NearbyResponse(resultsList2);

        assertNotEquals(nearby1, nearby2);
    }
}
