package com.phuctv.englishpodcast.domain.usecases;

import com.phuctv.englishpodcast.data.BakingRepository;
import com.phuctv.englishpodcast.domain.models.FavouriteModel;
import com.phuctv.englishpodcast.utils.RxUtils;

import io.reactivex.Single;

/**
 * Created by phuctran on 11/7/17.
 */

public class GetFavouriteByIdUseCase {
    private BakingRepository mRepository;

    public GetFavouriteByIdUseCase(BakingRepository mRepository) {
        this.mRepository = mRepository;
    }

    public Single<FavouriteModel> getFavouriteById(String url) {
        return mRepository.getFavouriteById(url).compose(RxUtils.applySchedulersSingle());
    }
}
