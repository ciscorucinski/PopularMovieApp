package com.udacity.rucinskic.spotify_streamer.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.udacity.rucinskic.spotify_streamer.App;
import com.udacity.rucinskic.spotify_streamer.R;
import com.udacity.rucinskic.spotify_streamer.enums.API;
import com.udacity.rucinskic.spotify_streamer.enums.Search;
import com.udacity.rucinskic.spotify_streamer.ui.support.SettingsActivity;
import com.udacity.rucinskic.spotify_streamer.ui.support.ViewPagerFragmentAdapter;

import java.util.EnumSet;

public abstract class BaseSearchTabbedActivity extends AppCompatActivity
        implements SearchView.OnQueryTextListener, NavigationView.OnNavigationItemSelectedListener,
                   View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

    Search searchMethod;

    public DrawerLayout drawerLayout;

    private SearchView searchView;
    private ViewPagerFragmentAdapter adapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private CharSequence savedInstanceStateSearchQuery = "";
    private boolean isFromVoiceSearch;

    private static final String QUERIED_TEXT = "queriedText";
    private static final String SEARCH_METHOD = "searchMethod";

    private static EnumSet<API> displayedTabs;

    abstract EnumSet<API> getDefaultTabs();

    public void setCurrentTabs(EnumSet<API> tabs) {

        displayedTabs = tabs;

        clearAllTabs();

        addFragments(displayedTabs);
        viewPager.setCurrentItem(0, true);

        viewPager.setAdapter(adapter);
        tabLayout.removeAllTabs();
        tabLayout.setupWithViewPager(viewPager);
    }

    public void closeDrawer() { drawerLayout.closeDrawer(GravityCompat.START); }

    void initializeToolbar() {

        // Initialize all components. Tabs, Pager, and Adapter
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new ViewPagerFragmentAdapter(getSupportFragmentManager());

        // Add the tabs
        if (displayedTabs == null) displayedTabs = getDefaultTabs();
        addFragments(displayedTabs);

        // Bind the adapter to the tabs
        viewPager.setAdapter(adapter);
        tabLayout.removeAllTabs();
        tabLayout.setupWithViewPager(viewPager);

        PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .registerOnSharedPreferenceChangeListener(this);

        // Initialize the Navigation View and select the first item
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        // Setup the toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        toolbar.setNavigationOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onClick(View v) {

        drawerLayout.openDrawer(GravityCompat.START);

    }

    private static void performSearch(final String search) {

        App.clearData();
        App.getSearchFragment()
                .getAsyncTask(API.SEARCH)
                .execute(search);

        App.notifySearchFragmentAdapter();

    }

    private void addFragments(final Iterable<API> set) {

        for (API tab : set) {

            adapter.addFragment(MovieListFragment.newInstance(tab), tab);

        }

        adapter.notifyDataSetChanged();

    }

    @Override
    public void onRestoreInstanceState(@NonNull final Bundle savedInstanceState) {

        savedInstanceStateSearchQuery = savedInstanceState.getCharSequence(QUERIED_TEXT);
        searchMethod = Search.valueOf(savedInstanceState.getString(SEARCH_METHOD));

        //        displayedTabs = (EnumSet<API>)savedInstanceState.getSerializable(DISPLAYED_TABS);

        super.onSaveInstanceState(savedInstanceState);

    }

    @Override
    public void onSaveInstanceState(final Bundle savedInstanceState) {

        if (searchMethod != null)
        savedInstanceState.putString(SEARCH_METHOD, searchMethod.name());

        if (searchView != null)
            savedInstanceState.putCharSequence(QUERIED_TEXT, searchView.getQuery());

        //        if (displayedTabs != null)
        //            savedInstanceState.putSerializable(DISPLAYED_TABS, displayedTabs.toArray(API.values()));

        super.onSaveInstanceState(savedInstanceState);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // This static call will reset default values only on the first ever read
        PreferenceManager.setDefaultValues(getBaseContext(), R.xml.preferences, false);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

        String enumString = pref.getString(
                getString(R.string.pref_search_method_key),
                Search.BUFFER.name());

        searchMethod = Search.valueOf(enumString);

    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final MenuItem searchMenu = menu.findItem(R.id.search);

        searchView = (SearchView) MenuItemCompat.getActionView(searchMenu);
        searchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(searchMenu, new OnInitiateSearchListener());

        // Handle Rotation changes for search

        if (wasScreenRotated()) {

            searchMenu.expandActionView();
            searchView.setQuery(savedInstanceStateSearchQuery, false);
            savedInstanceStateSearchQuery = "";

            App.notifySearchFragmentAdapter();

        }

        return true;

    }

    @Override
    public boolean onQueryTextSubmit(final String query) {

        performSearch(query);
        return false;

    }

    @Override
    public boolean onQueryTextChange(final String newText) {

        if (wasScreenRotated()) return false;
        if (isFromVoiceSearch) {
            handleVoiceSearch(newText);
        }

        if (searchMethod.canSearch(newText)) {

            performSearch(Search.getSearchableWord());

        }

        return false;

    }

    private boolean wasScreenRotated() { return savedInstanceStateSearchQuery.length() > 0; }

    private void handleVoiceSearch(final String word) {

        this.isFromVoiceSearch = false;
        this.onQueryTextSubmit(word);

    }

    @Override
    protected void onNewIntent(final Intent intent) {

        if (intent.getAction().equals(Intent.ACTION_SEARCH)) {

            final String query = intent.getStringExtra(SearchManager.QUERY);

            isFromVoiceSearch = true;

            // Emulates clicking Search button - onQueryTextChange(). No other action needed.
            searchView.setQuery(query, false);

        }

    }

    private void addTabs(final Iterable<API> set) {

        addFragments(set);

        //        viewPager.setAdapter(adapter);
        //        tabLayout.removeAllTabs();
        //        tabLayout.setupWithViewPager(viewPager);

        //        adapter.notifyDataSetChanged();

    }

    private void clearAllTabs() {

        adapter.clearTabs();
        App.clearData();

    }

    private class OnInitiateSearchListener implements MenuItemCompat.OnActionExpandListener {

        int previousTabPosition;

        @Override
        public boolean onMenuItemActionCollapse(final MenuItem item) {

            clearAllTabs();

            addTabs(displayedTabs);

            tabLayout.removeAllTabs();
            tabLayout.setupWithViewPager(viewPager);

            viewPager.setCurrentItem(previousTabPosition, true);

            return true;

        }

        @Override
        public boolean onMenuItemActionExpand(final MenuItem item) {

            previousTabPosition = viewPager.getCurrentItem();

            addTabs(API.SEARCH_GROUP);

            tabLayout.removeAllTabs();
            tabLayout.setupWithViewPager(viewPager);

            viewPager.setCurrentItem(adapter.getCount() - 1, true);

            return true;

        }

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if (key.equals(getString(R.string.pref_search_method_key))) {

            String enumString = sharedPreferences.getString(
                    getString(R.string.pref_search_method_key),
                    Search.BUFFER.name());

            searchMethod = Search.valueOf(enumString);

        }

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        //        if (previousMenuItem != null) previousMenuItem.setChecked(false);
        //
        //        previousMenuItem = item;
        //        item.setChecked(true);

        switch (item.getItemId()) {

            case R.id.navigationSetting:

                startActivity(new Intent(this, SettingsActivity.class));
                break;

            case R.id.navigationOnline:
                setCurrentTabs(API.WEB_GROUP);
                break;
            case R.id.navigationMyCollection:
                setCurrentTabs(API.OFFLINE_GROUP);
                break;

            default: // Do nothing

        }

        closeDrawer();

        return true;

    }

}
