package com.example.androidassignment.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/*
Simple POJO class of the Movie Entity.
Contains all the attributes specified in the Movie API.

Implements the Parcelable interface to pass a whole object to other activities.
*/

@Entity(tableName = "tbl-movie")
public class Movie implements Parcelable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "imdb_id")
    private String movieIMDBId;

    @ColumnInfo(name = "title")
    private String movieTitle;

    @ColumnInfo(name = "long_summary")
    @Nullable
    private String movieLongSummary;

    @ColumnInfo(name = "short_summary")
    @Nullable
    private String movieShortSummary;

    @ColumnInfo(name = "genres")
    @Nullable
    private String movieGenres;

    @ColumnInfo(name = "youtube_trailer")
    @Nullable
    private String movieYouTubeTrailerId;

    @ColumnInfo(name = "movie_poster_url")
    @Nullable
    private String moviePosterUrl;

    @ColumnInfo(name = "director")
    @Nullable
    private String movieDirector;

    @ColumnInfo(name = "writers")
    @Nullable
    private String movieWriters;

    @ColumnInfo(name = "cast")
    private String movieCast;

    @ColumnInfo(name = "year")
    private int movieYear;

    @ColumnInfo(name = "runtime")
    private int movieRuntime;

    @ColumnInfo(name = "rating")
    @Nullable
    private float movieRating;

    public Movie(@NonNull String movieIMDBId, String movieTitle, @Nullable String movieLongSummary, @Nullable String movieShortSummary, @Nullable String movieGenres, @Nullable String movieYouTubeTrailerId, @Nullable String moviePosterUrl, @Nullable String movieDirector, @Nullable String movieWriters, String movieCast, int movieYear, int movieRuntime, float movieRating) {
        this.movieIMDBId = movieIMDBId;
        this.movieTitle = movieTitle;
        this.movieLongSummary = movieLongSummary;
        this.movieShortSummary = movieShortSummary;
        this.movieGenres = movieGenres;
        this.movieYouTubeTrailerId = movieYouTubeTrailerId;
        this.moviePosterUrl = moviePosterUrl;
        this.movieDirector = movieDirector;
        this.movieWriters = movieWriters;
        this.movieCast = movieCast;
        this.movieYear = movieYear;
        this.movieRuntime = movieRuntime;
        this.movieRating = movieRating;
    }

    protected Movie(Parcel in) {
        movieIMDBId = in.readString();
        movieTitle = in.readString();
        movieLongSummary = in.readString();
        movieShortSummary = in.readString();
        movieGenres = in.readString();
        movieYouTubeTrailerId = in.readString();
        moviePosterUrl = in.readString();
        movieDirector = in.readString();
        movieWriters = in.readString();
        movieCast = in.readString();
        movieYear = in.readInt();
        movieRuntime = in.readInt();
        movieRating = in.readFloat();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @NonNull
    public String getMovieIMDBId() {
        return movieIMDBId;
    }

    public void setMovieIMDBId(@NonNull String movieIMDBId) {
        this.movieIMDBId = movieIMDBId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    @Nullable
    public String getMovieLongSummary() {
        return movieLongSummary;
    }

    public void setMovieLongSummary(@Nullable String movieLongSummary) {
        this.movieLongSummary = movieLongSummary;
    }

    @Nullable
    public String getMovieShortSummary() {
        return movieShortSummary;
    }

    public void setMovieShortSummary(@Nullable String movieShortSummary) {
        this.movieShortSummary = movieShortSummary;
    }

    @Nullable
    public String getMovieGenres() {
        return movieGenres;
    }

    public void setMovieGenres(@Nullable String movieGenres) {
        this.movieGenres = movieGenres;
    }

    @Nullable
    public String getMovieYouTubeTrailerId() {
        return movieYouTubeTrailerId;
    }

    public void setMovieYouTubeTrailerId(@Nullable String movieYouTubeTrailerId) {
        this.movieYouTubeTrailerId = movieYouTubeTrailerId;
    }

    @Nullable
    public String getMoviePosterUrl() {
        return moviePosterUrl;
    }

    public void setMoviePosterUrl(@Nullable String moviePosterUrl) {
        this.moviePosterUrl = moviePosterUrl;
    }

    @Nullable
    public String getMovieDirector() {
        return movieDirector;
    }

    public void setMovieDirector(@Nullable String movieDirector) {
        this.movieDirector = movieDirector;
    }

    @Nullable
    public String getMovieWriters() {
        return movieWriters;
    }

    public void setMovieWriters(@Nullable String movieWriters) {
        this.movieWriters = movieWriters;
    }

    public String getMovieCast() {
        return movieCast;
    }

    public void setMovieCast(String movieCast) {
        this.movieCast = movieCast;
    }

    public int getMovieYear() {
        return movieYear;
    }

    public void setMovieYear(int movieYear) {
        this.movieYear = movieYear;
    }

    public int getMovieRuntime() {
        return movieRuntime;
    }

    public void setMovieRuntime(int movieRuntime) {
        this.movieRuntime = movieRuntime;
    }

    public float getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(float movieRating) {
        this.movieRating = movieRating;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movieIMDBId='" + movieIMDBId + '\'' +
                ", movieTitle='" + movieTitle + '\'' +
                ", movieLongSummary='" + movieLongSummary + '\'' +
                ", movieShortSummary='" + movieShortSummary + '\'' +
                ", movieGenres='" + movieGenres + '\'' +
                ", movieYouTubeTrailerId='" + movieYouTubeTrailerId + '\'' +
                ", moviePosterUrl='" + moviePosterUrl + '\'' +
                ", movieDirector='" + movieDirector + '\'' +
                ", movieWriters='" + movieWriters + '\'' +
                ", movieCast='" + movieCast + '\'' +
                ", movieYear=" + movieYear +
                ", movieRuntime=" + movieRuntime +
                ", movieRating=" + movieRating +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(movieIMDBId);
        parcel.writeString(movieTitle);
        parcel.writeString(movieLongSummary);
        parcel.writeString(movieShortSummary);
        parcel.writeString(movieGenres);
        parcel.writeString(movieYouTubeTrailerId);
        parcel.writeString(moviePosterUrl);
        parcel.writeString(movieDirector);
        parcel.writeString(movieWriters);
        parcel.writeString(movieCast);
        parcel.writeInt(movieYear);
        parcel.writeInt(movieRuntime);
        parcel.writeFloat(movieRating);
    }
}
