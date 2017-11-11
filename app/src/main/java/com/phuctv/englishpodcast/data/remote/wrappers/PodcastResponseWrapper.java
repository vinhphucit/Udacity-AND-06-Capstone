package com.phuctv.englishpodcast.data.remote.wrappers;

import com.phuctv.englishpodcast.domain.models.PodcastModel;

import java.util.List;

/**
 * Created by phuctran on 11/8/17.
 */

public class PodcastResponseWrapper {
    private List<PodcastModel> data;

    public PodcastResponseWrapper(List<PodcastModel> data) {
        this.data = data;
    }

    public List<PodcastModel> getData() {
        return data;
    }

    public void setData(List<PodcastModel> data) {
        this.data = data;
    }
}
