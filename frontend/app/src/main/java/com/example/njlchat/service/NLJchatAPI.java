package com.example.njlchat.service;

import com.example.njlchat.model.Post;
import com.example.njlchat.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface NLJchatAPI {
    @POST("/login")
    Call<Void> login(@Body User user);

    //create account
    @POST("/auth/sign-up")
    Call<Void> createUser(@Body User user);

    @GET("/posts/myposts")
    Call<List<Post>> getMyPosts(@Header("Authorization") String bearerString);

    @GET("/posts")
    Call<List<Post>> getPosts(@Header("Authorization") String bearerString);

    @POST("/posts")
    Call<Post> createPost(@Header("Authorization") String bearerString, @Body Post post);
}
