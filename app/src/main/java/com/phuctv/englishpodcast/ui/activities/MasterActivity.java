package com.phuctv.englishpodcast.ui.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.transition.TransitionInflater;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.google.firebase.crash.FirebaseCrash;
import com.phuctv.englishpodcast.R;
import com.phuctv.englishpodcast.domain.models.ChannelModel;
import com.phuctv.englishpodcast.domain.models.PodcastModel;
import com.phuctv.englishpodcast.ui.adapters.PodcastAdapter;
import com.phuctv.englishpodcast.ui.fragments.BaseFragment;
import com.phuctv.englishpodcast.ui.fragments.BaseMasterFragment;
import com.phuctv.englishpodcast.ui.fragments.ChannelsFragment;
import com.phuctv.englishpodcast.ui.fragments.AudioPlayingFragment;
import com.phuctv.englishpodcast.ui.fragments.PodcastsFragment;

/**
 * Created by phuctran on 11/2/17.
 */

public class MasterActivity extends BaseActivity implements BaseMasterFragment.BaseFragmentResponder {

    private static final String FRAGMENT_CHANNELS = "FRAGMENT_CHANNELS";
    private static final String FRAGMENT_PODCASTS = "FRAGMENT_PODCASTS";
    private static final String FRAGMENT_PLAYING = "FRAGMENT_PLAYING";

    @Override
    protected void updateFollowingViewBinding(Bundle savedInstanceState) {
        FirebaseCrash.report(new Exception("My first Android non-fatal error"));
        FirebaseCrash.log("Activity created");
        goToChannelsScreen();
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_master;
    }

    @Override
    public void goToChannelsScreen() {
        showFragment(ChannelsFragment.newInstance(), FRAGMENT_CHANNELS);
    }

    @Override
    public void goToPodcastsScreen(ChannelModel channelModel) {
        showFragment(PodcastsFragment.newInstance(channelModel), FRAGMENT_PODCASTS);
    }

    @Override
    public void goToPodcastListeningScreen(PodcastModel podcastId, int position, View view, PodcastsFragment podcastsFragment) {
        BaseFragment newFragment = AudioPlayingFragment.newInstance(podcastId, PodcastAdapter.TRANSITION_PODCAST_KEY + position);
        showFragmentWithShareElementTransition(podcastsFragment, newFragment, FRAGMENT_PLAYING, view, PodcastAdapter.TRANSITION_PODCAST_KEY + position);
    }
}
