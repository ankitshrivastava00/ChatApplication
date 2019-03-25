package com.ziasy.xmppchatapplication.group_chat.adapter;

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
import com.ziasy.xmppchatapplication.ChatActivity;
import com.ziasy.xmppchatapplication.ChatUserListActivity;
import com.ziasy.xmppchatapplication.R;
import com.ziasy.xmppchatapplication.User;

import java.util.ArrayList;
import java.util.Locale;

public class AddUserGroupAdapter extends BaseAdapter {
    private ViewHolder viewHolder;
    private static LayoutInflater inflater = null;
    private String[] userList;
    private ArrayList<User> list;
    private ArrayList<User> searchList;
    private ImageLoader imageLoader;
    private Context activity;



    public AddUserGroupAdapter(Context activity, ArrayList<User> list) {
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // imageLoader = ((TextChatApplication)activity.getApplication()).getImageLoader();
        this.list = list;
        this.searchList = new ArrayList<>();
        this.searchList.addAll(list);
        this.activity = activity;

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
            for (User wp : searchList)
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
        private LinearLayout userLayout;
        private RelativeLayout view_career;
        // private SwipeLayout swipeLayout;
    }
}