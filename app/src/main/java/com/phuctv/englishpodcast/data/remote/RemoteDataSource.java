package com.phuctv.englishpodcast.data.remote;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.phuctv.englishpodcast.BuildConfig;
import com.phuctv.englishpodcast.data.BakingDataSource;
import com.phuctv.englishpodcast.domain.models.FavouriteModel;
import com.phuctv.englishpodcast.domain.models.PodcastModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by phuctran on 9/11/17.
 */

public class RemoteDataSource implements BakingDataSource {

    private static RemoteDataSource INSTANCE;
    private final RetrofitApi mApi;

    public RemoteDataSource() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient client = new OkHttpClient.Builder().
                connectTimeout(60, TimeUnit.SECONDS).
                readTimeout(60, TimeUnit.SECONDS).
                addInterceptor(logging).build();

        Gson customGsonInstance = new GsonBuilder()
                .create();

        Retrofit retrofitApiAdapter = new Retrofit.Builder()
                .baseUrl(BuildConfig.ROOT_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(customGsonInstance))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

        mApi = retrofitApiAdapter.create(RetrofitApi.class);
    }

    public static RemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteDataSource();
        }
        return INSTANCE;
    }


    @Override
    public Single<List<PodcastModel>> getPodcasts(String url) {
        return mApi.getPodcasts(url).map(podcastResponseWrapper -> podcastResponseWrapper.getData());
    }

    @Override
    public Single<String> getTranscript(String url) {
        return mApi.getTranscript(url);
    }

    @Override
    public Single<List<FavouriteModel>> getFavourites() {
        return null;
    }

    @Override
    public Single<FavouriteModel> getFavouriteById(String url) {
        return null;
    }

    @Override
    public Single saveFavourite(PodcastModel favouriteModel) {
        return null;
    }

    @Override
    public Single deleteFavouriteById(String url) {
        return null;
    }
}
