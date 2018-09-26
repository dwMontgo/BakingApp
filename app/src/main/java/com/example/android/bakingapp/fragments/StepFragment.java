package com.example.android.bakingapp.fragments;


import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.StepList;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StepFragment extends Fragment implements View.OnClickListener {

    private static final String STEP = "step";
    private static final String ID = "id";
    private static final String POSITION = "position";
    private static final String STATE = "state";

    @BindView(R.id.text_only)
    TextView textOnly;

    @BindView(R.id.step_text)
    TextView stepText;

    @BindView(R.id.player)
    PlayerView player;

    @BindView(R.id.forward_button)
    FloatingActionButton forwardButton;

    @BindView(R.id.back_button)
    FloatingActionButton backButton;

    private boolean isReady = true;
    private long exoPosition = 0;
    private int id;
    private StepList stepList;
    private SimpleExoPlayer simpleExoPlayer;
    private ClickListener listener;

    public StepFragment() {

    }

    public static StepFragment newInstance(StepList s, int i) {
        StepFragment stepFragment = new StepFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(STEP, s);
        bundle.putInt(ID, i);
        stepFragment.setArguments(bundle);
        return stepFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**/
        if (getArguments() != null) {
            stepList = getArguments().getParcelable(STEP);
            id = getArguments().getInt(ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_ind_step, container, false);
        ButterKnife.bind(this, v);

        int orientation = getResources().getConfiguration().orientation;


        if (stepText != null) {
            stepText.setText(stepList.getDescription());
        }
        if (forwardButton != null) {
            forwardButton.setOnClickListener(this);
            backButton.setOnClickListener(this);
            showHideNextPreButtons();
        }


        if (!stepList.getVideoURL().isEmpty()) {
            textOnly.setVisibility(View.GONE);
        }

        if (savedInstanceState != null) {
            isReady = savedInstanceState.getBoolean(STATE);
            exoPosition = savedInstanceState.getLong(POSITION);
        }
        playVideo();

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (simpleExoPlayer != null) {
            outState.putLong(POSITION, simpleExoPlayer.getCurrentPosition());
            outState.putBoolean(STATE, simpleExoPlayer.getPlayWhenReady());
        }
    }


    private void showHideNextPreButtons() {
        if (stepList.getId() <= 0) {
            backButton.setVisibility(View.INVISIBLE);
            forwardButton.setVisibility(View.VISIBLE);
        } else if (stepList.getId() >= id) {
            backButton.setVisibility(View.VISIBLE);
            forwardButton.setVisibility(View.INVISIBLE);
        } else {
            backButton.setVisibility(View.VISIBLE);
            forwardButton.setVisibility(View.VISIBLE);
        }
    }

    private void playVideo() {
        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);

        DataSource.Factory dataSource = new DefaultDataSourceFactory(getActivity(),
                Util.getUserAgent(getActivity(), getString(R.string.app_name)), null);

        MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSource)
                .createMediaSource(Uri.parse(stepList.getVideoURL()));

        simpleExoPlayer.prepare(mediaSource);
        simpleExoPlayer.setPlayWhenReady(isReady);
        simpleExoPlayer.seekTo(exoPosition);

        player.setPlayer(simpleExoPlayer);
    }


    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        listener = (ClickListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onClick(View view) {
        releasePlayer();

        if (view.getId() == R.id.forward_button) {
            listener.selectedStep(stepList.getId() + 1);
        } else if (view.getId() == R.id.back_button) {
            listener.selectedStep(stepList.getId() - 1);
        }

        showHideNextPreButtons();
    }

    public interface ClickListener {
        void selectedStep(int position);
    }
}
