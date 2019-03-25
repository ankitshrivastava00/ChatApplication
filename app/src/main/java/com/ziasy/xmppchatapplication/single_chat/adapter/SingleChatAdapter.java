package com.ziasy.xmppchatapplication.single_chat.adapter;

/**
 * Created by Khushvinders on 15-Nov-16.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.maps.MapsInitializer;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import com.ziasy.xmppchatapplication.R;
import com.ziasy.xmppchatapplication.VideoAvtivity;
import com.ziasy.xmppchatapplication.ViewFullImageActivity;
import com.ziasy.xmppchatapplication.common.Confiq;
import com.ziasy.xmppchatapplication.common.OnBottomRechedListener;
import com.ziasy.xmppchatapplication.common.PdfViewr;
import com.ziasy.xmppchatapplication.common.SessionManagement;
import com.ziasy.xmppchatapplication.model.JsonModelForChat;
import com.ziasy.xmppchatapplication.single_chat.activity.SingleChatActivity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import github.ankushsachdeva.emojicon.EmojiconTextView;

public class SingleChatAdapter extends RecyclerView.Adapter<SingleChatAdapter.ViewHolder> {

    public List<JsonModelForChat> selected_usersList;
    private static MediaPlayer mediaPlayer;

    public List<JsonModelForChat> chatMessageList;
    private SessionManagement sd;
    private Context context;
    ViewHolder vh;
    Timer timer;
    RecyclerView recyclerView;
    Timer timer2;
    int position;
    SingleChatActivity activity;

    public SingleChatAdapter(Context context, RecyclerView msgListView, int resource, List<JsonModelForChat> list, List<JsonModelForChat> selected_usersList) {
        this.recyclerView = msgListView;
        this.chatMessageList = list;
       this. activity=(SingleChatActivity)context;
        this.selected_usersList = selected_usersList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_chat_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        vh = new ViewHolder(v); // pass the view to View Holder
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        this.position = position;
        final JsonModelForChat message = (JsonModelForChat) chatMessageList.get(position);
        sd = new SessionManagement(context);
/*

        // if message is mine then align to right

            if (message.getType().equalsIgnoreCase("msg")) {

                viewHolder.outgoingTextImage.setText(message.getMessage());

        }*/
      /*  if (timer != null) {
            timer.cancel();
            timer = null;
            Log.e("timer", "stop");
        }
*/

        if (message.getSendName().equalsIgnoreCase(sd.getKeyId())) {

            if (message.getType().equalsIgnoreCase("audio")) {
                viewHolder.incoming_audio_relative.setVisibility(View.GONE);
                viewHolder.outgoing_audio_relative.setVisibility(View.VISIBLE);

                viewHolder.outgoing_audio_time.setText(message.getTime());

                if (message.isSelect()) {
                    viewHolder.outgoing_image_view_audio.setVisibility(View.GONE);
                    viewHolder.outgoing_image_view_audio_stop.setVisibility(View.VISIBLE);

                    if (mediaPlayer != null) {
                        int duration = mediaPlayer.getDuration();
                        Log.e("duration", "" + mediaPlayer.getDuration());
                        viewHolder.outgoing_seekbar.setProgress(mediaPlayer.getCurrentPosition());

                    }
                } else if (!message.isSelect()) {
                    viewHolder.outgoing_seekbar.setProgress(0);
                    viewHolder.outgoing_image_view_audio.setVisibility(View.VISIBLE);
                    viewHolder.outgoing_image_view_audio_stop.setVisibility(View.GONE);

                }


                viewHolder.outgoing_image_view_audio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        message.setSelect(true);
                        //  notifyDataSetChanged();

                        if (message.isSelect()) {
                          playSong(message.getData(), viewHolder.outgoing_seekbar
                            );
                            viewHolder.outgoing_image_view_audio.setVisibility(View.GONE);
                            viewHolder.outgoing_image_view_audio_stop.setVisibility(View.VISIBLE);
                        }

                        // Toast.makeText(context, "PLAY" + position, Toast.LENGTH_SHORT).show();

                    }
                });
                viewHolder.outgoing_image_view_audio_stop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        message.setSelect(false);
                        //  notifyDataSetChanged();

                        try {
                            mediaPlayer.stop();
                            mediaPlayer.release();
                            mediaPlayer = null;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //   Toast.makeText(context, "STOPs" + position, Toast.LENGTH_SHORT).show();
                        if (!message.isSelect()) {

                            viewHolder.outgoing_image_view_audio.setVisibility(View.VISIBLE);
                            viewHolder.outgoing_image_view_audio_stop.setVisibility(View.GONE);
                        }
                    }
                });
            }


        } else {


            if (message.getType().equalsIgnoreCase("audio")) {
                viewHolder.incoming_audio_relative.setVisibility(View.VISIBLE);
                viewHolder.outgoing_audio_relative.setVisibility(View.GONE);


                viewHolder.incoming_audio_time.setText(message.getTime());

            }

        }
        // If not mine then align to left


    }

    public void playSong(String url, SeekBar seekBar) {
        try {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer = null;

            }


            if (mediaPlayer == null) {
                mediaPlayer = new MediaPlayer();
            }
            //fab.setImageDrawable(ContextCompat.getDrawable(context, android.R.drawable.ic_media_pause));
            //  AssetFileDescriptor descriptor = getAssets().openFd("suits.mp3");
            //    mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            ///   descriptor.close();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            mediaPlayer.getAudioSessionId();
            mediaPlayer.setVolume(0.5f, 0.5f);

            mediaPlayer.setLooping(false);
            int duration = mediaPlayer.getDuration();
            seekBar.setMax(duration);

            mediaPlayer.start();

            Log.e("Playsong", "Running");
            Log.e("duration", "" + mediaPlayer.getDuration());
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (mediaPlayer != null && mediaPlayer.getCurrentPosition() != 0) {
                        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForItemId(position);
                        View view = viewHolder.itemView;
                        SeekBar seekBar = (SeekBar) view.findViewById(R.id.outgoing_seekbar);
                        ///  if(viewHolder.outgoing_image_view_audio_stop.getVisibility()==View.VISIBLE)
                        Log.e("timer running", "shubh");
                        seekBar.setProgress(mediaPlayer.getCurrentPosition());
                        if (duration <= mediaPlayer.getCurrentPosition()) {
                            Log.e("stop media", "stoped");
                            timer.cancel();
                            timer = null;
                        }
                    }
                }
            }, 1, 1000);
        } catch (Exception e) {
            e.printStackTrace();

        }


    }

    @Override
    public int getItemCount() {
        return chatMessageList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView incoming_contact_time, incoming_contact_number, outgoing_contact_name, outgoing_contact_number, outgoing_contact_time, incoming_video_time, outgoing_video_time, incoming_emoji_time, outgoing_emoji_time, dateTextView, incoming_text_time, outgoing_text_time, outgoing_pdf_time, incoming_pdf_time, outgoing_image_time, incoming_image_time;
        private ImageView incoming_doc_icon, outgoing_doc_icon, incoming_emoji_image, outgoing_emoji_image;
        private ImageView incoming_play_btn, outgoing_play_btn, incoming_video_IV, image_view_audiio, incoming_image_view_audiio;
        private RoundedImageView outgoing_image_IV, outgoing_video_IV, incoming_image_IV;
        private LinearLayout incoming_linear_contact, incoming_video_cab, parent_layout, incoming_image, out_pdf, incoming_pdf, outgoing_linear_contact;
        ////////AUDIO/////////////
        private RelativeLayout container_list_item;
        private RelativeLayout outgoing_audio_relative, incoming_audio_relative;
        private TextView outgoing_audio_time, incoming_audio_time;
        private ImageView tickMarkFirstOutAudio, incoming_image_view_audio, outgoing_image_view_audio, outgoing_image_view_audio_stop;
        private SeekBar outgoing_seekbar, incoming_seekbar;

        ///////
        private View outgoing_view, incoming_view;
        private RelativeLayout outgoing_location_relative, incoming_location_relative;
        private TextView outgoing_location_time, incoming_location_time;
        private ImageView tickMarkLocation;
        //   private MapView outgoing_location_map, incoming_location_map;
        // private GoogleMap map, map_incoming;
        private MapView outgoing_location_map, incoming_location_map;
        private MapboxMap map, map_incoming;

        //Loader
        //    private ProgressBar progress_bar, outgoing_video_progress_bar, incoming_progress_bar, incoming_video_progress_bar;
        private RelativeLayout out_image, relativeDate, layout, out_video_cab, outgoing_relative_emoji, incoming_relative_emoji;
        private EmojiconTextView incomming_text, txtoutgoing_pdf, txtincomming_pdf, outgoingTextImage;
        private ImageView tickMarkContactOut, tickMarkFirstOut, tickMarkFirstOutImage, tickMarkFirstOutVideo, tickMarkFirstOutPDF, tickMarkFirstOutEmoji;

        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            // get the reference of item view's
            outgoingTextImage = (EmojiconTextView) view.findViewById(R.id.outgoing_text);


            outgoing_audio_relative = (RelativeLayout) itemView.findViewById(R.id.outgoing_audio_relative);
            incoming_audio_relative = (RelativeLayout) itemView.findViewById(R.id.incoming_audio_relative);
            outgoing_audio_time = (TextView) itemView.findViewById(R.id.outgoing_audio_time);
            incoming_audio_time = (TextView) itemView.findViewById(R.id.incoming_audio_time);
            tickMarkFirstOutAudio = (ImageView) itemView.findViewById(R.id.tickMarkFirstOutAudio);
            outgoing_image_view_audio_stop = (ImageView) itemView.findViewById(R.id.outgoing_image_view_audio_stop);
            outgoing_image_view_audio = (ImageView) itemView.findViewById(R.id.outgoing_image_view_audio);
            incoming_image_view_audio = (ImageView) itemView.findViewById(R.id.incoming_image_view_audio);
            incoming_seekbar = (SeekBar) itemView.findViewById(R.id.incoming_seekbar);
            outgoing_seekbar = (SeekBar) itemView.findViewById(R.id.outgoing_seekbar);

        }


    }

    public List<JsonModelForChat> getWorldPopulation() {
        return chatMessageList;
    }


    public void add(JsonModelForChat object) {
        chatMessageList.add(object);
    }

}