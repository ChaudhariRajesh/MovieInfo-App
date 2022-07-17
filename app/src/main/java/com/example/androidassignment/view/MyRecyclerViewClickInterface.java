package com.example.androidassignment.view;

import com.example.androidassignment.model.Movie;

//Interface for implementing the click event on the RecyclerView
public interface MyRecyclerViewClickInterface {

    void onItemClick(Movie movie); //Takes the object of the item clicked

}
