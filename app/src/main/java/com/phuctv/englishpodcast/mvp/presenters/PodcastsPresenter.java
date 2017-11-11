package com.phuctv.englishpodcast.mvp.presenters;

import com.phuctv.englishpodcast.domain.models.PodcastModel;
import com.phuctv.englishpodcast.domain.usecases.GetPodcastsUseCase;
import com.phuctv.englishpodcast.mvp.views.PodcastsContract;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by phuctran on 11/7/17.
 */

public class PodcastsPresenter implements PodcastsContract.Presenter {
    private final PodcastsContract.View mView;
    private final GetPodcastsUseCase mGetPodcastsUseCase;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public PodcastsPresenter(PodcastsContract.View mView, GetPodcastsUseCase getRecipesUseCase) {
        this.mView = mView;
        this.mGetPodcastsUseCase = getRecipesUseCase;
        mView.setPresenter(this);
    }


    @Override
    public void doGetPodcasts(String podcastsUrl) {
        mView.showLoading();
        compositeDisposable.add(mGetPodcastsUseCase.getPodcastList(podcastsUrl).subscribeWith(new DisposableSingleObserver<List<PodcastModel>>() {
            @Override
            public void onSuccess(@NonNull List<PodcastModel> recipes) {
                mView.hideLoading();
                mView.renderPodcasts(recipes);
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
