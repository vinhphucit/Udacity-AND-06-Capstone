package com.phuctv.englishpodcast.domain.usecases;

import com.phuctv.englishpodcast.data.BakingRepository;
import com.phuctv.englishpodcast.domain.models.PodcastModel;
import com.phuctv.englishpodcast.utils.RxUtils;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by phuctran on 11/7/17.
 */

public class GetTranscriptUseCase {
    private BakingRepository mRepository;

    public GetTranscriptUseCase(BakingRepository mRepository) {
        this.mRepository = mRepository;
    }

    public Single<String> getTranscript(String url) {
        return mRepository.getTranscript(url).compose(RxUtils.applySchedulersSingle());
    }
}
