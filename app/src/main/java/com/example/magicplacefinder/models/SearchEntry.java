package com.example.magicplacefinder.models;

import com.example.magicplacefinder.BuildConfig;

public class SearchEntry {
    LatLng latlng;
    String type;
    String keyword;
    String radius;
    final String apiKey = BuildConfig.PLACES_API_KEY;

    public SearchEntry(LatLng latlng, String type, String keyword, String radius) {
        this.latlng = latlng;
        this.type = type;
        this.keyword = keyword;
        this.radius = radius;
    }

    public SearchEntry() {
    }

    public LatLng getLatlng() {
        return latlng;
    }

    public String getType() {
        return type;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getRadius() {
        return radius;
    }

    public String getApiKey() {
        return apiKey;
    }
}
