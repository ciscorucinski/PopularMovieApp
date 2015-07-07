package com.udacity.rucinskic.spotify_streamer.enums;

import com.udacity.rucinskic.spotify_streamer.interfaces.Searchable;

public enum OnTextChange implements Searchable {  // todo Part 2: rename class

    CHARACTER { @Override public boolean canSearch(String word) { return isNotEmpty(word); }    },
    WORD      { @Override public boolean canSearch(String word) { return isNewWord(word); }     },
    PHRASE    { @Override public boolean canSearch(String word) { return false; }               },
    BUFFER    { @Override public boolean canSearch(String word) { return reachedBuffer(word); } };

    // Note: Phrase returns false because there is no efficient way to tell a user is done typing.
    // They can press the search button to search

    // TODO add enums dealing with timing.

    private static String searchTerm;
    private static final int BUFFER_SIZE = 3;

    private static boolean isConditionMet(String word, boolean newSearchWordCondition) {

        searchTerm = (newSearchWordCondition) ? word : "";
        return newSearchWordCondition;

    }

    private static boolean isNewWord(String word) {

        return isConditionMet(word,
                word.endsWith(" ") && (isNotEmpty(word)));

    }
    private static boolean reachedBuffer(String word) {

        return isConditionMet(word,
                word.length() > BUFFER_SIZE);

    }
    private static boolean isNotEmpty(String word) {

        return isConditionMet(word,
                !word.isEmpty());

    }

    public String getSearchWord() { return searchTerm; }

}
