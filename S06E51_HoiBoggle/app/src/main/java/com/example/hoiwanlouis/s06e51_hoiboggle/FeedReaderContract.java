package com.example.hoiwanlouis.s06e51_hoiboggle;

/**
 * Created by Michael on 10/25/2014.
 */

import android.provider.BaseColumns;

public final class FeedReaderContract {
    private final String DEBUG_TAG = this.getClass().getSimpleName();

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public FeedReaderContract() {}

    /* Inner class that defines the table contents */
    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "highscore";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_PLAYER = "player";
        public static final String COLUMN_NAME_SCORE = "score";
    }
}
