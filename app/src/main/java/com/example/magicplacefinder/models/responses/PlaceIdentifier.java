package com.example.magicplacefinder.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaceIdentifier {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("place_id")
        @Expose
        private String placeId;

        public PlaceIdentifier(String id, String name, String placeId) {
            this.id = id;
            this.name = name;
            this.placeId = placeId;
        }

        public PlaceIdentifier() {
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
