package com.example.android.themusicgenreator;

import android.graphics.drawable.Drawable;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.example.android.themusicgenreator.MainActivity.letters;
import static com.example.android.themusicgenreator.MainActivity.musicGenresDB;

public class CountryBrowserActivity extends AppCompatActivity {

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

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container_countries);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_countries);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.search_genres) {
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
            View rootView = inflater.inflate(R.layout.country_browser_fragment, container, false);

            //Get the letter from the charArray to search by to match the letter heading of the tab
            char searchLetter = letters[getArguments().getInt(ARG_SECTION_NUMBER) - 1];

            Country[] countriesArray = musicGenresDB.getCountryByLetter(searchLetter);
            LinearLayout scroller = (LinearLayout) rootView.findViewById(R.id.countries_scroller);

            for (Country country:countriesArray) {
                TextView tv = new TextView(new ContextThemeWrapper(getActivity(), R.style.ListItemStyle));
                tv.setText(country.getmCountryName());
                tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.countries_more, 0);
                scroller.addView(tv);
            }

            if(countriesArray.length == 0){
                TextView tv = new TextView(new ContextThemeWrapper(getActivity(), R.style.ListItemStyle));
                tv.setText(getString(R.string.browse_countries_none, Character.toLowerCase(searchLetter)));
                scroller.addView(tv);
            }

            //Create and add a button for adding new countries to the database.
            //Set the colors and add drawables to the left and right
            Button addButton = new Button(new ContextThemeWrapper(getActivity(), R.style.AddItemButtonStyle));
            addButton.setText(getString(R.string.add_country));
            addButton.setBackgroundResource(R.color.browse_countries_button_color);
            addButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.earth, 0, R.drawable.add, 0);
            scroller.addView(addButton);

            //A blank text view to ensure no views are cut off the end of the screen
            TextView tv = new TextView(new ContextThemeWrapper(getActivity(), R.style.ListItemStyle));
            scroller.addView(tv);

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