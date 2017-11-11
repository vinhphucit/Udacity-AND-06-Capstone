package com.phuctv.englishpodcast.ui.fragments;

import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.phuctv.englishpodcast.R;
import com.phuctv.englishpodcast.domain.models.ChannelModel;
import com.phuctv.englishpodcast.domain.models.PodcastModel;
import com.phuctv.englishpodcast.mvp.presenters.ChannelsPresenter;
import com.phuctv.englishpodcast.mvp.presenters.PodcastsPresenter;
import com.phuctv.englishpodcast.mvp.views.ChannelsContract;
import com.phuctv.englishpodcast.mvp.views.PodcastsContract;
import com.phuctv.englishpodcast.ui.adapters.ChannelAdapter;
import com.phuctv.englishpodcast.ui.adapters.PodcastAdapter;
import com.phuctv.englishpodcast.utils.Injection;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;

/**
 * Created by phuctran on 11/2/17.
 */

public class PodcastsFragment extends BaseMasterFragment implements PodcastsContract.View, PodcastAdapter.ListItemClickListener {
    private static final String TAG = PodcastsFragment.class.getSimpleName();

    public static final String ARGS_CHANNEL = "ARGS_CHANNEL";
    @BindView(R.id.recycler_view)
    RecyclerView mRvRecipes;
    private PodcastsContract.Presenter mPresenter;
    private ChannelModel mChannelModel;
    private PodcastAdapter mMovieAdapter;
    private List<PodcastModel> mRecipes;

    public PodcastsFragment() {
        this.mPresenter = new PodcastsPresenter(this, Injection.provideGetPodcastsUseCase());
    }

    public static BaseFragment newInstance(ChannelModel channel) {
        BaseFragment fragment = new PodcastsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGS_CHANNEL, Parcels.wrap(channel));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected String getSubclassName() {
        return TAG;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(ARGS_CHANNEL)) {
            this.mChannelModel = Parcels.unwrap(getArguments().getParcelable(ARGS_CHANNEL));
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, mChannelModel.getId());
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, mChannelModel.getTitle());
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Channel");
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_podcasts;
    }

    @Override
    protected void updateFollowingViewBinding() {
        if (mRecipes == null)
            mPresenter.doGetPodcasts(mChannelModel.getPodcastsUrl());
        else
            renderPodcasts(mRecipes);
    }

    @Override
    public void setPresenter(PodcastsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void renderPodcasts(List<PodcastModel> podcasts) {
        setupRecyclerView();
        this.mRecipes = podcasts;
        mMovieAdapter = new PodcastAdapter(getContext(), mRecipes,
                this);
        mRvRecipes.setAdapter(mMovieAdapter);
    }

    private void setupRecyclerView() {
        int mSpanCount = getContext().getResources().getInteger(R.integer.span);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), mSpanCount);
        mRvRecipes.setLayoutManager(layoutManager);
        mRvRecipes.setHasFixedSize(true);
    }

    @Override
    public void onListItemClick(PodcastModel movbieModel, int position, View view) {
        goToPodcastListeningScreen(movbieModel, position, view, this);
    }
}
