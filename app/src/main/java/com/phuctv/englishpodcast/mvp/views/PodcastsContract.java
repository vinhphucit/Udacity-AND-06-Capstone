package com.phuctv.englishpodcast.mvp.views;

import com.phuctv.englishpodcast.domain.models.PodcastModel;
import com.phuctv.englishpodcast.mvp.presenters.BasePresenter;

import java.util.List;

/**
 * Created by phuctran on 11/7/17.
 */

public interface PodcastsContract {
    interface View extends LoadDataBaseView<Presenter> {
        void renderPodcasts(List<PodcastModel> podcasts);
    }

    interface Presenter extends BasePresenter {
        void doGetPodcasts(String podcastsUrl);
    }
}
