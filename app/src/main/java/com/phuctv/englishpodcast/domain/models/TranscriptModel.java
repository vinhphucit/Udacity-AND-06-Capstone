package com.phuctv.englishpodcast.domain.models;

/**
 * Created by phuctran on 11/8/17.
 */

public class TranscriptModel {
    private String text;
    private long time;

    public TranscriptModel(String text, long time) {
        this.text = text;
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
