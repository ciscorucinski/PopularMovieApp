package com.udacity.rucinskic.spotify_streamer.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.udacity.rucinskic.spotify_streamer.App;
import com.udacity.rucinskic.spotify_streamer.R;
import com.udacity.rucinskic.spotify_streamer.enums.API;
import com.udacity.rucinskic.spotify_streamer.enums.Search;
import com.udacity.rucinskic.spotify_streamer.ui.support.ViewPagerFragmentAdapter;

import java.util.EnumSet;

public abstract class BaseSearchTabbedActivity extends AppCompatActivity
        implements SearchView.OnQueryTextListener {

    Search searchMethod = Search.BUFFER;
    private EnumSet<API> tabs;

    private SearchView searchView;

    private ViewPagerFragmentAdapter adapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private CharSequence savedInstanceStateSearchQuery = "";
    private boolean isFromVoiceSearch;

    private static final String QUERIED_TEXT = "queriedText";
    private static final String SEARCH_METHOD = "searchMethod";

    abstract void collectTabValues();

    void setTabs(final EnumSet<API> tabSet) { this.tabs = tabSet; }

    void initializeToolbar() {

        // Setup the toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize all components. Tabs, Pager, and Adapter
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new ViewPagerFragmentAdapter(getSupportFragmentManager());

        // Add the tabs
        collectTabValues();
        addFragments(tabs);

        // Bind the adapter to the tabs
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

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

        MenuItemCompat.setOnActionExpandListener(
                searchMenu, new MenuItemCompat.OnActionExpandListener() {

            int previousTabPosition;

            @Override
            public boolean onMenuItemActionCollapse(final MenuItem item) {

                adapter.clearTabs();

                App.clearData();

                addFragments(tabs);
                adapter.notifyDataSetChanged();

                viewPager.setAdapter(adapter);
                tabLayout.setupWithViewPager(viewPager);

                viewPager.setCurrentItem(previousTabPosition, true);

                return true;

            }

            @Override
            public boolean onMenuItemActionExpand(final MenuItem item) {

                previousTabPosition = viewPager.getCurrentItem();

                adapter.addFragment(MovieListFragment.newInstance(API.SEARCH), API.SEARCH);
                adapter.notifyDataSetChanged();

                viewPager.setAdapter(adapter);
                tabLayout.setupWithViewPager(viewPager);

                viewPager.setCurrentItem(adapter.getCount() - 1, true);

                return true;

            }

        });

        // Handle Rotation changes for search

        if (savedInstanceStateSearchQuery.length() > 0) {

            searchMenu.expandActionView();
            searchView.setQuery(savedInstanceStateSearchQuery, false);
            savedInstanceStateSearchQuery = "";

            App.notifySearchFragmentAdapter();

        }

        // Variable is initialized and declared as class global. Will not be null
        switch (searchMethod) {

            case CHARACTER:     menu.findItem(R.id.search_character).setChecked(true); break;
            case WORD:          menu.findItem(R.id.search_word).setChecked(true); break;
            case PHRASE:        menu.findItem(R.id.search_phrase).setChecked(true); break;
            case BUFFER:        menu.findItem(R.id.search_buffer).setChecked(true); break;

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

        if (savedInstanceStateSearchQuery.length() > 0) return false;
        if (isFromVoiceSearch) { handleVoiceSearch(newText); }
        
        if (searchMethod.canSearch(newText)) {

            performSearch(Search.getSearchableWord());

        }

        return false;

    }

    private void handleVoiceSearch(final String word) {

        this.isFromVoiceSearch = false;
        this.onQueryTextSubmit(word);

    }

    @Override
    protected void onNewIntent(final Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {

            final String query = intent.getStringExtra(SearchManager.QUERY);

            isFromVoiceSearch = true;

            // Emulates clicking Search button - onQueryTextChange(). No other action needed.
            searchView.setQuery(query, false);

        }
    }

}
