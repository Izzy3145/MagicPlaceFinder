package com.example.magicplacefinder.network;

import com.example.magicplacefinder.models.responses.NearbyResponse;
import com.example.magicplacefinder.models.responses.PlaceResponse;

import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RESTService {

    @GET("nearbysearch/json")
    Flowable<NearbyResponse> getPlaces(@Query("location") String location, @Query("type") String type,
                                       @Query("keyword") String keyword,
                                       @Query("radius") String radius, @Query("key") String key);

    @GET ("details/json")
    Flowable<PlaceResponse> getPlaceDetails(@Query("placeID") String placeID, @Query("fields") String field, @Query("key") String key);
}
