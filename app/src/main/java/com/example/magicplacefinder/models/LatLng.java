package com.example.magicplacefinder.models;

import androidx.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;

public class LatLng {

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

}
