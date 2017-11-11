package com.phuctv.englishpodcast.domain.usecases;

import com.phuctv.englishpodcast.data.BakingRepository;
import com.phuctv.englishpodcast.domain.models.ChannelModel;
import com.phuctv.englishpodcast.domain.models.PodcastModel;
import com.phuctv.englishpodcast.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by phuctran on 11/7/17.
 */

public class GetPodcastsUseCase {
    private BakingRepository mRepository;

    public GetPodcastsUseCase(BakingRepository mRepository) {
        this.mRepository = mRepository;
    }

    public Single<List<PodcastModel>> getPodcastList(String url) {
        return mRepository.getPodcasts(url).doOnSuccess(podcastModels -> {

        }).compose(RxUtils.applySchedulersSingle());
    }
}
