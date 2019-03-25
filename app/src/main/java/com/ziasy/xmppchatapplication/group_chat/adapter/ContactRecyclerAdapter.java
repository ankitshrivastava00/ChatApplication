package com.ziasy.xmppchatapplication.group_chat.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.ziasy.xmppchatapplication.R;
import com.ziasy.xmppchatapplication.group_chat.activity.CreateContactActivity;
import com.ziasy.xmppchatapplication.group_chat.base.BaseRecyclerAdapter;
import com.ziasy.xmppchatapplication.group_chat.interfaces.OnItemClickListener;
import com.ziasy.xmppchatapplication.group_chat.model.Contact;

import java.util.ArrayList;

/**
 * Created by intel on 04-Mar-17.
 */

public class ContactRecyclerAdapter extends BaseRecyclerAdapter<ContactRecyclerAdapter.ViewHolder, Contact> {
     //   CreateContactActivity createContactActivity;
    public ContactRecyclerAdapter(Context context, ArrayList<Contact> items, OnItemClickListener onClickListener) {
        super(context, R.layout.group_contact_item, items, onClickListener);
      //  this.createContactActivity= (CreateContactActivity) context;
    }

    class ViewHolder extends BaseRecyclerAdapter<BaseRecyclerAdapter.ViewHolder, Contact>.ViewHolder
            implements CompoundButton.OnCheckedChangeListener {
        TextView tvName, tvEmail;
        CheckBox cbSelected;
        RelativeLayout view_career,relativeChange;
        int count=0;


        public ViewHolder(View view, OnItemClickListener onClickListener) {
            super(view, onClickListener);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvEmail = (TextView) view.findViewById(R.id.tv_products);
            view_career = (RelativeLayout) view.findViewById(R.id.view_career);
            relativeChange = (RelativeLayout) view.findViewById(R.id.relativeChange);
            cbSelected = (CheckBox) view.findViewById(R.id.checkbox);
            cbSelected.setOnCheckedChangeListener(this);

        }

        @Override
        public void bindData(Contact data,int position,Context context) {
            tvName.setText(data.getName());
            tvEmail.setText(data.getUsername());
            cbSelected.setOnCheckedChangeListener(null);
            cbSelected.setChecked(data.isSelected());
            cbSelected.setOnCheckedChangeListener(this);

            if (position % 2 == 0) {

                view_career.setBackgroundColor(ContextCompat.getColor(context, R.color.orange));
            } else {
                view_career.setBackgroundColor(ContextCompat.getColor(context, R.color.ud_blue));
            }
            if (data.isSelected()) {
              //  count++;
            //    createContactActivity.setCounterFor(context,count);
                relativeChange.setBackgroundColor(Color.parseColor("#84caff"));
            } else {
                //count--;
                relativeChange.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }

        }

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(view, getLayoutPosition());
            }
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (onItemClickListener != null) {
                ((OnItemClickListener) onItemClickListener).onCheckedChange(buttonView, isChecked, getLayoutPosition());

            }
        }
    }


    @Override
    protected ViewHolder onCreateViewHolder(View view, int viewType) {
        return new ViewHolder(view, (OnItemClickListener) onItemClickListener);
    }

    public interface OnItemClickListener extends com.ziasy.xmppchatapplication.group_chat.interfaces.OnItemClickListener {
        void onCheckedChange(CompoundButton buttonView, boolean isChecked, int position);
    }

}
