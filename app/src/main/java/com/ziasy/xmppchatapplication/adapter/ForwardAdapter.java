package com.ziasy.xmppchatapplication.adapter;

/**
 * Created by Khushvinders on 15-Nov-16.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;

import com.ziasy.xmppchatapplication.ChatActivity;
import com.ziasy.xmppchatapplication.ChatUserList;
import com.ziasy.xmppchatapplication.ProfileActivity;
import com.ziasy.xmppchatapplication.R;
import com.ziasy.xmppchatapplication.User;
import com.ziasy.xmppchatapplication.group_chat.activity.GroupChatActivity;
import com.ziasy.xmppchatapplication.single_chat.activity.SingleChatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ForwardAdapter extends BaseAdapter {
    private ViewHolder viewHolder;
    private static LayoutInflater inflater = null;
    private String[] userList;
    private ArrayList<ChatUserList> list;
    private ArrayList<ChatUserList> searchList;
    private ImageLoader imageLoader;
    private Context activity;
    private Intent i ;
    private ArrayList<String> forwardString, typeString;


    public ForwardAdapter(Context activity, ArrayList<ChatUserList> list, ArrayList<String> forwardString, ArrayList<String> typeString) {
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // imageLoader = ((TextChatApplication)activity.getApplication()).getImageLoader();
        this.list = list;
        this.searchList = new ArrayList<>();
        this.searchList.addAll(list);
        this.activity = activity;
        this.forwardString = forwardString;
        this.typeString = typeString;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null) {
            vi = inflater.inflate(R.layout.ud_user_list_row, null);
            viewHolder = new ViewHolder();
            viewHolder.userName = (TextView) vi.findViewById(R.id.tv_name);
            viewHolder.userLayout = (LinearLayout) vi.findViewById(R.id.mainLayout);
            viewHolder.iv_profilePic = (CircleImageView) vi.findViewById(R.id.iv_profilePic);

            viewHolder.view_career = (RelativeLayout) vi.findViewById(R.id.view_career);
            vi.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) vi.getTag();
        }


        if (position % 2 == 0) {

            viewHolder.view_career.setBackgroundColor(ContextCompat.getColor(activity, R.color.orange));
        } else {
            viewHolder.view_career.setBackgroundColor(ContextCompat.getColor(activity, R.color.ud_blue));
        }

        if (list.get(position).getChattype().equalsIgnoreCase("group")) {
            if (list.get(position).getImageUrl().equalsIgnoreCase("No Image Found")){
                viewHolder.iv_profilePic.setImageResource(R.drawable.team);
            }else {
                Glide.with(activity).load(list.get(position).getImageUrl()).into(viewHolder.iv_profilePic);
            }
        } else if (list.get(position).getChattype().equalsIgnoreCase("indivisual")) {
            viewHolder.iv_profilePic.setImageResource(R.drawable.user_placeholder);
        }
        viewHolder.userLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (list.get(position).getChattype().equalsIgnoreCase("group")) {
                    i = new Intent(activity, GroupChatActivity.class);
                    i.putExtra("image", list.get(position).getImageUrl());
                    i.putExtra("description", list.get(position).getDescription());
                    i.putExtra("admin", list.get(position).getAdmin());

                } else if (list.get(position).getChattype().equalsIgnoreCase("indivisual")) {
                    //i = new Intent(activity, SingleChatActivity.class);
                    i = new Intent(activity, ChatActivity.class);
                }

             //   Intent i = new Intent(activity, SingleChatActivity.class);
                i.putExtra("name", list.get(position).getName());
                i.putExtra("rid", list.get(position).getId());
                i.putStringArrayListExtra("forwardString", forwardString);
                i.putExtra("chattype", "indivisual");
                i.putExtra("did", list.get(position).getDid());
                i.putStringArrayListExtra("type", typeString);
                ((Activity) activity).setResult(Activity.RESULT_OK,i);
                activity.startActivity(i);
                 ((Activity) activity).finish();


              //  ActivityCompat.finishAffinity((Activity) activity);
            }
        });


        viewHolder.userName.setText(list.get(position).getName());
//        viewHolder.status.setImageUrl("http://"+activity.getString(R.string.server)+":9090/plugins/presence/status?jid="+userList.get(position).getUsername()+"@domain_name", imageLoader);
        return vi;
    }
    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        list.clear();
        if (charText.length() == 0) {
            list.addAll(searchList);
        }
        else
        {
            for (ChatUserList wp : searchList)
            {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    list.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    private class ViewHolder {
        NetworkImageView status;
        TextView userName;
        private CircleImageView iv_profilePic;
        private LinearLayout userLayout;
        private RelativeLayout view_career;
        // private SwipeLayout swipeLayout;
    }
}