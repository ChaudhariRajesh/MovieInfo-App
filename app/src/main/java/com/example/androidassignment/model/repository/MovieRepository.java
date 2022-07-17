package com.example.androidassignment.model.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.example.androidassignment.model.APIHelper;
import com.example.androidassignment.model.DatabaseHelper;
import com.example.androidassignment.model.Movie;
import com.example.androidassignment.model.MovieDao;
import com.example.androidassignment.util.NetworkChecker;

import java.util.List;

/*
Repository class of the application, acts as an intermediary between ViewModel and the multiple data sources.
Handles multiple data sources - Database and APIs.
*/

public class MovieRepository {

    //Required objects declaration
    private DatabaseHelper databaseHelper;
    private LiveData<List<Movie>> movieList;
    private Application application;
    private APIHelper apiHelper;

    private int API_TYPE;

    public MovieRepository(Application application, int apiType)
    {
        this.application = application;
        databaseHelper = DatabaseHelper.getDatabaseHelper(application);
        apiHelper = new APIHelper();
        apiHelper.initialiseAPI(databaseHelper);
        API_TYPE = apiType;
    }


    public void setData()
    {
        //If the Network is present, then fetch the data from primary API and store is into the database
        if(NetworkChecker.connectionStatus(application)) //If network is present
        {
            apiHelper.setAPIType(API_TYPE);
            apiHelper.insertMovieDataToDatabaseFromAPI();
        }
        //Now get the data that is stored in the database
        movieList = databaseHelper.movieDao().getAllMovies();
    }


    //Insert the data using AsyncTask making the UI thread operate without any problem
    public void insert(List<Movie> movieList)
    {
        new InsertAsyncTask(databaseHelper).execute(movieList);
    }

    //Get the Live Data list with the specified number
    public LiveData<List<Movie>> getMovieByCount(int count)
    {
        return databaseHelper.movieDao().getMovieByCount(count);
    }

    //Get the count of movie objects
    public int getMovieCount()
    {
        return databaseHelper.movieDao().getMovieCount();
    }

    //Get Live Data list of all movie objects
    public LiveData<List<Movie>> getMovieList()
    {
        return movieList;
    }

    //Inner class to perform asynchronous insertion operation
    static class InsertAsyncTask extends AsyncTask<List<Movie>,Void,Void>
    {
        private final MovieDao movieDao;
        InsertAsyncTask(DatabaseHelper db)
        {
            movieDao = db.movieDao();
        }

        @Override
        protected Void doInBackground(List<Movie>... lists) {
            movieDao.insertMovie(lists[0]);
            return null;
        }
    }

}
