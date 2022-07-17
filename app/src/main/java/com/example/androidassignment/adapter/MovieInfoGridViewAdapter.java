package com.example.androidassignment.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.androidassignment.R;

/*
Custom adapter to set the GridView of Movie cast, director and writer in the MovieInfoActivity.
*/

public class MovieInfoGridViewAdapter extends BaseAdapter {

    private String names[]; //Stores the names of people, passed from the MovieInfoActivity
    private Context context;

    public MovieInfoGridViewAdapter(String[] names, Context context) {
        this.names = names;
        this.context = context;
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int i) {
        return names[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        //Only one instance of view is created
        if(view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.movie_info_cardview_layout, null);
            CardView cv = view.findViewById(R.id.movie_info_cardview);
            TextView nameTextView = cv.findViewById(R.id.movie_info_cardview_textview1);
            nameTextView.setText(names[i]);
        }
        return view;
    }
}
