package com.ziasy.xmppchatapplication;

/**
 * Created by Khushvinders on 15-Nov-16.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.ziasy.xmppchatapplication.database.EmployeeModel;
import com.ziasy.xmppchatapplication.localDB.LocalDBHelper;
import com.ziasy.xmppchatapplication.single_chat.activity.SingleChatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import github.ankushsachdeva.emojicon.EmojiconTextView;

public class UserListAdapter extends BaseAdapter {
    private  ViewHolder viewHolder;
    private static LayoutInflater inflater = null;
    String[] userList;
    private List<User> list;
    private ArrayList<User> searchList;
    private ImageLoader imageLoader;
    private Context activity;
    private LocalDBHelper localDBHelper;

    public UserListAdapter(Context activity, List<User> list) {
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // imageLoader = ((TextChatApplication)activity.getApplication()).getImageLoader();
        this.list = list;
        this.searchList = new ArrayList<>();
        this.searchList.addAll(list);
        this.activity = activity;
        localDBHelper= new LocalDBHelper(activity);
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
         vi = inflater.inflate(R.layout.adduser_swipe_row, null);
        viewHolder = new ViewHolder();
        viewHolder.userName = (TextView) vi.findViewById(R.id.tv_name);
        viewHolder.userLayout = (LinearLayout) vi.findViewById(R.id.mainLayout);
        viewHolder.ll_view_profile = (LinearLayout) vi.findViewById(R.id.ll_view_profile);
        viewHolder.view_add_new_user = (LinearLayout) vi.findViewById(R.id.view_add_new_user);
        viewHolder.view_career = (RelativeLayout) vi.findViewById(R.id.view_career);
        viewHolder.swipeLayout = (SwipeLayout) vi.findViewById(R.id.sample1);
        vi.setTag(viewHolder);

        } else{
            viewHolder = (ViewHolder) vi.getTag();
        }
        viewHolder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.bottom_wrapper));
            }
        });

        if (position % 2 == 0) {

            viewHolder.view_career.setBackgroundColor(ContextCompat.getColor(activity, R.color.orange));
        } else {
            viewHolder.view_career.setBackgroundColor(ContextCompat.getColor(activity, R.color.ud_blue));
        }
        viewHolder.ll_view_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(activity,ProfileActivity.class);
                i.putExtra("name",list.get(position).getName());
                i.putExtra("rid",list.get(position).getId());
                i.putExtra("chattype", "indivisual");
                i.putExtra("did", list.get(position).getDid());
                i.putStringArrayListExtra("forwardString",new ArrayList<>());
                i.putExtra("type", "");
                activity.startActivity(i);
                ((Activity)activity).finish();
            }
        });

        viewHolder.view_add_new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(activity,ChatActivity.class);
              //  Intent i=new Intent(activity,SingleChatActivity.class);
                i.putExtra("name",list.get(position).getName());
                i.putExtra("rid",list.get(position).getId());
                i.putExtra("chattype", "indivisual");
                i.putExtra("did", list.get(position).getDid());
                i.putStringArrayListExtra("forwardString",new ArrayList<>());
                i.putExtra("type", "");
                activity.startActivity(i);
                ((Activity)activity).finish();
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
    class ViewHolder {
        NetworkImageView status;
        TextView userName;
       private LinearLayout userLayout,ll_view_profile,view_add_new_user;
        private RelativeLayout view_career;
        private SwipeLayout swipeLayout;
    }
}