package com.example.android.themusicgenreator;

/**
 * A class for Streaming Services such as Spotify
 */

public class StreamingService {

    private int mStreamingServiceID;
    private String mServiceName;

    public StreamingService(int mStreamingServiceID, String mServiceName){
        this.mStreamingServiceID = mStreamingServiceID;
        this.mServiceName = mServiceName;
    }

    public StreamingService(String mServiceName){
        this.mServiceName = mServiceName;
    }

    public int getmStreamingServiceID() {
        return mStreamingServiceID;
    }

    public String getmCountryName() {
        return mServiceName;
    }

    public void setmStreamingServiceID(int mStreamingServiceID) {
        this.mStreamingServiceID = mStreamingServiceID;
    }

    public void setmServiceName(String mServiceName) {
        this.mServiceName = mServiceName;
    }
}
