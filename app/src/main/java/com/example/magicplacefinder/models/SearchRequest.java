package com.example.magicplacefinder.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.magicplacefinder.BuildConfig;

public class SearchRequest implements Parcelable {
    LatLng latlng;
    String radius;

    String type;
    String keyword;
    final String apiKey = BuildConfig.PLACES_API_KEY;

    public SearchRequest(LatLng latlng, String radius, String type, String keyword) {
        this.latlng = latlng;
        this.radius = radius;
        this.type = type;
        this.keyword = keyword;
    }

    public SearchRequest(String radius, String type, String keyword) {
        this.radius = radius;
        this.type = type;
        this.keyword = keyword;
    }

    public SearchRequest() {
    }

    public LatLng getLatlng() {
        return latlng;
    }

    public String getRadius() {
        return radius;
    }

    public String getType() {
        return type;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setLatlng(LatLng latlng) {
        this.latlng = latlng;
    }

    public String getApiKey() {
        return apiKey;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(radius);
        parcel.writeString(type);
        parcel.writeString(keyword);
    }

    private SearchRequest(Parcel in){
        radius = in.readString();
        type = in.readString();
        keyword = in.readString();
    }


    public static final Creator CREATOR = new Creator() {
        @Override
        public SearchRequest createFromParcel(Parcel parcel) {
            return new SearchRequest(parcel);
        }

        @Override
        public SearchRequest[] newArray(int i) {
            return new SearchRequest[i];
        }
    };
}
