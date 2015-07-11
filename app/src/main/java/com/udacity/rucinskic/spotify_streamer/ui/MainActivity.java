package com.udacity.rucinskic.spotify_streamer.ui;

import android.os.Bundle;
import android.view.MenuItem;

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
    public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId()) {

            case R.id.search_character:   searchMethod = Search.CHARACTER; break;
            case R.id.search_word:        searchMethod = Search.WORD; break;
            case R.id.search_phrase:      searchMethod = Search.PHRASE; break;
            case R.id.search_buffer:      searchMethod = Search.BUFFER; break;

            default:                    // Do nothing

        }

        item.setChecked(true);

        return super.onOptionsItemSelected(item);

    }

    @Override
    EnumSet<API> collectTabValues() { return API.WEB_GROUP; }

}
