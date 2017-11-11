package com.phuctv.englishpodcast.data;


import com.phuctv.englishpodcast.domain.models.FavouriteModel;
import com.phuctv.englishpodcast.domain.models.PodcastModel;

import java.util.List;

import io.reactivex.Single;

public interface BakingDataSource {


    Single<List<PodcastModel>> getPodcasts(String url);

    Single<String> getTranscript(String url);

    Single<List<FavouriteModel>> getFavourites();

    Single<FavouriteModel> getFavouriteById(String url);

    Single<Boolean> saveFavourite(PodcastModel favouriteModel);

    Single<Boolean> deleteFavouriteById(String url);
}
