package com.phuctv.englishpodcast.domain.usecases;

import com.phuctv.englishpodcast.data.BakingRepository;
import com.phuctv.englishpodcast.domain.models.PodcastModel;
import com.phuctv.englishpodcast.utils.RxUtils;

import io.reactivex.Single;

/**
 * Created by phuctran on 11/7/17.
 */

public class AddToFavouriteUseCase {
    private BakingRepository mRepository;

    public AddToFavouriteUseCase(BakingRepository mRepository) {
        this.mRepository = mRepository;
    }

    public Single<Boolean> addToFavourite(PodcastModel podcastModel) {
        return mRepository.saveFavourite(podcastModel).compose(RxUtils.applySchedulersSingle());
    }
}
