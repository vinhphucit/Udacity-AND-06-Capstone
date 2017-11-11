package com.phuctv.englishpodcast.mvp.views;

import com.phuctv.englishpodcast.domain.models.ChannelModel;
import com.phuctv.englishpodcast.mvp.presenters.BasePresenter;

import java.util.List;

/**
 * Created by phuctran on 11/7/17.
 */

public interface ChannelsContract {
    interface View extends LoadDataBaseView<Presenter> {
        void renderChannels(List<ChannelModel> channels);
    }

    interface Presenter extends BasePresenter {
        void doGetChannels();
    }
}
