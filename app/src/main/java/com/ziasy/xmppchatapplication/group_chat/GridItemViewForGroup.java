package com.ziasy.xmppchatapplication.group_chat;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ziasy.xmppchatapplication.R;
import com.ziasy.xmppchatapplication.User;

import java.util.ArrayList;

public class GridItemViewForGroup extends FrameLayout {

    TextView tvName, tvEmail;
    CheckBox cbSelected;
    RelativeLayout view_career,relativeChange;
    public GridItemViewForGroup(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.group_contact_item, this);
        tvName = (TextView) getRootView().findViewById(R.id.tv_name);
        tvEmail = (TextView) getRootView().findViewById(R.id.tv_products);
        view_career = (RelativeLayout) getRootView().findViewById(R.id.view_career);
        relativeChange = (RelativeLayout) getRootView().findViewById(R.id.relativeChange);
    }

    public void display(ArrayList<User>list, boolean isSelected, int position) {
        tvName.setText(list.get(position).getName());
        tvEmail.setText(list.get(position).getUsername());
        display(isSelected);
    }

    public void display(boolean isSelected) {
        relativeChange.setBackgroundResource(isSelected ? R.color.white : R.color.career_text_color);
    }
}