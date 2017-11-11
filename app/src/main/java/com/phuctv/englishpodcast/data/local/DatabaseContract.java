package com.phuctv.englishpodcast.data.local;

import android.content.ContentUris;
import android.net.Uri;

public class DatabaseContract {
    public static String AUTHORITY = "com.phuctv.englishpodcast";
    public static Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_FAVOURITES = "favourites";

    public static class FavouriteEntry {
        public static final String TABLE_NAME = "favourite";
        public static final String COLUMN_NAME_URL = "url";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_AUDIO_LINK = "audio_link";
        public static final String COLUMN_NAME_LYRIC_LINK = "lyric_link";
        public static final String COLUMN_NAME_IMAGE_LINK = "image_link";
        public static final String COLUMN_NAME_TIME = "time";
        public static final String COLUMN_NAME_DESC = "description";
        public static final String COLUMN_NAME_TIMESTAMP = "insert_timestamp";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVOURITES).build();

        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

}
