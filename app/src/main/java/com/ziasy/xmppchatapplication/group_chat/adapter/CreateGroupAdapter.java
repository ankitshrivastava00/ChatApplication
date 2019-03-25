package com.ziasy.xmppchatapplication.group_chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.ziasy.xmppchatapplication.User;
import com.ziasy.xmppchatapplication.common.GridItemView;
import com.ziasy.xmppchatapplication.group_chat.GridItemViewForGroup;

import java.util.ArrayList;
import java.util.List;

public class CreateGroupAdapter extends ArrayAdapter<User> {
    private Context context;
    private ViewHolder viewHolder;
    private ArrayList<User> list;
    private static LayoutInflater inflater = null;
    public List<Integer> selectedPositions;

    public CreateGroupAdapter(Context context, int Resource, ArrayList<User> list) {
        super(context, Resource, list);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.list = list;
        selectedPositions=  new ArrayList<>();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public User getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GridItemViewForGroup customView = (convertView == null) ? new GridItemViewForGroup(context) : (GridItemViewForGroup) convertView;
        customView.display(list, selectedPositions.contains(position),position);


        return customView;
    }
    private class ViewHolder {
        private ImageView emoji_image;
    }
}