package com.example.android.themusicgenreator;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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


    //The constructor for the DB
    public MusicGenresDB(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * A method for getting a Genre from the DB based on the genreID
     * @param genreID the unique ID of the Genre
     * @return returns an object of type Genre
     */
    public Genre getGenre(int genreID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query =
                "SELECT * FROM " + GENRE_TABLE_NAME + " WHERE " +
                        GENRE_GENRE_ID + " = " + genreID + ";";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        String genreName = c.getString(c.getColumnIndex(GENRE_GENRE_NAME));
        c.close();
        return new Genre(genreID, genreName);
    }

    /**
     * A method for getting a City from the DB based on the cityID
     * @param cityID the unique ID of the City
     * @return returns an object of type City
     */
    public City getCity(int cityID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query =
                "SELECT * FROM " + CITY_TABLE_NAME + " WHERE " +
                        CITY_CITY_ID + " = " + cityID + ";";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        String cityName = c.getString(c.getColumnIndex(CITY_CITY_NAME));
        c.close();
        return new City(cityID, cityName);
    }

    /**
     * A method for getting a Country from the DB based on the countryID
     * @param countryID the unique ID of the Country
     * @return returns an object of type Country
     */
    public Country getCountry(int countryID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query =
                "SELECT * FROM " + COUNTRY_TABLE_NAME + " WHERE " +
                        COUNTRY_COUNTRY_ID + " = " + countryID + ";";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        String countryName = c.getString(c.getColumnIndex(COUNTRY_COUNTRY_NAME));
        c.close();
        return new Country(countryID, countryName);
    }

    /**
     * A method for getting a Playlist from the DB based on the playlistID
     * @param playlistID the unique ID of the Playlist
     * @return returns an object of type Playlist
     */
    public Playlist getPlaylist(int playlistID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query =
                "SELECT * FROM " + PLAYLIST_TABLE_NAME + " WHERE " +
                        PLAYLIST_PLAYLIST_ID + " = " + playlistID + ";";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        String playlistName = c.getString(c.getColumnIndex(PLAYLIST_PLAYLIST_NAME));
        String link = c.getString(c.getColumnIndex(PLAYLIST_LINK));
        int streamingServiceID = c.getInt(c.getColumnIndex(PLAYLIST_STREAMING_SERVICE_ID));
        c.close();
        return new Playlist(playlistID, playlistName, link, streamingServiceID);
    }

    /**
     * A method for getting a StreamingService from the DB based on the streamingServiceID
     * @param streamingServiceID the unique ID of the StreamingService
     * @return returns an object of type StreamingService
     */
    public StreamingService getStreamingService(int streamingServiceID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query =
                "SELECT * FROM " + STREAMING_SERVICE_TABLE_NAME + " WHERE " +
                        STREAMING_SERVICE_STREAMING_SERVICE_ID + " = " + streamingServiceID + ";";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        String serviceName = c.getString(c.getColumnIndex(STREAMING_SERVICE_SERVICE_NAME));
        c.close();
        return new StreamingService(streamingServiceID, serviceName);
    }

    /**
     * A method for getting an array of Playlists based on the genre of music they contain
     * @param genreID the unique ID of the Genre we are searching with
     * @return returns an array of Playlists
     */
    public Playlist[] getPlaylistByGenre(int genreID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query =
                "SELECT " + GENRE_PLAYLIST_PLAYLIST_ID + " FROM " + GENRE_PLAYLIST_TABLE_NAME +
                        " WHERE " + GENRE_PLAYLIST_GENRE_ID + " = " + genreID + ";";
        Cursor c = db.rawQuery(query, null);
        if(c != null){
            c.moveToFirst();
            Playlist[] playlists = new Playlist[c.getCount()];
            for (int i = 0; i < playlists.length; i++) {
                int playlistID = c.getInt(c.getColumnIndex(GENRE_PLAYLIST_PLAYLIST_ID));
                playlists[i] = getPlaylist(playlistID);
                c.moveToNext();
            }
            c.close();
            return playlists;
        }
        return null;
    }

    /**
     * A method for getting an array of Genres based on the playlists they are in
     * @param playlistID the unique ID of the Playlist we are searching with
     * @return returns an array of Genres
     */
    public Genre[] getGenreByPlaylist(int playlistID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query =
                "SELECT " + GENRE_PLAYLIST_GENRE_ID + " FROM " + GENRE_PLAYLIST_TABLE_NAME +
                        " WHERE " + GENRE_PLAYLIST_PLAYLIST_ID + " = " + playlistID + ";";
        Cursor c = db.rawQuery(query, null);
        if(c != null){
            c.moveToFirst();
            Genre[] genres = new Genre[c.getCount()];
            for (int i = 0; i < genres.length; i++) {
                int genreID = c.getInt(c.getColumnIndex(GENRE_PLAYLIST_GENRE_ID));
                genres[i] = getGenre(genreID);
                c.moveToNext();
            }
            c.close();
            return genres;
        }
        return null;
    }

    /**
     * A method for getting an array of Genres based on the Cities that have produced much of
     * the music and influenced many of the artists
     * @param cityID the unique ID of the City we are searching with
     * @return returns an array of Genres
     */
    public Genre[] getGenreByCity(int cityID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query =
                "SELECT " + GENRE_CITY_GENRE_ID + " FROM " + GENRE_CITY_TABLE_NAME +
                        " WHERE " + GENRE_CITY_CITY_ID + " = " + cityID + ";";
        Cursor c = db.rawQuery(query, null);
        if(c != null){
            c.moveToFirst();
            Genre[] genres = new Genre[c.getCount()];
            for (int i = 0; i < genres.length; i++) {
                int genreID = c.getInt(c.getColumnIndex(GENRE_CITY_GENRE_ID));
                genres[i] = getGenre(genreID);
                c.moveToNext();
            }
            c.close();
            return genres;
        }
        return null;
    }

    /**
     * A method for getting an array of Genres based on the Countries where the genre
     * originated or was very influential
     * @param countryID the unique ID of the Country we are searching with
     * @return returns an array of Genres
     */
    public Genre[] getGenreByCountry(int countryID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query =
                "SELECT " + GENRE_COUNTRY_GENRE_ID + " FROM " + GENRE_COUNTRY_TABLE_NAME +
                        " WHERE " + GENRE_COUNTRY_COUNTRY_ID + " = " + countryID + ";";
        Cursor c = db.rawQuery(query, null);
        if(c != null){
            c.moveToFirst();
            Genre[] genres = new Genre[c.getCount()];
            for (int i = 0; i < genres.length; i++) {
                int genreID = c.getInt(c.getColumnIndex(GENRE_COUNTRY_GENRE_ID));
                genres[i] = getGenre(genreID);
                c.moveToNext();
            }
            c.close();
            return genres;
        }
        return null;
    }





}
