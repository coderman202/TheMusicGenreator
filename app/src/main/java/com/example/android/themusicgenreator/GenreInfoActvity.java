package com.example.android.themusicgenreator;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.TextKeyListener;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.android.themusicgenreator.MainActivity.musicGenresDB;

public class GenreInfoActvity extends AppCompatActivity {


    //A Genre variable to store the genre object passed via the Intent.
    private static Genre inGenre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_info);

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

        //Getting the genre object from the Intent. Use it to set the title of the ActionBar.
        inGenre = getIntent().getParcelableExtra("PASSED_GENRE");

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_genre_info);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(inGenre.getmGenreName());
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.home);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        Drawable searchIcon = ContextCompat.getDrawable(getApplicationContext(), R.drawable.search);
        toolbar.setOverflowIcon(searchIcon);

        toolbar.inflateMenu(R.menu.browser_menu);

        // Setting an OnClickListener to allow user to open a search dialog upon clicking
        // the search menu button
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                Log.i(getClass().getSimpleName(), " " + id);
                switch (id) {
                    case R.id.search_db:
                        final Dialog dialog = SearchDialogCreator.createSearchDialog
                                (toolbar.getContext(), R.color.browse_genre_button_color);
                        dialog.show();
                        return true;
                }
                return false;
            }
        });

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter and the TabLayout with the ViewPager.
        mViewPager = (ViewPager) findViewById(R.id.container_genre_info);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_genre_info);
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
        switch (id) {
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
            final View rootView = inflater.inflate(R.layout.genre_info_fragment, container, false);

            //Setting a TextView with a description of the screen on display
            ExpandableTextView expandableTextView = (ExpandableTextView) rootView.findViewById(R.id.genre_info_description);
            expandableTextView.setText(getString(R.string.genre_info_activity));

            //Create array lists of each related playlist, city and country to the genre.
            final Playlist[] playlistsArray = musicGenresDB.getPlaylistByGenre(inGenre.getmGenreID());
            City[] citiesArray = musicGenresDB.getCitiesOfInfluence(inGenre.getmGenreID());
            final Country[] countriesArray = musicGenresDB.getCountriesOfInfluence(inGenre.getmGenreID());

            //Get the LinearLayout inside the scrollview for adding all the lists.
            LinearLayout scroller = (LinearLayout) rootView.findViewById(R.id.genre_info_scroller);

            //Get the floating action button that we will use to add records to the database.
            //Use onClickListener to open a custom Dialog
            FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab_genres_info);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog dialog = new Dialog(getContext());
                    dialog.setContentView(R.layout.add_items_to_genres_dialog);
                    //Setting the colour of the dialog title
                    String str = getResources().getString(R.string.add_items_dialog_title, inGenre.getmGenreName());
                    SpannableString dialogTitle = new SpannableString(str);
                    dialogTitle.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),
                            R.color.browse_buttons_text_color)), 0, str.length(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    dialog.setTitle(dialogTitle);
                    if (dialog.getWindow() != null) {
                        dialog.getWindow().setBackgroundDrawableResource(R.color.browse_genre_button_color);
                    }

                    TextView headerText = (TextView) dialog.findViewById(R.id.add_items_to_genres_dialog_header);
                    headerText.setText(getString(R.string.add_items_to_genre_dialog_header));

                    //Set an adapter for the AutoCompleteTextView, displaying all the cities
                    final City[] allCities = musicGenresDB.getAllCities();
                    String[] cityNames = new String[allCities.length];
                    for (int i = 0; i < allCities.length; i++) {
                        cityNames[i] = allCities[i].getmCityName();
                    }
                    ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(getContext(), R.layout.drop_down_list_item_layout, R.id.drop_down_item, cityNames);
                    final AutoCompleteTextView addCityAutoComplete = (AutoCompleteTextView) dialog.findViewById(R.id.add_city);
                    addCityAutoComplete.setThreshold(1);
                    addCityAutoComplete.setAdapter(cityAdapter);

                    addCityAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            City addedCity = musicGenresDB.getCityByName(addCityAutoComplete.getText().toString());
                            musicGenresDB.addGenreToCity(inGenre, addedCity);
                            InputMethodManager in = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            in.hideSoftInputFromWindow(parent.getApplicationWindowToken(), 0);
                            TextKeyListener.clear(addCityAutoComplete.getText());
                            Toast.makeText(getContext(), getString(R.string.added_items, addedCity.getmCityName(), inGenre.getmGenreName()), Toast.LENGTH_LONG).show();
                        }
                    });

                    //Set an adapter for the AutoCompleteTextView, displaying all the cities
                    Country[] allCountries = musicGenresDB.getAllCountries();
                    String[] countryNames = new String[allCountries.length];
                    for (int i = 0; i < allCountries.length; i++) {
                        countryNames[i] = allCountries[i].getmCountryName();
                    }
                    ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(getContext(), R.layout.drop_down_list_item_layout, R.id.drop_down_item, countryNames);
                    final AutoCompleteTextView addCountryAutoComplete = (AutoCompleteTextView) dialog.findViewById(R.id.add_country);
                    addCountryAutoComplete.setThreshold(1);
                    addCountryAutoComplete.setAdapter(countryAdapter);

                    addCountryAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Country addedCountry = musicGenresDB.getCountryByName(addCountryAutoComplete.getText().toString());
                            musicGenresDB.addGenreToCountry(inGenre, addedCountry);
                            InputMethodManager in = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            in.hideSoftInputFromWindow(parent.getApplicationWindowToken(), 0);
                            TextKeyListener.clear(addCountryAutoComplete.getText());
                            Toast.makeText(getContext(), getString(R.string.added_items, addedCountry.getmCountryName(), inGenre.getmGenreName()), Toast.LENGTH_LONG).show();

                        }
                    });
                    dialog.show();
                }
            });

            //Get the two styles for styling the textviews that will be added like lists.
            ContextThemeWrapper listItemStyle =
                    new ContextThemeWrapper(getActivity(), R.style.ListItemStyle);
            ContextThemeWrapper listHeaderStyle =
                    new ContextThemeWrapper(getActivity(), R.style.ListHeaderStyle);

            //For checking which tab we are in and getting the right list for each one.
            //Using the appropriate array to generate the lists for each tab.
            switch (getArguments().getInt(ARG_SECTION_NUMBER) - 1) {
                case 0:
                    if (playlistsArray == null) {
                        TextView tv = new TextView(listItemStyle);
                        tv.setText(getString(R.string.browse_playlists_none));
                        scroller.addView(tv);
                    } else {
                        for (final Playlist playlist : playlistsArray) {
                            TextView tv = new TextView(listItemStyle);
                            tv.setText(playlist.getmPlaylistName());
                            StreamingService service = musicGenresDB.getStreamingService(playlist.getmStreamingServiceID());
                            tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, service.getmIcon(), 0);
                            //OnClickListener for opening the streaming service with
                            // the correct playlist link.
                            tv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent startPlayer = new Intent(Intent.ACTION_VIEW,
                                            Uri.parse(playlist.getmLink()));
                                    Intent goToPlayStore = new Intent(Intent.ACTION_VIEW,
                                            Uri.parse(musicGenresDB.getStreamingService(playlist.getmStreamingServiceID()).getmPlayStoreLink()));

                                    PackageManager pm = getContext().getPackageManager();
                                    if (startPlayer.resolveActivity(pm) != null) {
                                        startActivity(startPlayer);
                                    } else if (goToPlayStore.resolveActivity(pm) != null) {
                                        startActivity(goToPlayStore);
                                    } else {
                                        Snackbar.make(rootView, R.string.play_store_msg, Snackbar.LENGTH_LONG).show();
                                    }
                                }
                            });
                            scroller.addView(tv);
                        }
                    }
                    break;
                case 1:
                    //Set a header for the cities list
                    TextView cityHeader = new TextView(listHeaderStyle);
                    cityHeader.setText(R.string.country_city_header);
                    scroller.addView(cityHeader);

                    if (citiesArray == null) {
                        TextView tv1 = new TextView(listItemStyle);
                        tv1.setText(getString(R.string.genre_info_none));
                        scroller.addView(tv1);
                    } else {
                        for (final City city : citiesArray) {
                            TextView tv1 = new TextView(listItemStyle);
                            tv1.setText(city.getmCityName());
                            tv1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.genres_more, 0);
                            tv1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent i = new Intent(getActivity(), CityBrowserActivity.class);
                                    i.putExtra("PASSED_CITY", city);
                                    startActivity(i);
                                }
                            });
                            scroller.addView(tv1);
                        }
                    }
                    break;
                case 2:
                    //Set a header for the countries list
                    TextView countryHeader = new TextView(listHeaderStyle);
                    countryHeader.setText(R.string.country_city_header);
                    scroller.addView(countryHeader);

                    if (countriesArray == null) {
                        TextView tv2 = new TextView(listItemStyle);
                        tv2.setText(getString(R.string.genre_info_none));
                        scroller.addView(tv2);
                    } else {
                        for (final Country country : countriesArray) {
                            TextView tv2 = new TextView(listItemStyle);
                            tv2.setText(country.getmCountryName());
                            tv2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.genres_more, 0);
                            tv2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent i = new Intent(getActivity(), CountryBrowserActivity.class);
                                    i.putExtra("PASSED_COUNTRY", country);
                                    startActivity(i);
                                }
                            });
                            scroller.addView(tv2);
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
