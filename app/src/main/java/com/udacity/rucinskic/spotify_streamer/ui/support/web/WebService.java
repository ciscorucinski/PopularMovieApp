package com.udacity.rucinskic.spotify_streamer.ui.support.web;

import android.net.Uri;
import android.os.Handler;
import android.os.Message;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebService {

    private final Uri uri;

    public WebService(final Uri uri) { this.uri = uri; }

    public void acquireDataWithCallback(final Handler.Callback callback) throws JSONException {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        Message jsonObject;

        try {

            URL url = new URL(uri.toString());

            // Create the request to the URI, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();

            if (inputStream == null) { return; }

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {

                buffer.append(line).append('\n');

            }

            if (buffer.length() == 0) { return; }

            JSONObject movieListJson = new JSONObject(buffer.toString());

            jsonObject = Message.obtain();
            jsonObject.obj = movieListJson;

        } catch (IOException e) {

            // If the code didn't successfully get the  data, there's no point in attempting
            // to parse it.
            return;

        } finally {

            if (urlConnection != null) { urlConnection.disconnect(); }
            if (reader != null) { try { reader.close(); } catch (final IOException ignored) { } }

        }

        callback.handleMessage(jsonObject);

//        try {
//
//            return getMovieDataFromJson(jsonString);
//
//        } catch (JSONException e) { e.printStackTrace(); }

        // This will only happen if there was an error getting or parsing the forecast.

    }




}
