package com.example.magicplacefinder.network;

import com.example.magicplacefinder.models.NearbyResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RESTService {

    @GET("nearbysearch/json")
    Call<NearbyResponse> getPlaces(@Query("location") String location, @Query("type") String type, @Query("rankby") String rankby, @Query("key") String key);
}
