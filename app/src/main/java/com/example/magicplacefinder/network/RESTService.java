package com.example.magicplacefinder.network;

import com.example.magicplacefinder.models.responses.NearbyResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RESTService {

    @GET("nearbysearch/json")
    Call<NearbyResponse> getPlaces(@Query("location") String location, @Query("type") String type,
                                   @Query("keyword") String keyword,
                                   @Query("radius") String radius, @Query("key") String key);
}
