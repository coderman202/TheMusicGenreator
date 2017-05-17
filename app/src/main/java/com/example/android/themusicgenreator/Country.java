package com.example.android.themusicgenreator;

/**
 * A class for Countries
 */

public class Country {

    private int mCountryID;
    private String mCountryName;

    public Country(int mCountryID, String mCountryName){
        this.mCountryID = mCountryID;
        this.mCountryName = mCountryName;
    }

    public Country(String mCountryName){
        this.mCountryName = mCountryName;
    }

    public int getmCountryID() {
        return mCountryID;
    }

    public String getmCountryName() {
        return mCountryName;
    }

    public void setmCountryID(int mCountryID) {
        this.mCountryID = mCountryID;
    }

    public void setmCountryName(String mCountryName) {
        this.mCountryName = mCountryName;
    }
}
