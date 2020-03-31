package com.example.magicplacefinder.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;

public class LatLng implements Parcelable {

    private double lat;
    private double lng;

    public LatLng(double lat, double lng) {
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
        LatLng latLng = (LatLng) obj;
        return latLng.getLat() == getLat() && latLng.getLng() == getLng();
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

    private LatLng(Parcel in){
        lat = in.readDouble();
        lng = in.readDouble();
    }
    public static final Creator CREATOR = new Creator() {
        @Override
        public LatLng createFromParcel(Parcel parcel) {
            return new LatLng(parcel);
        }

        @Override
        public LatLng[] newArray(int i) {
            return new LatLng[i];
        }
    };
}
