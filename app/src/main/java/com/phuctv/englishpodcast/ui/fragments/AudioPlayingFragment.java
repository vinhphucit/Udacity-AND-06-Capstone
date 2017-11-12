package com.phuctv.englishpodcast.ui.fragments;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.lzyzsd.circleprogress.CircleProgress;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.phuctv.englishpodcast.BuildConfig;
import com.phuctv.englishpodcast.R;
import com.phuctv.englishpodcast.domain.models.PodcastModel;
import com.phuctv.englishpodcast.domain.models.TranscriptModel;
import com.phuctv.englishpodcast.mvp.presenters.AudioPlayingPresenter;
import com.phuctv.englishpodcast.mvp.views.AudioPlayingContract;
import com.phuctv.englishpodcast.ui.adapters.TranscriptAdapter;
import com.phuctv.englishpodcast.ui.widgets.IngredientWidgetProvider;
import com.phuctv.englishpodcast.utils.Injection;
import com.phuctv.englishpodcast.utils.MiscUtils;
import com.phuctv.englishpodcast.utils.Toaster;

import org.parceler.Parcels;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.R.drawable.btn_star_big_off;
import static android.R.drawable.btn_star_big_on;

/**
 * Created by phuctran on 11/2/17.
 */

public class AudioPlayingFragment extends BaseMasterFragment implements AudioPlayingContract.View, LoaderManager.LoaderCallbacks<String>, TranscriptAdapter.ListItemClickListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, SeekBar.OnSeekBarChangeListener {
    private static final String TAG = AudioPlayingFragment.class.getSimpleName();

    private static final String ARGS_PODCAST = "ARGS_PODCAST";
    private static final String ARGS_TRANSITION_NAME = "ARGS_TRANSITION_NAME";
    private static final int PODCAST_AUDIO_LOADER_ID = 1;
    private static final int PERMISSION_REQUEST_CODE = 101;

