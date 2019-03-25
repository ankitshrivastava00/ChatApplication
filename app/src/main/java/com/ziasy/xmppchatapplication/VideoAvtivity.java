package com.ziasy.xmppchatapplication;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

public class VideoAvtivity extends AppCompatActivity {

    private VideoView video_view;
    private ProgressBar progressBar;
    private MediaController mediacontroller;
    private Uri uri;
    private int mCurrentPosition = -1;
    int mCurrentPath;

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_player);
        progressBar = (ProgressBar) findViewById(R.id.progrss);

        video_view = (VideoView) findViewById(R.id.vv);

        progressBar.setVisibility(View.VISIBLE);
        mediacontroller = new MediaController(this);
        mediacontroller.setAnchorView(video_view);
        //  String uriPath = "https://www.demonuts.com/Demonuts/smallvideo.mp4"; //update package name
        String uriPath = getIntent().getStringExtra("video"); //update package name
        uri = Uri.parse(uriPath);
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt("curre");
        }

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(video_view);
        video_view.setMediaController(mediaController);
    }

    @Override
    protected void onStart() {

        play();
        video_view.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {

                //play();
            }
        });
        video_view.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                progressBar.setVisibility(View.GONE);
            }
        });
        super.onStart();
    }

    public void play() {
        // if(count < paths.size())
        //                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     video_view.stopPlayback();

        video_view.setMediaController(mediacontroller);
        video_view.setVideoURI(uri);
        video_view.requestFocus();
        video_view.start();
        if (mCurrentPosition != -1) {
            video_view.seekTo(mCurrentPosition);
        }
        count++;
    }
    //  }

    @Override
    protected void onPause() {
        mCurrentPosition = video_view.getCurrentPosition();
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("path", mCurrentPath);
        outState.putInt("curre", mCurrentPosition);
        super.onSaveInstanceState(outState);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //we use onRestoreInstanceState in order to play the video playback from the stored position
        mCurrentPosition = savedInstanceState.getInt("curre");
    }
}


/*extends AppCompatActivity {

    private VideoView myVideoView;
    private int position = 0;
    private ProgressBar progressBar;
    private MediaController mediaControls;
    private Uri uri;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the main layout of the activity
        setContentView(R.layout.video_player);

        //set the media controller buttons
        if (mediaControls == null) {
            mediaControls = new MediaController(VideoAvtivity.this);
        }

        //initialize the VideoView
        myVideoView = (VideoView) findViewById(R.id.vv);

        // create a progress bar while the video file is loading
        progressBar = (ProgressBar) findViewById(R.id.progrss);

        progressBar.setVisibility(View.VISIBLE);
        try {
            //set the media controller in the VideoView
            myVideoView.setMediaController(mediaControls);

            //set the uri of the video to be played
            String uriPath = getIntent().getStringExtra("video"); //update package name
            uri = Uri.parse(uriPath);
            myVideoView.setVideoURI(uri);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        myVideoView.requestFocus();
        //we also set an setOnPreparedListener in order to know when the video file is ready for playback
        myVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mediaPlayer) {
                // close the progress bar and play the video
                progressBar.setVisibility(View.GONE);
                //if we have a position on savedInstanceState, the video playback should start from here
                myVideoView.seekTo(position);
                if (position == 0) {
                    myVideoView.start();
                } else {
                    //if we come from a resumed activity, video playback will be paused
                    myVideoView.pause();
                }
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        //we use onSaveInstanceState in order to store the video playback position for orientation change
        savedInstanceState.putInt("Position", myVideoView.getCurrentPosition());
        myVideoView.pause();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //we use onRestoreInstanceState in order to play the video playback from the stored position
        position = savedInstanceState.getInt("Position");
        myVideoView.seekTo(position);
    }
}*/




/*extends AppCompatActivity {

    private VideoView video_view;
    private ProgressBar progressBar;
    private MediaController mediacontroller;
    private Uri uri;
    private int mCurrentPosition = -1;
    int mCurrentPath;

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_player);
        progressBar = (ProgressBar) findViewById(R.id.progrss);

        video_view = (VideoView) findViewById(R.id.vv);

        progressBar.setVisibility(View.VISIBLE);
        mediacontroller = new MediaController(this);
        mediacontroller.setAnchorView(video_view);
        //  String uriPath = "https://www.demonuts.com/Demonuts/smallvideo.mp4"; //update package name
        String uriPath = getIntent().getStringExtra("video"); //update package name
        uri = Uri.parse(uriPath);
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt("curre");
        }

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(video_view);
        video_view.setMediaController(mediaController);
    }

    @Override
    protected void onStart() {
        play();
        video_view.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {

                //play();
            }
        });
        video_view.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                progressBar.setVisibility(View.GONE);
            }
        });
        super.onStart();
    }

    public void play() {
        // if(count < paths.size()) {
        video_view.stopPlayback();

        video_view.setMediaController(mediacontroller);
        video_view.setVideoURI(uri);
        video_view.requestFocus();
        video_view.start();
        if (mCurrentPosition != -1) {
            video_view.seekTo(mCurrentPosition);
        }
        count++;
    }
    //  }

    @Override
    protected void onPause() {
        mCurrentPosition = video_view.getCurrentPosition();
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("path", mCurrentPath);
        outState.putInt("curre", mCurrentPosition);
        super.onSaveInstanceState(outState);
    }

}*/