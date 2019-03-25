package com.ziasy.xmppchatapplication.attachment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ziasy.xmppchatapplication.R;
import com.ziasy.xmppchatapplication.common.Confiq;

import java.io.File;
import java.util.ArrayList;


public class PDFAdapter extends ArrayAdapter<File> {


    Context context;
    ViewHolder viewHolder;
    ArrayList<File> al_pdf;

    public PDFAdapter(Context context, ArrayList<File> al_pdf) {
        super(context, R.layout.adapter_pdf, al_pdf);
        this.context = context;
        this.al_pdf = al_pdf;

    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        if (al_pdf.size() > 0) {
            return al_pdf.size();
        } else {
            return 1;
        }
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {


        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.adapter_pdf, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tv_filename = (TextView) view.findViewById(R.id.tv_name);
            viewHolder.iv_image = (ImageView) view.findViewById(R.id.iv_image);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();

        }
        viewHolder.tv_filename.setText(al_pdf.get(position).getName());
        if (Confiq.getExtenstion(al_pdf.get(position).getName()).equalsIgnoreCase(".pdf")){
            viewHolder.iv_image.setImageResource(R.drawable.pdf);
        }else if (Confiq.getExtenstion(al_pdf.get(position).getName()).equalsIgnoreCase(".docx"))
        {
            viewHolder.iv_image.setImageResource(R.drawable.word);
        }else if (Confiq.getExtenstion(al_pdf.get(position).getName()).equalsIgnoreCase(".xlsx")){
            viewHolder.iv_image.setImageResource(R.drawable.excel);
        }
        return view;

    }

    public class ViewHolder {

        TextView tv_filename;
        ImageView iv_image;


    }

}
