package com.example.androidassignment.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.androidassignment.R;
import com.example.androidassignment.adapter.MovieInfoGridViewAdapter;
import com.example.androidassignment.model.Movie;
import com.example.androidassignment.util.NetworkChecker;

//Activity for displaying the movie data in detail

public class MovieInfoActivity extends AppCompatActivity {

    //Required variable declaration
    private ImageView moviePosterImageView;
    private TextView movieTitleTextView, movieLongSummaryTextView,movieGenreTextView, movieIMDBRatingTextView, movieDurationTextView, movieYearTextView;
    private GridView gridView;
    private Button watchMovieButton, watchTrailerButton;

    private MovieInfoGridViewAdapter adapter;

    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Set the back navigation from the Activity

        //Get the data from the MainActivity in a Movie entity object
        Intent intent = getIntent();
        movie = intent.getParcelableExtra("movieObject");

        //View components instantiation
        moviePosterImageView = findViewById(R.id.moviePosterImageView);
        movieTitleTextView = findViewById(R.id.titleTextView);
        movieLongSummaryTextView = findViewById(R.id.longSummaryTextView);
        movieGenreTextView = findViewById(R.id.genreTextView);
        movieIMDBRatingTextView = findViewById(R.id.imdbRatingTextView);
        movieDurationTextView = findViewById(R.id.durationTextView);
        movieYearTextView = findViewById(R.id.movieyearTextView);
        watchMovieButton = findViewById(R.id.watchNowButton);
        watchTrailerButton = findViewById(R.id.watchTrailerButton);

        //Set the data to the respective views
        setMoviePosterImageView();
        setTextData();
        setMovieCast();
        setMovieDirectors();
        setMovieWriters();


        //Setting appropriate message for the watch movie action
        watchMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Data not available", Toast.LENGTH_SHORT).show();
            }
        });

        //When the trailer button is clicked, a new activity starts to diplay the movie trailer
        watchTrailerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(NetworkChecker.connectionStatus(getApplicationContext()))
                {
                    Intent newIntent = new Intent(MovieInfoActivity.this,YouTubeVideoDisplay.class);
                    newIntent.putExtra("trailerId", movie.getMovieYouTubeTrailerId());
                    startActivity(newIntent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please make sure you have an active internet connection.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //Set the movie poster to the imageview using Glide library
    void setMoviePosterImageView()
    {
        Glide.with(this)
                .load(movie.getMoviePosterUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .into(moviePosterImageView);
    }

    //Set the required text data to the respective views using the movie object
    void setTextData()
    {
        movieTitleTextView.setText(movie.getMovieTitle());
        movieLongSummaryTextView.setText(movie.getMovieLongSummary());
        movieGenreTextView.setText(movie.getMovieGenres());
        movieIMDBRatingTextView.setText("IMDB: " + movie.getMovieRating());
        movieDurationTextView.setText(movie.getMovieRuntime() + " Min");
        movieYearTextView.setText(String.valueOf(movie.getMovieYear()));
    }

    //Set the movie cast to the GridView
    void setMovieCast()
    {
        gridView = findViewById(R.id.cast_gridview); //Get the respective GridView reference

        String[] data = movie.getMovieCast().split("\\|"); //Split the data with the delimiter "|"

        //Set the height of the GridView such that it can accommodate all the data members
        ViewGroup.LayoutParams layoutParams = gridView.getLayoutParams();
        layoutParams.height = getGridViewHeightForData(data.length);
        gridView.setLayoutParams(layoutParams);

        //Set adapter to the GridView
        adapter = new MovieInfoGridViewAdapter(data, this);
        gridView.setAdapter(adapter);

    }

    //Applied the same logic to display Movie Directors as of the Movie Cast
    void setMovieDirectors()
    {
        gridView = findViewById(R.id.director_gridview);

        String[] data = movie.getMovieDirector().split("\\|");

        ViewGroup.LayoutParams layoutParams = gridView.getLayoutParams();
        layoutParams.height = getGridViewHeightForData(data.length);
        gridView.setLayoutParams(layoutParams);

        adapter = new MovieInfoGridViewAdapter(data, this);
        gridView.setAdapter(adapter);

    }

    //Applied the same logic to display Movie Writers as of the Movie Cast and Directors
    void setMovieWriters()
    {
        gridView = findViewById(R.id.writer_gridview);

        String[] data = movie.getMovieWriters().split("\\|");

        ViewGroup.LayoutParams layoutParams = gridView.getLayoutParams();
        layoutParams.height = getGridViewHeightForData(data.length);
        gridView.setLayoutParams(layoutParams);

        adapter = new MovieInfoGridViewAdapter(data, this);
        gridView.setAdapter(adapter);

    }

    //Returns the required height for the GridView by considering the number of data items to display
    int getGridViewHeightForData(int dataLength)
    {
        int gridviewHeight;

        if(dataLength % 3 == 0) // 3 is the number of columns of GridView
        {
            gridviewHeight = (int) ((Math.ceil((double)(dataLength / 3))) * 320); //320 is a hardcoded height value for grid view items. The sizes may vary according to screen sizes.
        }
        else
        {
            gridviewHeight = (int) ((Math.ceil((double)(dataLength / 3)) + 1) * 320);
        }

        return gridviewHeight;
    }
}