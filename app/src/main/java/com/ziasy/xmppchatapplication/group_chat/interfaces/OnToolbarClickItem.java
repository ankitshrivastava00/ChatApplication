package com.ziasy.xmppchatapplication.group_chat.interfaces;

import android.widget.AdapterView;

public interface OnToolbarClickItem extends AdapterView.OnItemClickListener {
    void attachment(boolean a);
    void setting(boolean s);
}
