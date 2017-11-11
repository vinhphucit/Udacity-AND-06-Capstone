package com.phuctv.englishpodcast.domain.usecases;

import com.phuctv.englishpodcast.data.BakingRepository;
import com.phuctv.englishpodcast.utils.RxUtils;

import io.reactivex.Single;

/**
 * Created by phuctran on 11/7/17.
 */

public class DeleteFavouriteUseCase {
    private BakingRepository mRepository;

    public DeleteFavouriteUseCase(BakingRepository mRepository) {
        this.mRepository = mRepository;
    }

    public Single<Boolean> deleteFavourite(String url) {
        return mRepository.deleteFavouriteById(url).compose(RxUtils.applySchedulersSingle());
    }
}
