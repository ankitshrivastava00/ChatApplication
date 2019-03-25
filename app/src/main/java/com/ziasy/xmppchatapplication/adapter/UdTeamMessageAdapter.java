package com.ziasy.xmppchatapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ziasy.xmppchatapplication.R;
import com.ziasy.xmppchatapplication.model.JsonModelForChat;
import com.ziasy.xmppchatapplication.model.UDTeamMessageModel;
import com.ziasy.xmppchatapplication.multiimage.models.Image;

import java.util.List;

public class UdTeamMessageAdapter extends ArrayAdapter<UDTeamMessageModel> {
    private Context context;
    private List<UDTeamMessageModel> list;

    public UdTeamMessageAdapter(Context context, int Resource, List<UDTeamMessageModel> list) {
        super(context, Resource, list);
        this.context = context;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public UDTeamMessageModel getItem(int position) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.udteam_message_item, null, false);
            viewHolder.videoIcon = (ImageView) convertView.findViewById(R.id.videoIcon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context)
                .load("https://www.extremetech.com/wp-content/uploads/2015/10/Galaxy-640x360.jpg")
                .into(viewHolder.videoIcon);
        return convertView;
    }

    private class ViewHolder {
        private ImageView videoIcon;
    }
}
