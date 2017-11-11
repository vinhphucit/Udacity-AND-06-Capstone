package com.phuctv.englishpodcast.mvp.views;

import com.phuctv.englishpodcast.domain.models.PodcastModel;
import com.phuctv.englishpodcast.domain.models.TranscriptModel;
import com.phuctv.englishpodcast.mvp.presenters.BasePresenter;

import java.util.List;

/**
 * Created by phuctran on 11/7/17.
 */

public interface AudioPlayingContract {
    interface View extends LoadDataBaseView<Presenter> {
        void renderTranscript(List<TranscriptModel> podcasts);
        void renderFavouriteButton(boolean isFavourite);
    }

    interface Presenter extends BasePresenter {
        void doGetTranscript(String podcastsUrl);

        void doCheckIsFavourite(String url);

        void doRemoveFavourite(String url);

        void doAddToFavourite(PodcastModel podcastModel);
    }
}
