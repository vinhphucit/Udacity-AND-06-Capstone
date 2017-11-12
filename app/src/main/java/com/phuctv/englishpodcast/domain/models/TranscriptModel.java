package com.phuctv.englishpodcast.domain.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by phuctran on 11/8/17.
 */

public class TranscriptModel implements Parcelable {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.text);
        dest.writeLong(this.time);
    }

    protected TranscriptModel(Parcel in) {
        this.text = in.readString();
        this.time = in.readLong();
    }

    public static final Parcelable.Creator<TranscriptModel> CREATOR = new Parcelable.Creator<TranscriptModel>() {
        @Override
        public TranscriptModel createFromParcel(Parcel source) {
            return new TranscriptModel(source);
        }

        @Override
        public TranscriptModel[] newArray(int size) {
            return new TranscriptModel[size];
        }
    };
}
