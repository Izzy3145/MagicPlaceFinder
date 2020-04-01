package com.example.magicplacefinder.network;

import android.util.Log;

import com.example.magicplacefinder.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RESTClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override public void log(String message) {
                Log.i("OkHttp response: ", message);
            }
        });
        if(BuildConfig.DEBUG) {
            logging.level(HttpLoggingInterceptor.Level.BODY);
        } else {
            logging.level(HttpLoggingInterceptor.Level.NONE);
        }
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();


        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }

}
