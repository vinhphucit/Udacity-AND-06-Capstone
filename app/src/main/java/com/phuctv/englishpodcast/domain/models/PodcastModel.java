package com.phuctv.englishpodcast.domain.models;

import org.parceler.Parcel;

/**
 * Created by phuctran on 11/8/17.
 */

@Parcel
public class PodcastModel {
    String name;
    String url;
    String audio_link;
    String lyric_links;
    String image_link;
    String time;
    String desc;

    public PodcastModel() {
    }

    public PodcastModel(String name, String url, String audio_link, String lyric_links, String image_link, String time, String desc) {
        this.name = name;
        this.url = url;
        this.audio_link = audio_link;
        this.lyric_links = lyric_links;
        this.image_link = image_link;
        this.time = time;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAudio_link() {
        return audio_link;
    }

    public void setAudio_link(String audio_link) {
        this.audio_link = audio_link;
    }

    public String getLyric_links() {
        return lyric_links;
    }

    public void setLyric_links(String lyric_links) {
        this.lyric_links = lyric_links;
    }

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
