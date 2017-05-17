package com.example.android.themusicgenreator;

/**
 * A class for Playlists
 */

public class Playlist {

    private int mPlaylistID;
    private String mPlaylistName;
    private String mLink;
    private int mStreamingServiceID;

    public Playlist(int mPlaylistID, String mPlaylistName, String mLink, int mStreamingServiceID){
        this.mPlaylistID = mPlaylistID;
        this.mPlaylistName = mPlaylistName;
        this.mLink = mLink;
        this.mStreamingServiceID = mStreamingServiceID;
    }

    public Playlist(String mPlaylistName, String mLink, int mStreamingServiceID){
        this.mPlaylistName = mPlaylistName;
        this.mLink = mLink;
        this.mStreamingServiceID = mStreamingServiceID;
    }

    public int getmPlaylistID() {
        return mPlaylistID;
    }

    public String getmPlaylistName() {
        return mPlaylistName;
    }

    public String getmLink() {
        return mLink;
    }

    public int getmStreamingServiceID() {
        return mStreamingServiceID;
    }

    public void setmPlaylistID(int mPlaylistID) {
        this.mPlaylistID = mPlaylistID;
    }

    public void setmPlaylistName(String mPlaylistName) {
        this.mPlaylistName = mPlaylistName;
    }

    public void setmLink(String mLink) {
        this.mLink = mLink;
    }

    public void setmStreamingServiceID(int mStreamingServiceID) {
        this.mStreamingServiceID = mStreamingServiceID;
    }
}