package com.phuctv.englishpodcast.domain.usecases;

import com.phuctv.englishpodcast.data.BakingRepository;
import com.phuctv.englishpodcast.domain.models.FavouriteModel;
import com.phuctv.englishpodcast.utils.RxUtils;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by phuctran on 11/7/17.
 */

public class GetFavouritesUseCase {
    private BakingRepository mRepository;

    public GetFavouritesUseCase(BakingRepository mRepository) {
        this.mRepository = mRepository;
    }

    public Single<List<FavouriteModel>> getFavourites() {
        return mRepository.getFavourites().compose(RxUtils.applySchedulersSingle());
    }
}
