package com.udacity.rucinskic.spotify_streamer.ui.support.model;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class ReleaseDate {

	private final GregorianCalendar releaseDate;

	private String year;
	private String month;

	private boolean hasReleaseDate;
	private boolean hasReleaseMonth;

	public ReleaseDate(int year, int month) {

		this.releaseDate = new GregorianCalendar(year, month, 1);

		calculateYear();
		calculateMonth();

	}

	private void calculateYear() {

		try {

			this.year = String.valueOf(releaseDate.get(Calendar.YEAR));
			this.hasReleaseDate = true;

		} catch (Exception ignored) {
		}

	}

	private void calculateMonth() {

		try {

			this.month =
					releaseDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
			this.hasReleaseMonth = true;

		} catch (Exception ignored) {
		}

	}

	public boolean hasReleaseDate() { return hasReleaseDate; }

	public boolean hasReleaseMonth() { return hasReleaseMonth; }

	public String getYear() { return this.year; }

	public String getMonth() { return this.month; }

}