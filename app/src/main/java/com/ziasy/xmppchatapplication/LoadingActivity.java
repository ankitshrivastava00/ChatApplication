package com.ziasy.xmppchatapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ziasy.xmppchatapplication.common.SessionManagement;

public class LoadingActivity extends AppCompatActivity {
    private SessionManagement sd;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_activity);
        sd=new SessionManagement(LoadingActivity.this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(LoadingActivity.this,ChatUserListActivity.class);
                startActivity(i);
                sd.setBackupStatus("true");
                finish();
            }
        },5000);

    }
}
