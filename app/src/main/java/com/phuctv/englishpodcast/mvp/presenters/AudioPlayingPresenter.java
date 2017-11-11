package com.phuctv.englishpodcast.mvp.presenters;

import com.phuctv.englishpodcast.domain.models.FavouriteModel;
import com.phuctv.englishpodcast.domain.models.PodcastModel;
import com.phuctv.englishpodcast.domain.models.TranscriptModel;
import com.phuctv.englishpodcast.domain.usecases.AddToFavouriteUseCase;
import com.phuctv.englishpodcast.domain.usecases.DeleteFavouriteUseCase;
import com.phuctv.englishpodcast.domain.usecases.GetFavouriteByIdUseCase;
import com.phuctv.englishpodcast.domain.usecases.GetTranscriptUseCase;
import com.phuctv.englishpodcast.mvp.views.AudioPlayingContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by phuctran on 11/7/17.
 */

public class AudioPlayingPresenter implements AudioPlayingContract.Presenter {
    private final AudioPlayingContract.View mView;
    private final GetTranscriptUseCase mGetPodcastsUseCase;
    private final DeleteFavouriteUseCase mDeleteFavouriteUseCase;
    private final GetFavouriteByIdUseCase mGetFavouriteByIdUseCase;
    private final AddToFavouriteUseCase mAddToFavouriteUseCase;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public AudioPlayingPresenter(AudioPlayingContract.View mView, GetTranscriptUseCase getRecipesUseCase, DeleteFavouriteUseCase deleteFavouriteUseCase, GetFavouriteByIdUseCase getFavouriteByIdUseCase, AddToFavouriteUseCase addToFavouriteUseCase) {
        this.mView = mView;
        this.mGetPodcastsUseCase = getRecipesUseCase;
        this.mDeleteFavouriteUseCase = deleteFavouriteUseCase;
        this.mGetFavouriteByIdUseCase = getFavouriteByIdUseCase;
        this.mAddToFavouriteUseCase = addToFavouriteUseCase;
        mView.setPresenter(this);
    }


    @Override
    public void doGetTranscript(String podcastsUrl) {
        compositeDisposable.add(mGetPodcastsUseCase.getTranscript(podcastsUrl).subscribeWith(new DisposableSingleObserver<String>() {
            @Override
            public void onSuccess(@NonNull String recipes) {
                List<TranscriptModel> transcriptModels = new ArrayList<>();
                String[] lines = recipes.split("\n");
                for (String line : lines) {
                    String[] transcript = line.split("\t");
                    if (transcript.length == 2) {
                        try {
                            TranscriptModel transcriptModel = new TranscriptModel(transcript[1], Long.parseLong(transcript[0]));
                            transcriptModels.add(transcriptModel);
                        } catch (Exception ex) {

                        }
                    }
                }

                mView.renderTranscript(transcriptModels);
            }

            @Override
            public void onError(@NonNull Throwable e) {
            }
        }));
    }

    @Override
    public void doCheckIsFavourite(String url) {
        compositeDisposable.add(mGetFavouriteByIdUseCase.getFavouriteById(url).subscribeWith(new DisposableSingleObserver<FavouriteModel>() {
            @Override
            public void onSuccess(FavouriteModel favouriteModel) {
                if (favouriteModel == null) {
                    mView.renderFavouriteButton(false);
                } else {
                    mView.renderFavouriteButton(true);
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.renderFavouriteButton(false);
            }
        }));
    }

    @Override
    public void doRemoveFavourite(String url) {
        compositeDisposable.add(mDeleteFavouriteUseCase.deleteFavourite(url).subscribeWith(new DisposableSingleObserver<Boolean>() {
            @Override
            public void onSuccess(Boolean o) {
                mView.renderFavouriteButton(false);
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    @Override
    public void doAddToFavourite(PodcastModel podcastModel) {
        compositeDisposable.add(mAddToFavouriteUseCase.addToFavourite(podcastModel).subscribeWith(new DisposableSingleObserver<Boolean>() {
            @Override
            public void onSuccess(Boolean o) {
                mView.renderFavouriteButton(true);
            }

            @Override
            public void onError(Throwable e) {

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
