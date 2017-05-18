package com.example.android.themusicgenreator;

/**
 * A class for Cities
 */

public class City {

    private int mCityID;
    private String mCityName;

    /**
     * Principle constructor for the City class
     * @param mCityID an int ID of the City
     * @param mCityName a String name of the City
     */
    public City(int mCityID, String mCityName){
        this.mCityID = mCityID;
        this.mCityName = mCityName;
    }

    /**
     * Secondary constructor using onle the String name
     * @param mCityName a String name of the City
     */
    public City(String mCityName){
        this.mCityName = mCityName;
    }

    /**
     * get() method for city ID
     * @return returns int ID of the city
     */
    public int getmCityID() {
        return mCityID;
    }

    /**
     * get() method for city name
     * @return returns a String name of the city
     */
    public String getmCityName() {
        return mCityName;
    }

    /**
     * set() method for city ID
     * @param mCityID int ID of the city
     */
    public void setmCityID(int mCityID) {
        this.mCityID = mCityID;
    }

    /**
     * set() method for city name
     * @param mCityName String name of the city
     */
    public void setmCityName(String mCityName) {
        this.mCityName = mCityName;
    }
}
