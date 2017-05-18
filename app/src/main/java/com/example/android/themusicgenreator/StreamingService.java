package com.example.android.themusicgenreator;

/**
 * A class for Streaming Services such as Spotify
 */

public class StreamingService {

    private int mStreamingServiceID;
    private String mServiceName;

    /**
     * Principle constructor of the StreamingService
     * @param mStreamingServiceID int ID of the Streaming Service
     * @param mServiceName String name of the Streaming Service
     */
    public StreamingService(int mStreamingServiceID, String mServiceName){
        this.mStreamingServiceID = mStreamingServiceID;
        this.mServiceName = mServiceName;
    }

    /**
     * Secondary constructor of StreamingService, only taking String name as the parameter
     * @param mServiceName the String name of the Streaming Service
     */
    public StreamingService(String mServiceName){
        this.mServiceName = mServiceName;
    }

    /**
     * get() method for the ID
     * @return returns int ID of the Streaming Service
     */
    public int getmStreamingServiceID() {
        return mStreamingServiceID;
    }

    /**
     * get() method for the name
     * @return returns the String name of the Streaming Service
     */
    public String getmCountryName() {
        return mServiceName;
    }

    /**
     * set() method for the ID
     * @param mStreamingServiceID int ID of the Streaming Service
     */
    public void setmStreamingServiceID(int mStreamingServiceID) {
        this.mStreamingServiceID = mStreamingServiceID;
    }

    /**
     * set() method for the name
     * @param mServiceName String name of the Streaming Service
     */
    public void setmServiceName(String mServiceName) {
        this.mServiceName = mServiceName;
    }
}
