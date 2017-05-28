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
import android.text.method.TextKeyListener;
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

import static com.example.android.themusicgenreator.MainActivity.musicGenresDB;

public class PlaylistBrowserActivity extends AppCompatActivity {



    //Set the number of tabs to be equal to the number of streaming services in the DB, to ensure
    // each one has their own tab.
    private final int NUM_TABS = musicGenresDB.getNumStreamingServices();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_browser);

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
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(R.string.browse_playlists_title);
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
                                (toolbar.getContext(), R.color.browse_playlists_button_color);
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

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
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
        public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                                 final Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.playlist_browser_fragment, container, false);

            int serviceID = getArguments().getInt(ARG_SECTION_NUMBER);

            Playlist[] playlistsArray = musicGenresDB.getPlaylistByStreamingService(serviceID);
            LinearLayout scroller = (LinearLayout) rootView.findViewById(R.id.playlists_scroller);

            //Get the floating action button that we will use to add records to the database.
            //This feature for adding playlists is currently unavailable
            FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab_playlists);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(rootView, R.string.not_available, Snackbar.LENGTH_LONG).show();
                }
            });

            //Get the style for styling the textviews that will be added like lists.
            ContextThemeWrapper listItemStyle =
                    new ContextThemeWrapper(getActivity(), R.style.ListItemStyle);

            final AutoCompleteTextView searcher = (AutoCompleteTextView) rootView.findViewById(R.id.playlists_searcher);
            if(playlistsArray == null){
                searcher.setVisibility(View.GONE);
                TextView tv = new TextView(listItemStyle);
                tv.setText(getString(R.string.no_playlists_by_service));
                scroller.addView(tv);
            }
            else{
                String[] playlistNames = new String[playlistsArray.length];
                for (int i = 0; i < playlistsArray.length; i++) {
                    playlistNames[i] = playlistsArray[i].getmPlaylistName();
                }
                ArrayAdapter<String> playlistsAdapter = new ArrayAdapter<>(getContext(), R.layout.drop_down_list_item_layout, R.id.drop_down_item, playlistNames);
                searcher.setThreshold(3);
                searcher.setAdapter(playlistsAdapter);

                searcher.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        InputMethodManager in = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        in.hideSoftInputFromWindow(parent.getApplicationWindowToken(), 0);
                        TextKeyListener.clear(searcher.getText());
                        //onCreateView(inflater, container, savedInstanceState);
                    }
                });
                for (final Playlist playlist:playlistsArray) {
                    TextView tv = new TextView(listItemStyle);
                    tv.setText(playlist.getmPlaylistName());
                    tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, musicGenresDB.getStreamingService(playlist.getmStreamingServiceID()).getmIcon(), 0);
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(Intent.ACTION_VIEW,
                                    Uri.parse(playlist.getmLink()));
                            PackageManager pm = getContext().getPackageManager();
                            if(i.resolveActivity(pm) != null){
                                startActivity(i);
                            }
                            else{
                                i = new Intent(Intent.ACTION_VIEW,
                                        Uri.parse(musicGenresDB.getStreamingService(playlist.getmStreamingServiceID()).getmPlayStoreLink()));
                                startActivity(i);
                            }
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
            return NUM_TABS;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position < NUM_TABS){
                String pageTitle = musicGenresDB.getStreamingService(position + 1).getmServiceName();
                pageTitle += " ( " +
                        musicGenresDB.getNumPlaylistsByStreamingService(position + 1) + ")";
                return pageTitle;
            }
            return null;
        }
    }
}