package com.example.magicplacefinder.models.responses;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlaceResponse {

    private List<Object> htmlAttributions = null;

    @SerializedName("result")
    @Expose
    private Result result;

    public Result getResult() {
        return result;
    }

    public class Result {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("opening_hours")
        @Expose
        private OpeningHours openingHours;
        @SerializedName("rating")
        @Expose
        private Double rating;

        public String getName() {
            return name;
        }

        public OpeningHours getOpeningHours() {
            return openingHours;
        }

        public Double getRating() {
            return rating;
        }


    }

    public class OpeningHours {

        @SerializedName("open_now")
        @Expose
        private Boolean openNow;

        @SerializedName("weekday_text")
        @Expose
        private List<String> weekdayText = null;

        public Boolean getOpenNow() {
            return openNow;
        }

        public List<String> getWeekdayText() {
            return weekdayText;
        }

    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj == null){
            return false;
        }
        if(getClass() != obj.getClass()){
            return false;
        }
        PlaceResponse placeResponse = (PlaceResponse) obj;
        return placeResponse.getResult() == getResult() && placeResponse.getResult().getName() == getResult().getName()
                    &&placeResponse.getResult().openingHours == getResult().openingHours;
    }
}
