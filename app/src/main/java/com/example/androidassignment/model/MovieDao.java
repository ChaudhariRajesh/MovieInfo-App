package com.example.androidassignment.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/*Movie Data Access Object for the Room database.
Contains all the required methods to operate with the database
*/

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) //If duplicate data is present, then replace it.
    void insertMovie(List<Movie> movieList);

    @Query("SELECT * FROM `tbl-movie`")
    LiveData<List<Movie>> getAllMovies();

    @Query("SELECT * FROM `tbl-movie` LIMIT :count")
    LiveData<List<Movie>> getMovieByCount(int count);

    @Query("SELECT COUNT(*) FROM `tbl-movie`")
    int getMovieCount();

    @Query("DELETE FROM `tbl-movie`")
    void deleteAllRecords();

    @Query("SELECT `youtube_trailer` FROM `tbl-movie` WHERE `imdb_id` = :imdbid")
    String getYouTubeTrailerID(String imdbid);

    @Query("SELECT `movie_poster_url` FROM `tbl-movie` WHERE `imdb_id` = :imdbid")
    String getPosterUrl(String imdbid);

}
