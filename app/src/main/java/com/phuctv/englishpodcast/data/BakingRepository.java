package com.phuctv.englishpodcast.data;


import com.phuctv.englishpodcast.domain.models.FavouriteModel;
import com.phuctv.englishpodcast.domain.models.PodcastModel;

import java.util.List;

import io.reactivex.Single;

public class BakingRepository implements BakingDataSource {

    BakingDataSource mLocalDataSource;
    BakingDataSource mRemoteDataSource;

    public BakingRepository(BakingDataSource mLocalDataSource,
                            BakingDataSource mRemoteDataSource) {
        this.mLocalDataSource = mLocalDataSource;
        this.mRemoteDataSource = mRemoteDataSource;
    }

    @Override
    public Single<List<PodcastModel>> getPodcasts(String url) {
        return mRemoteDataSource.getPodcasts(url);
    }

    @Override
    public Single<String> getTranscript(String url) {
        return mRemoteDataSource.getTranscript(url);
    }

    @Override
    public Single<List<FavouriteModel>> getFavourites() {
        return mLocalDataSource.getFavourites();
    }

    @Override
    public Single<FavouriteModel> getFavouriteById(String url) {
        return mLocalDataSource.getFavouriteById(url);
    }

    @Override
    public Single<Boolean> saveFavourite(PodcastModel favouriteModel) {
        return mLocalDataSource.saveFavourite(favouriteModel);
    }

    @Override
    public Single<Boolean> deleteFavouriteById(String url) {
        return mLocalDataSource.deleteFavouriteById(url);
    }
}
