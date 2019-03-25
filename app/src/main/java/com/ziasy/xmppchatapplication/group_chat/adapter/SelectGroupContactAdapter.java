package com.ziasy.xmppchatapplication.group_chat.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
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
import com.ziasy.xmppchatapplication.ChatActivity;
import com.ziasy.xmppchatapplication.ProfileActivity;
import com.ziasy.xmppchatapplication.R;
import com.ziasy.xmppchatapplication.User;
import com.ziasy.xmppchatapplication.VideoAvtivity;
import com.ziasy.xmppchatapplication.activity.FullScreenViewActivity;
import com.ziasy.xmppchatapplication.common.Confiq;
import com.ziasy.xmppchatapplication.common.OnBottomRechedListener;
import com.ziasy.xmppchatapplication.common.PdfViewr;
import com.ziasy.xmppchatapplication.common.SessionManagement;
import com.ziasy.xmppchatapplication.mapbox.MapBoxActivity;
import com.ziasy.xmppchatapplication.model.JsonModelForChat;

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

public class SelectGroupContactAdapter extends RecyclerView.Adapter<SelectGroupContactAdapter.ViewHolder> {

    OnBottomRechedListener onBottomReachedListener;
    public ArrayList<User> selected_usersList;
    public ArrayList<User> chatMessageList;
    public ArrayList<User> searchList;
    private SessionManagement sd;
    private Activity context;


    public SelectGroupContactAdapter(Activity activity, int resource, ArrayList<User> list, ArrayList<User> selected_usersList) {

        this.chatMessageList = list;
        this.searchList = new ArrayList<>();
        this.searchList.addAll(chatMessageList);
        this.selected_usersList = selected_usersList;
        this.context = activity;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_contact_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final User message = (User) chatMessageList.get(position);


        sd = new SessionManagement(context);
        // Log.e("SELLELE",selected_usersList+"");
        if (selected_usersList.contains(chatMessageList.get(position))) {
            viewHolder.relativeChange.setBackgroundColor(context.getResources().getColor(R.color.career_text_color));
        } else {
            viewHolder.relativeChange.setBackgroundColor(context.getResources().getColor(android.R.color.white));
        }        // if message is mine then align to right
        viewHolder.tv_name.setText(message.getName());

    }


    @Override
    public int getItemCount() {
        return chatMessageList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_name;
        private RelativeLayout relativeChange;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            relativeChange = (RelativeLayout) itemView.findViewById(R.id.relativeChange);

        }
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        chatMessageList.clear();
        if (charText.length() == 0) {
            chatMessageList.addAll(searchList);
        } else {
            for (User wp : searchList) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    chatMessageList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}