package com.example.android.themusicgenreator;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A class for Genres
 */

public class Genre implements Parcelable {

    private int mGenreID;
    private String mGenreName;

    /**
     * Principle Genre constructor
     *
     * @param mGenreID   int ID of the genre
     * @param mGenreName String name of the genre
     */
    public Genre(int mGenreID, String mGenreName) {
        this.mGenreID = mGenreID;
        this.mGenreName = mGenreName;
    }

    /**
     * Secondary constructor of Genre class. Only taking in a name String
     *
     * @param mGenreName String name of the genre
     */
    public Genre(String mGenreName) {
        this.mGenreName = mGenreName;
    }

    /**
     * get() method for the genre ID
     *
     * @return returns the Genre ID
     */
    public int getmGenreID() {
        return mGenreID;
    }

    /**
     * get() method for the genre name
     *
     * @return returns the Genre name
     */
    public String getmGenreName() {
        return mGenreName;
    }

    /**
     * set() method for genre ID
     *
     * @param mGenreID the int ID of the genre
     */
    public void setmGenreID(int mGenreID) {
        this.mGenreID = mGenreID;
    }

    /**
     * set() method for genre name
     *
     * @param mGenreName the String name of the genre
     */
    public void setmGenreName(String mGenreName) {
        this.mGenreName = mGenreName;
    }

    //Parcelable methods below....
    private Genre(Parcel in) {
        this.mGenreID = in.readInt();
        this.mGenreName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mGenreID);
        dest.writeString(mGenreName);
    }

    public static final Creator<Genre> CREATOR = new Creator<Genre>() {
        @Override
        public Genre createFromParcel(Parcel in) {
            return new Genre(in);
        }

        @Override
        public Genre[] newArray(int size) {
            return new Genre[size];
        }
    };
}
