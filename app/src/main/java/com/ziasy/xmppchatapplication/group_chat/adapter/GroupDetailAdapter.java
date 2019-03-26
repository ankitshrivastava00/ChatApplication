package com.ziasy.xmppchatapplication.group_chat.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ziasy.xmppchatapplication.ProfileActivity;
import com.ziasy.xmppchatapplication.R;
import com.ziasy.xmppchatapplication.common.SessionManagement;
import com.ziasy.xmppchatapplication.group_chat.activity.GroupProfileActivity;
import com.ziasy.xmppchatapplication.group_chat.model.GroupUserModel;
import java.util.ArrayList;

public class GroupDetailAdapter extends ArrayAdapter<GroupUserModel> {
    private Context context;

    private ArrayList<GroupUserModel> list;
    private SessionManagement sd;
    //  public List<Integer> selectedPositions;
    GroupProfileActivity activity;
    private String admin;

    public GroupDetailAdapter(Context context, int Resource, ArrayList<GroupUserModel> list,String admin) {
        super(context, Resource, list);
        this.context = context;
        this.activity = (GroupProfileActivity) context;
        this.list = list;
        this.admin = admin;
        this.sd=new SessionManagement(context);
        //  selectedPositions = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public GroupUserModel getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adduser_swipe_row, null, false);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.ll_view_profile = (LinearLayout) convertView.findViewById(R.id.ll_view_profile);
            viewHolder.view_add_new_user = (LinearLayout) convertView.findViewById(R.id.view_add_new_user);
            viewHolder.deleteUser = (LinearLayout) convertView.findViewById(R.id.deleteUser);
            viewHolder.viewUserDetail = (LinearLayout) convertView.findViewById(R.id.viewUserDetail);
            viewHolder.deleteUser.setVisibility(View.VISIBLE);
            viewHolder.view_add_new_user.setVisibility(View.GONE);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (sd.getUserName().equalsIgnoreCase(admin)){
            viewHolder.ll_view_profile.setVisibility(View.VISIBLE);
            viewHolder.deleteUser.setVisibility(View.VISIBLE);
            viewHolder.viewUserDetail.setVisibility(View.GONE);
        }else {
            viewHolder.viewUserDetail.setVisibility(View.VISIBLE);
            viewHolder.deleteUser.setVisibility(View.GONE);
            viewHolder.ll_view_profile.setVisibility(View.GONE);
        }
        viewHolder.tv_name.setText(list.get(position).getName());
        viewHolder.deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.removeGroup(list.get(position).getNum(),list.get(position).getName());
            }
        });
        viewHolder.ll_view_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(activity, ProfileActivity.class);
                i.putExtra("name",list.get(position).getName());
                i.putExtra("rid",list.get(position).getId());
                i.putExtra("chattype", "indivisual");
                i.putExtra("did", list.get(position).getDevice());
                i.putStringArrayListExtra("forwardString",new ArrayList<>());
                i.putExtra("type", "");
                activity.startActivity(i);
                ((Activity)activity).finish();
            }
        });
        viewHolder.viewUserDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(activity, ProfileActivity.class);
                i.putExtra("name",list.get(position).getName());
                i.putExtra("rid",list.get(position).getId());
                i.putExtra("chattype", "indivisual");
                i.putExtra("did", list.get(position).getDevice());
                i.putStringArrayListExtra("forwardString",new ArrayList<>());
                i.putExtra("type", "");
                activity.startActivity(i);
                ((Activity)activity).finish();
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private LinearLayout ll_view_profile,viewUserDetail, view_add_new_user, deleteUser;
        private TextView tv_name;
    }
}