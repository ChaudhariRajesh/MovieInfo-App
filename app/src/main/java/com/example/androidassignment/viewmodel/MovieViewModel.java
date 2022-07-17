package com.example.androidassignment.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.androidassignment.model.Movie;
import com.example.androidassignment.model.repository.MovieRepository;

import java.util.List;

/*
ViewModel Class for the application, handles all the backend functionality.
Single point of contact for the views.
*/

public class MovieViewModel extends AndroidViewModel {

    //Required object creation
    private MovieRepository movieRepository;
    private LiveData<List<Movie>> movieList;
    private int API_TYPE;
    private Application application;

    public MovieViewModel(Application application)
    {
        super(application);
        this.application = application;
    }

    //Initializing the Movie Repository to store the data and fetch the data
    private void initialise()
    {
        movieRepository = new MovieRepository(application,API_TYPE);
        movieRepository.setData();
        movieList = movieRepository.getMovieList();
    }

    //Set the API TYPE :  if 1 then load data from primary API, if 2 then load data secondary API
    public void setApiType(int apiType)
    {
        this.API_TYPE = apiType;
        initialise();
    }

    //Insert a list of Movie objects to the database
    public void insert(List<Movie> list)
    {
        movieRepository.insert(list);
    }

    //Get the Live Data movie object list from the repository
    public LiveData<List<Movie>> getMovieList()
    {
        return movieList;
    }

    //Get the specified number of movie objects as a LiveData
    public LiveData<List<Movie>> getMovieByCount(int count)
    {
        return movieRepository.getMovieByCount(count);
    }

    //Get the total number of records stored in the database
    public int getMovieCount()
    {
        return movieRepository.getMovieCount();
    }
}
