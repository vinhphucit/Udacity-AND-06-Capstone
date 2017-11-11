package com.phuctv.englishpodcast.mvp.presenters;

import com.phuctv.englishpodcast.domain.models.ChannelModel;
import com.phuctv.englishpodcast.domain.usecases.GetChannelsUseCase;
import com.phuctv.englishpodcast.mvp.views.ChannelsContract;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by phuctran on 11/7/17.
 */

public class ChannelsPresenter implements ChannelsContract.Presenter {
    private final ChannelsContract.View mView;
    private final GetChannelsUseCase mGetChannelsUseCase;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public ChannelsPresenter(ChannelsContract.View mView, GetChannelsUseCase getRecipesUseCase) {
        this.mView = mView;
        this.mGetChannelsUseCase = getRecipesUseCase;
        mView.setPresenter(this);
    }


    @Override
    public void doGetChannels() {
        mView.showLoading();
        compositeDisposable.add(mGetChannelsUseCase.getChannelList().subscribeWith(new DisposableSingleObserver<List<ChannelModel>>() {
            @Override
            public void onSuccess(@NonNull List<ChannelModel> recipes) {
                mView.hideLoading();
                mView.renderChannels(recipes);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                mView.hideLoading();
            }
        }));
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }
}
