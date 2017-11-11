package com.phuctv.englishpodcast.ui.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.phuctv.englishpodcast.R;

import butterknife.BindView;

public class SplashActivity extends BaseActivity {
    private static final int SPLASH_SCREEN_SHOW_TIME_MS = 2000;

    @BindView(R.id.ivBigBen)
    ImageView ivBigBen;

    @Override
    protected void updateFollowingViewBinding(Bundle savedInstanceState) {
        goToFirstActivity();

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_splash;
    }

    private void goToFirstActivity() {
        (new Handler()).postDelayed(() -> {
            Bundle bundle = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                bundle = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this, ivBigBen, ivBigBen.getTransitionName()).toBundle();
            }

            Intent intent = new Intent(SplashActivity.this, MasterActivity.class);
            if (bundle != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent, bundle);
                }
            } else {
                startActivity(intent);
            }
            (new Handler()).postDelayed(() -> finish(), 500);
        }, SPLASH_SCREEN_SHOW_TIME_MS);
    }
}
