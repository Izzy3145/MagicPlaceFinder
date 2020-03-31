package com.example.magicplacefinder.utils;

import com.example.magicplacefinder.models.responses.PlaceResponse;

import java.util.List;

public interface RequestListener {

    void onResultsFound(List<PlaceResponse> results);

    void onApiCallFailure(Throwable e);

    void noResults();

}
