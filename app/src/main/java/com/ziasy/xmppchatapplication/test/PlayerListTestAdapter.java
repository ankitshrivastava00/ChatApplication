package com.ziasy.xmppchatapplication.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ziasy.xmppchatapplication.R;

import java.util.ArrayList;

public class PlayerListTestAdapter extends ArrayAdapter<Integer> {
    private ArrayList<Integer>arrayList;
    private Context context;
   private ViewHolder viewHolder;
    public PlayerListTestAdapter(Context context, int Resource, ArrayList<Integer>arrayList) {
        super(context,Resource,arrayList);
        this.context = context;
        this.arrayList = arrayList;

    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Integer getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.outgoing_audio, null, false);
            viewHolder.wallIcon = (ImageView) convertView.findViewById(R.id.outgoing_image_view_audio);
            viewHolder.wallIcon_stop = (ImageView) convertView.findViewById(R.id.outgoing_image_view_audio_stop);
            viewHolder.txtData=(TextView)convertView.findViewById(R.id.outgoing_audio_time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        viewHolder.txtData.setText(arrayList.get(position));
        viewHolder.wallIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.wallIcon_stop.setVisibility(View.VISIBLE);
                viewHolder.wallIcon.setVisibility(View.GONE);
            }
        });
        viewHolder.wallIcon_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.wallIcon_stop.setVisibility(View.GONE);
                viewHolder.wallIcon.setVisibility(View.VISIBLE);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private ImageView wallIcon,wallIcon_stop;
        private TextView txtData;
    }
}
