package com.example.magicplacefinder.repositoryTests;

import com.example.magicplacefinder.BaseTest;
import com.example.magicplacefinder.network.RESTService;
import com.example.magicplacefinder.repository.PlacesRepository;

import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import okhttp3.mockwebserver.MockWebServer;

public class PlacesRepositoryTests extends BaseTest {

    MockWebServer mockServer;
    RESTService restService;
    @Mock
    PlacesRepository placesRepository;
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

    /*    Gson gson = new GsonBuilder()
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
        }*/
    }

    @Test
    void testApiService_correctReponse_returnTrue() {

    }

    @Test
    void fetchGeneralInfoPlacesMethod_correctReponseType_returnTrue() {

    }

    @Test
    void fetchPlaceDetailDataMethod_correctReponseType_returnTrue() {

    }

    @Test
    void fetchPlaceDetailsFollowingGeneralPlaceInfoMethod_noException_returnTrue() {
    }


    @AfterEach
    void tearDown() {
       /* try {
            mockServer.shutdown();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }*/
    }
}
