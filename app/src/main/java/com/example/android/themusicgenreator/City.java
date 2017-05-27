package com.example.android.themusicgenreator;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A class for Cities
 */

public class City implements Parcelable {

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

    //Parcelable methods below....
    private City(Parcel in){
        this.mCityID = in.readInt();
        this.mCityName = in.readString();
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeInt(mCityID);
        dest.writeString(mCityName);
    }

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };
}
