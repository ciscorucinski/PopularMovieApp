package com.udacity.rucinskic.spotify_streamer.ui.support.model;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * Created by Christopher on 7/27/2015.
 */
public class ReleaseDate extends SimpleDateFormat {

    SimpleDateFormat yearFormatter;
    SimpleDateFormat monthFormatter;

    GregorianCalendar releaseDate;

    String year;
    String month;

    boolean hasReleaseDate;
    boolean hasReleaseMonth;

    public ReleaseDate(int year, int month) {

        this.releaseDate = new GregorianCalendar(year, month, 0);

        this.yearFormatter = new SimpleDateFormat("yyyy");
        this.monthFormatter = new SimpleDateFormat("MMMM");

        this.hasReleaseDate = false;
        this.hasReleaseMonth = false;

    }

    public boolean hasReleaseDate() {

        try {

            year = yearFormatter.format(releaseDate);
            hasReleaseDate = true;

        } catch (Exception ignored) {
        }

        return hasReleaseDate;

    }

    public boolean hasReleaseMonth() {

        try {

            month = monthFormatter.format(releaseDate);
            hasReleaseMonth = true;

        } catch (Exception ignored) {
        }

        return hasReleaseMonth;

    }

    public String getYear() { return yearFormatter.format(releaseDate); }

    public String getMonth() { return String.valueOf(this.month); }

}