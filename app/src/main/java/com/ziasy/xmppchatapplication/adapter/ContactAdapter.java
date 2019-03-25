package com.ziasy.xmppchatapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ziasy.xmppchatapplication.R;
import com.ziasy.xmppchatapplication.model.ContactModel;

import java.util.List;

public class ContactAdapter extends ArrayAdapter<ContactModel> {
    private Context context;
    private ViewHolder viewHolder;
    private List<ContactModel> list;
    private static LayoutInflater inflater = null;

    public ContactAdapter(Context context, int Resource, List<ContactModel> list) {
        super(context, Resource, list);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ContactModel getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null) {
            vi = inflater.inflate(R.layout.contact_item, null);
            viewHolder = new ViewHolder();
            viewHolder.userName = (TextView) vi.findViewById(R.id.textName);
            viewHolder.userMobile = (TextView) vi.findViewById(R.id.textMobile);
            viewHolder.userLayout = (LinearLayout) vi.findViewById(R.id.linear_layout);
            vi.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) vi.getTag();
        }

      /*  viewHolder.userLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ChatActivity.class);
               *//* i.putExtra("name", list.get(position).getUsername());
                i.putExtra("rid", list.get(position).getUsername());
                i.putExtra("forwardString", forwardString);*//*
                //context.startActivity(i);
                ((Activity) context).finish();
            }
        });
*/
           viewHolder.userName.setText(list.get(position).getName());
           viewHolder.userMobile.setText(list.get(position).getMobile());
//        viewHolder.status.setImageUrl("http://"+activity.getString(R.string.server)+":9090/plugins/presence/status?jid="+userList.get(position).getUsername()+"@domain_name", imageLoader);
        return vi;
    }


    private class ViewHolder {

        TextView userName,userMobile;
        private LinearLayout userLayout;
    }
}
