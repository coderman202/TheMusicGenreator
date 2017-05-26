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

public class CityBrowserActivity extends AppCompatActivity {


    private static City inCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_browser);

        inCity = getIntent().getParcelableExtra("PASSED_CITY");


        //Set the title of the toolbar and add a search icon instead of the standard menu icon
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_cities);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(R.string.browse_cities_title);
        }
        Drawable searchIcon = ContextCompat.getDrawable(getApplicationContext(), R.drawable.search);
        toolbar.setOverflowIcon(searchIcon);

        City[] citiesArray = musicGenresDB.getAllCities();
        String [] cityNames = new String[citiesArray.length];

        for (int i = 0; i < citiesArray.length; i++) {
            cityNames[i] = citiesArray[i].getmCityName();
            cityNames[i] += (" (" + musicGenresDB.getNumGenresByCity(citiesArray[i].getmCityID()) + ")");
        }

        // Setup spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinner_cities);
        spinner.setAdapter(new MyAdapter(toolbar.getContext(),cityNames));

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // When the given dropdown item is selected, show its contents in the
                // container view.
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_cities, PlaceholderFragment.newInstance(position + 1))
                        .commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        if(inCity != null){
            spinner.setSelection(inCity.getmCityID() - 1);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.search_genres) {
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
            View rootView = inflater.inflate(R.layout.city_browser_fragment, container, false);

            //Get the cityID by taking the section number minus one, as it should be
            // equivalent to the cityID
            int cityID = getArguments().getInt(ARG_SECTION_NUMBER);

            Genre[] genresArray = musicGenresDB.getGenreByCity(cityID);
            LinearLayout scroller = (LinearLayout) rootView.findViewById(R.id.cities_scroller);

            //Check if the City object passed via the Intent is null.
            // If so, show the default dialog window. Else, show the dialog for adding a
            // genre to the city.
            FloatingActionButton fab = (FloatingActionButton)
                    getActivity().findViewById(R.id.fab_cities);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog dialog = new Dialog(getContext());
                    if(inCity == null){
                        dialog.setContentView(R.layout.add_new_cities_dialog);
                        //Setting the colour of the dialog title
                        String str = getResources().getString(R.string.add_cities);
                        SpannableString dialogTitle = new SpannableString(str);
                        dialogTitle.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),
                                R.color.browse_buttons_text_color)), 0, str.length(),
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        dialog.setTitle(dialogTitle);
                        if(dialog.getWindow() != null){
                            dialog.getWindow().setBackgroundDrawableResource(R.color.browse_cities_button_color);
                        }

                        EditText addGenre = (EditText) dialog.findViewById(R.id.add_city);

                        addGenre.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                            @Override
                            public boolean onEditorAction(TextView v, int actionId,
                                                          KeyEvent event) {
                                if (actionId == EditorInfo.IME_ACTION_DONE) {
                                    String message = "";
                                    String newCity = v.getText().toString();
                                    if(musicGenresDB.getCityByName(newCity) == null){
                                        musicGenresDB.addCity(new City(newCity));
                                        message = getString(R.string.added_city, newCity);
                                    }
                                    else{
                                        message = getString(R.string.not_added_city, newCity);
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
                    }
                    else{
                        dialog.setContentView(R.layout.add_items_to_cities_dialog);
                        String str = getResources().getString(R.string.add_genres);
                        SpannableString dialogTitle = new SpannableString(str);
                        dialogTitle.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),
                                R.color.browse_buttons_text_color)), 0, str.length(),
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        dialog.setTitle(dialogTitle);
                        if(dialog.getWindow() != null){
                            dialog.getWindow().setBackgroundDrawableResource(R.color.browse_cities_button_color);
                        }
                        TextView headerText = (TextView) dialog.findViewById(R.id.add_items_to_cities_dialog_header);
                        headerText.setText(getString(R.string.add_items_dialog_header, inCity.getmCityName()));

                        //Set an adapter for the AutoCompleteTextView, displaying all the cities
                        final City[] allCities = musicGenresDB.getAllCities();
                        String[] cityNames = new String[allCities.length];
                        for (int i = 0; i < allCities.length; i++) {
                            cityNames[i] = allCities[i].getmCityName();
                        }
                        ArrayAdapter<String> genreAdapter = new ArrayAdapter<>(getContext(), android.R.layout.select_dialog_singlechoice, cityNames);
                        final AutoCompleteTextView addGenreAutoComplete = (AutoCompleteTextView) dialog.findViewById(R.id.add_item_to_city);
                        addGenreAutoComplete.setThreshold(1);
                        addGenreAutoComplete.setAdapter(genreAdapter);

                        addGenreAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Genre addedGenre = musicGenresDB.getGenreByName(addGenreAutoComplete.getText().toString());
                                musicGenresDB.addGenreToCity(addedGenre, inCity);
                                InputMethodManager in = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                in.hideSoftInputFromWindow(parent.getApplicationWindowToken(), 0);
                                TextKeyListener.clear(addGenreAutoComplete.getText());
                            }
                        });
                    }
                    dialog.show();

                }
            });

            //Get the style for styling the textviews that will be added like lists.
            ContextThemeWrapper listItemStyle =
                    new ContextThemeWrapper(getActivity(), R.style.ListItemStyle);

            for (final Genre genre:genresArray) {
                TextView tv = new TextView(listItemStyle);
                tv.setText(genre.getmGenreName());
                tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.cities_more, 0);
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