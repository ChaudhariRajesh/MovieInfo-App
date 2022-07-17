package com.example.androidassignment.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.androidassignment.R;
import com.example.androidassignment.adapter.MyRecyclerViewAdapter;
import com.example.androidassignment.model.Movie;
import com.example.androidassignment.util.NetworkChecker;
import com.example.androidassignment.viewmodel.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

/*
Author: Rajesh Chaudhari.

Architectural Design Pattern Implemented : MVVM (Model - View - ViewModel)
Database Used : Room
API Used : Retrofit

*/

//Main Activity class, serves as the entrypoint of the application
public class MainActivity extends AppCompatActivity implements MyRecyclerViewClickInterface{

    private MovieViewModel movieViewModel;

    private boolean isScrolling; //Required for the scrolling event of RecyclerView
    private Context context;

    private ProgressBar progressBar; //Progress bar for scrolling
    private RecyclerView recyclerView; //Recycler view to display movie data

    private static int initiallyDisplayedCount, incrementCount, newCount, totalMovieItems, previousSum; //Variables required to pass data to RecyclerView adapter
    private List<Movie> movieDataList; //List of Movie Entity objects

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        progressBar = findViewById(R.id.progress_bar);
        movieDataList = new ArrayList<>();

        //Setting up the RecyclerView
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        //Initiating the Custom Adapter for RecyclerView
        List<Movie> temporaryMovieList = new ArrayList<>();
        MyRecyclerViewAdapter customAdapter = new MyRecyclerViewAdapter(getApplicationContext(), temporaryMovieList, this);


        //Initiating the ViewModel object
        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        movieViewModel.setApiType(1); // Api Type = 1 for first API and 2 for second API
        if (movieViewModel.getMovieCount() != 0) //If Database Not Empty
        {
            //Get the Movie Entity object list and add observer on this LiveData
            movieViewModel.getMovieList().observe(this, new Observer<List<Movie>>() {
                @Override
                public void onChanged(List<Movie> movies) {

                    initiallyDisplayedCount = 10; //These number of movies will be displayed initially
                    incrementCount = 3; //On each scroll these number of movies will be added
                    totalMovieItems = movieViewModel.getMovieCount(); //Store the total movies count
                    newCount = initiallyDisplayedCount + incrementCount; //Updating the count

                    customAdapter.getMovieList(movies.subList(0, initiallyDisplayedCount)); //Adding only 10 movies to the RecyclerView initially
                    movieDataList = movies;
                    recyclerView.setAdapter(customAdapter); //Set the adapter to the RecyclerView
                }
            });
        }
        else if(NetworkChecker.connectionStatus(this) == false) //If Network is not present
        {
            Toast.makeText(getApplicationContext(), "We need internet for the first time only.", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "We ran into a problem, try restarting.", Toast.LENGTH_LONG).show();
        }


        //RecyclerView scroll event handling
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (isScrolling && dy > 0) {

                    //Logic to add items on scroll
                    if (newCount <= totalMovieItems) {
                        previousSum = newCount;
                        newCount += incrementCount;
                        addItemsToAdapter(previousSum);
                    } else if (Math.abs(totalMovieItems - previousSum) < incrementCount) {
                        addItemsToAdapter((previousSum) + (totalMovieItems - previousSum));
                        previousSum += incrementCount;
                    }
                    isScrolling = false;
                }
            }

            private void addItemsToAdapter(int updatedRange) {
                progressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        customAdapter.getMovieList(movieDataList.subList(0, updatedRange));
                        customAdapter.notifyDataSetChanged(); //Notify the adapter about the dataset changed, this is not the most appropriate method
                        progressBar.setVisibility(View.GONE);
                    }
                }, 3000); //The ProgressBar will be displayed for three seconds when the scrolling happens
            }

        });


    }

    //Button for refreshing the data
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.refresh_menu, menu);
        return true;
    }

    //If Network is not present, then the button is disabled
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(!NetworkChecker.connectionStatus(getApplicationContext()))
        {
            menu.findItem(R.id.refresh_button).setEnabled(false);
        }
        return true;
    }

    //When the refresh button is clicked, the data from second API is fetched, stored into database and displayed.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh_button:
                movieViewModel.setApiType(2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    //On click event handling of RecyclerView items
    @Override
    public void onItemClick(Movie movie) {

        //When the card is clicked, new activity starts to display the movie data
        Intent intent = new Intent(this,MovieInfoActivity.class);
        intent.putExtra("movieObject", movie);
        startActivity(intent);

    }
}