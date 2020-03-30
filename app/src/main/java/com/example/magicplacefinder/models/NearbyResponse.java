package com.example.magicplacefinder.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NearbyResponse {

    @SerializedName("results")
    @Expose
    private List<Result> results = null;

    public NearbyResponse(List<Result> results) {
        this.results = results;
    }

    public NearbyResponse() {
    }

    public List<Result> getResults() {
        return results;
    }

    public class Result{
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("place_id")
        @Expose
        private String placeId;

        public Result(String id, String name, String placeId) {
            this.id = id;
            this.name = name;
            this.placeId = placeId;
        }

        public Result() {
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getPlaceId() {
            return placeId;
        }
    }
}
