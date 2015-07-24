package com.udacity.rucinskic.spotify_streamer.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.udacity.rucinskic.spotify_streamer.ui.support.ViewPagerFragmentAdapter;

import java.util.EnumSet;

public abstract class BaseSearchTabbedActivity extends AppCompatActivity
        implements SearchView.OnQueryTextListener, NavigationView.OnNavigationItemSelectedListener,
                   View.OnClickListener {

    Search searchMethod = Search.BUFFER;

    public DrawerLayout drawerLayout;

    private SearchView searchView;
    private ViewPagerFragmentAdapter adapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private CharSequence savedInstanceStateSearchQuery = "";
    private boolean isFromVoiceSearch;

    private static final String QUERIED_TEXT = "queriedText";
    private static final String SEARCH_METHOD = "searchMethod";

    abstract EnumSet<API> collectTabValues();

    void initializeToolbar() {

        // Setup the toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        NavigationView view = (NavigationView) findViewById(R.id.navigationView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        toolbar.setNavigationOnClickListener(this);
        view.setNavigationItemSelectedListener(this);

        // Initialize all components. Tabs, Pager, and Adapter
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new ViewPagerFragmentAdapter(getSupportFragmentManager());

        // Add the tabs
        addFragments(collectTabValues());

        // Bind the adapter to the tabs
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

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

    }

    @Override
    public void onRestoreInstanceState(@NonNull final Bundle savedInstanceState) {

        savedInstanceStateSearchQuery = savedInstanceState.getCharSequence(QUERIED_TEXT);
        searchMethod = Search.valueOf(savedInstanceState.getString(SEARCH_METHOD));

        super.onSaveInstanceState(savedInstanceState);

    }

    @Override
    public void onSaveInstanceState(final Bundle savedInstanceState) {

        savedInstanceState.putString(SEARCH_METHOD, searchMethod.name());

        if (searchView != null)
            savedInstanceState.putCharSequence(QUERIED_TEXT, searchView.getQuery());


        super.onSaveInstanceState(savedInstanceState);

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

        // Variable is initialized and declared as class global. Will not be null
        switch (searchMethod) {

            case CHARACTER:
                menu.findItem(R.id.search_character).setChecked(true);
                break;
            case WORD:
                menu.findItem(R.id.search_word).setChecked(true);
                break;
            case PHRASE:
                menu.findItem(R.id.search_phrase).setChecked(true);
                break;
            case BUFFER:
                menu.findItem(R.id.search_buffer).setChecked(true);
                break;

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

    private class OnInitiateSearchListener implements MenuItemCompat.OnActionExpandListener {

        int previousTabPosition;

        @Override
        public boolean onMenuItemActionCollapse(final MenuItem item) {

            adapter.clearTabs();
            App.clearData();

            addTabs(API.WEB_GROUP);
            viewPager.setCurrentItem(previousTabPosition, true);

            return true;

        }

        @Override
        public boolean onMenuItemActionExpand(final MenuItem item) {

            previousTabPosition = viewPager.getCurrentItem();

            addTabs(API.SEARCH_GROUP);
            viewPager.setCurrentItem(adapter.getCount() - 1, true);

            return true;

        }

        private void addTabs(final Iterable<API> set) {

            addFragments(set);
            adapter.notifyDataSetChanged();

            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);

        }

    }

}
