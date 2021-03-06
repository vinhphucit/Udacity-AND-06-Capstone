package com.phuctv.englishpodcast.ui.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.transition.TransitionInflater;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.phuctv.englishpodcast.R;
import com.phuctv.englishpodcast.ui.fragments.BaseFragment;
import com.phuctv.englishpodcast.utils.Toaster;

import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by phuctran on 9/18/17.
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseFragment.BaseFragmentResponder {
    private static final String TAG = BaseActivity.class.getSimpleName();
    protected String mTopFragment = null;
    protected InterstitialAd mInterstitialAd;
    AdRequest ar = new AdRequest
            .Builder()
            .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
            .build();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        ButterKnife.bind(this);
        Timber.plant(new Timber.DebugTree());
        updateFollowingViewBinding(savedInstanceState);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_right);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd.loadAd(ar);
    }

    protected abstract void updateFollowingViewBinding(Bundle savedInstanceState);

    protected abstract int getLayoutResource();

    public void showAdv(Runnable r) {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    mInterstitialAd.loadAd(ar);
                    if (r != null) {
                        r.run();
                    }
                }
            });
            mInterstitialAd.show();
        } else {
            if (r != null) {
                r.run();
            }
        }
    }

    public void showFragmentWithShareElementTransition(BaseFragment podcastsFragment, BaseFragment newFragment, String fragmentTag, View sharedView, String sharedElementName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            podcastsFragment.setSharedElementReturnTransition(TransitionInflater.from(this).inflateTransition(R.transition.default_transition));
            podcastsFragment.setExitTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.no_transition));

            newFragment.setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.default_transition));
            newFragment.setEnterTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.no_transition));
        }
        Timber.w("showFragment - DEFAULT implementation called in BaseActivity");
        FragmentTransaction fragmenttransaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        fragmenttransaction.replace(R.id.fragmentContainer, newFragment, fragmentTag);
        mTopFragment = fragmentTag;
        fragmenttransaction.addToBackStack(fragmentTag);
        fragmenttransaction.addSharedElement(sharedView, sharedElementName);
        fragmenttransaction.commit();

    }

    public void showFragment(BaseFragment fragment, String fragmentTag) {

        Timber.w("showFragment - DEFAULT implementation called in BaseActivity");
        FragmentTransaction fragmenttransaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        fragmenttransaction.replace(R.id.fragmentContainer, fragment, fragmentTag);
        mTopFragment = fragmentTag;
        fragmenttransaction.addToBackStack(fragmentTag);
        fragmenttransaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int iBackStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        Timber.d((new StringBuilder()).append("onBackPressed - backStackEntryCount is: ").append(iBackStackEntryCount).toString());
        if (iBackStackEntryCount <= 0) finish();

    }


    public void setActionBarTitle(String actionBarTitle) {
        if (actionBarTitle == null) {
            Timber.w("setActionBarTitle - title is null, so defaulting to full_app_name.");
            actionBarTitle = getString(R.string.app_name);
        }
        getSupportActionBar().setTitle(actionBarTitle);
    }

}
