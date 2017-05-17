package com.example.android.themusicgenreator;

/**
 * A class for Cities
 */

public class City {

    private int mCityID;
    private String mCityName;

    public City(int mCityID, String mCityName){
        this.mCityID = mCityID;
        this.mCityName = mCityName;
    }

    public City(String mCityName){
        this.mCityName = mCityName;
    }

    public int getmCityID() {
        return mCityID;
    }

    public String getmCityName() {
        return mCityName;
    }

    public void setmCityID(int mCityID) {
        this.mCityID = mCityID;
    }

    public void setmCityName(String mCityName) {
        this.mCityName = mCityName;
    }
}
