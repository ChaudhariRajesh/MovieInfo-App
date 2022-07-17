package com.example.androidassignment.model;

import android.util.Log;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Url;

/*
The Retrofit API handler class, handles all the API related functions.
Gets the data from the specified API and stores it into the database.
*/

public class APIHelper {

    private static final String BASE_URL = "http://task.auditflo.in/";

    private Retrofit retrofit;
    private MovieAPI api;
    private DatabaseHelper databaseHelper;
    private int API_TYPE;

    //Create only one object of the Retrofit to avoid problems
    public void initialiseAPI(DatabaseHelper databaseHelper) {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        api = retrofit.create(MovieAPI.class);
        this.databaseHelper = databaseHelper;

    }


    //Set the API Type
    public void setAPIType(int apiType) {
        if (apiType == 1) //Primary API
        {
            API_TYPE = 1;
        } else //Secondary API
        {
            API_TYPE = 2;
        }

    }


    //Gets the data from api and stores it into the Room database
    public void insertMovieDataToDatabaseFromAPI() {
        //List to store the movie objects
        List<Movie> movieList = new ArrayList<Movie>();

        Call<JsonObject> call;
        //Set the API
        if (API_TYPE == 1) {
            call = api.getMoviesFromFirstAPI();
        } else {
            call = api.getMoviesFromSecondAPI();
        }

        //The call object enqueues the objects
        call.enqueue(new Callback<JsonObject>() {

            //When the response is received from the server
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                //Store the data to JSONObject
                JSONObject obj = null;
                try {
                    //Get the body of the response in string form
                    obj = new JSONObject(response.body().toString());

                    //Get the JSON array stored in the API
                    JSONArray array = obj.getJSONArray("Movie List");

                    //Parsing the JSONArray to fetch the data into Movie object
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = new JSONObject(array.get(i).toString());
                        Movie movie = new Movie(jsonObject.getString("IMDBID"),
                                jsonObject.getString("Title"),
                                jsonObject.optString("Summary"),
                                jsonObject.optString("Short Summary"),
                                jsonObject.optString("Genres"),
                                jsonObject.optString("YouTube Trailer"),
                                jsonObject.optString("Movie Poster"),
                                jsonObject.optString("Director"),
                                jsonObject.optString("Writers"),
                                jsonObject.getString("Cast"),
                                Integer.parseInt(jsonObject.getString("Year")),
                                Integer.parseInt(jsonObject.getString("Runtime")),
                                Float.parseFloat(jsonObject.optString("Rating")));

                        movieList.add(movie); //Add the new objects to the list
                    }

                    //First clear the database and then add all the new data to the database
                    databaseHelper.movieDao().deleteAllRecords();
                    databaseHelper.movieDao().insertMovie(movieList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            //If any error occurs
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.i("Data", t.toString());
            }
        });

    }

}
