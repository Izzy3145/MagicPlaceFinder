package com.example.magicplacefinder.models;

import com.example.magicplacefinder.BuildConfig;

public class PlaceDetailRequest {
    String placeID;
    final String fields = "name,rating,opening_hours";
    final String apiKey = BuildConfig.PLACES_API_KEY;

    public PlaceDetailRequest(String placeID) {
        this.placeID = placeID;
    }

    public String getPlaceID() {
        return placeID;
    }

    public String getFields() {
        return fields;
    }

    public String getApiKey() {
        return apiKey;
    }
}
