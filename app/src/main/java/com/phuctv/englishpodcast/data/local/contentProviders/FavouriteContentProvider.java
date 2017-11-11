package com.phuctv.englishpodcast.data.local.contentProviders;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.phuctv.englishpodcast.data.local.DatabaseContract;
import com.phuctv.englishpodcast.data.local.PodcastDatabaseHelper;

/**
 * Created by phuctran on 11/9/17.
 */

public class FavouriteContentProvider extends ContentProvider {
    public static final int FAVOURITES = 100;
    public static final int FAVOURITE_WITH_ID = 101;
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private PodcastDatabaseHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new PodcastDatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase mDb = mDbHelper.getReadableDatabase();
        switch (sUriMatcher.match(uri)) {

            case FAVOURITES: {

                return mDb.query(DatabaseContract.FavouriteEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
            }
            case FAVOURITE_WITH_ID: {
                String id = uri.getPathSegments().get(1);

                String mSelection = DatabaseContract.FavouriteEntry.COLUMN_NAME_URL + "=?";
                String[] mSelectionArgs = new String[]{id};

                return mDb.query(DatabaseContract.FavouriteEntry.TABLE_NAME,
                        projection,
                        mSelection,
                        mSelectionArgs,
                        null,
                        null,
                        sortOrder);
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        Uri returnUri;
        switch (sUriMatcher.match(uri)) {
            case FAVOURITES: {
                SQLiteDatabase mDb = mDbHelper.getReadableDatabase();
                long id = mDb.insert(DatabaseContract.FavouriteEntry.TABLE_NAME, null, contentValues);
                returnUri = DatabaseContract.FavouriteEntry.buildMovieUri(id);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        switch (sUriMatcher.match(uri)) {
            case FAVOURITES: {
                SQLiteDatabase mDb = mDbHelper.getWritableDatabase();


                int numRowDeleted = mDb.delete(DatabaseContract.FavouriteEntry.TABLE_NAME, s, strings);
                if (numRowDeleted != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return numRowDeleted;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.PATH_FAVOURITES, FAVOURITES);
        uriMatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.PATH_FAVOURITES + "/*", FAVOURITE_WITH_ID);
        return uriMatcher;
    }
}
