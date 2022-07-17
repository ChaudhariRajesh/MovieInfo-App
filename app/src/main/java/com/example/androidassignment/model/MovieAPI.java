package com.example.androidassignment.model;

import com.google.gson.JsonObject;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

//Interface for the Retrofit API
public interface MovieAPI {

    @GET("1.json") //First file name
    Call<JsonObject> getMoviesFromFirstAPI();

    @GET("2.json") //Second file name
    Call<JsonObject> getMoviesFromSecondAPI();

}
