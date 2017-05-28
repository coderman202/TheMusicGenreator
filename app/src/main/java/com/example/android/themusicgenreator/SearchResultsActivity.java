package com.example.android.themusicgenreator;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.example.android.themusicgenreator.MainActivity.musicGenresDB;

public class SearchResultsActivity extends AppCompatActivity {

    static String searchTerm;

    static Genre[] genreResults;
    static City[] cityResults;
    static Country[] countryResults;
    static Playlist[] playlistResults;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        //Get the text entered via the EditText in the search Dialog
        searchTerm = getIntent().getStringExtra("SEARCH_TERM");

        // Search the DB and store arrays of each object to generate textviews to
        // populate the scrollview with later.
        genreResults = musicGenresDB.getGenreBySearchTerm(searchTerm);
        cityResults = musicGenresDB.getCityBySearchTerm(searchTerm);
        countryResults = musicGenresDB.getCountryBySearchTerm(searchTerm);
        playlistResults = musicGenresDB.getPlaylistBySearchTerm(searchTerm);

        /*
         * The {@link android.support.v4.view.PagerAdapter} that will provide
         * fragments for each of the sections. We use a
         * {@link FragmentPagerAdapter} derivative, which will keep every
         * loaded fragment in memory. If this becomes too memory intensive, it
         * may be best to switch to a
         * {@link android.support.v4.app.FragmentStatePagerAdapter}.
         */
        SectionsPagerAdapter mSectionsPagerAdapter;

        /*
         * The {@link ViewPager} that will host the section contents.
         */
        ViewPager mViewPager;

        //Set the title of the toolbar and add a search icon instead of the standard menu icon
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_search_results);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(R.string.search_results_title);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.home);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        toolbar.inflateMenu(R.menu.browser_menu);

        // Setting an OnClickListener to allow user to open a search dialog upon clicking
        // the search menu button
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                Log.i(getClass().getSimpleName(), " " + id);
                switch(id) {
                    case R.id.search_db:
                        final Dialog dialog = SearchDialogCreator.createSearchDialog
                                (toolbar.getContext(), R.color.colorPrimary);
                        dialog.show();
                        return true;
                }
                return false;
            }
        });

        Drawable searchIcon = ContextCompat.getDrawable(getApplicationContext(), R.drawable.search);
        toolbar.setOverflowIcon(searchIcon);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter and the TabLayout with the ViewPager.
        mViewPager = (ViewPager) findViewById(R.id.container_search_results);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_search_results);
        tabLayout.setupWithViewPager(mViewPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.browser_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        switch(id) {
            case android.R.id.home:
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
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
            View rootView = inflater.inflate(R.layout.fragment_search_results, container, false);

            //Get the LinearLayout inside the scrollview for adding all the lists.
            LinearLayout scroller = (LinearLayout) rootView.findViewById(R.id.results_scroller);

            //Get the two styles for styling the textviews that will be added like lists.
            ContextThemeWrapper listItemStyle =
                    new ContextThemeWrapper(getActivity(), R.style.ListItemStyle);

            //For checking which tab we are in and getting the right list for each one.
            //Using the appropriate array to generate the lists for each tab.
            switch(getArguments().getInt(ARG_SECTION_NUMBER)-1){
                case 0:
                    if(genreResults == null) {
                        TextView tv = new TextView(listItemStyle);
                        tv.setText(getString(R.string.search_results_genres_none));
                        tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.search_coloured, 0);
                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final Dialog dialog = SearchDialogCreator.createSearchDialog
                                        (getActivity(), R.color.colorPrimary);
                                dialog.show();
                            }
                        });
                        scroller.addView(tv);
                    }
                    else{
                        for (final Genre genre:genreResults) {
                            TextView tv = new TextView(listItemStyle);
                            tv.setText(genre.getmGenreName());
                            tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.genres_more, 0);
                            //OnClickListener for opening the streaming service with
                            // the correct playlist link.
                            tv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent i  = new Intent(getActivity(), GenreInfoActvity.class);
                                    i.putExtra("PASSED_GENRE", genre);
                                    startActivity(i);
                                }
                            });
                            scroller.addView(tv);
                        }
                    }
                    break;
                case 1:
                    if(cityResults == null){
                        TextView tv1 = new TextView(listItemStyle);
                        tv1.setText(getString(R.string.search_results_cities_none));
                        tv1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.search_coloured, 0);
                        tv1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final Dialog dialog = SearchDialogCreator.createSearchDialog
                                        (getActivity(), R.color.colorPrimary);
                                dialog.show();
                            }
                        });
                        scroller.addView(tv1);
                    }
                    else{
                        for (final City city:cityResults) {
                            TextView tv1 = new TextView(listItemStyle);
                            tv1.setText(city.getmCityName());
                            tv1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.cities_more, 0);
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
                    }
                    break;
                case 2:
                    if(countryResults == null){
                        TextView tv2 = new TextView(listItemStyle);
                        tv2.setText(getString(R.string.search_results_coutnries_none));
                        tv2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.search_coloured, 0);
                        tv2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final Dialog dialog = SearchDialogCreator.createSearchDialog
                                        (getActivity(), R.color.colorPrimary);
                                dialog.show();
                            }
                        });
                        scroller.addView(tv2);
                    }
                    else{
                        for (final Country country:countryResults) {
                            TextView tv2 = new TextView(listItemStyle);
                            tv2.setText(country.getmCountryName());
                            tv2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.countries_more, 0);
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
                    }
                    break;
                case 3:
                    if(playlistResults == null) {
                        TextView tv3 = new TextView(listItemStyle);
                        tv3.setText(getString(R.string.search_results_playlists_none));
                        tv3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.search_coloured, 0);
                        tv3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final Dialog dialog = SearchDialogCreator.createSearchDialog
                                        (getActivity(), R.color.colorPrimary);
                                dialog.show();
                            }
                        });
                        scroller.addView(tv3);
                    }
                    else{
                        for (final Playlist playlist:playlistResults) {
                            TextView tv = new TextView(listItemStyle);
                            tv.setText(playlist.getmPlaylistName());
                            StreamingService service = musicGenresDB.getStreamingService(playlist.getmStreamingServiceID());
                            tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, service.getmIcon(), 0);
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
                    }
                    break;
            }
            //A blank text view to ensure no views are cut off the end of the screen
            TextView tv = new TextView(listItemStyle);
            scroller.addView(tv);
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
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
            // Show 4 total pages.
            return 4;
        }

        //Set the page title, and include the number of results for each page, in brackets.
        @Override
        public CharSequence getPageTitle(int position) {
            int numResults;
            switch (position) {
                case 0:
                    if(genreResults == null){
                        numResults = 0;
                    }
                    else{
                        numResults = genreResults.length;
                    }
                    return getString(R.string.genres, numResults + "");
                case 1:
                    if(cityResults == null){
                        numResults = 0;
                    }
                    else{
                        numResults = cityResults.length;
                    }
                    return getString(R.string.cities, numResults + "");
                case 2:
                    if(countryResults == null){
                        numResults = 0;
                    }
                    else{
                        numResults = countryResults.length;
                    }
                    return getString(R.string.countries, numResults + "");
                case 3:
                    if(playlistResults == null){
                        numResults = 0;
                    }
                    else{
                        numResults = playlistResults.length;
                    }
                    return getString(R.string.playlists, numResults + "");
            }
            return null;
        }
    }
}
