package com.phuctv.englishpodcast.ui.fragments;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.phuctv.englishpodcast.R;
import com.phuctv.englishpodcast.domain.models.ChannelModel;
import com.phuctv.englishpodcast.mvp.presenters.ChannelsPresenter;
import com.phuctv.englishpodcast.mvp.views.ChannelsContract;
import com.phuctv.englishpodcast.ui.adapters.ChannelAdapter;
import com.phuctv.englishpodcast.utils.Injection;

import java.util.List;

import butterknife.BindView;

/**
 * Created by phuctran on 11/2/17.
 */

public class ChannelsFragment extends BaseMasterFragment implements ChannelsContract.View, ChannelAdapter.ListItemClickListener {
    private static final String TAG = ChannelsFragment.class.getSimpleName();

    private ChannelsContract.Presenter mPresenter;
    @BindView(R.id.recycler_view)
    RecyclerView mRvRecipes;
    private int mSpanCount = 1;
    private ChannelAdapter mMovieAdapter;
    private List<ChannelModel> mRecipes;

    public static BaseFragment newInstance() {
        BaseFragment fragment = new ChannelsFragment();
        return fragment;
    }

    public ChannelsFragment() {
        this.mPresenter = new ChannelsPresenter(this, Injection.provideGetChannelsUseCase());
    }

    @Override
    protected String getSubclassName() {
        return TAG;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_channels;
    }

    @Override
    protected void updateFollowingViewBinding(Bundle savedInstanceState) {
        setupRecyclerView();
//        setActionBarTitle(R.string.baking_time);

        mPresenter.doGetChannels();
    }

    @Override
    public void setPresenter(ChannelsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void renderChannels(List<ChannelModel> channels) {
        this.mRecipes = channels;
        mMovieAdapter = new ChannelAdapter(getContext(), mRecipes,
                this);
        mRvRecipes.setAdapter(mMovieAdapter);
    }

    private void setupRecyclerView() {
//        mSpanCount = getContext().getResources().getInteger(R.integer.span);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), mSpanCount);
        mRvRecipes.setLayoutManager(layoutManager);
        mRvRecipes.setHasFixedSize(true);
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
    public void onListItemClick(ChannelModel movieModel) {
        goToPodcastsScreen(movieModel);
    }
}
