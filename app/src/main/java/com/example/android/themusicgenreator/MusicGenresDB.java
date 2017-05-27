package com.example.android.themusicgenreator;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.io.File;
import java.util.Arrays;

/**
 * Class for handling all interactions with the Music Genres Database
 */

class MusicGenresDB extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "MusicGenresDB.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DB_STORAGE_DIR = "TheMusicGenreator";

    //Declaring an instance of the DB
    private static MusicGenresDB instance;

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
    private static final String STREAMING_SERVICE_PLAY_STORE_LINK = "PlayStoreLink";

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
    private MusicGenresDB(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Quick check to initialise a storage directory for the database
    static {
        File dir = new File(DB_STORAGE_DIR);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                Log.e("BEEP", "Create database folder failed! BANG!");
            }
        }
    }

    /**
     * Creates an instance of the DB
     * @param context passing context as the param
     * @return returns an instance of the DB
     */
    static MusicGenresDB getInstance(Context context){
        if(instance == null){
            instance = new MusicGenresDB(context);
        }
        return instance;
    }

    /**
     * A method for getting a Genre from the DB based on the genreID
     * @param genreID the unique ID of the Genre
     * @return returns an object of type Genre
     */
    Genre getGenre(int genreID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query =
                "SELECT * FROM " + GENRE_TABLE_NAME + " WHERE " +
                        GENRE_GENRE_ID + " = " + genreID + ";";
        Cursor c = db.rawQuery(query, null);
        if(c != null && c.moveToFirst()){
            c.moveToFirst();
            String genreName = c.getString(c.getColumnIndex(GENRE_GENRE_NAME));
            c.close();
            return new Genre(genreID, genreName);
        }
        return null;
    }

    /**
     * A method for getting a Genre from the DB based on the genreName
     * @param genreName a String name of the Genre
     * @return returns an object of type Genre
     */
    Genre getGenreByName(String genreName){
        SQLiteDatabase db = this.getReadableDatabase();
        String query =
                "SELECT * FROM " + GENRE_TABLE_NAME + " WHERE " +
                        GENRE_GENRE_NAME + " = '" + genreName + "';";
        Cursor c = db.rawQuery(query, null);
        if(c != null && c.moveToFirst()){
            c.moveToFirst();
            int genreID  = c.getInt(c.getColumnIndex(GENRE_GENRE_ID));
            c.close();
            return new Genre(genreID, genreName);
        }
        return null;
    }

    /**
     * A method for getting every genre in the DB
     * @return returns an array of type Genre
     */
    Genre[] getAllGenres(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query =
                "SELECT * FROM " + GENRE_TABLE_NAME + ";";
        Cursor c = db.rawQuery(query, null);
        if(c != null && c.moveToFirst()){
            c.moveToFirst();
            Genre[] genres = new Genre[c.getCount()];
            for (int i = 0; i < genres.length; i++) {
                int genreID = c.getInt(c.getColumnIndex(GENRE_GENRE_ID));
                String genreName = c.getString(c.getColumnIndex(GENRE_GENRE_NAME));
                genres[i] = new Genre(genreID, genreName);
                c.moveToNext();
            }
            c.close();
            return genres;
        }
        return null;
    }

    /**
     * A method for getting every Genre beginning with a particular letter
     * @param letter a letter of type char
     * @return returns an array of Genres beginning with @param letter
     */
    Genre[] getGenreByLetter(char letter){
        SQLiteDatabase db = this.getReadableDatabase();
        String query =
                "SELECT * FROM " + GENRE_TABLE_NAME + " WHERE " +
                        GENRE_GENRE_NAME + " LIKE '" + letter + "%';";
        Cursor c = db.rawQuery(query, null);
        if(c != null && c.moveToFirst()){
            c.moveToFirst();
            Genre[] genres = new Genre[c.getCount()];
            for (int i = 0; i < genres.length; i++) {
                int genreID = c.getInt(c.getColumnIndex(GENRE_GENRE_ID));
                String genreName = c.getString(c.getColumnIndex(GENRE_GENRE_NAME));
                genres[i] = new Genre(genreID, genreName);
                c.moveToNext();
            }
            c.close();
            return genres;
        }
        return null;
    }

    /**
     * A method for getting a City from the DB based on the cityID
     * @param cityID the unique ID of the City
     * @return returns an object of type City
     */
    City getCity(int cityID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query =
                "SELECT * FROM " + CITY_TABLE_NAME + " WHERE " +
                        CITY_CITY_ID + " = " + cityID + ";";
        Cursor c = db.rawQuery(query, null);
        if(c != null && c.moveToFirst()){
            c.moveToFirst();
            String cityName = c.getString(c.getColumnIndex(CITY_CITY_NAME));
            c.close();
            return new City(cityID, cityName);
        }
        return null;
    }

    /**
     * A method for getting a City from the DB based on the city name
     * @param cityName the name of the City
     * @return returns an object of type City
     */
    City getCityByName(String cityName){
        SQLiteDatabase db = this.getReadableDatabase();
        String query =
                "SELECT * FROM " + CITY_TABLE_NAME + " WHERE " +
                        CITY_CITY_NAME + " = '" + cityName + "';";
        Cursor c = db.rawQuery(query, null);
        if(c != null && c.moveToFirst()){
            c.moveToFirst();
            cityName = c.getString(c.getColumnIndex(CITY_CITY_NAME));
            int cityID = c.getInt(c.getColumnIndex(CITY_CITY_ID));
            c.close();
            return new City(cityID, cityName);
        }
        return null;
    }

    /**
     * A method for getting every City beginning with a particular letter
     * @param letter a letter of type char
     * @return returns an array of Cities beginning with @param letter
     */
    City[] getCityByLetter(char letter){
        SQLiteDatabase db = this.getReadableDatabase();
        String query =
                "SELECT * FROM " + CITY_TABLE_NAME + " WHERE " +
                        CITY_CITY_NAME + " LIKE '" + letter + "%';";
        Cursor c = db.rawQuery(query, null);
        if(c != null && c.moveToFirst()){
            c.moveToFirst();
            City[] cities = new City[c.getCount()];
            for (int i = 0; i < cities.length; i++) {
                int cityID = c.getInt(c.getColumnIndex(CITY_CITY_ID));
                String cityName = c.getString(c.getColumnIndex(CITY_CITY_NAME));
                cities[i] = new City(cityID, cityName);
                c.moveToNext();
            }
            c.close();
            return cities;
        }
        return null;
    }

    /**
     * A method for getting every city in the DB
     * @return returns an array of type City
     */
    City[] getAllCities(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query =
                "SELECT * FROM " + CITY_TABLE_NAME + ";";
        Cursor c = db.rawQuery(query, null);
        if(c != null && c.moveToFirst()){
            c.moveToFirst();
            City[] cities = new City[c.getCount()];
            for (int i = 0; i < cities.length; i++) {
                int cityID = c.getInt(c.getColumnIndex(CITY_CITY_ID));
                String cityName = c.getString(c.getColumnIndex(CITY_CITY_NAME));
                cities[i] = new City(cityID, cityName);
                c.moveToNext();
            }
            c.close();
            return cities;
        }
        return null;
    }

    /**
     * A method for getting the total number of genres that have originated in or
     * been heavily influenced by a particular city
     * @param cityID the unique ID of the City
     * @return returns the total number of genres as an int
     */
    int getNumGenresByCity(int cityID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query =
                "SELECT * FROM " + GENRE_CITY_TABLE_NAME + " WHERE " +
                        GENRE_CITY_CITY_ID + " = " + cityID + ";";
        Cursor c = db.rawQuery(query, null);
        if(c != null && c.moveToFirst()){
            c.moveToFirst();
            int numGenres = c.getCount();
            c.close();
            return numGenres;
        }
        return 0;
    }

    /**
     * A method for getting the total number of genres that have originated in or
     * been heavily influenced by a particular country.
     * @param countryID the unique ID of the Country
     * @return returns the total number of genres as an int
     */
    int getNumGenresByCountry(int countryID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query =
                "SELECT * FROM " + GENRE_COUNTRY_TABLE_NAME + " WHERE " +
                        GENRE_COUNTRY_COUNTRY_ID + " = " + countryID + ";";
        Cursor c = db.rawQuery(query, null);
        if(c != null && c.moveToFirst()){
            c.moveToFirst();
            int numGenres = c.getCount();
            c.close();
            return numGenres;
        }
        return 0;
    }

    int getNumPlaylistsByStreamingService(int serviceID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query =
                "SELECT * FROM " + PLAYLIST_TABLE_NAME + " WHERE " +
                        PLAYLIST_STREAMING_SERVICE_ID + " = " + serviceID + ";";
        Cursor c = db.rawQuery(query, null);
        if(c != null && c.moveToFirst()){
            c.moveToFirst();
            int numPlaylists = c.getCount();
            c.close();
            return numPlaylists;
        }
        return 0;
    }

    /**
     * A method for getting a Country from the DB based on the countryID
     * @param countryID the unique ID of the Country
     * @return returns an object of type Country
     */
    Country getCountry(int countryID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query =
                "SELECT * FROM " + COUNTRY_TABLE_NAME + " WHERE " +
                        COUNTRY_COUNTRY_ID + " = " + countryID + ";";
        Cursor c = db.rawQuery(query, null);
        if(c != null && c.moveToFirst()){
            c.moveToFirst();
            String countryName = c.getString(c.getColumnIndex(COUNTRY_COUNTRY_NAME));
            c.close();
            return new Country(countryID, countryName);
        }
        return null;
    }

    /**
     * A method for getting a Country from the DB based on the countryID
     * @param countryName the unique ID of the Country
     * @return returns an object of type Country
     */
    Country getCountryByName(String countryName){
        SQLiteDatabase db = this.getReadableDatabase();
        String query =
                "SELECT * FROM " + COUNTRY_TABLE_NAME + " WHERE " +
                        COUNTRY_COUNTRY_NAME + " = '" + countryName + "';";
        Cursor c = db.rawQuery(query, null);
        if(c != null && c.moveToFirst()){
            c.moveToFirst();
            countryName = c.getString(c.getColumnIndex(COUNTRY_COUNTRY_NAME));
            int countryID = c.getInt(c.getColumnIndex(COUNTRY_COUNTRY_ID));
            c.close();
            return new Country(countryID, countryName);
        }
        return null;
    }

    /**
     * A method for getting every City beginning with a particular letter
     * @param letter a letter of type char
     * @return returns an array of Cities beginning with @param letter
     */
    Country[] getCountryByLetter(char letter){
        SQLiteDatabase db = this.getReadableDatabase();
        String query =
                "SELECT * FROM " + COUNTRY_TABLE_NAME + " WHERE " +
                        COUNTRY_COUNTRY_NAME + " LIKE '" + letter + "%';";
        Cursor c = db.rawQuery(query, null);
        if(c != null && c.moveToFirst()){
            c.moveToFirst();
            Country[] countries = new Country[c.getCount()];
            for (int i = 0; i < countries.length; i++) {
                int countryID = c.getInt(c.getColumnIndex(COUNTRY_COUNTRY_ID));
                String countryName = c.getString(c.getColumnIndex(COUNTRY_COUNTRY_NAME));
                countries[i] = new Country(countryID, countryName);
                c.moveToNext();
            }
            c.close();
            return countries;
        }
        return null;
    }

    /**
     * A method for getting every country in the DB
     * @return returns an array of type Country
     */
    Country[] getAllCountries(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query =
                "SELECT * FROM " + COUNTRY_TABLE_NAME + ";";
        Cursor c = db.rawQuery(query, null);
        if(c != null && c.moveToFirst()){
            c.moveToFirst();
            Country[] countries = new Country[c.getCount()];
            for (int i = 0; i < countries.length; i++) {
                int countryID = c.getInt(c.getColumnIndex(COUNTRY_COUNTRY_ID));
                String countryName = c.getString(c.getColumnIndex(COUNTRY_COUNTRY_NAME));
                countries[i] = new Country(countryID, countryName);
                c.moveToNext();
            }
            c.close();
            return countries;
        }
        return null;
    }

    /**
     * A method for getting a Playlist from the DB based on the playlistID
     * @param playlistID the unique ID of the Playlist
     * @return returns an object of type Playlist
     */
    Playlist getPlaylist(int playlistID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query =
                "SELECT * FROM " + PLAYLIST_TABLE_NAME + " WHERE " +
                        PLAYLIST_PLAYLIST_ID + " = " + playlistID + ";";
        Cursor c = db.rawQuery(query, null);
        if(c != null && c.moveToFirst()){
            c.moveToFirst();
            String playlistName = c.getString(c.getColumnIndex(PLAYLIST_PLAYLIST_NAME));
            String link = c.getString(c.getColumnIndex(PLAYLIST_LINK));
            int streamingServiceID = c.getInt(c.getColumnIndex(PLAYLIST_STREAMING_SERVICE_ID));
            c.close();
            return new Playlist(playlistID, playlistName, link, streamingServiceID);
        }
        return null;
    }

    /**
     * A method for getting a StreamingService from the DB based on the streamingServiceID
     * @param streamingServiceID the unique ID of the StreamingService
     * @return returns an object of type StreamingService
     */
    StreamingService getStreamingService(int streamingServiceID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query =
                "SELECT * FROM " + STREAMING_SERVICE_TABLE_NAME + " WHERE " +
                        STREAMING_SERVICE_STREAMING_SERVICE_ID + " = " + streamingServiceID + ";";
        Cursor c = db.rawQuery(query, null);
        if(c != null && c.moveToFirst()){
            c.moveToFirst();
            String serviceName = c.getString(c.getColumnIndex(STREAMING_SERVICE_SERVICE_NAME));
            String playStoreLink = c.getString(c.getColumnIndex(STREAMING_SERVICE_PLAY_STORE_LINK));
            c.close();
            return new StreamingService(streamingServiceID, serviceName, playStoreLink);
        }
        return null;
    }

    /**
     * A method for getting all Streaming Services
     * @return returns an array of type StreamingService
     */
    StreamingService[] getAllStreamingServices(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query =
                "SELECT * FROM " + STREAMING_SERVICE_TABLE_NAME  + ";";
        Cursor c = db.rawQuery(query, null);
        if(c != null && c.moveToFirst()){
            c.moveToFirst();
            StreamingService[] services = new StreamingService[c.getCount()];
            for (int i = 0; i < services.length; i++) {
                String serviceName = c.getString(c.getColumnIndex(STREAMING_SERVICE_SERVICE_NAME));
                int streamingServiceID = c.getInt(c.getColumnIndex(STREAMING_SERVICE_STREAMING_SERVICE_ID));
                String playStoreLink = c.getString(c.getColumnIndex(STREAMING_SERVICE_PLAY_STORE_LINK));
                services[i] = new StreamingService(streamingServiceID, serviceName, playStoreLink);
                c.moveToNext();
            }
            c.close();
            return services;
        }
        return null;
    }

    /**
     * A method for getting the amount of Streaming Services
     * @return returns an int count of the number of StreamingService
     */
    int getNumStreamingServices(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query =
                "SELECT * FROM " + STREAMING_SERVICE_TABLE_NAME  + ";";
        Cursor c = db.rawQuery(query, null);
        if(c != null && c.moveToFirst()) {
            c.moveToFirst();
            int numServices = c.getCount();
            c.close();
            return numServices;
        }
        return 0;
    }

    /**
     * A method for getting an array of Playlists based on the genre of music they contain
     * @param genreID the unique ID of the Genre we are searching with
     * @return returns an array of Playlists
     */
    Playlist[] getPlaylistByGenre(int genreID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query =
                "SELECT " + GENRE_PLAYLIST_PLAYLIST_ID + " FROM " + GENRE_PLAYLIST_TABLE_NAME +
                        " WHERE " + GENRE_PLAYLIST_GENRE_ID + " = " + genreID + ";";
        Cursor c = db.rawQuery(query, null);
        if(c != null && c.moveToFirst()){
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
     * A method for getting an array of Playlists based on the Streaming Service
     * they were created/stored with.
     * @param serviceID the unique ID of the Streaming Service we are searching with
     * @return returns an array of Playlists
     */
    Playlist[] getPlaylistByStreamingService(int serviceID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query =
                "SELECT * FROM " + PLAYLIST_TABLE_NAME +
                        " WHERE " + PLAYLIST_STREAMING_SERVICE_ID + " = " + serviceID + ";";
        Cursor c = db.rawQuery(query, null);
        if(c != null && c.moveToFirst()){
            c.moveToFirst();
            Playlist[] playlists = new Playlist[c.getCount()];
            for (int i = 0; i < playlists.length; i++) {
                int playlistID = c.getInt(c.getColumnIndex(PLAYLIST_PLAYLIST_ID));
                String playlistName = c.getString(c.getColumnIndex(PLAYLIST_PLAYLIST_NAME));
                String link  = c.getString(c.getColumnIndex(PLAYLIST_LINK));
                playlists[i] = new Playlist(playlistID, playlistName, link, serviceID);
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
    Genre[] getGenreByPlaylist(int playlistID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query =
                "SELECT " + GENRE_PLAYLIST_GENRE_ID + " FROM " + GENRE_PLAYLIST_TABLE_NAME +
                        " WHERE " + GENRE_PLAYLIST_PLAYLIST_ID + " = " + playlistID + ";";
        Cursor c = db.rawQuery(query, null);
        if(c != null && c.moveToFirst()){
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
    Genre[] getGenreByCity(int cityID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query =
                "SELECT " + GENRE_CITY_GENRE_ID + " FROM " + GENRE_CITY_TABLE_NAME +
                        " WHERE " + GENRE_CITY_CITY_ID + " = " + cityID + ";";
        Cursor c = db.rawQuery(query, null);
        if(c != null && c.moveToFirst()){
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
    Genre[] getGenreByCountry(int countryID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query =
                "SELECT " + GENRE_COUNTRY_GENRE_ID + " FROM " + GENRE_COUNTRY_TABLE_NAME +
                        " WHERE " + GENRE_COUNTRY_COUNTRY_ID + " = " + countryID + ";";
        Cursor c = db.rawQuery(query, null);
        if(c != null && c.moveToFirst()){
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

    /**
     * A method for getting an array of Cities which influenced a particular genre
     * or where the genre had roots
     * @param genreID the unique ID of the genre we are searching with
     * @return returns an array of City objects
     */
    City[] getCitiesOfInfluence(int genreID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query =
                "SELECT " + GENRE_CITY_CITY_ID + " FROM " + GENRE_CITY_TABLE_NAME +
                        " WHERE " + GENRE_CITY_GENRE_ID + " = " + genreID + ";";
        Cursor c = db.rawQuery(query, null);
        if(c != null && c.moveToFirst()){
            c.moveToFirst();
            City[] cities = new City[c.getCount()];
            for (int i = 0; i < cities.length; i++) {
                int cityID = c.getInt(c.getColumnIndex(GENRE_CITY_CITY_ID));
                cities[i] = getCity(cityID);
                c.moveToNext();
            }
            c.close();
            return cities;
        }
        return null;
    }

    /**
     * A method for getting an array of Countries which influenced a particular genre
     * or where the genre had roots
     * @param genreID the unique ID of the genre we are searching with
     * @return returns an array of Country objects
     */
    Country[] getCountriesOfInfluence(int genreID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query =
                "SELECT " + GENRE_COUNTRY_COUNTRY_ID + " FROM " + GENRE_COUNTRY_TABLE_NAME +
                        " WHERE " + GENRE_COUNTRY_GENRE_ID + " = " + genreID + ";";
        Cursor c = db.rawQuery(query, null);
        if(c != null && c.moveToFirst()){
            c.moveToFirst();
            Country[] countries = new Country[c.getCount()];
            for (int i = 0; i < countries.length; i++) {
                int countryID = c.getInt(c.getColumnIndex(GENRE_COUNTRY_COUNTRY_ID));
                countries[i] = getCountry(countryID);
                c.moveToNext();
            }
            c.close();
            return countries;
        }
        return null;
    }

    /**
     * A method for getting an array of Genres based on the search term the user entered
     * @param searchTerm the search term the user entered
     * @return returns an array of Genres
     */
    Genre[] getGenreBySearchTerm(String searchTerm){
        SQLiteDatabase db = this.getReadableDatabase();
        String query =
                "SELECT * FROM " + GENRE_TABLE_NAME + " WHERE " + GENRE_GENRE_NAME +
                        " LIKE %" + searchTerm + "%;";
        Cursor c = db.rawQuery(query, null);
        if(c != null && c.moveToFirst()){
            c.moveToFirst();
            Genre[] genres = new Genre[c.getCount()];
            for (int i = 0; i < genres.length; i++) {
                int genreID = c.getInt(c.getColumnIndex(GENRE_GENRE_ID));
                String genreName = c.getString(c.getColumnIndex(GENRE_GENRE_NAME));
                genres[i] = new Genre(genreID, genreName);
                c.moveToNext();
            }
            c.close();
            return genres;
        }
        return null;
    }

    /**
     * A method for getting an array of Playlist based on the search term the user entered
     * @param searchTerm the search term the user entered
     * @return returns an array of Playlist
     */
    Playlist[] getPlaylistBySearchTerm(String searchTerm){
        SQLiteDatabase db = this.getReadableDatabase();
        String query =
                "SELECT * FROM " + PLAYLIST_TABLE_NAME + " WHERE " + PLAYLIST_PLAYLIST_NAME +
                        " LIKE %" + searchTerm + "%;";
        Cursor c = db.rawQuery(query, null);
        if(c != null && c.moveToFirst()){
            c.moveToFirst();
            Playlist[] playlists = new Playlist[c.getCount()];
            for (int i = 0; i < playlists.length; i++) {
                int playlistID = c.getInt(c.getColumnIndex(PLAYLIST_PLAYLIST_ID));
                String playlistName = c.getString(c.getColumnIndex(PLAYLIST_PLAYLIST_NAME));
                String link = c.getString(c.getColumnIndex(PLAYLIST_LINK));
                int streamingServiceID = c.getInt(c.getColumnIndex(PLAYLIST_STREAMING_SERVICE_ID));
                playlists[i] = new Playlist(playlistID, playlistName, link, streamingServiceID);
                c.moveToNext();
            }
            c.close();
            return playlists;
        }
        return null;
    }


    /**
     * A method for sorting an array of Genres alphabetically.
     * Create an String Array of the genre names. Use the Arrays.sort() method to sort them.
     * Then create a new array of Genres. Loop through the original Genre Array and insert the
     * genres in alphabetical order.
     * @param genresArray a array of genres
     * @return returns the sorted array
     */
    Genre[] sortGenresAtoZ(Genre[] genresArray){

        String[] namesArray = new String[genresArray.length];
        for (int i = 0; i < genresArray.length; i++) {
            namesArray[i] = genresArray[i].getmGenreName();
        }
        Arrays.sort(namesArray);

        Genre[] sortedGenresArray = new Genre[genresArray.length];
        for (int i = 0; i < genresArray.length; i++) {
            int count = 0;
            while(!namesArray[count].equals(genresArray[i].getmGenreName())){
                count++;
            }
            sortedGenresArray[i] = genresArray[count];
        }
        return sortedGenresArray;
    }

    /**
     * A method for adding a new Genre to the database
     * @param genre A Genre object parameter
     */
    void addGenre(Genre genre){
        SQLiteDatabase db = this.getWritableDatabase();
        String insert = "INSERT INTO "+ GENRE_TABLE_NAME + " (" + GENRE_GENRE_NAME +
                ") VALUES ('" + genre.getmGenreName() + "');";
        db.execSQL(insert);
        db.close();
    }

    /**
     * A method for adding a new City to the database
     * @param city A City object parameter
     */
    void addCity(City city){
        SQLiteDatabase db = this.getWritableDatabase();
        String insert = "INSERT INTO "+ CITY_TABLE_NAME + " (" + CITY_CITY_NAME +
                ") VALUES ('" + city.getmCityName() + "');";
        db.execSQL(insert);
        db.close();
    }

    /**
     * A method for adding a new Country to the database
     * @param country A Country object parameter
     */
    void addCountry(Country country){
        SQLiteDatabase db = this.getWritableDatabase();
        String insert = "INSERT INTO "+ COUNTRY_TABLE_NAME + " (" + COUNTRY_COUNTRY_NAME +
                ") VALUES ('" + country.getmCountryName() + "');";
        db.execSQL(insert);
        db.close();
    }

    /**
     * A method for adding a new Playlist to the database
     * @param playlist A Playlist object parameter
     */
    void addPlaylist(Playlist playlist){
        SQLiteDatabase db = this.getWritableDatabase();
        String insert = "INSERT INTO "+ PLAYLIST_TABLE_NAME + " (" + PLAYLIST_PLAYLIST_NAME +
                ", " + PLAYLIST_STREAMING_SERVICE_ID + ", " + PLAYLIST_LINK + ") VALUES ('" +
                playlist.getmPlaylistName() + "', " + playlist.getmStreamingServiceID() + ", '" +
                playlist.getmLink() + "');";
        db.execSQL(insert);
        db.close();
    }

    /**
     * A method for adding a genre to a playlist
     * @param genre A Genre object parameter
     * @param playlist A playlist object parameter
     */
    void addGenreToPlaylist(Genre genre, Playlist playlist){
        SQLiteDatabase db = this.getWritableDatabase();
        String insert = "INSERT INTO "+ GENRE_PLAYLIST_TABLE_NAME + " (" + GENRE_PLAYLIST_GENRE_ID +
                ", " + GENRE_PLAYLIST_PLAYLIST_ID + ") VALUES (" +
                genre.getmGenreID() + ", " + playlist.getmPlaylistID() + ");";
        db.execSQL(insert);
        db.close();
    }

    /**
     * A method for adding a genre to a city
     * @param genre A Genre object parameter
     * @param city A City object parameter
     */
    void addGenreToCity(Genre genre, City city){
        SQLiteDatabase db = this.getWritableDatabase();
        String insert = "INSERT INTO "+ GENRE_CITY_TABLE_NAME + " (" + GENRE_CITY_GENRE_ID +
                ", " + GENRE_CITY_CITY_ID + ") VALUES (" +
                genre.getmGenreID() + ", " + city.getmCityID() + ");";
        db.execSQL(insert);
        db.close();
    }

    /**
     * A method for adding a genre to a Country
     * @param genre A Genre object parameter
     * @param country A Country object parameter
     */
    void addGenreToCountry(Genre genre, Country country){
        SQLiteDatabase db = this.getWritableDatabase();
        String insert = "INSERT INTO "+ GENRE_COUNTRY_TABLE_NAME + " (" + GENRE_COUNTRY_GENRE_ID +
                ", " + GENRE_COUNTRY_COUNTRY_ID + ") VALUES (" +
                genre.getmGenreID() + ", " + country.getmCountryID() + ");";
        db.execSQL(insert);
        db.close();
    }





}
