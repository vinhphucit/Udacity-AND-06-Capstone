package com.phuctv.englishpodcast.domain.models;

import org.parceler.Parcel;

/**
 * Created by phuctran on 11/9/17.
 */

@Parcel
public class FavouriteModel extends PodcastModel{
    String insertTime;

    public FavouriteModel() {
    }

    public FavouriteModel(String name, String url, String audio_link, String lyric_links, String image_link, String time, String desc, String insertTime) {
        super(name, url, audio_link, lyric_links, image_link, time, desc);
        this.insertTime = insertTime;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }
}
