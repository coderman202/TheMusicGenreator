package com.example.android.themusicgenreator;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources.Theme;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.TextKeyListener;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.android.themusicgenreator.MainActivity.musicGenresDB;

public class CountryBrowserActivity extends AppCompatActivity {

    private static Country inCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_browser);

        inCountry = getIntent().getParcelableExtra("PASSED_COUNTRY");
        if (inCountry == null) {
            inCountry = musicGenresDB.getCountry(1);
        }

        //Set the title of the toolbar and add a search icon instead of the standard menu icon
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_countries);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.browse_countries_title);
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
                                (toolbar.getContext(), R.color.browse_countries_button_color);
                        dialog.show();
                        return true;
                }
                return false;
            }
        });

        Country[] countriesArray = musicGenresDB.getAllCountries();
        String[] countryNames = new String[countriesArray.length];

        for (int i = 0; i < countriesArray.length; i++) {
            countryNames[i] = countriesArray[i].getmCountryName();
            countryNames[i] += (" (" + musicGenresDB.getNumGenresByCountry(countriesArray[i].getmCountryID()) + ")");
        }

        // Setup spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinner_countries);
        spinner.setAdapter(new MyAdapter(toolbar.getContext(), countryNames));

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
            }
        });

        if (inCountry != null) {
            spinner.setSelection(inCountry.getmCountryID() - 1);
        }
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

    private static class MyAdapter extends ArrayAdapter<String> implements ThemedSpinnerAdapter {
        private final ThemedSpinnerAdapter.Helper mDropDownHelper;

        MyAdapter(Context context, String[] objects) {
            super(context, android.R.layout.simple_list_item_1, objects);
            mDropDownHelper = new ThemedSpinnerAdapter.Helper(context);
        }

        @Override
        public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
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

            //Setting a TextView with a description of the screen on display
            ExpandableTextView expandableTextView = (ExpandableTextView) rootView.findViewById(R.id.country_description);
            expandableTextView.setText(getString(R.string.country_browser_activity));

            //Get the countryID by taking the section number minus one, as it should be
            // equivalent to the countryID
            int countryID = getArguments().getInt(ARG_SECTION_NUMBER);

            Genre[] genresArray = musicGenresDB.getGenreByCountry(countryID);
            LinearLayout scroller = (LinearLayout) rootView.findViewById(R.id.countries_scroller);

            //Open a dialog when FAB is pressed to allow addition of new
            // countries or a genre to the selected country.
            FloatingActionButton fab = (FloatingActionButton)
                    getActivity().findViewById(R.id.fab_countries);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog dialog = new Dialog(getContext());
                    dialog.setContentView(R.layout.add_countries_dialog);
                    //Setting the colour of the dialog title
                    String str = getResources().getString(R.string.add_countries_dialog_title);
                    SpannableString dialogTitle = new SpannableString(str);
                    dialogTitle.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),
                            R.color.browse_buttons_text_color)), 0, str.length(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    dialog.setTitle(dialogTitle);
                    if (dialog.getWindow() != null) {
                        dialog.getWindow().setBackgroundDrawableResource(R.color.browse_countries_button_color);
                    }

                    //First deal with adding a genre to the selected country.
                    // Use an AutocompleteTextView to take the chosen genre and add it to the country.
                    //Set an adapter for the AutoCompleteTextView, displaying all the genres
                    final Genre[] allGenres = musicGenresDB.getAllGenres();
                    String[] genresNames = new String[allGenres.length];
                    for (int i = 0; i < allGenres.length; i++) {
                        genresNames[i] = allGenres[i].getmGenreName();
                    }
                    ArrayAdapter<String> genreAdapter = new ArrayAdapter<>(getContext(), R.layout.drop_down_list_item_layout, R.id.drop_down_item, genresNames);
                    final AutoCompleteTextView addGenreAutoComplete = (AutoCompleteTextView) dialog.findViewById(R.id.add_genre_to_country);
                    addGenreAutoComplete.setThreshold(1);
                    addGenreAutoComplete.setAdapter(genreAdapter);
                    addGenreAutoComplete.setHint(getString(R.string.add_genre_to, inCountry.getmCountryName()));

                    addGenreAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Genre addedGenre = musicGenresDB.getGenreByName(addGenreAutoComplete.getText().toString());
                            musicGenresDB.addGenreToCountry(addedGenre, inCountry);
                            InputMethodManager in = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            in.hideSoftInputFromWindow(parent.getApplicationWindowToken(), 0);
                            TextKeyListener.clear(addGenreAutoComplete.getText());
                        }
                    });

                    //Second use an EditText to allow the user to add a new country to the DB
                    EditText addCity = (EditText) dialog.findViewById(R.id.add_new_country);
                    addCity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView v, int actionId,
                                                      KeyEvent event) {
                            if (actionId == EditorInfo.IME_ACTION_DONE) {
                                String message;
                                String newCountry = v.getText().toString();
                                if (musicGenresDB.getCountryByName(newCountry) == null) {
                                    musicGenresDB.addCountry(new Country(newCountry));
                                    message = getString(R.string.added_country, newCountry);
                                } else {
                                    message = getString(R.string.not_added_country, newCountry);
                                }
                                InputMethodManager in = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                in.hideSoftInputFromWindow(v.getRootView().getApplicationWindowToken(), 0);
                                Toast msg = Toast.makeText(getContext(), message, Toast.LENGTH_LONG);
                                msg.show();
                                v.setText("");
                                return true;
                            }
                            return false;
                        }
                    });
                    dialog.show();
                }
            });

            //Get the style for styling the textviews that will be added like lists.
            ContextThemeWrapper listItemStyle =
                    new ContextThemeWrapper(getActivity(), R.style.ListItemStyle);

            if (genresArray == null) {
                TextView tv = new TextView(listItemStyle);
                scroller.addView(tv);
            } else {
                for (final Genre genre : genresArray) {
                    TextView tv = new TextView(listItemStyle);
                    tv.setText(genre.getmGenreName());
                    tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.countries_more, 0);
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(getActivity(), GenreInfoActvity.class);
                            Log.d("name", genre.getmGenreName());
                            Log.d("id", genre.getmGenreID() + "");
                            i.putExtra("PASSED_GENRE", genre);
                            startActivity(i);
                        }
                    });
                    scroller.addView(tv);
                }
            }


            //A blank text view to ensure no views are cut off the end of the screen
            TextView tv = new TextView(listItemStyle);
            scroller.addView(tv);

            return rootView;
        }
    }
}