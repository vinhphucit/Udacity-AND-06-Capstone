package com.phuctv.englishpodcast.ui.fragments;

import android.view.View;

import com.phuctv.englishpodcast.domain.models.ChannelModel;
import com.phuctv.englishpodcast.domain.models.PodcastModel;

/**
 * Created by phuctran on 11/2/17.
 */

public abstract class BaseMasterFragment extends BaseFragment {
    public void goToChannelsScreen() {
        ((BaseFragmentResponder) getActivity()).goToChannelsScreen();
    }

    public void goToPodcastsScreen(ChannelModel channelModel) {
        ((BaseFragmentResponder) getActivity()).goToPodcastsScreen(channelModel);
    }

    public void goToPodcastListeningScreen(PodcastModel podcastId, int position, View view, PodcastsFragment podcastsFragment) {
        ((BaseFragmentResponder) getActivity()).goToPodcastListeningScreen(podcastId, position, view, podcastsFragment);
    }

    public interface BaseFragmentResponder extends BaseFragment.BaseFragmentResponder {
        void goToChannelsScreen();

        void goToPodcastsScreen(ChannelModel channelModel);

        void goToPodcastListeningScreen(PodcastModel podcastId, int position, View view, PodcastsFragment podcastsFragment);
    }
}
