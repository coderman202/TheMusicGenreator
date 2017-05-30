package com.example.android.themusicgenreator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    //Declare the 4 LinearLayouts in the activity_main.xml
    LinearLayout genreBrowseButton, cityBrowseButton, countryBrowseButton, playlistBrowseButton;

    //My db of musical genres. Accessing it to print all the genres to the screen
    public static MusicGenresDB musicGenresDB;

    //A Char Array of letters to be used for the titles of each tab
    public final static char[] letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setting a TextView with a description of the screen on display
        ExpandableTextView expandableTextView = (ExpandableTextView) findViewById(R.id.main_description);
        expandableTextView.setText(getString(R.string.main_activity));

        //Initialise a single instance of the MusicGenres Database to be used across all activities
        musicGenresDB = MusicGenresDB.getInstance(this);

        // Initialise all 4 LinearLayouts and set OnClickListeners on each one with explicit
        // Intents to take to the relevant Activity.
        genreBrowseButton = (LinearLayout) findViewById(R.id.browse_genres);
        cityBrowseButton = (LinearLayout) findViewById(R.id.browse_cities);
        countryBrowseButton = (LinearLayout) findViewById(R.id.browse_countries);
        playlistBrowseButton = (LinearLayout) findViewById(R.id.browse_playlists);

        genreBrowseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(MainActivity.this, GenreBrowserActvity.class);
                startActivity(i);
            }
        });

        cityBrowseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(MainActivity.this, CityBrowserActivity.class);
                startActivity(i);
            }
        });

        countryBrowseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(MainActivity.this, CountryBrowserActivity.class);
                startActivity(i);
            }
        });

        playlistBrowseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(MainActivity.this, PlaylistBrowserActivity.class);
                startActivity(i);
            }
        });
    }


}
