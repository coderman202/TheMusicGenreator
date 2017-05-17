package com.example.android.themusicgenreator;


import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Class for handling all interactions with the Music Genres Database
 */

public class MusicGenresDB extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "MusicGenresDB.db";
    private static final int DATABASE_VERSION = 1;

    //Declaring all the table names in the DB
    private static final String CITY_TABLE_NAME = "City";
    private static final String COUNTRY_TABLE_NAME = "Country";
    private static final String GENRE_TABLE_NAME = "Genre";
    private static final String PLAYLIST_TABLE_NAME = "Playlist";
    private static final String STREAMING_SERVICE_TABLE_NAME = "StreamingService";
    private static final String GENRE_CITY_TABLE_NAME = "GenreCity";
    private static final String GENRE_COUNTRY_TABLE_NAME = "GenreCountry";
    private static final String GENRE_PLAYLIST_TABLE_NAME = "GenrePlaylist";

    //Column names for each table
    private static final String CITY_CITY_ID = "CityID";
    private static final String CITY_CITY_NAME = "CityName";

    private static final String COUNTRY_COUNTRY_ID = "CountryID";
    private static final String COUNTRY_COUNTRY_NAME = "CountryName";

    private static final String GENRE_GENRE_ID = "GenreID";
    private static final String GENRE_GENRE_NAME = "GenreName";

    private static final String STREAMING_SERVICE_STREAMING_SERVICE_ID = "StreamingServiceID";
    private static final String STREAMING_SERVICE_SERVICE_NAME = "ServiceName";

    private static final String PLAYLIST_PLAYLIST_ID = "PlaylistID";
    private static final String PLAYLIST_PLAYLIST_NAME = "PlaylistName";
    private static final String PLAYLIST_LINK = "Link";
    private static final String PLAYLIST_STREAMING_SERVICE_ID = "StreamingServiceID";

    private static final String GENRE_PLAYLIST_GENRE_PLAYLIST_ID = "GenrePlaylistID";
    private static final String GENRE_PLAYLIST_GENRE_ID = "GenreID";
    private static final String GENRE_PLAYLIST_PLAYLIST_ID = "PlaylistID";

    private static final String GENRE_COUNTRY_GENRE_COUNTRY_ID = "GenreCountryID";
    private static final String GENRE_COUNTRY_GENRE_ID = "GenreID";
    private static final String GENRE_COUNTRY_COUNTRY_ID = "CountryID";

    private static final String GENRE_CITY_GENRE_CITY_ID = "GenreCityID";
    private static final String GENRE_CITY_GENRE_ID = "GenreID";
    private static final String GENRE_CITY_CITY_ID = "CityID";



    public MusicGenresDB(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



}
