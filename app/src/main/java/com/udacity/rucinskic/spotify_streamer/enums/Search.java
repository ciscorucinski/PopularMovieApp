package com.udacity.rucinskic.spotify_streamer.enums;

import com.udacity.rucinskic.spotify_streamer.interfaces.Searchable;

public enum Search implements Searchable {

    CHARACTER { @Override public boolean canSearch(String word) { return isNotEmpty(word); }    },
    WORD      { @Override public boolean canSearch(String word) { return isNewWord(word); }     },
    PHRASE    { @Override public boolean canSearch(String word) { return false; }               },
    BUFFER    { @Override public boolean canSearch(String word) { return reachedBuffer(word); } };

    // TODO add enums dealing with timing.

    private static String searchTerm;
    private static final int BUFFER_SIZE = 3;

    private static boolean isConditionMet(final String word, final boolean newSearchWordCondition) {

        searchTerm = (newSearchWordCondition) ? word : "";
        return newSearchWordCondition;

    }

    private static boolean isNewWord(final String word) {

        return isConditionMet(word,
                word.endsWith(" ") && (isNotEmpty(word)));

    }
    private static boolean reachedBuffer(final String word) {

        return isConditionMet(word,
                word.length() > BUFFER_SIZE);

    }
    private static boolean isNotEmpty(final String word) {

        return isConditionMet(word,
                !word.isEmpty());

    }

    public static String getSearchableWord() { return searchTerm; }

}
