package com.ziasy.xmppchatapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.maps.MapsInitializer;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.makeramen.roundedimageview.RoundedImageView;
import com.ziasy.xmppchatapplication.activity.FullScreenViewActivity;
import com.ziasy.xmppchatapplication.common.Confiq;
import com.ziasy.xmppchatapplication.common.OnBottomRechedListener;
import com.ziasy.xmppchatapplication.common.PdfViewr;
import com.ziasy.xmppchatapplication.common.SessionManagement;
import com.ziasy.xmppchatapplication.group_chat.ChatEnum;
import com.ziasy.xmppchatapplication.mapbox.MapBoxActivity;
import com.ziasy.xmppchatapplication.model.JsonModelForChat;
import com.ziasy.xmppchatapplication.util.Utils;
import com.ziasy.xmppchatapplication.video_compressor.Util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import github.ankushsachdeva.emojicon.EmojiconTextView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    public List<JsonModelForChat> selected_usersList;
    public List<JsonModelForChat> chatMessageList;
    private List<JsonModelForChat> searchList;
    private SessionManagement sd;
    private Activity context;
    private ChatActivity chatActivity;
    private final HashSet<MapView> mMaps = new HashSet<MapView>();
    private final HashSet<MapView> mMapsincoming = new HashSet<MapView>();
    private static MediaPlayer mediaPlayer;
    private Timer timer;
    private Timer timer2;
    private JsonModelForChat message;
    private SeekBar seekBar;
    private ImageView outgoing_image_view_audio,outgoing_image_view_audio_stop,incoming_image_view_audio,incoming_image_view_audio_stop;
    RecyclerView recyclerView;
    int position;
    private String mode;

    public ChatAdapter(Activity activity, RecyclerView msgListView, int resource, List<JsonModelForChat> list, List<JsonModelForChat> selected_usersList, String mode) {
        this.recyclerView = msgListView;
        this.chatMessageList = list;
        this.searchList = new ArrayList<>();
        this.searchList.addAll(chatMessageList);
        this.selected_usersList = selected_usersList;
        this.context = activity;
        this.chatActivity = (ChatActivity) activity;
        this.mode= mode;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_list_item, null, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final JsonModelForChat message = (JsonModelForChat) chatMessageList.get(position);
        sd = new SessionManagement(context);
        // Log.e("SELLELE",selected_usersList+"");
        if (selected_usersList.contains(chatMessageList.get(position))) {
            viewHolder.mainLinearLayout.setBackgroundColor(context.getResources().getColor(R.color.grey_light));
        } else {
            viewHolder.mainLinearLayout.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
        }        // if message is mine then align to right
        if (message.getSendName().equalsIgnoreCase(sd.getKeyId())) {

            if (message.getResponse().equalsIgnoreCase("All")) {
                if (message.getIsread().equalsIgnoreCase("true") && message.getDeliver().equalsIgnoreCase("true")) {
                    viewHolder.tickMarkFirstOut.setImageResource(R.drawable.viewed);
                    viewHolder.tickMarkFirstOutImage.setImageResource(R.drawable.viewed);
                    viewHolder.tickMarkFirstOutVideo.setImageResource(R.drawable.viewed);
                    viewHolder.tickMarkFirstOutEmoji.setImageResource(R.drawable.viewed);
                    viewHolder.tickMarkFirstOutPDF.setImageResource(R.drawable.viewed);
                    viewHolder.tickMarkContactOut.setImageResource(R.drawable.viewed);
                    viewHolder.tickMarkLocation.setImageResource(R.drawable.viewed);
                    viewHolder.tickMarkFirstOutAudio.setImageResource(R.drawable.viewed);
                } else if (message.getIsread().equalsIgnoreCase("false") && message.getDeliver().equalsIgnoreCase("true")) {
                    viewHolder.tickMarkFirstOut.setImageResource(R.drawable.delivered);
                    viewHolder.tickMarkFirstOutImage.setImageResource(R.drawable.delivered);
                    viewHolder.tickMarkFirstOutVideo.setImageResource(R.drawable.delivered);
                    viewHolder.tickMarkFirstOutEmoji.setImageResource(R.drawable.delivered);
                    viewHolder.tickMarkFirstOutPDF.setImageResource(R.drawable.delivered);
                    viewHolder.tickMarkContactOut.setImageResource(R.drawable.delivered);
                    viewHolder.tickMarkLocation.setImageResource(R.drawable.delivered);
                    viewHolder.tickMarkFirstOutAudio.setImageResource(R.drawable.delivered);
                } else if (message.getIsread().equalsIgnoreCase("false") && message.getDeliver().equalsIgnoreCase("false")) {
                    viewHolder.tickMarkFirstOut.setImageResource(R.drawable.uploaded);
                    viewHolder.tickMarkFirstOutImage.setImageResource(R.drawable.uploaded);
                    viewHolder.tickMarkFirstOutVideo.setImageResource(R.drawable.uploaded);
                    viewHolder.tickMarkFirstOutEmoji.setImageResource(R.drawable.uploaded);
                    viewHolder.tickMarkContactOut.setImageResource(R.drawable.uploaded);
                    viewHolder.tickMarkFirstOutPDF.setImageResource(R.drawable.uploaded);
                    viewHolder.tickMarkLocation.setImageResource(R.drawable.uploaded);
                    viewHolder.tickMarkFirstOutAudio.setImageResource(R.drawable.uploaded);
                }

            } else if (message.getResponse().equalsIgnoreCase("read")) {
                viewHolder.tickMarkFirstOut.setImageResource(R.drawable.viewed);
                viewHolder.tickMarkFirstOutImage.setImageResource(R.drawable.viewed);
                viewHolder.tickMarkFirstOutEmoji.setImageResource(R.drawable.viewed);
                viewHolder.tickMarkFirstOutVideo.setImageResource(R.drawable.viewed);
                viewHolder.tickMarkFirstOutPDF.setImageResource(R.drawable.viewed);
                viewHolder.tickMarkContactOut.setImageResource(R.drawable.viewed);
                viewHolder.tickMarkLocation.setImageResource(R.drawable.viewed);
                viewHolder.tickMarkFirstOutAudio.setImageResource(R.drawable.viewed);
            } else if (message.getResponse().equalsIgnoreCase("delivered")) {
                viewHolder.tickMarkFirstOut.setImageResource(R.drawable.delivered);
                viewHolder.tickMarkFirstOutImage.setImageResource(R.drawable.delivered);
                viewHolder.tickMarkFirstOutVideo.setImageResource(R.drawable.delivered);
                viewHolder.tickMarkFirstOutEmoji.setImageResource(R.drawable.delivered);
                viewHolder.tickMarkFirstOutPDF.setImageResource(R.drawable.delivered);
                viewHolder.tickMarkContactOut.setImageResource(R.drawable.delivered);
                viewHolder.tickMarkLocation.setImageResource(R.drawable.delivered);
                viewHolder.tickMarkFirstOutAudio.setImageResource(R.drawable.delivered);
            } else if (message.getResponse().equalsIgnoreCase("not avilable")) {
                viewHolder.tickMarkFirstOut.setImageResource(R.drawable.undelivered);
                viewHolder.tickMarkFirstOutImage.setImageResource(R.drawable.undelivered);
                viewHolder.tickMarkFirstOutVideo.setImageResource(R.drawable.undelivered);
                viewHolder.tickMarkFirstOutEmoji.setImageResource(R.drawable.undelivered);
                viewHolder.tickMarkFirstOutPDF.setImageResource(R.drawable.undelivered);
                viewHolder.tickMarkContactOut.setImageResource(R.drawable.undelivered);
                viewHolder.tickMarkLocation.setImageResource(R.drawable.undelivered);
                viewHolder.tickMarkFirstOutAudio.setImageResource(R.drawable.undelivered);
            }

            if (message.getDate().equalsIgnoreCase("")) {
                viewHolder.relativeDate.setVisibility(View.GONE);
                viewHolder.dateTextView.setText(message.getDate());
            } else {
                viewHolder.relativeDate.setVisibility(View.VISIBLE);
                viewHolder.dateTextView.setText(message.getDate());
            }

            switch (message.getType()) {
                case "audio":
                    viewHolder.incoming_audio_relative.setVisibility(View.GONE);
                    viewHolder.outgoing_audio_relative.setVisibility(View.VISIBLE);
                    viewHolder.outgoing_location_relative.setVisibility(View.GONE);
                    viewHolder.incoming_location_relative.setVisibility(View.GONE);
                    viewHolder.layout.setVisibility(View.GONE);
                    viewHolder.parent_layout.setVisibility(View.GONE);
                    viewHolder.outgoing_linear_contact.setVisibility(View.GONE);
                    viewHolder.incoming_linear_contact.setVisibility(View.GONE);
                    viewHolder.incoming_pdf.setVisibility(View.GONE);
                    viewHolder.out_video_cab.setVisibility(View.GONE);
                    viewHolder.outgoing_relative_emoji.setVisibility(View.GONE);
                    viewHolder.incoming_relative_emoji.setVisibility(View.GONE);
                    viewHolder.incoming_video_cab.setVisibility(View.GONE);
                    viewHolder.out_pdf.setVisibility(View.GONE);
                    viewHolder.out_image.setVisibility(View.GONE);
                    viewHolder.incoming_image.setVisibility(View.GONE);
                    viewHolder.outgoing_audio_time.setText(message.getTime());
                    if (message.isSelect()) {
                        viewHolder.outgoing_image_view_audio.setVisibility(View.GONE);
                        viewHolder.incoming_image_view_audio_stop.setVisibility(View.GONE);
                        viewHolder.incoming_image_view_audio.setVisibility(View.GONE);
                        viewHolder.outgoing_image_view_audio_stop.setVisibility(View.VISIBLE);

                        if (mediaPlayer != null) {
                            viewHolder.outgoing_seekbar.setProgress(mediaPlayer.getCurrentPosition());
                        }
                    } else if (!message.isSelect()) {
                        viewHolder.outgoing_seekbar.setProgress(0);
                        viewHolder.incoming_image_view_audio.setVisibility(View.GONE);
                        viewHolder.incoming_image_view_audio_stop.setVisibility(View.GONE);
                        viewHolder.outgoing_image_view_audio.setVisibility(View.VISIBLE);
                        viewHolder.outgoing_image_view_audio_stop.setVisibility(View.GONE);
                    }

                    viewHolder.outgoing_image_view_audio.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                       /* for (int i = 0; i < chatMessageList.size(); i++) {
                            if (position == i) {
                                message.setSelect(true);
                            } else {
                                message.setSelect(false);
                            }

                        }*/
                            for (JsonModelForChat model:chatMessageList) {
                                //if (model.isSelect == true) {
                                    model.setSelect(false);
                               // }
                            }
                            message.setSelect(true);
                            //  notifyDataSetChanged();
                            playSong(message,message.getMessage(), viewHolder.outgoing_seekbar, position, viewHolder.outgoing_image_view_audio, viewHolder.outgoing_image_view_audio_stop, viewHolder.incoming_image_view_audio_stop, viewHolder.incoming_image_view_audio);
                            if (message.isSelect()) {
                                viewHolder.outgoing_image_view_audio.setVisibility(View.GONE);
                                viewHolder.outgoing_image_view_audio_stop.setVisibility(View.VISIBLE);
                                viewHolder.incoming_image_view_audio_stop.setVisibility(View.GONE);
                                viewHolder.incoming_image_view_audio.setVisibility(View.GONE);
                            }
                            // Toast.makeText(context, "PLAY" + position, Toast.LENGTH_SHORT).show();
                        }
                    });
                    viewHolder.outgoing_image_view_audio_stop.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            message.setSelect(false);
                            //  notifyDataSetChanged();

                            viewHolder.outgoing_seekbar.setProgress(0);
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
                                viewHolder.incoming_image_view_audio_stop.setVisibility(View.GONE);
                                viewHolder.incoming_image_view_audio.setVisibility(View.GONE);
                            }
                        }
                    });
                    break;
                case "video":
                    viewHolder.incoming_audio_relative.setVisibility(View.GONE);
                    viewHolder.outgoing_audio_relative.setVisibility(View.GONE);
                    viewHolder.outgoing_location_relative.setVisibility(View.GONE);
                    viewHolder.incoming_location_relative.setVisibility(View.GONE);
                    viewHolder.layout.setVisibility(View.GONE);
                    viewHolder.outgoing_linear_contact.setVisibility(View.GONE);
                    viewHolder.parent_layout.setVisibility(View.GONE);
                    viewHolder.out_image.setVisibility(View.GONE);
                    viewHolder.incoming_pdf.setVisibility(View.GONE);
                    viewHolder.incoming_linear_contact.setVisibility(View.GONE);
                    viewHolder.out_pdf.setVisibility(View.GONE);
                    viewHolder.outgoing_relative_emoji.setVisibility(View.GONE);
                    viewHolder.incoming_relative_emoji.setVisibility(View.GONE);
                    viewHolder.incoming_image.setVisibility(View.GONE);
                    viewHolder.incoming_video_cab.setVisibility(View.GONE);
                    viewHolder.out_video_cab.setVisibility(View.VISIBLE);
                    /*Glide.with(context)
                            .load(message.getMessage())
                            .into(viewHolder.outgoing_video_IV);*/
                    if (mode.equals("offline")){
                        Glide.with(context)
                                .load(new File(message.getMessage()))
                                .into(viewHolder.outgoing_video_IV);
                    }else{
                        Glide.with(context)
                                .load(message.getMessage())
                                .into(viewHolder.outgoing_video_IV);
                    }
                    viewHolder.outgoing_video_time.setText(message.getTime());
                    viewHolder.out_video_cab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(context, VideoAvtivity.class);
                            i.putExtra("video", message.getMessage());
                            context.startActivity(i);
                        }
                    });
                    break;

                case "emoji":
                    viewHolder.incoming_audio_relative.setVisibility(View.GONE);
                    viewHolder.outgoing_audio_relative.setVisibility(View.GONE);
                    viewHolder.outgoing_location_relative.setVisibility(View.GONE);
                    viewHolder.incoming_location_relative.setVisibility(View.GONE);
                    viewHolder.out_image.setVisibility(View.GONE);
                    viewHolder.out_video_cab.setVisibility(View.GONE);
                    viewHolder.incoming_image.setVisibility(View.GONE);
                    viewHolder.layout.setVisibility(View.GONE);
                    viewHolder.outgoing_linear_contact.setVisibility(View.GONE);
                    viewHolder.incoming_pdf.setVisibility(View.GONE);
                    viewHolder.incoming_linear_contact.setVisibility(View.GONE);
                    viewHolder.incoming_video_cab.setVisibility(View.GONE);
                    viewHolder.out_pdf.setVisibility(View.GONE);
                    viewHolder.outgoing_relative_emoji.setVisibility(View.VISIBLE);
                    viewHolder.incoming_relative_emoji.setVisibility(View.GONE);
                    viewHolder.parent_layout.setVisibility(View.GONE);
                    viewHolder.outgoing_emoji_time.setText(message.getTime());
                    Resources res =  context.getResources();
                    String mDrawableName = message.getMessage();
                    int resID = res.getIdentifier(mDrawableName , "drawable", context.getPackageName());
                    Drawable drawable = res.getDrawable(resID );
                    viewHolder.outgoing_emoji_image.setImageDrawable(drawable );
                    break;
                case "share":
                    viewHolder.incoming_audio_relative.setVisibility(View.GONE);
                    viewHolder.outgoing_audio_relative.setVisibility(View.GONE);
                    viewHolder.outgoing_location_relative.setVisibility(View.GONE);
                    viewHolder.incoming_location_relative.setVisibility(View.GONE);
                    viewHolder.out_image.setVisibility(View.GONE);
                    viewHolder.out_video_cab.setVisibility(View.GONE);
                    viewHolder.incoming_image.setVisibility(View.GONE);
                    viewHolder.layout.setVisibility(View.GONE);
                    viewHolder.incoming_pdf.setVisibility(View.GONE);

                    viewHolder.incoming_linear_contact.setVisibility(View.GONE);
                    viewHolder.incoming_video_cab.setVisibility(View.GONE);
                    viewHolder.out_pdf.setVisibility(View.GONE);
                    viewHolder.outgoing_relative_emoji.setVisibility(View.GONE);
                    viewHolder.incoming_relative_emoji.setVisibility(View.GONE);
                    viewHolder.parent_layout.setVisibility(View.GONE);

                    viewHolder.outgoing_linear_contact.setVisibility(View.VISIBLE);
                    viewHolder.outgoing_contact_time.setText(message.getTime());
                    String str[] = message.getMessage().split(",");
                    viewHolder.outgoing_contact_number.setText(str[0]);
                    viewHolder.outgoing_contact_view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(context, ProfileActivity.class);
                            i.putExtra("name", str[0]);
                            i.putExtra("rid", str[2]);
                            i.putExtra("did", str[3]);
                            context.startActivity(i);
                        }
                    });
                    break;
                case "msg":
                    viewHolder.incoming_audio_relative.setVisibility(View.GONE);
                    viewHolder.outgoing_audio_relative.setVisibility(View.GONE);
                    viewHolder.outgoing_location_relative.setVisibility(View.GONE);
                    viewHolder.incoming_location_relative.setVisibility(View.GONE);
                    viewHolder.outgoingTextImage.setText(message.getMessage());
                    viewHolder.outgoing_text_time.setText(message.getTime());
                    viewHolder.layout.setVisibility(View.VISIBLE);

                    viewHolder.outgoing_relative_emoji.setVisibility(View.GONE);
                    viewHolder.outgoing_linear_contact.setVisibility(View.GONE);
                    viewHolder.incoming_relative_emoji.setVisibility(View.GONE);
                    viewHolder.parent_layout.setVisibility(View.GONE);
                    viewHolder.incoming_pdf.setVisibility(View.GONE);
                    viewHolder.out_video_cab.setVisibility(View.GONE);

                    viewHolder.incoming_linear_contact.setVisibility(View.GONE);
                    viewHolder.incoming_video_cab.setVisibility(View.GONE);
                    viewHolder.out_pdf.setVisibility(View.GONE);
                    viewHolder.out_image.setVisibility(View.GONE);
                    viewHolder.incoming_image.setVisibility(View.GONE);

                    break;
                case "img":
                    viewHolder.incoming_audio_relative.setVisibility(View.GONE);
                    viewHolder.outgoing_audio_relative.setVisibility(View.GONE);
                    viewHolder.incoming_location_relative.setVisibility(View.GONE);
                    viewHolder.outgoing_location_relative.setVisibility(View.GONE);
                    viewHolder.layout.setVisibility(View.GONE);
                    viewHolder.parent_layout.setVisibility(View.GONE);
                    viewHolder.incoming_linear_contact.setVisibility(View.GONE);
                    viewHolder.outgoing_relative_emoji.setVisibility(View.GONE);
                    viewHolder.incoming_relative_emoji.setVisibility(View.GONE);
                    viewHolder.outgoing_linear_contact.setVisibility(View.GONE);
                    viewHolder.incoming_pdf.setVisibility(View.GONE);
                    viewHolder.out_pdf.setVisibility(View.GONE);
                    viewHolder.out_image.setVisibility(View.VISIBLE);
                    viewHolder.incoming_image.setVisibility(View.GONE);
                    viewHolder.out_video_cab.setVisibility(View.GONE);
                    viewHolder.incoming_video_cab.setVisibility(View.GONE);

                    /*Glide.with(context)
                            .load(message.getMessage())
                            .into(viewHolder.outgoing_image_IV);*/
                    if (mode.equals("offline")){
                        Glide.with(context)
                                .load(new File(message.getMessage()))
                                .into(viewHolder.outgoing_image_IV);

                    }else{

                        Glide.with(context)
                                .load(message.getMessage())
                                .into(viewHolder.outgoing_image_IV);
                    }

                    viewHolder.outgoing_image_time.setText(message.getTime());
                    viewHolder.outgoing_image_IV.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(context, FullScreenViewActivity.class);
                            i.putExtra("id", message.getMid());
                            i.putExtra("rid", message.getRecieverName());
                            context.startActivityForResult(i,88);
                        }
                    });
                    break;
                case "pdf":
                    viewHolder.incoming_audio_relative.setVisibility(View.GONE);
                    viewHolder.outgoing_audio_relative.setVisibility(View.GONE);
                    viewHolder.outgoing_location_relative.setVisibility(View.GONE);
                    viewHolder.incoming_location_relative.setVisibility(View.GONE);
                    viewHolder.layout.setVisibility(View.GONE);
                    viewHolder.parent_layout.setVisibility(View.GONE);
                    viewHolder.out_image.setVisibility(View.GONE);
                    viewHolder.outgoing_linear_contact.setVisibility(View.GONE);
                    viewHolder.incoming_linear_contact.setVisibility(View.GONE);
                    viewHolder.outgoing_relative_emoji.setVisibility(View.GONE);
                    viewHolder.incoming_relative_emoji.setVisibility(View.GONE);
                    viewHolder.incoming_pdf.setVisibility(View.GONE);
                    viewHolder.out_video_cab.setVisibility(View.GONE);
                    viewHolder.incoming_video_cab.setVisibility(View.GONE);
                    viewHolder.out_pdf.setVisibility(View.VISIBLE);
                    viewHolder.incoming_image.setVisibility(View.GONE);
                    String str1 = message.getMessage();
                    if (str1.toLowerCase().endsWith(".xls") || str1.toLowerCase().endsWith(".xlsb") || str1.toLowerCase().endsWith(".xlsm") || str1.toLowerCase().endsWith(".xlsx") || str1.toLowerCase().endsWith(".xlt") || str1.toLowerCase().endsWith(".xltx")) {
                        viewHolder.txtoutgoing_pdf.setText("Excel File");
                        viewHolder.outgoing_doc_icon.setImageResource(R.drawable.excel);
                    } else if (str1.toLowerCase().endsWith(".pdf")) {
                        viewHolder.txtoutgoing_pdf.setText("PDF File");
                        viewHolder.outgoing_doc_icon.setImageResource(R.drawable.pdf);
                    } else {
                        viewHolder.txtoutgoing_pdf.setText("Word File");
                        viewHolder.outgoing_doc_icon.setImageResource(R.drawable.word);
                    }
                    viewHolder.outgoing_pdf_time.setText(message.getTime());
                    viewHolder.out_lineat_bubble.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                          /*  Intent i = new Intent(context, PdfViewr.class);
                            i.putExtra("pdf", message.getMessage());
                            context.startActivity(i);*/
                          String pad= message.getMessage().replace(Confiq.RETURN_IMAGE_URL,"");
                            Utils.downloadFile(context,  message.getMessage(), pad);
                        }
                    });
                    break;
                case "location":
                    try {
                        viewHolder.incoming_audio_relative.setVisibility(View.GONE);
                        viewHolder.outgoing_audio_relative.setVisibility(View.GONE);
                        viewHolder.outgoing_location_relative.setVisibility(View.VISIBLE);
                        viewHolder.incoming_location_relative.setVisibility(View.GONE);
                        viewHolder.out_image.setVisibility(View.GONE);
                        viewHolder.out_video_cab.setVisibility(View.GONE);
                        viewHolder.incoming_image.setVisibility(View.GONE);
                        viewHolder.layout.setVisibility(View.GONE);
                        viewHolder.incoming_pdf.setVisibility(View.GONE);

                        viewHolder.incoming_linear_contact.setVisibility(View.GONE);
                        viewHolder.incoming_video_cab.setVisibility(View.GONE);
                        viewHolder.out_pdf.setVisibility(View.GONE);
                        viewHolder.outgoing_relative_emoji.setVisibility(View.GONE);
                        viewHolder.incoming_relative_emoji.setVisibility(View.GONE);
                        viewHolder.parent_layout.setVisibility(View.GONE);

                        viewHolder.outgoing_linear_contact.setVisibility(View.GONE);
                        viewHolder.outgoing_location_time.setText(message.getTime());
                        String strLocation[] = message.getMessage().split(",");
                        viewHolder.outgoing_location_address.setText(strLocation[2]);

                        viewHolder.outgoing_view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String strL[] = message.getMessage().split(",");
                                Intent i = new Intent(context, MapBoxActivity.class);
                                i.putExtra("lat", Double.valueOf(strL[0]));
                                i.putExtra("lang", Double.valueOf(strL[1]));
                                i.putExtra("title", strL[2]);
                                context.startActivity(i);
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                    break;
                default:
                    viewHolder.incoming_audio_relative.setVisibility(View.GONE);
                    viewHolder.outgoing_audio_relative.setVisibility(View.GONE);
                    viewHolder.incoming_location_relative.setVisibility(View.GONE);
                    viewHolder.outgoing_location_relative.setVisibility(View.GONE);
                    viewHolder.outgoing_relative_emoji.setVisibility(View.GONE);
                    viewHolder.incoming_relative_emoji.setVisibility(View.GONE);
                    viewHolder.out_image.setVisibility(View.GONE);
                    viewHolder.incoming_linear_contact.setVisibility(View.GONE);
                    viewHolder.out_video_cab.setVisibility(View.GONE);
                    viewHolder.incoming_image.setVisibility(View.GONE);
                    viewHolder.layout.setVisibility(View.GONE);
                    viewHolder.incoming_pdf.setVisibility(View.GONE);
                    viewHolder.outgoing_linear_contact.setVisibility(View.GONE);
                    viewHolder.incoming_video_cab.setVisibility(View.GONE);
                    viewHolder.out_pdf.setVisibility(View.GONE);
                    viewHolder.parent_layout.setVisibility(View.GONE);
                    break;
            }
        }
        // If not mine then align to left
        else {
            if (message.getDate().equalsIgnoreCase("")) {
                viewHolder.relativeDate.setVisibility(View.GONE);
                viewHolder.dateTextView.setText(message.getDate());
            } else {
                viewHolder.relativeDate.setVisibility(View.VISIBLE);
                viewHolder.dateTextView.setText(message.getDate());
            }
            switch (message.getType()) {
                case "img":
                    viewHolder.incoming_audio_relative.setVisibility(View.GONE);
                    viewHolder.outgoing_audio_relative.setVisibility(View.GONE);
                    viewHolder.outgoing_location_relative.setVisibility(View.GONE);
                    viewHolder.incoming_location_relative.setVisibility(View.GONE);
                    viewHolder.out_image.setVisibility(View.GONE);
                    viewHolder.incoming_image.setVisibility(View.VISIBLE);
                    viewHolder.layout.setVisibility(View.GONE);
                    viewHolder.incoming_pdf.setVisibility(View.GONE);
                    viewHolder.outgoing_linear_contact.setVisibility(View.GONE);
                    viewHolder.out_video_cab.setVisibility(View.GONE);
                    viewHolder.outgoing_relative_emoji.setVisibility(View.GONE);
                    viewHolder.incoming_relative_emoji.setVisibility(View.GONE);
                    viewHolder.incoming_video_cab.setVisibility(View.GONE);

                    viewHolder.incoming_linear_contact.setVisibility(View.GONE);

                    viewHolder.out_pdf.setVisibility(View.GONE);
                    viewHolder.parent_layout.setVisibility(View.GONE);
                    Glide.with(context)
                            .load(message.getMessage())
                            .into(viewHolder.incoming_image_IV);

                    viewHolder.incoming_image_time.setText(message.getTime());
                    viewHolder.incoming_image_IV.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(context, FullScreenViewActivity.class);
                            //  i.putStringArrayListExtra("image", imgage);
                            i.putExtra("id", message.getMid());
                            i.putExtra("rid", message.getSendName());
                            context.startActivityForResult(i,88);

                        }
                    });
                    break;
                case "pdf":
                    viewHolder.incoming_audio_relative.setVisibility(View.GONE);
                    viewHolder.outgoing_audio_relative.setVisibility(View.GONE);
                    viewHolder.outgoing_location_relative.setVisibility(View.GONE);
                    viewHolder.incoming_location_relative.setVisibility(View.GONE);
                    viewHolder.layout.setVisibility(View.GONE);
                    viewHolder.parent_layout.setVisibility(View.GONE);

                    viewHolder.out_image.setVisibility(View.GONE);
                    viewHolder.incoming_image.setVisibility(View.GONE);
                    viewHolder.out_pdf.setVisibility(View.GONE);

                    viewHolder.outgoing_relative_emoji.setVisibility(View.GONE);
                    viewHolder.outgoing_linear_contact.setVisibility(View.GONE);
                    viewHolder.incoming_relative_emoji.setVisibility(View.GONE);
                    viewHolder.out_video_cab.setVisibility(View.GONE);
                    viewHolder.incoming_video_cab.setVisibility(View.GONE);
                    viewHolder.incoming_linear_contact.setVisibility(View.GONE);
                    viewHolder.incoming_pdf.setVisibility(View.VISIBLE);
                    String str = message.getMessage().replace(Confiq.RETURN_IMAGE_URL, "");
                    if (str.toLowerCase().endsWith(".xls") || str.toLowerCase().endsWith(".xlsb") || str.toLowerCase().endsWith(".xlsm") || str.toLowerCase().endsWith(".xlsx") || str.toLowerCase().endsWith(".xlt") || str.toLowerCase().endsWith(".xltx")) {
                        viewHolder.txtincomming_pdf.setText("Excel File");
                        viewHolder.incoming_doc_icon.setImageResource(R.drawable.excel);
                    } else if (str.toLowerCase().endsWith(".pdf")) {
                        viewHolder.txtincomming_pdf.setText("PDF File");
                        viewHolder.incoming_doc_icon.setImageResource(R.drawable.pdf);
                    } else {
                        viewHolder.txtincomming_pdf.setText("Word File");
                        viewHolder.incoming_doc_icon.setImageResource(R.drawable.word);
                    }
                    viewHolder.incoming_pdf_time.setText(message.getTime());
                    viewHolder.incoming_linear_click.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                          /*  Intent i = new Intent(context, PdfViewr.class);
                            i.putExtra("pdf", message.getMessage());
                            context.startActivity(i);*/
                            String pad= message.getMessage().replace(Confiq.RETURN_IMAGE_URL,"");
                            Utils.downloadFile(context,  message.getMessage(), pad);
                        }
                    });
                    break;
                case "video":
                    viewHolder.incoming_audio_relative.setVisibility(View.GONE);
                    viewHolder.outgoing_audio_relative.setVisibility(View.GONE);
                    viewHolder.outgoing_location_relative.setVisibility(View.GONE);
                    viewHolder.incoming_location_relative.setVisibility(View.GONE);
                    viewHolder.layout.setVisibility(View.GONE);
                    viewHolder.parent_layout.setVisibility(View.GONE);
                    viewHolder.out_image.setVisibility(View.GONE);
                    viewHolder.incoming_image.setVisibility(View.GONE);
                    viewHolder.outgoing_relative_emoji.setVisibility(View.GONE);
                    viewHolder.outgoing_linear_contact.setVisibility(View.GONE);
                    viewHolder.outgoing_linear_contact.setVisibility(View.GONE);
                    viewHolder.incoming_linear_contact.setVisibility(View.GONE);
                    viewHolder.incoming_video_cab.setVisibility(View.VISIBLE);
                    viewHolder.out_pdf.setVisibility(View.GONE);
                    viewHolder.out_video_cab.setVisibility(View.GONE);
                    viewHolder.incoming_pdf.setVisibility(View.GONE);
                    viewHolder.incoming_video_time.setText(message.getTime());
                    Glide.with(context)
                            //.load("https://www.demonuts.com/Demonuts/smallvideo.mp4")
                            .load(message.getMessage())
                            //.placeholder(R.drawable.group_icon)
                            .into(viewHolder.incoming_video_IV);

                    viewHolder.incoming_video_cab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(context, VideoAvtivity.class);
                            i.putExtra("video", message.getMessage());
                            context.startActivity(i);
                        }
                    });
                    break;
                case "audio":
                    viewHolder.incoming_audio_relative.setVisibility(View.VISIBLE);
                    viewHolder.outgoing_audio_relative.setVisibility(View.GONE);
                    viewHolder.outgoing_location_relative.setVisibility(View.GONE);
                    viewHolder.incoming_location_relative.setVisibility(View.GONE);
                    viewHolder.out_image.setVisibility(View.GONE);
                    viewHolder.out_video_cab.setVisibility(View.GONE);
                    viewHolder.incoming_image.setVisibility(View.GONE);
                    viewHolder.layout.setVisibility(View.GONE);
                    viewHolder.outgoing_linear_contact.setVisibility(View.GONE);
                    viewHolder.incoming_pdf.setVisibility(View.GONE);
                    viewHolder.outgoing_relative_emoji.setVisibility(View.GONE);
                    viewHolder.incoming_relative_emoji.setVisibility(View.GONE);
                    viewHolder.incoming_linear_contact.setVisibility(View.GONE);
                    viewHolder.incoming_video_cab.setVisibility(View.GONE);
                    viewHolder.out_pdf.setVisibility(View.GONE);
                    viewHolder.parent_layout.setVisibility(View.GONE);
                    viewHolder.incoming_audio_time.setText(message.getTime());

                    if (message.isSelect()) {
                        viewHolder.outgoing_image_view_audio.setVisibility(View.GONE);
                        viewHolder.incoming_image_view_audio_stop.setVisibility(View.VISIBLE);
                        viewHolder.incoming_image_view_audio.setVisibility(View.GONE);
                        viewHolder.outgoing_image_view_audio_stop.setVisibility(View.GONE);

                        if (mediaPlayer != null) {
                            viewHolder.incoming_seekbar.setProgress(mediaPlayer.getCurrentPosition());
                        }
                    } else if (!message.isSelect()) {

                        viewHolder.incoming_seekbar.setProgress(0);
                        viewHolder.incoming_image_view_audio.setVisibility(View.VISIBLE);
                        viewHolder.incoming_image_view_audio_stop.setVisibility(View.GONE);
                        viewHolder.outgoing_image_view_audio.setVisibility(View.GONE);
                        viewHolder.outgoing_image_view_audio_stop.setVisibility(View.GONE);
                    }
                    viewHolder.incoming_image_view_audio.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                       /* for (int i = 0; i < chatMessageList.size(); i++) {
                            if (position == i) {
                                message.setSelect(true);
                            } else {
                                message.setSelect(false);
                            }
                        }
*/
                            for (JsonModelForChat model:chatMessageList) {
                             //   if (model.isSelect == true) {
                                    model.setSelect(false);
                             //   }
                            }
                       message.setSelect(true);

                            //  notifyDataSetChanged();
                            playSong(message,message.getMessage(), viewHolder.incoming_seekbar, position,viewHolder.outgoing_image_view_audio, viewHolder.outgoing_image_view_audio_stop, viewHolder.incoming_image_view_audio_stop, viewHolder.incoming_image_view_audio);
                            if (message.isSelect()) {
                                viewHolder.outgoing_image_view_audio.setVisibility(View.GONE);
                                viewHolder.outgoing_image_view_audio_stop.setVisibility(View.GONE);
                                viewHolder.incoming_image_view_audio_stop.setVisibility(View.VISIBLE);
                                viewHolder.incoming_image_view_audio.setVisibility(View.GONE);
                            }

                            // Toast.makeText(context, "PLAY" + position, Toast.LENGTH_SHORT).show();

                        }
                    });
                    viewHolder.incoming_image_view_audio_stop.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            message.setSelect(false);
                            //  notifyDataSetChanged();

                            viewHolder.incoming_seekbar.setProgress(0);
                            try {
                                mediaPlayer.stop();
                                mediaPlayer.release();
                                mediaPlayer = null;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            //   Toast.makeText(context, "STOPs" + position, Toast.LENGTH_SHORT).show();
                            if (!message.isSelect()) {

                                viewHolder.outgoing_image_view_audio.setVisibility(View.GONE);
                                viewHolder.outgoing_image_view_audio_stop.setVisibility(View.GONE);
                                viewHolder.incoming_image_view_audio_stop.setVisibility(View.GONE);
                                viewHolder.incoming_image_view_audio.setVisibility(View.VISIBLE);
                            }
                        }
                    });

                    break;
                case "emoji":
                    viewHolder.incoming_audio_relative.setVisibility(View.GONE);
                    viewHolder.outgoing_audio_relative.setVisibility(View.GONE);
                    viewHolder.outgoing_location_relative.setVisibility(View.GONE);
                    viewHolder.incoming_location_relative.setVisibility(View.GONE);
                    viewHolder.incoming_emoji_time.setText(message.getTime());
                    viewHolder.out_image.setVisibility(View.GONE);
                    viewHolder.out_video_cab.setVisibility(View.GONE);
                    viewHolder.incoming_image.setVisibility(View.GONE);
                    viewHolder.layout.setVisibility(View.GONE);
                    viewHolder.incoming_pdf.setVisibility(View.GONE);
                    viewHolder.outgoing_linear_contact.setVisibility(View.GONE);
                    viewHolder.incoming_linear_contact.setVisibility(View.GONE);

                    viewHolder.incoming_video_cab.setVisibility(View.GONE);
                    viewHolder.out_pdf.setVisibility(View.GONE);
                    viewHolder.outgoing_relative_emoji.setVisibility(View.GONE);
                    viewHolder.incoming_relative_emoji.setVisibility(View.VISIBLE);
                    viewHolder.parent_layout.setVisibility(View.GONE);
                    Resources res =  context.getResources();
                    String mDrawableName = message.getMessage();
                    int resID = res.getIdentifier(mDrawableName , "drawable", context.getPackageName());
                    Drawable drawable = res.getDrawable(resID );
                    viewHolder.incoming_emoji_image.setImageDrawable(drawable );
                    break;
                case "share":
                    viewHolder.incoming_audio_relative.setVisibility(View.GONE);
                    viewHolder.outgoing_audio_relative.setVisibility(View.GONE);
                    viewHolder.outgoing_location_relative.setVisibility(View.GONE);
                    viewHolder.incoming_location_relative.setVisibility(View.GONE);
                    viewHolder.incoming_contact_time.setText(message.getTime());
                    viewHolder.out_image.setVisibility(View.GONE);
                    viewHolder.out_video_cab.setVisibility(View.GONE);
                    viewHolder.incoming_image.setVisibility(View.GONE);
                    viewHolder.layout.setVisibility(View.GONE);
                    viewHolder.incoming_pdf.setVisibility(View.GONE);

                    viewHolder.outgoing_linear_contact.setVisibility(View.GONE);
                    viewHolder.incoming_video_cab.setVisibility(View.GONE);
                    viewHolder.out_pdf.setVisibility(View.GONE);
                    viewHolder.outgoing_relative_emoji.setVisibility(View.GONE);
                    viewHolder.incoming_relative_emoji.setVisibility(View.GONE);
                    viewHolder.incoming_linear_contact.setVisibility(View.VISIBLE);
                    viewHolder.parent_layout.setVisibility(View.GONE);
                    String[] strData = message.getMessage().split(",");
                    viewHolder.incoming_contact_number.setText(strData[0]);
                    viewHolder.incoming_view_image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(context, ProfileActivity.class);
                            i.putExtra("name", strData[0]);
                            i.putExtra("rid", strData[2]);
                            i.putExtra("did", strData[3]);
                            context.startActivity(i);
                        }
                    });
                    break;
                case "location":
                    try {
                        viewHolder.incoming_audio_relative.setVisibility(View.GONE);
                        viewHolder.outgoing_audio_relative.setVisibility(View.GONE);
                        viewHolder.outgoing_location_relative.setVisibility(View.GONE);
                        viewHolder.incoming_location_relative.setVisibility(View.VISIBLE);

                        viewHolder.out_image.setVisibility(View.GONE);
                        viewHolder.out_video_cab.setVisibility(View.GONE);
                        viewHolder.incoming_image.setVisibility(View.GONE);
                        viewHolder.layout.setVisibility(View.GONE);
                        viewHolder.incoming_pdf.setVisibility(View.GONE);
                        viewHolder.outgoing_linear_contact.setVisibility(View.GONE);
                        viewHolder.incoming_video_cab.setVisibility(View.GONE);
                        viewHolder.out_pdf.setVisibility(View.GONE);
                        viewHolder.outgoing_relative_emoji.setVisibility(View.GONE);
                        viewHolder.incoming_relative_emoji.setVisibility(View.GONE);
                        viewHolder.incoming_linear_contact.setVisibility(View.GONE);
                        viewHolder.parent_layout.setVisibility(View.GONE);

                        viewHolder.incoming_location_time.setText(message.getTime());
                        String strLocation[] = message.getMessage().split(",");
                        //String currLocation = "&daddr=" + strLocation[2];
                        viewHolder.incoming_location_address.setText(strLocation[2]);

                        viewHolder.incoming_view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String strL[] = message.getMessage().split(",");
                                String currLocation = "&daddr=" + strL[2];
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                Uri.parse("http://maps.google.com/maps?saddr=" + currLocation
                                        + "&dirflg=d"));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                & Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        intent.setClassName("com.google.android.apps.maps",
                                "com.google.android.maps.MapsActivity");
                        context.startActivity(intent);
                                Intent i = new Intent(context, MapBoxActivity.class);
                                i.putExtra("lat", Double.valueOf(strL[0]));
                                i.putExtra("lang", Double.valueOf(strL[1]));
                                i.putExtra("title", strL[2]);
                                context.startActivity(i);
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "msg":
                    viewHolder.incoming_audio_relative.setVisibility(View.GONE);
                    viewHolder.outgoing_audio_relative.setVisibility(View.GONE);
                    viewHolder.incoming_location_relative.setVisibility(View.GONE);
                    viewHolder.outgoing_location_relative.setVisibility(View.GONE);
                    viewHolder.outgoing_relative_emoji.setVisibility(View.GONE);
                    viewHolder.incoming_relative_emoji.setVisibility(View.GONE);
                    viewHolder.out_image.setVisibility(View.GONE);
                    viewHolder.incoming_linear_contact.setVisibility(View.GONE);
                    viewHolder.out_video_cab.setVisibility(View.GONE);
                    viewHolder.incoming_image.setVisibility(View.GONE);
                    viewHolder.layout.setVisibility(View.GONE);
                    viewHolder.incoming_pdf.setVisibility(View.GONE);
                    viewHolder.outgoing_linear_contact.setVisibility(View.GONE);
                    viewHolder.incoming_video_cab.setVisibility(View.GONE);
                    viewHolder.out_pdf.setVisibility(View.GONE);
                    viewHolder.parent_layout.setVisibility(View.VISIBLE);
                    viewHolder.incomming_text.setText(message.getMessage());
                    viewHolder.incoming_text_time.setText(message.getTime());
                    break;
                default:
                    viewHolder.incoming_audio_relative.setVisibility(View.GONE);
                    viewHolder.outgoing_audio_relative.setVisibility(View.GONE);
                    viewHolder.incoming_location_relative.setVisibility(View.GONE);
                    viewHolder.outgoing_location_relative.setVisibility(View.GONE);
                    viewHolder.outgoing_relative_emoji.setVisibility(View.GONE);
                    viewHolder.incoming_relative_emoji.setVisibility(View.GONE);
                    viewHolder.out_image.setVisibility(View.GONE);
                    viewHolder.incoming_linear_contact.setVisibility(View.GONE);
                    viewHolder.out_video_cab.setVisibility(View.GONE);
                    viewHolder.incoming_image.setVisibility(View.GONE);
                    viewHolder.layout.setVisibility(View.GONE);
                    viewHolder.incoming_pdf.setVisibility(View.GONE);
                    viewHolder.outgoing_linear_contact.setVisibility(View.GONE);
                    viewHolder.incoming_video_cab.setVisibility(View.GONE);
                    viewHolder.out_pdf.setVisibility(View.GONE);
                    viewHolder.parent_layout.setVisibility(View.GONE);
                    break;
            }


        }
    }

    public void playSong(JsonModelForChat message1,String url, SeekBar seekBar1, int position,ImageView outgoing_image_view_audio1,
                         ImageView outgoing_image_view_audio_stop1
            ,ImageView incoming_image_view_audio_stop1,ImageView incoming_image_view_audio1) {
        this.seekBar = seekBar1;
        this.message = message1;
        this.outgoing_image_view_audio = outgoing_image_view_audio1;
        this.outgoing_image_view_audio_stop = outgoing_image_view_audio_stop1;
        this.incoming_image_view_audio_stop = incoming_image_view_audio_stop1;
        this.incoming_image_view_audio = incoming_image_view_audio1;

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
                    try {
                        if (mediaPlayer != null && mediaPlayer.getCurrentPosition() != 0) {
                            seekBar.setProgress(mediaPlayer.getCurrentPosition());
                            if (duration <= mediaPlayer.getCurrentPosition()) {
                                try {
                                    Log.e("stop media", "stoped");
                                    timer.cancel();
                                    timer = null;

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        clearMusic();
                    }
                }
            }, 1, 1000);
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    //  seekBarHint.setVisibility(View.VISIBLE);
                }

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
                    // seekBarHint.setVisibility(View.VISIBLE);
                    int x = (int) Math.ceil(progress / 1000f);
                  /*  if (x < 10)
                   //     seekBarHint.setText("0:0" + x);
                    else
                        seekBarHint.setText("0:" + x);*/
                    double percent = progress / (double) seekBar.getMax();
                    int offset = seekBar.getThumbOffset();
                    int seekWidth = seekBar.getWidth();
                    int val = (int) Math.round(percent * (seekWidth - 2 * offset));
                  /*  int labelWidth = seekBarHint.getWidth();
                    seekBarHint.setX(offset + seekBar.getX() + val
                            - Math.round(percent * offset)
                            - Math.round(percent * labelWidth / 2));*/
                    if (progress > 0 && mediaPlayer != null && !mediaPlayer.isPlaying()) {
                        clearMusic();
                        seekBar.setProgress(0);
                        outgoing_image_view_audio.setVisibility(View.VISIBLE);
                        outgoing_image_view_audio_stop.setVisibility(View.GONE);
                        incoming_image_view_audio_stop.setVisibility(View.GONE);
                        incoming_image_view_audio.setVisibility(View.VISIBLE);
                        message.setSelect(false);

                    }
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        mediaPlayer.seekTo(seekBar.getProgress());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            clearMusic();
        }
    }

    @Override
    public int getItemCount() {
        return chatMessageList.size();
    }

    private static String getFileSizeMegaBytes(String file) {
        try {
            URL url = new URL(file);
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();
            int file_size = urlConnection.getContentLength();
            // return  (double) file_size / (1024 * 1024) + " MB".trim();
            return (double) file_size + " MB".trim();

        } catch (MalformedURLException e) {

        } catch (IOException e) {

        }
        return "0.0 MB".trim();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // SELECTION LINEAR LAYOUT
        //   private  RelativeLayout outgoing_linear_selection,incoming_linear_selection;
        private LinearLayout mainLinearLayout;
        //
        private TextView incoming_contact_time, incoming_contact_number, outgoing_contact_name, outgoing_contact_number, outgoing_contact_time, incoming_video_time, outgoing_video_time, incoming_emoji_time, outgoing_emoji_time, dateTextView, incoming_text_time, outgoing_text_time, outgoing_pdf_time, incoming_pdf_time, outgoing_image_time, incoming_image_time;
        private ImageView incoming_doc_icon, outgoing_doc_icon, incoming_emoji_image, outgoing_emoji_image;
        private ImageView incoming_play_btn, outgoing_play_btn, incoming_video_IV;
        private RoundedImageView outgoing_image_IV, outgoing_video_IV, incoming_image_IV;
        private LinearLayout incoming_linear_contact, incoming_video_cab, parent_layout, incoming_image, out_lineat_bubble,out_pdf, incoming_linear_click,incoming_pdf, outgoing_linear_contact;
        ////////AUDIO/////////////
        private RelativeLayout container_list_item;
        private RelativeLayout outgoing_audio_relative, incoming_audio_relative;
        private TextView outgoing_audio_time, incoming_audio_time;
        private ImageView tickMarkFirstOutAudio, incoming_image_view_audio, incoming_image_view_audio_stop, outgoing_image_view_audio, outgoing_image_view_audio_stop;
        private SeekBar outgoing_seekbar, incoming_seekbar;

        ///////
        private View outgoing_view, incoming_view;
        private RelativeLayout outgoing_location_relative, incoming_location_relative;
        private TextView outgoing_location_time, incoming_location_time,incoming_location_address,outgoing_location_address;
        private ImageView tickMarkLocation;
        //   private MapView outgoing_location_map, incoming_location_map;
        // private GoogleMap map, map_incoming;
        //  private MapView outgoing_location_map, incoming_location_map;
        //  private MapboxMap map, map_incoming;

        //Loader
        //    private ProgressBar progress_bar, outgoing_video_progress_bar, incoming_progress_bar, incoming_video_progress_bar;
        private RelativeLayout out_image, relativeDate, layout, out_video_cab, outgoing_relative_emoji, incoming_relative_emoji;
        private EmojiconTextView incomming_text, outgoingTextImage, txtoutgoing_pdf, txtincomming_pdf;

        private ImageView tickMarkContactOut, tickMarkFirstOut, tickMarkFirstOutImage, tickMarkFirstOutVideo, tickMarkFirstOutPDF, tickMarkFirstOutEmoji;
        // VIEW CONTACT
        private Button outgoing_contact_view, incoming_view_image;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            // get the reference of item view's
            container_list_item = (RelativeLayout) itemView.findViewById(R.id.container_list_item);
            outgoing_emoji_time = (TextView) itemView.findViewById(R.id.outgoing_emoji_time);
            incoming_emoji_time = (TextView) itemView.findViewById(R.id.incoming_emoji_time);
            dateTextView = (TextView) itemView.findViewById(R.id.dateTextView);

            incoming_text_time = (TextView) itemView.findViewById(R.id.incoming_text_time);
            outgoing_text_time = (TextView) itemView.findViewById(R.id.outgoing_text_time);
            outgoing_pdf_time = (TextView) itemView.findViewById(R.id.outgoing_pdf_time);
            incoming_pdf_time = (TextView) itemView.findViewById(R.id.incoming_pdf_time);
            outgoing_image_time = (TextView) itemView.findViewById(R.id.outgoing_image_time);
            incoming_image_time = (TextView) itemView.findViewById(R.id.incoming_image_time);

            outgoing_video_time = (TextView) itemView.findViewById(R.id.outgoing_video_time);
            outgoing_contact_name = (TextView) itemView.findViewById(R.id.outgoing_contact_name);
            incoming_video_time = (TextView) itemView.findViewById(R.id.incoming_video_time);
            outgoing_contact_time = (TextView) itemView.findViewById(R.id.outgoing_contact_time);
            outgoing_contact_number = (TextView) itemView.findViewById(R.id.outgoing_contact_number);
            incoming_contact_number = (TextView) itemView.findViewById(R.id.incoming_contact_number);
            incoming_contact_time = (TextView) itemView.findViewById(R.id.incoming_contact_number);
            incomming_text = (EmojiconTextView) itemView.findViewById(R.id.incomming_text);
            outgoingTextImage = (EmojiconTextView) itemView.findViewById(R.id.outgoing_text);

            txtoutgoing_pdf = (EmojiconTextView) itemView.findViewById(R.id.txtoutgoing_pdf);
            txtincomming_pdf = (EmojiconTextView) itemView.findViewById(R.id.txtincomming_pdf);

            incoming_play_btn = (ImageView) itemView.findViewById(R.id.incoming_play_btn);
            incoming_doc_icon = (ImageView) itemView.findViewById(R.id.incoming_doc_icon);
            outgoing_doc_icon = (ImageView) itemView.findViewById(R.id.outgoing_doc_icon);
            outgoing_image_IV = (RoundedImageView) itemView.findViewById(R.id.outgoing_image_IV);
            incoming_image_IV = (RoundedImageView) itemView.findViewById(R.id.incoming_image_IV);
            incoming_emoji_image = (ImageView) itemView.findViewById(R.id.incoming_emoji_image);
            outgoing_emoji_image = (ImageView) itemView.findViewById(R.id.outgoing_emoji_image);

            outgoing_video_IV = (RoundedImageView) itemView.findViewById(R.id.outgoing_video_IV);
            outgoing_play_btn = (ImageView) itemView.findViewById(R.id.outgoing_play_btn);

            incoming_video_IV = (ImageView) itemView.findViewById(R.id.incoming_video_IV);


            mainLinearLayout = (LinearLayout) itemView.findViewById(R.id.mainLinearLayout);
            incoming_video_cab = (LinearLayout) itemView.findViewById(R.id.incoming_video_cab);

            incoming_linear_contact = (LinearLayout) itemView.findViewById(R.id.incoming_linear_contact);

            layout = (RelativeLayout) itemView.findViewById(R.id.out_tv_cab);
            parent_layout = (LinearLayout) itemView.findViewById(R.id.incoming_txt_cab);
            incoming_image = (LinearLayout) itemView.findViewById(R.id.incoming_img_cab);
            out_pdf = (LinearLayout) itemView.findViewById(R.id.out_pdf);
            incoming_pdf = (LinearLayout) itemView.findViewById(R.id.incoming_pdf);
            out_lineat_bubble = (LinearLayout) itemView.findViewById(R.id.out_lineat_bubble);
            incoming_linear_click = (LinearLayout) itemView.findViewById(R.id.incoming_linear_click);
            outgoing_linear_contact = (LinearLayout) itemView.findViewById(R.id.outgoing_linear_contact);
            out_image = (RelativeLayout) itemView.findViewById(R.id.out_image_cab);
            relativeDate = (RelativeLayout) itemView.findViewById(R.id.relativeDate);
            out_video_cab = (RelativeLayout) itemView.findViewById(R.id.out_video_cab);
            outgoing_relative_emoji = (RelativeLayout) itemView.findViewById(R.id.outgoing_relative_emoji);
            incoming_relative_emoji = (RelativeLayout) itemView.findViewById(R.id.incoming_relative_emoji);
            // CONTACT VIEW
            outgoing_contact_view = (Button) itemView.findViewById(R.id.outgoing_contact_view);
            incoming_view_image = (Button) itemView.findViewById(R.id.incoming_view_image);

            /////
            incoming_view = (View) itemView.findViewById(R.id.incoming_view);
            outgoing_view = (View) itemView.findViewById(R.id.outgoing_view);
            outgoing_location_relative = (RelativeLayout) itemView.findViewById(R.id.outgoing_location_relative);
            incoming_location_relative = (RelativeLayout) itemView.findViewById(R.id.incoming_location_relative);

            outgoing_location_address = (TextView) itemView.findViewById(R.id.outgoing_location_address);
            incoming_location_address = (TextView) itemView.findViewById(R.id.incoming_location_address);
            incoming_location_time = (TextView) itemView.findViewById(R.id.incoming_location_time);
            outgoing_location_time = (TextView) itemView.findViewById(R.id.outgoing_location_time);
            tickMarkLocation = (ImageView) itemView.findViewById(R.id.tickMarkLocation);
            outgoing_audio_relative = (RelativeLayout) itemView.findViewById(R.id.outgoing_audio_relative);
            incoming_audio_relative = (RelativeLayout) itemView.findViewById(R.id.incoming_audio_relative);
            outgoing_audio_time = (TextView) itemView.findViewById(R.id.outgoing_audio_time);
            incoming_audio_time = (TextView) itemView.findViewById(R.id.incoming_audio_time);
            tickMarkFirstOutAudio = (ImageView) itemView.findViewById(R.id.tickMarkFirstOutAudio);
            outgoing_image_view_audio_stop = (ImageView) itemView.findViewById(R.id.outgoing_image_view_audio_stop);
            outgoing_image_view_audio = (ImageView) itemView.findViewById(R.id.outgoing_image_view_audio);
            incoming_image_view_audio = (ImageView) itemView.findViewById(R.id.incoming_image_view_audio);
            incoming_image_view_audio_stop = (ImageView) itemView.findViewById(R.id.incoming_image_view_audio_stop);
            incoming_seekbar = (SeekBar) itemView.findViewById(R.id.incoming_seekbar);
            outgoing_seekbar = (SeekBar) itemView.findViewById(R.id.outgoing_seekbar);

            //TICKUI OPTIOPN FOR CHAT
            tickMarkFirstOut = (ImageView) itemView.findViewById(R.id.tickMarkFirstOut);
            tickMarkContactOut = (ImageView) itemView.findViewById(R.id.tickMarkContactOut);
            tickMarkFirstOutImage = (ImageView) itemView.findViewById(R.id.tickMarkFirstOutImage);
            tickMarkFirstOutVideo = (ImageView) itemView.findViewById(R.id.tickMarkFirstOutVideo);
            tickMarkFirstOutPDF = (ImageView) itemView.findViewById(R.id.tickMarkFirstOutPDF);
            tickMarkFirstOutEmoji = (ImageView) itemView.findViewById(R.id.tickMarkFirstOutEmoji);

        }
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        chatMessageList.clear();
        if (charText.trim().length() == 0) {
            chatMessageList.addAll(searchList);
        } else {
            for (JsonModelForChat wp : searchList) {
                if (wp.getMessage().toLowerCase(Locale.getDefault()).contains(charText) && wp.getType().equalsIgnoreCase("msg")) {
                    chatMessageList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void clearMusic() {
        try {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}