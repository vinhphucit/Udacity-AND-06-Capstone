package com.phuctv.englishpodcast;

import android.app.Application;
import android.content.Context;

/**
 * Created by phuctran on 11/2/17.
 */

public class PodcastApplication extends Application {
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }

    public static Context getContext() {
        return sContext;
    }

}
