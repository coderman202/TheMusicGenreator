package com.example.android.themusicgenreator;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A class for Countries
 */

public class Country implements Parcelable {

    private int mCountryID;
    private String mCountryName;

    /**
     * Principle constructor of the Country class.
     *
     * @param mCountryID   int ID of the country
     * @param mCountryName String name of the country
     */
    public Country(int mCountryID, String mCountryName) {
        this.mCountryID = mCountryID;
        this.mCountryName = mCountryName;
    }

    /**
     * Secondary constructor taking only String name as a parameter
     *
     * @param mCountryName String name of the country
     */
    public Country(String mCountryName) {
        this.mCountryName = mCountryName;
    }

    /**
     * get() method for the country ID
     *
     * @return returns int ID of the Country
     */
    public int getmCountryID() {
        return mCountryID;
    }

    /**
     * get() method for the country name
     *
     * @return returns String name of the country
     */
    public String getmCountryName() {
        return mCountryName;
    }

    /**
     * set() method for the country ID
     *
     * @param mCountryID int ID of the country
     */
    public void setmCountryID(int mCountryID) {
        this.mCountryID = mCountryID;
    }

    /**
     * set() method for the name of the country
     *
     * @param mCountryName String name of the country
     */
    public void setmCountryName(String mCountryName) {
        this.mCountryName = mCountryName;
    }

    //Parcelable methods below....
    private Country(Parcel in) {
        this.mCountryID = in.readInt();
        this.mCountryName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mCountryID);
        dest.writeString(mCountryName);
    }

    public static final Creator<Country> CREATOR = new Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel in) {
            return new Country(in);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };
}
