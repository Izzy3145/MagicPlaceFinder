package com.example.magicplacefinder.models;

import com.example.magicplacefinder.BuildConfig;

import org.apache.commons.lang3.StringUtils;

public class PlaceDetailRequest {
    String placeID;
    final String fields;
    final String apiKey = BuildConfig.PLACES_API_KEY;

    public PlaceDetailRequest(String placeID) {
        this.placeID = placeID;
        fields = StringUtils.join("name",",","rating",",","opening_hours");
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
