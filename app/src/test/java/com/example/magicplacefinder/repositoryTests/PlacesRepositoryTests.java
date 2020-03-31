package com.example.magicplacefinder.repositoryTests;

import com.example.magicplacefinder.BaseTest;
import com.example.magicplacefinder.network.RESTClient;
import com.example.magicplacefinder.network.RESTService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlacesRepositoryTests extends BaseTest {

    MockWebServer mockServer;
    RESTService restService;

    @BeforeEach
    void setUp() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        restService =  new Retrofit.Builder()
                .baseUrl(mockServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
                .create(RESTService.class);

        mockServer = new MockWebServer();
        try {
            mockServer.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    @AfterEach
    void tearDown() {
        try {
            mockServer.shutdown();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
