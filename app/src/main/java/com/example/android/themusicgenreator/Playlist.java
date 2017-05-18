package com.example.android.themusicgenreator;

/**
 * A class for Playlists
 */

public class Playlist {

    private int mPlaylistID;
    private String mPlaylistName;
    private String mLink;
    private int mStreamingServiceID;

    /**
     * Principle constructor for the Playlist class
     * @param mPlaylistID int ID of the Playlist
     * @param mPlaylistName String name of the Playlist
     * @param mLink A link for the playlist, such as a hyperlink, represented by type String
     * @param mStreamingServiceID int ID of the Streaming Service the playlist is on
     */
    public Playlist(int mPlaylistID, String mPlaylistName, String mLink, int mStreamingServiceID){
        this.mPlaylistID = mPlaylistID;
        this.mPlaylistName = mPlaylistName;
        this.mLink = mLink;
        this.mStreamingServiceID = mStreamingServiceID;
    }

    /**
     * Secondary constructor of the Playlist class, not setting
     * the int ID of the Playlist via a parameter
     * @param mPlaylistName String name of the Playlist
     * @param mLink A link for the playlist, such as a hyperlink, represented by type String
     * @param mStreamingServiceID int ID of the Streaming Service the playlist is on
     */
    public Playlist(String mPlaylistName, String mLink, int mStreamingServiceID){
        this.mPlaylistName = mPlaylistName;
        this.mLink = mLink;
        this.mStreamingServiceID = mStreamingServiceID;
    }

    /**
     * get() method for the ID
     * @return int ID of the Playlist
     */
    public int getmPlaylistID() {
        return mPlaylistID;
    }

    /**
     * get() method for the name of the playlist
     * @return String name of the Playlist
     */
    public String getmPlaylistName() {
        return mPlaylistName;
    }

    /**
     * get() method for the link to the playlist
     * @return String link to the Playlist
     */
    public String getmLink() {
        return mLink;
    }

    /**
     * get() method for the ID of the Streaming Service
     * @return int ID of the Streaming Service
     */
    public int getmStreamingServiceID() {
        return mStreamingServiceID;
    }

    /**
     * set() method for the playlist ID
     * @param mPlaylistID int ID of the playlist
     */
    public void setmPlaylistID(int mPlaylistID) {
        this.mPlaylistID = mPlaylistID;
    }

    /**
     * set() method for the playlist name
     * @param mPlaylistName String name of the playlist
     */
    public void setmPlaylistName(String mPlaylistName) {
        this.mPlaylistName = mPlaylistName;
    }

    /**
     * set() method for the playlist link
     * @param mLink String link to the playlist
     */
    public void setmLink(String mLink) {
        this.mLink = mLink;
    }

    /**
     * set() method for the Streaming Service ID
     * @param mStreamingServiceID int ID of the Streaming Service
     */
    public void setmStreamingServiceID(int mStreamingServiceID) {
        this.mStreamingServiceID = mStreamingServiceID;
    }
}