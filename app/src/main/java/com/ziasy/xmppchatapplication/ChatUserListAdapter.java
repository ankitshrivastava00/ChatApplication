package com.ziasy.xmppchatapplication;

/**
 * Created by Khushvinders on 15-Nov-16.
 */

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
//import com.squareup.picasso.Picasso;
import com.ziasy.xmppchatapplication.common.Permission;
import com.ziasy.xmppchatapplication.group_chat.activity.GroupChatActivity;
import com.ziasy.xmppchatapplication.group_chat.activity.GroupProfileActivity;
import com.ziasy.xmppchatapplication.multiimage.models.Image;
import com.ziasy.xmppchatapplication.single_chat.activity.SingleChatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import github.ankushsachdeva.emojicon.EmojiconTextView;

public class ChatUserListAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    String[] userList;
    private List<ChatUserList> list;
    private List<ChatUserList> searchList;
    private ImageLoader imageLoader;
    private Context activity;
    private ChatUserListActivity chatUserListActivity;
    private Intent i;

    public ChatUserListAdapter(Context activity, int Resource, List<ChatUserList> list) {

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = list;
        this.searchList = new ArrayList<>();
        this.searchList.addAll(list);
        this.activity = activity;
        this.chatUserListActivity = (ChatUserListActivity) activity;
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
        final ViewHolder viewHolder;
        View vi = convertView;
        if (convertView == null) {
            vi = inflater.inflate(R.layout.showuser_swipe_row, null);
            viewHolder = new ViewHolder();
            viewHolder.userName = (TextView) vi.findViewById(R.id.tv_name);
            // viewHolder.dateText = (TextView) vi.findViewById(R.id.chatDate);
            viewHolder.msgtText = (TextView) vi.findViewById(R.id.tv_products);
            viewHolder.view_career = (RelativeLayout) vi.findViewById(R.id.view_career);
            viewHolder.iv_profilePic = (CircleImageView) vi.findViewById(R.id.iv_profilePic);
            //   viewHolder.statusIV = (ImageView) vi.findViewById(R.id.statusIV);
            viewHolder.linearIndivitual = (LinearLayout) vi.findViewById(R.id.linearIndivitual);
            viewHolder.linear_group = (LinearLayout) vi.findViewById(R.id.linear_group);
            viewHolder.userLayout = (LinearLayout) vi.findViewById(R.id.mainLayout);
            viewHolder.view_call = (LinearLayout) vi.findViewById(R.id.view_call);
            viewHolder.ll_view_profile = (LinearLayout) vi.findViewById(R.id.ll_view_profile);
            viewHolder.view_profile_group = (LinearLayout) vi.findViewById(R.id.view_profile_group);
            viewHolder.view_delete = (LinearLayout) vi.findViewById(R.id.view_delete);
            viewHolder.view_block = (LinearLayout) vi.findViewById(R.id.view_block);
            viewHolder.view_more = (LinearLayout) vi.findViewById(R.id.view_more);
            viewHolder.view_add_new_user = (LinearLayout) vi.findViewById(R.id.view_add_new_user);
            viewHolder.swipeLayout = (SwipeLayout) vi.findViewById(R.id.sample1);
            //  viewHolder.statusIV.setVisibility(View.VISIBLE);
            viewHolder.msgtText.setVisibility(View.VISIBLE);
            //viewHolder.dateText.setVisibility(View.VISIBLE);
            vi.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) vi.getTag();
        }
        if (list.get(position).getChattype().equalsIgnoreCase("group")) {
            viewHolder.linear_group.setVisibility(View.VISIBLE);
            viewHolder.linearIndivitual.setVisibility(View.GONE);


        } else {
            viewHolder.linear_group.setVisibility(View.GONE);
            viewHolder.linearIndivitual.setVisibility(View.VISIBLE);

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
        if (position % 2 == 0) {
            viewHolder.view_career.setBackgroundColor(ContextCompat.getColor(activity, R.color.orange));
        } else {
            viewHolder.view_career.setBackgroundColor(ContextCompat.getColor(activity, R.color.ud_blue));
        }

        viewHolder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.bottom_wrapper));
            }
        });

        viewHolder.view_block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "Block User", Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.view_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               chatUserListActivity.deleteUser(list.get(position).getId(),position);
            }
        });
        viewHolder.ll_view_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ProfileActivity.class);
                intent.putExtra("name", list.get(position).getName());
                intent.putExtra("rid", list.get(position).getId());
                intent.putExtra("chattype", "indivisual");
                intent.putExtra("did", list.get(position).getDid());
                intent.putStringArrayListExtra("forwardString", new ArrayList<>());
                intent.putExtra("type", "");
                activity.startActivity(intent);
            }
        });
        viewHolder.view_profile_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, GroupProfileActivity.class);
                intent.putExtra("name", list.get(position).getName());
                intent.putExtra("rid", list.get(position).getId());
                intent.putExtra("image", list.get(position).getImageUrl());
                intent.putExtra("description", list.get(position).getDescription());
                intent.putExtra("chattype", "group");
                intent.putExtra("did", list.get(position).getDid());
                intent.putStringArrayListExtra("forwardString", new ArrayList<>());
                intent.putExtra("type", "");
                activity.startActivity(intent);
            }
        });
        viewHolder.view_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean callPermission = Permission.checkPermisionForCALL_PHONE(activity);
                if (callPermission) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + list.get(position).getName()));
                    if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    activity.startActivity(callIntent);
                }
            }
        });

        viewHolder.view_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Dialog dialog = new Dialog(activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.more_layout);
                dialog.show();
                dialog.setCancelable(false);
                TextView textCancel = (TextView) dialog.findViewById(R.id.textCancel);
                textCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

            }
        });


        viewHolder.view_add_new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatUserListActivity.leftGroup(list.get(position).getId());
            }
        });

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
                i.putExtra("name", list.get(position).getName());
                i.putExtra("rid", list.get(position).getId());
                i.putExtra("chattype", list.get(position).getChattype());
                i.putExtra("did", list.get(position).getDid());
                i.putStringArrayListExtra("forwardString", new ArrayList<>());
                activity.startActivity(i);
            }
        });

   /*     if (list.get(position).getFlag().equalsIgnoreCase("true")){

        }else {

        }*/
        viewHolder.userName.setText(list.get(position).getName());
        //   viewHolder.dateText.setText(list.get(position).getDatetime());
        if (list.get(position).getType().equalsIgnoreCase("msg")) {
            viewHolder.msgtText.setText(list.get(position).getLastMessage());
        } else if (list.get(position).getType().equalsIgnoreCase("video")) {
            viewHolder.msgtText.setText("VIDEO");
        } else if (list.get(position).getType().equalsIgnoreCase("img")) {
            viewHolder.msgtText.setText("IMAGE");
        } else if (list.get(position).getType().equalsIgnoreCase("location")) {
            viewHolder.msgtText.setText("LOCATION");
        } else if (list.get(position).getType().equalsIgnoreCase("emoji")) {
            viewHolder.msgtText.setText("EMOJI");
        } else if (list.get(position).getType().equalsIgnoreCase("audio")) {
            viewHolder.msgtText.setText("AUDIO");
        } else if (list.get(position).getType().equalsIgnoreCase("share")) {
            viewHolder.msgtText.setText("CONTACT SHARE");
        } else if (list.get(position).getType().equalsIgnoreCase("msg")) {
            viewHolder.msgtText.setText(list.get(position).getLastMessage());
        } else if (list.get(position).getType().equalsIgnoreCase("pdf")) {
            viewHolder.msgtText.setText("DOCUMENT");
        } else {
            viewHolder.msgtText.setText("");
        }

//        viewHolder.status.setImageUrl("http://"+activity.getString(R.string.server)+":9090/plugins/presence/status?jid="+userList.get(position).getUsername()+"@domain_name", imageLoader);
        return vi;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        list.clear();
        if (charText.length() == 0) {
            list.addAll(searchList);
        } else {
            for (ChatUserList wp : searchList) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    list.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    class ViewHolder {
        //  NetworkImageView status;
        private RelativeLayout view_career;
        private TextView userName;
        //TextView dateText;
        // private ImageView statusIV;
        private CircleImageView iv_profilePic;
        private TextView msgtText;
        private LinearLayout linear_group, linearIndivitual, userLayout, ll_view_profile, view_call, view_delete, view_block, view_more, view_add_new_user, view_profile_group;
        private SwipeLayout swipeLayout;

    }
}