package com.example.androidassignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidassignment.R;
import com.example.androidassignment.model.Movie;
import com.example.androidassignment.view.MyRecyclerViewClickInterface;

import java.util.List;

/*
Adapter class for the RecyclerView to display the movie data on the RecyclerView dynamically.
*/

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MovieViewHolder> {

    //Required objects declaration
    private Context context;
    private List<Movie> movieList;
    private MyRecyclerViewClickInterface recyclerViewClickInterface;

    public MyRecyclerViewAdapter(Context context, List<Movie> movieList, MyRecyclerViewClickInterface recyclerViewClickInterface) {
        this.context = context;
        this.movieList = movieList;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_layout, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);

        //Set the data to the respective views using the Movie object
        holder.title.setText(movie.getMovieTitle());
        holder.year.setText(String.valueOf(movie.getMovieYear()));
        holder.runtime.setText(String.valueOf(movie.getMovieRuntime())+" Min");
        holder.summary.setText(movie.getMovieShortSummary());
        Glide.with(context)
                .load(movie.getMoviePosterUrl())
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.poster);

        //Implemented the listener on the whole cardview, this will be useful for recycler view item click event
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewClickInterface.onItemClick(movieList.get(position));
            }
        });

    }

    public void getMovieList(List<Movie> list)
    {
        this.movieList = list;
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    //Static ViewHolder class to initialize the views
    public static class MovieViewHolder extends RecyclerView.ViewHolder
    {
        public TextView title,year,runtime,summary;
        public ImageView poster;
        public CardView cardView;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.posterImageView);
            title = itemView.findViewById(R.id.titleTextView);
            year = itemView.findViewById(R.id.yearTextView);
            runtime = itemView.findViewById(R.id.runtimeTextView);
            summary = itemView.findViewById(R.id.shortSummaryTextView);
            cardView = itemView.findViewById(R.id.cardView);

        }
    }
}
