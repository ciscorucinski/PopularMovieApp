package com.udacity.rucinskic.spotify_streamer.ui;

import android.os.Bundle;
import android.view.MenuItem;

import com.udacity.rucinskic.spotify_streamer.enums.OnTextChange;
import com.udacity.rucinskic.spotify_streamer.R;
import com.udacity.rucinskic.spotify_streamer.enums.API;

public class MainActivity extends BaseSearchTabbedActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeToolbar();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.search_character:   searchMethod = OnTextChange.CHARACTER; break;
            case R.id.search_word:        searchMethod = OnTextChange.WORD; break;
            case R.id.search_phrase:      searchMethod = OnTextChange.PHRASE; break;
            case R.id.search_buffer:      searchMethod = OnTextChange.BUFFER; break;

            default:                    // Do nothing

        }

        item.setChecked(true);

        return super.onOptionsItemSelected(item);

    }

    @Override
    void collectTabValues() { setTabs( API.WEB_GROUP); }

}
