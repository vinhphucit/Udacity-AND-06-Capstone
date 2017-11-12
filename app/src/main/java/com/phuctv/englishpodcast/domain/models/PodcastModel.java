package com.phuctv.englishpodcast.domain.models;

import android.os.Parcelable;

import org.parceler.Parcel;

/**
 * Created by phuctran on 11/8/17.
 */

public class PodcastModel implements Parcelable {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.url);
        dest.writeString(this.audio_link);
        dest.writeString(this.lyric_links);
        dest.writeString(this.image_link);
        dest.writeString(this.time);
        dest.writeString(this.desc);
    }

    protected PodcastModel(android.os.Parcel in) {
        this.name = in.readString();
        this.url = in.readString();
        this.audio_link = in.readString();
        this.lyric_links = in.readString();
        this.image_link = in.readString();
        this.time = in.readString();
        this.desc = in.readString();
    }

    public static final Parcelable.Creator<PodcastModel> CREATOR = new Parcelable.Creator<PodcastModel>() {
        @Override
        public PodcastModel createFromParcel(android.os.Parcel source) {
            return new PodcastModel(source);
        }

        @Override
        public PodcastModel[] newArray(int size) {
            return new PodcastModel[size];
        }
    };
}
