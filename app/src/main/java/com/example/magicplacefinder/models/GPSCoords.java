package com.example.magicplacefinder.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;

public class GPSCoords implements Parcelable {

    private double lat;
    private double lng;

    public GPSCoords(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Override public String toString() {
        return StringUtils.join(lat, ",", lng);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj == null){
            return false;
        }
        if(getClass() != obj.getClass()){
            return false;
        }
        GPSCoords GPSCoords = (GPSCoords) obj;
        return GPSCoords.getLat() == getLat() && GPSCoords.getLng() == getLng();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(lat);
        parcel.writeDouble(lng);
    }

    private GPSCoords(Parcel in){
        lat = in.readDouble();
        lng = in.readDouble();
    }
    public static final Creator CREATOR = new Creator() {
        @Override
        public GPSCoords createFromParcel(Parcel parcel) {
            return new GPSCoords(parcel);
        }

        @Override
        public GPSCoords[] newArray(int i) {
            return new GPSCoords[i];
        }
    };
}
