package com.example.android.themusicgenreator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.example.android.themusicgenreator.MainActivity.musicGenresDB;

public class GenreInfoActvity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    //A Genre variable to store the genre object passed via the Intent.
    public static Genre inGenre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_info);

        //Getting the genre object from the Intent. Use it to set the title of the ActionBar.
        inGenre = getIntent().getParcelableExtra("PASSED_GENRE");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_genre_info);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(inGenre.getmGenreName());

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter and the TabLayout with the ViewPager.
        mViewPager = (ViewPager) findViewById(R.id.container_genre_info);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_genre_info);
        tabLayout.setupWithViewPager(mViewPager);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.genre_info_fragment, container, false);

            //Create array lists of each related playlist, city and country to the genre.
            Playlist[] playlistsArray = musicGenresDB.getPlaylistByGenre(inGenre.getmGenreID());
            City[] citiesArray = musicGenresDB.getCitiesOfInfluence(inGenre.getmGenreID());
            Country[] countriesArray = musicGenresDB.getCountriesOfInfluence(inGenre.getmGenreID());

            //Get the LinearLayout inside the scrollview for adding all the lists.
            LinearLayout scroller = (LinearLayout) rootView.findViewById(R.id.genre_info_scroller);

            //Get the floating action button that we will use to add records to the database.
            //The appropriate onClickListeners will be set below in each tab.
            FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab_genres_info);

            //Get the two styles for styling the textviews that will be added like lists.
            ContextThemeWrapper listItemStyle =
                    new ContextThemeWrapper(getActivity(), R.style.ListItemStyle);
            ContextThemeWrapper listHeaderStyle =
                    new ContextThemeWrapper(getActivity(), R.style.ListHeaderStyle);

            //For checking which tab we are in and getting the right list for each one.
            //Using the appropriate array to generate the lists for each tab.
            switch(getArguments().getInt(ARG_SECTION_NUMBER)-1){
                case 0:
                    for (final Playlist playlist:playlistsArray) {
                        TextView tv = new TextView(listItemStyle);
                        tv.setText(playlist.getmPlaylistName());
                        tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.spotify_icon, 0);
                        //OnClickListener for opening the streaming service with
                        // the correct playlist link.
                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(Intent.ACTION_VIEW,
                                        Uri.parse(playlist.getmLink()));
                                startActivity(i);
                            }
                        });
                        scroller.addView(tv);
                    }

                    if(playlistsArray.length == 0){
                        TextView tv = new TextView(listItemStyle);
                        tv.setText(getString(R.string.browse_playlists_none));
                        scroller.addView(tv);
                    }

                    /*fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });*/

                    //A blank text view to ensure no views are cut off the end of the screen
                    TextView tv = new TextView(listItemStyle);
                    scroller.addView(tv);
                    break;
                case 1:
                    //Set a header for the cities list
                    TextView cityHeader = new TextView(listHeaderStyle);
                    cityHeader.setText(R.string.country_city_header);
                    scroller.addView(cityHeader);

                    for (final City city:citiesArray) {
                        TextView tv1 = new TextView(listItemStyle);
                        tv1.setText(city.getmCityName());
                        tv1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.genres_more, 0);
                        tv1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i  = new Intent(getActivity(), CityBrowserActivity.class);
                                i.putExtra("PASSED_CITY", city);
                                startActivity(i);
                            }
                        });
                        scroller.addView(tv1);
                    }

                    if(citiesArray.length == 0){
                        TextView tv1 = new TextView(listItemStyle);
                        tv1.setText(getString(R.string.genre_info_none));
                        scroller.addView(tv1);
                    }

                    /*fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });*/

                    //A blank text view to ensure no views are cut off the end of the screen
                    TextView tv1 = new TextView(listItemStyle);
                    scroller.addView(tv1);
                    break;
                case 2:
                    //Set a header for the countries list
                    TextView countryHeader = new TextView(listHeaderStyle);
                    countryHeader.setText(R.string.country_city_header);
                    scroller.addView(countryHeader);

                    for (final Country country:countriesArray) {
                        TextView tv2 = new TextView(listItemStyle);
                        tv2.setText(country.getmCountryName());
                        tv2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.genres_more, 0);
                        tv2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i  = new Intent(getActivity(), CountryBrowserActivity.class);
                                i.putExtra("PASSED_COUNTRY", country);
                                startActivity(i);
                            }
                        });
                        scroller.addView(tv2);
                    }

                    if(countriesArray.length == 0){
                        TextView tv2 = new TextView(listItemStyle);
                        tv2.setText(getString(R.string.genre_info_none));
                        scroller.addView(tv2);
                    }

                    /*fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });*/

                    //A blank text view to ensure no views are cut off the end of the screen
                    TextView tv2 = new TextView(listItemStyle);
                    scroller.addView(tv2);
                    break;
            }
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.playlists_header);
                case 1:
                    return getString(R.string.cities_header);
                case 2:
                    return getString(R.string.countries_header);
            }
            return null;
        }
    }
}
