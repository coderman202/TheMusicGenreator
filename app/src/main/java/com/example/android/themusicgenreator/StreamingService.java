package com.example.android.themusicgenreator;

/**
 * A class for Streaming Services such as Spotify
 */

public class StreamingService {

    private int mStreamingServiceID;
    private String mServiceName;
    private String mPlayStoreLink;
    private Integer mIcon;
    private final Integer[] mIconList = {
            R.drawable.amazon_prime_music_icon, R.drawable.apple_music_icon,
            R.drawable.deezer_icon, R.drawable.google_play_music_icon, R.drawable.pandora_icon,
            R.drawable.soundcloud_icon, R.drawable.spotify_icon
    };

    /**
     * Principle constructor of the StreamingService
     * @param mStreamingServiceID int ID of the Streaming Service
     * @param mServiceName String name of the Streaming Service, such as Spotify
     * @param mPlayStoreLink String link to the app on the Google Play store, in case the
     *                       user does not have the app already installed
     */
    public StreamingService(int mStreamingServiceID, String mServiceName, String mPlayStoreLink){
        this.mStreamingServiceID = mStreamingServiceID;
        this.mServiceName = mServiceName;
        this.mPlayStoreLink = mPlayStoreLink;
        this.mIcon = mIconList[mStreamingServiceID - 1];
    }

    /**
     * Secondary constructor of the StreamingService
     * @param mStreamingServiceID int ID of the Streaming Service
     * @param mServiceName String name of the Streaming Service
     */
    public StreamingService(int mStreamingServiceID, String mServiceName){
        this.mStreamingServiceID = mStreamingServiceID;
        this.mServiceName = mServiceName;
        this.mIcon = mIconList[mStreamingServiceID];
    }

    /**
     * Tertiary constructor of StreamingService, only taking String name as the parameter
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
    public String getmServiceName() {
        return mServiceName;
    }

    /**
     * get() method for the play store link
     * @return returns the String link to the Streaming Service on the google play store
     */
    public String getmPlayStoreLink() {
        return mPlayStoreLink;
    }

    /**
     * get() method for the icon to display the streaming service
     * @return returns an Integer of the icon
     */
    public Integer getmIcon() {
        return mIcon;
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

    /**
     * set() method for the link
     * @param mPlayStoreLink String link to the Streaming Service
     */
    public void setmPlayStoreLink(String mPlayStoreLink) {
        this.mPlayStoreLink = mPlayStoreLink;
    }

    /**
     * set() method for the icon
     * @param mIcon take a new icon
     */
    public void setmIcon(Integer mIcon) {
        this.mIcon = mIcon;
        this.mIconList[this.mStreamingServiceID - 1] = mIcon;
    }
}
