package com.phuctv.englishpodcast.domain.models;

import org.parceler.Parcel;

/**
 * Created by phuctran on 11/7/17.
 */

@Parcel
public class ChannelModel {
    String id;
    String title;
    String description;
    String logo;
    String podcastsUrl;

    public ChannelModel() {
    }

    public ChannelModel(String id, String title, String description, String logo, String podcastsUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.logo = logo;
        this.podcastsUrl = podcastsUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPodcastsUrl() {
        return podcastsUrl;
    }

    public void setPodcastsUrl(String podcastsUrl) {
        this.podcastsUrl = podcastsUrl;
    }
}
