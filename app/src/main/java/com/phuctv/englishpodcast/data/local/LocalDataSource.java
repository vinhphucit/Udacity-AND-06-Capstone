package com.phuctv.englishpodcast.data.local;


import android.content.ContentValues;
import android.database.Cursor;

import com.phuctv.englishpodcast.PodcastApplication;
import com.phuctv.englishpodcast.data.BakingDataSource;
import com.phuctv.englishpodcast.domain.models.FavouriteModel;
import com.phuctv.englishpodcast.domain.models.PodcastModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

import static com.phuctv.englishpodcast.data.local.DatabaseContract.FavouriteEntry.COLUMN_NAME_AUDIO_LINK;
import static com.phuctv.englishpodcast.data.local.DatabaseContract.FavouriteEntry.COLUMN_NAME_DESC;
import static com.phuctv.englishpodcast.data.local.DatabaseContract.FavouriteEntry.COLUMN_NAME_IMAGE_LINK;
import static com.phuctv.englishpodcast.data.local.DatabaseContract.FavouriteEntry.COLUMN_NAME_LYRIC_LINK;
import static com.phuctv.englishpodcast.data.local.DatabaseContract.FavouriteEntry.COLUMN_NAME_NAME;
import static com.phuctv.englishpodcast.data.local.DatabaseContract.FavouriteEntry.COLUMN_NAME_TIME;
import static com.phuctv.englishpodcast.data.local.DatabaseContract.FavouriteEntry.COLUMN_NAME_URL;
import static com.phuctv.englishpodcast.data.local.DatabaseContract.FavouriteEntry.CONTENT_URI;

/**
 * Created by phuctran on 10/3/17.
 */

public class LocalDataSource implements BakingDataSource {
    private static LocalDataSource INSTANCE = null;

    public LocalDataSource() {
    }

    public static LocalDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LocalDataSource();
        }
        return INSTANCE;
    }


    @Override
    public Single<List<PodcastModel>> getPodcasts(String url) {
        return null;
    }

    @Override
    public Single<String> getTranscript(String url) {
        return null;
    }

    @Override
    public Single<List<FavouriteModel>> getFavourites() {
        List<FavouriteModel> favouriteModels = new ArrayList<>();
        Cursor cursor = PodcastApplication.getContext().getContentResolver().query(CONTENT_URI, null, null, null, null);
        if (cursor.getCount() > 0) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
//                String name, String url, String audio_link, String lyric_links, String image_link, String time, String desc, String insertTime
                FavouriteModel favouriteModel = new FavouriteModel(cursor.getString(cursor.getColumnIndex(DatabaseContract.FavouriteEntry.COLUMN_NAME_NAME)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.FavouriteEntry.COLUMN_NAME_URL)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.FavouriteEntry.COLUMN_NAME_AUDIO_LINK)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.FavouriteEntry.COLUMN_NAME_LYRIC_LINK)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.FavouriteEntry.COLUMN_NAME_IMAGE_LINK)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.FavouriteEntry.COLUMN_NAME_TIME)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.FavouriteEntry.COLUMN_NAME_DESC)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.FavouriteEntry.COLUMN_NAME_TIMESTAMP)));
                favouriteModels.add(favouriteModel);
            }
        }
        return Single.fromCallable(() -> favouriteModels);
    }

    @Override
    public Single<FavouriteModel> getFavouriteById(String url) {
        String[] args = {url};
        Cursor cursor = PodcastApplication.getContext().getContentResolver().query(CONTENT_URI, null, DatabaseContract.FavouriteEntry.COLUMN_NAME_URL + "=?", args, null);
        System.out.println("");
        if (cursor.getCount() > 0) {
            return Single.fromCallable(() -> new FavouriteModel());
        }

        return Single.fromCallable(() -> null);
    }

    @Override
    public Single<Boolean> saveFavourite(PodcastModel mMovieModel) {
        ContentValues contentValues = new ContentValues();


        contentValues.put(COLUMN_NAME_URL, mMovieModel.getUrl());
        contentValues.put(COLUMN_NAME_NAME, mMovieModel.getName());
        contentValues.put(COLUMN_NAME_AUDIO_LINK, mMovieModel.getAudio_link());
        contentValues.put(COLUMN_NAME_LYRIC_LINK, mMovieModel.getLyric_links());
        contentValues.put(COLUMN_NAME_IMAGE_LINK, mMovieModel.getImage_link());
        contentValues.put(COLUMN_NAME_TIME, mMovieModel.getTime());
        contentValues.put(COLUMN_NAME_DESC, mMovieModel.getDesc());

        PodcastApplication.getContext().getContentResolver().insert(CONTENT_URI, contentValues);

        return Single.fromCallable(() -> true);
    }

    @Override
    public Single<Boolean> deleteFavouriteById(String url) {
        String[] args = {url};
        PodcastApplication.getContext().getContentResolver().delete(CONTENT_URI, COLUMN_NAME_URL + "=?", args);
        return Single.fromCallable(() -> true);
    }
}
