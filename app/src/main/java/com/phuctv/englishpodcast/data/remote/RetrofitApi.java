package com.phuctv.englishpodcast.data.remote;


import com.phuctv.englishpodcast.data.remote.wrappers.PodcastResponseWrapper;
import com.phuctv.englishpodcast.domain.models.PodcastModel;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by phuctran on 9/20/17.
 */

public interface RetrofitApi {

    @GET
    Single<PodcastResponseWrapper> getPodcasts(@Url String url);

    @GET
    Single<String> getTranscript(@Url String url);
}
