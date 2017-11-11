package com.phuctv.englishpodcast.utils;


import com.phuctv.englishpodcast.data.BakingRepository;
import com.phuctv.englishpodcast.data.local.LocalDataSource;
import com.phuctv.englishpodcast.data.remote.RemoteDataSource;
import com.phuctv.englishpodcast.domain.usecases.AddToFavouriteUseCase;
import com.phuctv.englishpodcast.domain.usecases.DeleteFavouriteUseCase;
import com.phuctv.englishpodcast.domain.usecases.GetChannelsUseCase;
import com.phuctv.englishpodcast.domain.usecases.GetFavouriteByIdUseCase;
import com.phuctv.englishpodcast.domain.usecases.GetFavouritesUseCase;
import com.phuctv.englishpodcast.domain.usecases.GetPodcastsUseCase;
import com.phuctv.englishpodcast.domain.usecases.GetTranscriptUseCase;

/**
 * Created by phuctran on 10/3/17.
 */

public class Injection {
    public static BakingRepository provideRxRepository() {
        return new BakingRepository(LocalDataSource.getInstance(),
                RemoteDataSource.getInstance());
    }

    public static GetChannelsUseCase provideGetChannelsUseCase() {
        return new GetChannelsUseCase();
    }
    public static GetTranscriptUseCase provideGetTranscriptUseCase() {
        return new GetTranscriptUseCase(provideRxRepository());
    }
    public static GetPodcastsUseCase provideGetPodcastsUseCase() {
        return new GetPodcastsUseCase(provideRxRepository());
    }

    public static AddToFavouriteUseCase provideAddToFavouriteUseCase() {
        return new AddToFavouriteUseCase(provideRxRepository());
    }

    public static DeleteFavouriteUseCase provideDeleteFavouriteUseCase() {
        return new DeleteFavouriteUseCase(provideRxRepository());
    }

    public static GetFavouriteByIdUseCase provideGetFavouriteByIdUseCase() {
        return new GetFavouriteByIdUseCase(provideRxRepository());
    }

    public static GetFavouritesUseCase provideGetFavouritesUseCase() {
        return new GetFavouritesUseCase(provideRxRepository());
    }
}
