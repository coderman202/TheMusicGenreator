package com.example.android.themusicgenreator;

/**
 * A class for Genres
 */

public class Genre {

    private int mGenreID;
    private String mGenreName;

    /**
     * Principle Genre constructor
     * @param mGenreID int ID of the genre
     * @param mGenreName String name of the genre
     */
    public Genre(int mGenreID, String mGenreName){
        this.mGenreID = mGenreID;
        this.mGenreName = mGenreName;
    }

    /**
     * Secondary constructor of Genre class. Only taking in a name String
     * @param mGenreName String name of the genre
     */
    public Genre(String mGenreName){
        this.mGenreName = mGenreName;
    }

    /**
     * get() method for the genre ID
     * @return returns the Genre ID
     */
    public int getmGenreID() {
        return mGenreID;
    }

    /**
     * get() method for the genre name
     * @return returns the Genre name
     */
    public String getmGenreName() {
        return mGenreName;
    }

    /**
     * set() method for genre ID
     * @param mGenreID the int ID of the genre
     */
    public void setmGenreID(int mGenreID) {
        this.mGenreID = mGenreID;
    }

    /**
     * set() method for genre name
     * @param mGenreName the String name of the genre
     */
    public void setmGenreName(String mGenreName) {
        this.mGenreName = mGenreName;
    }
}
