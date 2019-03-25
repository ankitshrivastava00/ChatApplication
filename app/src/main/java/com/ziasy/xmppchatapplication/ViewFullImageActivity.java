package com.ziasy.xmppchatapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.ziasy.xmppchatapplication.activity.ForwardActivity;
import com.ziasy.xmppchatapplication.common.Confiq;
import com.ziasy.xmppchatapplication.common.Permission;
import com.ziasy.xmppchatapplication.util.Utils;

import java.util.ArrayList;

public class ViewFullImageActivity extends AppCompatActivity {
    private PhotoView photoView;
    private ArrayList<String> strImagePath = null, type = null;
    private String strFileName = null, strFilePath;
    private ImageView imageBack, setting;
    private TextView nameTv;
    private LinearLayout deleteLinear, saveLinear, forwardLinear, scanLinear, ogAppbar, linear_bottom_layout;
    private boolean isImageFitToScreen = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_fullimage);
        photoView = (PhotoView) findViewById(R.id.imageView);
        imageBack = (ImageView) findViewById(R.id.imageBack);
        setting = (ImageView) findViewById(R.id.setting);
        nameTv = (TextView) findViewById(R.id.nameTv);
        linear_bottom_layout = (LinearLayout) findViewById(R.id.linear_bottom_layout);
        ogAppbar = (LinearLayout) findViewById(R.id.ogAppbar);
        forwardLinear = (LinearLayout) findViewById(R.id.forwardLinear);
        saveLinear = (LinearLayout) findViewById(R.id.saveLinear);
        deleteLinear = (LinearLayout) findViewById(R.id.deleteLinear);
        scanLinear = (LinearLayout) findViewById(R.id.scanLinear);
        strImagePath = new ArrayList<>();
        type = new ArrayList<>();
        type.add("img");
        strImagePath = getIntent().getStringArrayListExtra("image");

        // strImagePath = "https://akm-img-a-in.tosshub.com/indiatoday/styles/photo_slider_753x543/public/images/photogallery/201811/bride-kareena-mos2_IT_1542624129651.PNG?Ga2jN9sLXAWrJwNcOgMpEBmtRU2a45ov";
        strFilePath=strImagePath.get(0);
        strFileName = strFilePath.replace(Confiq.RETURN_IMAGE_URL, "");


        saveLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    boolean storagepermission = Permission.checkReadExternalStoragePermission(ViewFullImageActivity.this);
                    boolean externalStroage = Permission.permissionForExternal(ViewFullImageActivity.this);
                    if (storagepermission) {
                        if (externalStroage) {
                            // downloadFile(position);
                            Utils.downloadFile(ViewFullImageActivity.this, strImagePath.get(0), strFileName);
                            Toast.makeText(ViewFullImageActivity.this, "Image Saved", Toast.LENGTH_SHORT).show();

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(ViewFullImageActivity.this, "No space available", Toast.LENGTH_SHORT).show();
                }
            }
        });
        deleteLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        forwardLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewFullImageActivity.this, ForwardActivity.class);
                i.putStringArrayListExtra("forwardString", strImagePath);
                i.putStringArrayListExtra("type", type);
                startActivity(i);

            }
        });
        scanLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Glide.with(ViewFullImageActivity.this)
                .load(strImagePath.get(0))
                .into(photoView);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isImageFitToScreen) {
                    isImageFitToScreen = false;
                    ogAppbar.setVisibility(View.GONE);
                    linear_bottom_layout.setVisibility(View.GONE);
                } else {
                    isImageFitToScreen = true;
                    ogAppbar.setVisibility(View.VISIBLE);
                    linear_bottom_layout.setVisibility(View.VISIBLE);
                }
            }
        });
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        nameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

}
