package com.example.magicplacefinder.models.responses;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NearbyResponse {

    @SerializedName("results")
    @Expose
    private List<PlaceIdentifier> results = null;

    public NearbyResponse(List<PlaceIdentifier> results) {
        this.results = results;
    }

    public NearbyResponse() {
    }

    public List<PlaceIdentifier> getResults() {
        return results;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj == null){
            return false;
        }
        if(getClass() != obj.getClass()){
            return false;
        }
        NearbyResponse nearbyResponse = (NearbyResponse) obj;
        return nearbyResponse.getResults() == getResults();
    }
}
