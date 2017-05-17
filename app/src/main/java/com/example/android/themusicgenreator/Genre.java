package com.example.android.themusicgenreator;

/**
 * A class for Genres
 */

public class Genre {

    private int mGenreID;
    private String mGenreName;

    public Genre(int mGenreID, String mGenreName){
        this.mGenreID = mGenreID;
        this.mGenreName = mGenreName;
    }

    public Genre(String mGenreName){
        this.mGenreName = mGenreName;
    }

    public int getmGenreID() {
        return mGenreID;
    }

    public String getmGenreName() {
        return mGenreName;
    }

    public void setmGenreID(int mGenreID) {
        this.mGenreID = mGenreID;
    }

    public void setmGenreName(String mGenreName) {
        this.mGenreName = mGenreName;
    }
}
