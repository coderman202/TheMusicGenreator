package com.example.android.themusicgenreator;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A class for Playlists
 */

public class Playlist implements Parcelable {

    private int mPlaylistID;
    private String mPlaylistName;
    private String mLink;
    private int mStreamingServiceID;

    /**
     * Principle constructor for the Playlist class
     *
     * @param mPlaylistID         int ID of the Playlist
     * @param mPlaylistName       String name of the Playlist
     * @param mLink               A link for the playlist, such as a hyperlink, represented by type String
     * @param mStreamingServiceID int ID of the Streaming Service the playlist is on
     */
    public Playlist(int mPlaylistID, String mPlaylistName, String mLink, int mStreamingServiceID) {
        this.mPlaylistID = mPlaylistID;
        this.mPlaylistName = mPlaylistName;
        this.mLink = mLink;
        this.mStreamingServiceID = mStreamingServiceID;
    }

    /**
     * Secondary constructor of the Playlist class, not setting
     * the int ID of the Playlist via a parameter
     *
     * @param mPlaylistName       String name of the Playlist
     * @param mLink               A link for the playlist, such as a hyperlink, represented by type String
     * @param mStreamingServiceID int ID of the Streaming Service the playlist is on
     */
    public Playlist(String mPlaylistName, String mLink, int mStreamingServiceID) {
        this.mPlaylistName = mPlaylistName;
        this.mLink = mLink;
        this.mStreamingServiceID = mStreamingServiceID;
    }

    /**
     * get() method for the ID
     *
     * @return int ID of the Playlist
     */
    public int getmPlaylistID() {
        return mPlaylistID;
    }

    /**
     * get() method for the name of the playlist
     *
     * @return String name of the Playlist
     */
    public String getmPlaylistName() {
        return mPlaylistName;
    }

    /**
     * get() method for the link to the playlist
     *
     * @return String link to the Playlist
     */
    public String getmLink() {
        return mLink;
    }

    /**
     * get() method for the ID of the Streaming Service
     *
     * @return int ID of the Streaming Service
     */
    public int getmStreamingServiceID() {
        return mStreamingServiceID;
    }

    /**
     * set() method for the playlist ID
     *
     * @param mPlaylistID int ID of the playlist
     */
    public void setmPlaylistID(int mPlaylistID) {
        this.mPlaylistID = mPlaylistID;
    }

    /**
     * set() method for the playlist name
     *
     * @param mPlaylistName String name of the playlist
     */
    public void setmPlaylistName(String mPlaylistName) {
        this.mPlaylistName = mPlaylistName;
    }

    /**
     * set() method for the playlist link
     *
     * @param mLink String link to the playlist
     */
    public void setmLink(String mLink) {
        this.mLink = mLink;
    }

    /**
     * set() method for the Streaming Service ID
     *
     * @param mStreamingServiceID int ID of the Streaming Service
     */
    public void setmStreamingServiceID(int mStreamingServiceID) {
        this.mStreamingServiceID = mStreamingServiceID;
    }

    //Parcelable methods below....
    private Playlist(Parcel in) {
        this.mPlaylistID = in.readInt();
        this.mPlaylistName = in.readString();
        this.mLink = in.readString();
        this.mStreamingServiceID = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mPlaylistID);
        dest.writeString(mPlaylistName);
        dest.writeString(mLink);
        dest.writeInt(mStreamingServiceID);
    }

    public static final Creator<Playlist> CREATOR = new Creator<Playlist>() {
        @Override
        public Playlist createFromParcel(Parcel in) {
            return new Playlist(in);
        }

        @Override
        public Playlist[] newArray(int size) {
            return new Playlist[size];
        }
    };
}