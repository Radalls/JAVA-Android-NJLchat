package com.example.njlchat.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class HttpClient {

    private final static HttpClient instance;

    static {
        instance = new HttpClient();
    }

    private Retrofit client;
    private String url = "https://leo-jade-nadir-backend.herokuapp.com";
    private String bearerString;

    public HttpClient() {
        client = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Retrofit getClient() {
        return client;
    }

    public static HttpClient getInstance() {
        return instance;
    }

    public String getBearerString() {
        return bearerString;
    }

    public void setBearerString(String bearerString) {
        this.bearerString = bearerString;
    }

}
