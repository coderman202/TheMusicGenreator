package com.example.android.themusicgenreator;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources.Theme;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import static com.example.android.themusicgenreator.MainActivity.musicGenresDB;

public class CountryBrowserActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_browser);

        //Set the title of the toolbar and add a search icon instead of the standard menu icon
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_countries);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.browse_countries_title);
        Drawable searchIcon = ContextCompat.getDrawable(getApplicationContext(), R.drawable.search);
        toolbar.setOverflowIcon(searchIcon);

        Country[] countriesArray = musicGenresDB.getAllCountries();
        String [] countryNames = new String[countriesArray.length];

        for (int i = 0; i < countriesArray.length; i++) {
            countryNames[i] = countriesArray[i].getmCountryName();
        }

        // Setup spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinner_countries);
        spinner.setAdapter(new MyAdapter(toolbar.getContext(),countryNames));

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // When the given dropdown item is selected, show its contents in the
                // container view.
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_countries, PlaceholderFragment.newInstance(position + 1))
                        .commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // When no dropdown item is selected, show the first item's contents in the
                // container view.
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_countries, PlaceholderFragment.newInstance(0))
                        .commit();
            }
        });
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.search_genres) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

private static class MyAdapter extends ArrayAdapter<String> implements ThemedSpinnerAdapter {
    private final ThemedSpinnerAdapter.Helper mDropDownHelper;

    public MyAdapter(Context context, String[] objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        mDropDownHelper = new ThemedSpinnerAdapter.Helper(context);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            // Inflate the drop down using the helper's LayoutInflater
            LayoutInflater inflater = mDropDownHelper.getDropDownViewInflater();
            view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        } else {
            view = convertView;
        }

        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        textView.setMaxLines(1);
        textView.setText(getItem(position));

        return view;
    }

    @Override
    public Theme getDropDownViewTheme() {
        return mDropDownHelper.getDropDownViewTheme();
    }

    @Override
    public void setDropDownViewTheme(Theme theme) {
        mDropDownHelper.setDropDownViewTheme(theme);
    }
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
            View rootView = inflater.inflate(R.layout.country_browser_fragment, container, false);

            //Get the countryID by taking the section number minus one, as it should be
            // equivalent to the countryID
            int countryID = getArguments().getInt(ARG_SECTION_NUMBER);

            Genre[] genresArray = musicGenresDB.getGenreByCountry(countryID);
            LinearLayout scroller = (LinearLayout) rootView.findViewById(R.id.countries_scroller);

            //Get the floating action button that we will use to add records to the database.
            //The appropriate onClickListeners will be set below in each tab.
            FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab_countries);

            //Get the style for styling the textviews that will be added like lists.
            ContextThemeWrapper listItemStyle =
                    new ContextThemeWrapper(getActivity(), R.style.ListItemStyle);

            for (final Genre genre:genresArray) {
                TextView tv = new TextView(listItemStyle);
                tv.setText(genre.getmGenreName());
                tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.countries_more, 0);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i  = new Intent(getActivity(), GenreInfoActvity.class);
                        Log.d("name", genre.getmGenreName());
                        Log.d("id", genre.getmGenreID() + "");
                        i.putExtra("PASSED_GENRE", genre);
                        startActivity(i);
                    }
                });
                scroller.addView(tv);
            }

            if(genresArray.length == 0){
                TextView tv = new TextView(listItemStyle);
                scroller.addView(tv);
            }


            //A blank text view to ensure no views are cut off the end of the screen
            TextView tv = new TextView(listItemStyle);
            scroller.addView(tv);

            return rootView;
        }
    }
}