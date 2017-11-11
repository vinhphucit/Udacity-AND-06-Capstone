package com.phuctv.englishpodcast.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by phuctran on 9/12/17.
 */

public class PodcastDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "englishpodcastudacity.db";

    private static final int DATABASE_VERSION = 1;

    public PodcastDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_FAVOURITE_TALBE = "CREATE TABLE " +
                DatabaseContract.FavouriteEntry.TABLE_NAME + " (" +

                DatabaseContract.FavouriteEntry.COLUMN_NAME_URL + " TEXT PRIMARY KEY, " +
                DatabaseContract.FavouriteEntry.COLUMN_NAME_NAME + " TEXT, " +
                DatabaseContract.FavouriteEntry.COLUMN_NAME_DESC + " TEXT, " +
                DatabaseContract.FavouriteEntry.COLUMN_NAME_LYRIC_LINK + " TEXT, " +
                DatabaseContract.FavouriteEntry.COLUMN_NAME_AUDIO_LINK + " TEXT, " +
                DatabaseContract.FavouriteEntry.COLUMN_NAME_IMAGE_LINK + " TEXT, " +
                DatabaseContract.FavouriteEntry.COLUMN_NAME_TIME + " TEXT, " +
                DatabaseContract.FavouriteEntry.COLUMN_NAME_TIMESTAMP + " DATETIME DEFAULT (datetime('now','localtime'))) ";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVOURITE_TALBE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.FavouriteEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