    @BindView(R.id.favourite_fab)
    FloatingActionButton btnFavourite;
    @BindView(R.id.recycler_view)
    RecyclerView mRvRecipes;
    @BindView(R.id.ivBlurBackground)
    ImageView ivBlurBackground;
    @BindView(R.id.cpDownload)
    CircleProgress cpDownload;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tvTimeCurrentPlaying)
    TextView tvTimeCurrentPlaying;
    @BindView(R.id.ivPlayPause)
    ImageView ivPlayPause;
    @BindView(R.id.sbMedia)
    SeekBar sbMedia;

    private AudioPlayingContract.Presenter mPresenter;
    private PodcastModel mPodcastModel;
    private TranscriptAdapter mMovieAdapter;
    private List<TranscriptModel> mRecipes;
    private String mTransitionName;
    private boolean isFavourite;
    private MediaPlayer mPodcastMediaPlayer;
    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (mPodcastMediaPlayer != null && mPodcastMediaPlayer.isPlaying()) {

                tvTimeCurrentPlaying.setText(MiscUtils.convertMillisecondToMediaTimFormat(mPodcastMediaPlayer.getCurrentPosition()));
                int currentPlaying = mPodcastMediaPlayer.getCurrentPosition();
                mMovieAdapter.notifyCurrentPlayingChanged(currentPlaying);
                mHandler.postDelayed(mRunnable, 300);
            }
        }
    };

    public AudioPlayingFragment() {
        this.mPresenter = new AudioPlayingPresenter(this, Injection.provideGetTranscriptUseCase(), Injection.provideDeleteFavouriteUseCase(), Injection.provideGetFavouriteByIdUseCase(), Injection.provideAddToFavouriteUseCase());
    }

    public static BaseFragment newInstance(PodcastModel podcastId, String transitionName) {
        BaseFragment fragment = new AudioPlayingFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGS_PODCAST, Parcels.wrap(podcastId));
        bundle.putString(ARGS_TRANSITION_NAME, transitionName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(ARGS_PODCAST)) {
            this.mPodcastModel = Parcels.unwrap(getArguments().getParcelable(ARGS_PODCAST));
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, mPodcastModel.getUrl());
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, mPodcastModel.getName());
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Podcast");
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        }
        if (getArguments() != null && getArguments().containsKey(ARGS_TRANSITION_NAME))
            this.mTransitionName = getArguments().getString(ARGS_TRANSITION_NAME);
    }

    @Override
    protected String getSubclassName() {
        return TAG;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_audio_playing;
    }

    @Override
    protected void updateFollowingViewBinding(Bundle savedInstanceState) {
        mPresenter.doGetTranscript(mPodcastModel.getLyric_links());
        sbMedia.setEnabled(false);
        cpDownload.setMax(100);
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {
                getLoaderManager().initLoader(PODCAST_AUDIO_LOADER_ID, null, this);
            } else {
                requestPermission();
            }
        } else {
            getLoaderManager().initLoader(PODCAST_AUDIO_LOADER_ID, null, this);
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ivBlurBackground.setTransitionName(mTransitionName);
        }

        Glide.with(getContext())
                .load(mPodcastModel.getImage_link())
                .placeholder(R.color.photo_placeholder)
                .error(R.color.photo_placeholder)
                .into(ivBlurBackground);
        mPresenter.doCheckIsFavourite(mPodcastModel.getUrl());
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(getContext(), R.string.permission_alert, Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLoaderManager().initLoader(PODCAST_AUDIO_LOADER_ID, null, this);
                } else {

                }
                break;
        }
    }

    @Override
    public void setPresenter(AudioPlayingContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void renderTranscript(List<TranscriptModel> podcasts) {
        setupRecyclerView();
        this.mRecipes = podcasts;
        mMovieAdapter = new TranscriptAdapter(getContext(), mRecipes,
                this);
        mRvRecipes.setAdapter(mMovieAdapter);
    }

    @Override
    public void renderFavouriteButton(boolean isFavourite) {
        this.isFavourite = isFavourite;
        if (isFavourite) {
            btnFavourite.setImageResource(btn_star_big_on);
        } else {
            btnFavourite.setImageResource(btn_star_big_off);
        }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getContext());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(getActivity(), IngredientWidgetProvider.class));
        IngredientWidgetProvider.updateAppWidget(getContext(), appWidgetManager, appWidgetIds);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<String>(getContext()) {
            @Nullable
            @Override
            public String loadInBackground() {
                File myFile = null;
                try {
                    URL url = new URL(mPodcastModel.getAudio_link());

                    InputStream input = null;
                    String root = Environment.getExternalStorageDirectory().toString();
                    File myDir = new File(root + "/" + BuildConfig.EXTERNAL_DOWNLOAD_FOLDER);
                    if (!myDir.exists()) {
                        myDir.mkdirs();
                    }

                    String outputName = MiscUtils.convertPathToFileName(mPodcastModel.getAudio_link());
                    myFile = new File(myDir, outputName);
                    if (myFile.exists()) return myFile.getAbsolutePath();
                    FileOutputStream output = new FileOutputStream(myFile);
                    try {

                        input = url.openConnection().getInputStream();
                        long fileLength = url.openConnection().getContentLength();

                        if (fileLength > 0) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tvTime.setText(MiscUtils.convertMillisecondToMediaTimFormat(fileLength));
                                }
                            });
                        }

                        int read;
                        long total = 0;
                        byte[] data = new byte[1024];
                        while ((read = input.read(data)) != -1) {
                            output.write(data, 0, read);
                            total += read;
                            if (fileLength > 0) // only if total length is known
                                publishProgress((int) (total * 100 / fileLength));
                        }
                        return outputName;

                    } finally {
                        if (output != null)
                            output.close();
                        if (input != null)
                            input.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if (myFile != null)
                    return myFile.getAbsolutePath();
                return null;
            }

            @Override
            protected void onStartLoading() {
                forceLoad();

            }


        };
    }

    @Override
    public void onPause() {
        mHandler.removeCallbacks(mRunnable);
        pauseMediaPlayer();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        if (mPodcastMediaPlayer != null) {
            mPodcastMediaPlayer.stop();
            mPodcastMediaPlayer.release();
        }
        super.onDestroy();
    }

    private void pauseMediaPlayer() {
        if (mPodcastMediaPlayer != null && mPodcastMediaPlayer.isPlaying()) {
            ivPlayPause.setImageResource(R.drawable.ic_play_arrow_black_24dp);
            mPodcastMediaPlayer.pause();
        }
    }

    private void startMediaPlayer() {
        if (mPodcastMediaPlayer != null && !mPodcastMediaPlayer.isPlaying()) {
            ivPlayPause.setImageResource(R.drawable.ic_pause_black_24dp);
            mPodcastMediaPlayer.start();
            mHandler.post(mRunnable);
        }
    }

    private void publishProgress(int i) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (cpDownload != null)
                    cpDownload.setProgress(i);
            }
        });
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        showAdv(() -> {
            if (data != null) {
                cpDownload.setVisibility(View.GONE);
                ivPlayPause.setVisibility(View.VISIBLE);
                onFileDownloadSuccessfully(data);
            } else {
                Toaster.showToast(getContext(), getResources().getText(R.string.download_fail).toString());
            }
        });
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRvRecipes.setLayoutManager(layoutManager);
        mRvRecipes.setHasFixedSize(true);
    }

    @Override
    public void onListItemClick(TranscriptModel movieModel) {

    }

    @OnClick(R.id.favourite_fab)
    void onFavouriteClick() {
        if (!isFavourite) {
            mPresenter.doAddToFavourite(mPodcastModel);
        } else {
            mPresenter.doRemoveFavourite(mPodcastModel.getUrl());
        }

    }

    private void onFileDownloadSuccessfully(String uri) {
        mPodcastMediaPlayer = MediaPlayer.create(getContext(), Uri.parse(uri));
        if (mPodcastMediaPlayer != null) {
            tvTime.setText(MiscUtils.convertMillisecondToMediaTimFormat(mPodcastMediaPlayer.getDuration()));
        }
        mPodcastMediaPlayer.setOnCompletionListener(this);
        mPodcastMediaPlayer.setOnErrorListener(this);
        sbMedia.setMax(mPodcastMediaPlayer.getDuration());
        sbMedia.setEnabled(true);
        sbMedia.setOnSeekBarChangeListener(this);
        startMediaPlayer();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Log.d(TAG, "completion");
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        Log.d(TAG, "onError");
        mHandler.removeCallbacks(mRunnable);
        Toaster.showToast(getContext(), getResources().getText(R.string.download_fail).toString());
        return false;
    }

    @OnClick(R.id.ivPlayPause)
    void onClickPlayPause() {
        if (mPodcastMediaPlayer != null) {
            if (mPodcastMediaPlayer.isPlaying()) {
                pauseMediaPlayer();
            } else {
                startMediaPlayer();
            }
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (mPodcastMediaPlayer != null) {
            mPodcastMediaPlayer.seekTo(seekBar.getProgress());
            tvTimeCurrentPlaying.setText(MiscUtils.convertMillisecondToMediaTimFormat(mPodcastMediaPlayer.getCurrentPosition()));
            int currentPlaying = mPodcastMediaPlayer.getCurrentPosition();
            mMovieAdapter.notifyCurrentPlayingChanged(currentPlaying);
        }
    }
}
