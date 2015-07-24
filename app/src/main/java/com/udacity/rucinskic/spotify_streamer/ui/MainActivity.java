package com.udacity.rucinskic.spotify_streamer.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.udacity.rucinskic.spotify_streamer.R;
import com.udacity.rucinskic.spotify_streamer.enums.API;
import com.udacity.rucinskic.spotify_streamer.enums.Search;

import java.util.EnumSet;

public class MainActivity extends BaseSearchTabbedActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeToolbar();

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        //        switch (menuItem.getItemId()) {
        //
        //            case R.id.navigationSetting: // Get the new Shared Preferences activity
        //
        //            case R.id.navigationMyCollection:
        //
        //        }
        //
        //        drawerLayout.closeDrawer(GravityCompat.START);

        return true;

    }


    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId()) {

            case R.id.search_character:   searchMethod = Search.CHARACTER; break;
            case R.id.search_word:        searchMethod = Search.WORD; break;
            case R.id.search_phrase:      searchMethod = Search.PHRASE; break;
            case R.id.search_buffer:      searchMethod = Search.BUFFER; break;

            default:                    // Do nothing

        }

        item.setChecked(true);

        Toast.makeText(MainActivity.this, "Hello World", Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(item);

    }

    @Override
    EnumSet<API> collectTabValues() { return API.WEB_GROUP; }

}
