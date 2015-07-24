    package com.udacity.rucinskic.spotify_streamer.ui;

    import android.content.Intent;
    import android.os.AsyncTask;
    import android.os.Bundle;
    import android.os.Handler;
    import android.support.annotation.NonNull;
    import android.support.annotation.Nullable;
    import android.support.v4.app.Fragment;
    import android.support.v7.widget.GridLayoutManager;
    import android.support.v7.widget.RecyclerView;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;

    import com.udacity.rucinskic.spotify_streamer.App;
    import com.udacity.rucinskic.spotify_streamer.R;
    import com.udacity.rucinskic.spotify_streamer.enums.API;
    import com.udacity.rucinskic.spotify_streamer.interfaces.Asyncronizable;
    import com.udacity.rucinskic.spotify_streamer.interfaces.OnItemClickListener;
    import com.udacity.rucinskic.spotify_streamer.ui.support.MovieListAdapter;
    import com.udacity.rucinskic.spotify_streamer.ui.support.web.JsonHandlerCallback;
    import com.udacity.rucinskic.spotify_streamer.ui.support.web.WebService;

    import org.json.JSONException;


    public class MovieListFragment extends Fragment
        implements OnItemClickListener, Asyncronizable<String, Void, Void> {

    private MovieListAdapter adapter;
    private API api;

    private static final String API_STRING = "api";

    public MovieListFragment() {}

    public static MovieListFragment newInstance(API api) {

        MovieListFragment existingSearchFragment;

        existingSearchFragment = getSearchFragmentIfExists(api);

        if (existingSearchFragment != null) return existingSearchFragment;

        // Either the API isn't from the search_group, or it is from the search_group but doesn't exist
        MovieListFragment newFragment = createNewFragment(api);

        // If the API is from the search_group, but the fragment didn't exist, then make the fragment globally available
        if (api.isFrom(API.SEARCH_GROUP)) { App.setSearchFragment(newFragment); }

        return newFragment;

    }

    @Nullable
    private static MovieListFragment getSearchFragmentIfExists(API api) {

        if (api.isFrom(API.SEARCH_GROUP)) {

            MovieListFragment fragment = App.getSearchFragment();

            if (fragment != null) return fragment;

        }

        return null;

    }

    @NonNull
    private static MovieListFragment createNewFragment(API api) {

        MovieListFragment fragment = new MovieListFragment();

        Bundle args = new Bundle();
        args.putSerializable(API_STRING, api);
        fragment.setArguments(args);

        return fragment;

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_movies_list, container, false);

        api = (API) getArguments().getSerializable(API_STRING);

        adapter = new MovieListAdapter(getActivity(), api, this);

        assert api != null;
        if (api.isFrom(API.WEB_GROUP)) {

            //            adapter.clear();
            AsyncTask<String, Void, Void> getMovies = getAsyncTask(api);
            getMovies.execute();

        }

        RecyclerView movieListView;

        movieListView = (RecyclerView) rootView.findViewById(R.id.movie_list);
        movieListView.setAdapter(adapter);
        movieListView.setItemViewCacheSize(App.getMovieCollectionLimit());
        movieListView.setLayoutManager(
                new GridLayoutManager(  // Changes number of columns based on form-factor and orientation
                        getActivity(),
                        getResources().getInteger(R.integer.columns)));

        return rootView;

    }

    public MovieListAdapter getAdapter() { return this.adapter; }

    @Override
    public void onClick(final View v, final int position) {

        Intent activity = new Intent(this.getActivity(), SecondaryActivity.class);

        activity.putExtra("movie", api);
        activity.putExtra("position", position);

        startActivity(activity); // TODO pass data to second activity

    }

    @Override
    public AsyncTask<String, Void, Void> getAsyncTask(final API api) {

        if (api.isFrom(API.SEARCH_GROUP)) return new GetMoviesSearchAsync();
        if (api.isFrom(API.WEB_GROUP)) return new GetMoviesAsync();

        return null;

    }

    private class GetMoviesAsync extends AsyncTask<String, Void, Void> {

        private final Handler.Callback getMoviesFromJSON = new JsonHandlerCallback(api);

        @Override
        protected Void doInBackground(final String... params) {

            try {

                // This will get the JSON from the webservice, and will use the CallBack to assign the List of movies.
                new WebService(api.getUri()).acquireDataWithCallback(getMoviesFromJSON);

            } catch (JSONException e) { e.printStackTrace(); }

            return null;
        }

        @Override
        protected void onPostExecute(final Void aVoid) {

            adapter.notifyDataSetChanged(); // This needs to occur on the main thread

        }

    }

    private class GetMoviesSearchAsync extends AsyncTask<String, Void, Void> {

        private final Handler.Callback getMoviesFromJSON = new JsonHandlerCallback(api);

        @Override
        protected Void doInBackground(final String... params) {

            try {

                // This will get the JSON from the webservice, and will use the CallBack to assign the List of movies.
                new WebService(api.getUri(params[0])).acquireDataWithCallback(getMoviesFromJSON);

            } catch (JSONException e) { e.printStackTrace(); }

            return null;
        }

        @Override
        protected void onPostExecute(final Void aVoid) {

            App.getSearchFragment().getAdapter().notifyDataSetChanged();

        }

    }

}

