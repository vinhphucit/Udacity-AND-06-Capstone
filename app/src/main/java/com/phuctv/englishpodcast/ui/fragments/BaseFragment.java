package com.phuctv.englishpodcast.ui.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.phuctv.englishpodcast.R;
import com.phuctv.englishpodcast.ui.activities.MasterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

/**
 * Created by phuctran on 9/18/17.
 */

public abstract class BaseFragment extends Fragment {
    private static final String TAG = BaseFragment.class.getSimpleName();

    private Unbinder unbinder;
    protected AlertDialog.Builder errorDialog;
    protected FirebaseAnalytics mFirebaseAnalytics;
    @Nullable
    @BindView(R.id.rl_progress)
    RelativeLayout rl_progress;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Timber.d((new StringBuilder()).append("onCreateView:").append(getClass().getName()).toString());

        final View fragmentView = inflater.inflate(getLayoutResource(), container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        updateFollowingViewBinding(savedInstanceState);
        return fragmentView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        Timber.d((new StringBuilder()).append("onDestroyView:").append(getClass().getName()).toString());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
        mFirebaseAnalytics.setCurrentScreen(getActivity(), getSubclassName(), getSubclassName());
        Timber.d((new StringBuilder()).append("onCreate:").append(getClass().getName()).toString());
        errorDialog = new AlertDialog.Builder(getContext())
                .setTitle(R.string.error)
                .setMessage(R.string.error_message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);
    }


    public void showLoading() {
        if (rl_progress != null)
            rl_progress.setVisibility(View.VISIBLE);
    }


    public void hideLoading() {
        if (rl_progress != null)
            rl_progress.setVisibility(View.GONE);
    }


    public void showError(String message) {
        if (TextUtils.isEmpty(message))
            message = getString(R.string.application_error);
        errorDialog.setMessage(message);
        errorDialog.show();
    }


    protected void showAdv(Runnable r) {
        ((BaseFragmentResponder) getActivity()).showAdv(r);
    }

    protected abstract String getSubclassName();


    protected abstract int getLayoutResource();

    protected abstract void updateFollowingViewBinding(Bundle savedInstanceState);


    public interface BaseFragmentResponder {
        void showFragment(BaseFragment fragment, String fragmentTag);


        void setActionBarTitle(String actionBarTitle);


        void showAdv(Runnable r);

    }
}
