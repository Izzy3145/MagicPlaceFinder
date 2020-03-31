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
        private Integer rating;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public OpeningHours getOpeningHours() {
            return openingHours;
        }

        public void setOpeningHours(OpeningHours openingHours) {
            this.openingHours = openingHours;
        }

        public Integer getRating() {
            return rating;
        }

        public void setRating(Integer rating) {
            this.rating = rating;
        }

    }

    class OpeningHours {

        @SerializedName("open_now")
        @Expose
        private Boolean openNow;
        @SerializedName("periods")
        @Expose
        private List<Period> periods = null;
        @SerializedName("weekday_text")
        @Expose
        private List<String> weekdayText = null;

        public Boolean getOpenNow() {
            return openNow;
        }

        public void setOpenNow(Boolean openNow) {
            this.openNow = openNow;
        }

        public List<Period> getPeriods() {
            return periods;
        }

        public void setPeriods(List<Period> periods) {
            this.periods = periods;
        }

        public List<String> getWeekdayText() {
            return weekdayText;
        }

        public void setWeekdayText(List<String> weekdayText) {
            this.weekdayText = weekdayText;
        }

    }

    class Period {

        @SerializedName("close")
        @Expose
        private Close close;
        @SerializedName("open")
        @Expose
        private Open open;

        public Close getClose() {
            return close;
        }

        public void setClose(Close close) {
            this.close = close;
        }

        public Open getOpen() {
            return open;
        }

        public void setOpen(Open open) {
            this.open = open;
        }

    }


    class Open {

        @SerializedName("day")
        @Expose
        private Integer day;
        @SerializedName("time")
        @Expose
        private String time;

        public Integer getDay() {
            return day;
        }

        public void setDay(Integer day) {
            this.day = day;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

    }

    class Close {

        @SerializedName("day")
        @Expose
        private Integer day;
        @SerializedName("time")
        @Expose
        private String time;

        public Integer getDay() {
            return day;
        }

        public void setDay(Integer day) {
            this.day = day;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
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
