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

        calculateYear();
        calculateMonth();

    }

    private void calculateYear() {

        try {

            year = yearFormatter.format(releaseDate);
            hasReleaseDate = true;

        } catch (Exception ignored) {
        }

    }

    private void calculateMonth() {

        try {

            month = monthFormatter.format(releaseDate);
            hasReleaseMonth = true;

        } catch (Exception ignored) {
        }

    }

    public boolean hasReleaseDate() { return hasReleaseDate; }

    public boolean hasReleaseMonth() { return hasReleaseMonth; }

    public String getYear() { return this.year; }

    public String getMonth() { return this.month; }

}