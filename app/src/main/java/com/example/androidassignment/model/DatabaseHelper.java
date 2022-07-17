package com.example.androidassignment.model;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

/*
Helper class, Room database implementation.
Handles the connection between the database and outside objects
*/

@Database(entities = Movie.class, exportSchema = false, version = 1)
public abstract class DatabaseHelper extends RoomDatabase {

    private static final String DATABASE_NAME = "moviedb";

    //Create only single instance of the class
    public static DatabaseHelper databaseHelper;
    public static synchronized DatabaseHelper getDatabaseHelper(Context context)
    {
        if(databaseHelper == null)
        {
            databaseHelper = Room.databaseBuilder(context, DatabaseHelper.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .addCallback(callback)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return databaseHelper;
    }

    //Callback method for asynchronous function
    static Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateAsyncTask(databaseHelper);
        }
    };

    static class PopulateAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private MovieDao movieDao;

        PopulateAsyncTask(DatabaseHelper db)
        {
            movieDao = db.movieDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            movieDao.deleteAllRecords();
            return null;
        }
    }

    //Calling the Movie Data Access Object
    public abstract MovieDao movieDao();

}
