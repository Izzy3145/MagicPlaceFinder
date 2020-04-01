package com.example.magicplacefinder;

import com.example.magicplacefinder.models.GPSCoords;
import com.example.magicplacefinder.models.SearchRequest;
import com.example.magicplacefinder.models.responses.PlaceIdentifier;

public class BaseTest {
    protected static PlaceIdentifier result1 = new PlaceIdentifier("id", "name", "placeID");
    protected static PlaceIdentifier result2 = new PlaceIdentifier("id2", "name2", "placeID2");

    protected static SearchRequest searchRequest1 = new SearchRequest(new GPSCoords(50.412524,-5.1119065),
            "shop","Surf", "6");

}
