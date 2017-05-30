package com.example.android.themusicgenreator;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.android.themusicgenreator.MainActivity.letters;
import static com.example.android.themusicgenreator.MainActivity.musicGenresDB;

public class GenreBrowserActvity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_browser);

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
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_genres);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(R.string.browse_genres_title);
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
                switch(id) {
                    case R.id.search_db:
                        final Dialog dialog = SearchDialogCreator.createSearchDialog
                                (toolbar.getContext(), R.color.browse_genre_button_color);
                        dialog.show();
                        return true;
                    case android.R.id.home:
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        return true;
                }
                return false;
            }
        });

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container_genres);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_genres);
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
            View rootView = inflater.inflate(R.layout.genre_browser_fragment, container, false);

            //Setting a TextView with a description of the screen on display
            ExpandableTextView expandableTextView = (ExpandableTextView) rootView.findViewById(R.id.genre_description);
            expandableTextView.setText(getString(R.string.genre_browser_activity));

            //Get the letter from the charArray to search by to match the letter heading of the tab
            char searchLetter = letters[getArguments().getInt(ARG_SECTION_NUMBER) - 1];

            Genre[] genresArray = musicGenresDB.getGenreByLetter(searchLetter);
            LinearLayout scroller = (LinearLayout) rootView.findViewById(R.id.genres_scroller);

            //Get the floating action button that we will use to add records to the database.
            //Open a dialog with an Edittext. Treat the enter key as an entry.
            FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab_genres);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog dialog = new Dialog(getContext());
                    dialog.setContentView(R.layout.add_new_genres_dialog);
                    //Setting the colour of the dialog title
                    String str = getResources().getString(R.string.add_genres);
                    SpannableString dialogTitle = new SpannableString(str);
                    dialogTitle.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),
                            R.color.browse_buttons_text_color)), 0, str.length(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    dialog.setTitle(dialogTitle);
                    if(dialog.getWindow() != null){
                        dialog.getWindow().setBackgroundDrawableResource
                                (R.color.browse_genre_button_color);
                    }

                    EditText addGenre = (EditText) dialog.findViewById(R.id.add_genre);

                    addGenre.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView v, int actionId,
                                                      KeyEvent event) {
                            if (actionId == EditorInfo.IME_ACTION_DONE) {
                                String message;
                                String newGenre = v.getText().toString();
                                if(musicGenresDB.getGenreByName(newGenre) == null){
                                    musicGenresDB.addGenre(new Genre(newGenre));
                                    message = getString(R.string.added_genre, newGenre);
                                }
                                else{
                                    message = getString(R.string.not_added_genre, newGenre);
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

            for (final Genre genre:genresArray) {
                TextView tv = new TextView(listItemStyle);
                tv.setText(genre.getmGenreName());
                tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.genres_more, 0);
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

            if(genresArray.length == 0){
                TextView tv = new TextView(listItemStyle);
                tv.setText(getString(R.string.browse_genres_none, Character.toLowerCase(searchLetter)));
                scroller.addView(tv);
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
            // Show 1 page for each letter.
            return letters.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position < letters.length){
                return String.valueOf(letters[position]);
            }
            return null;
        }
    }
}

