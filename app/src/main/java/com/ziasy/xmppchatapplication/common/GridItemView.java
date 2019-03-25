package com.ziasy.xmppchatapplication.common;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ziasy.xmppchatapplication.R;

public class GridItemView extends FrameLayout {

    private ImageView emoji_image;

    public GridItemView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.emoji_layout, this);
        emoji_image = (ImageView) getRootView().findViewById(R.id.emoji_image);
    }

    public void display(int text, boolean isSelected) {
        emoji_image.setImageResource(text);
        display(isSelected);
    }

    public void display(boolean isSelected) {
        emoji_image.setBackgroundResource(isSelected ? R.drawable.border_mcq : R.drawable.round_border_gray);
    }
}