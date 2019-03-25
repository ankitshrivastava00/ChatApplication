package com.ziasy.xmppchatapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ziasy.xmppchatapplication.adapter.UdTeamMessageAdapter;
import com.ziasy.xmppchatapplication.model.UDTeamMessageModel;

import java.util.ArrayList;
import java.util.List;

public class UDTeamMessageActivity extends AppCompatActivity {
    private ListView userList;
    private UdTeamMessageAdapter adapter;
    private ImageView iv_back;
    private List<UDTeamMessageModel> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.udmessage_list_layout);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        userList = (ListView) findViewById(R.id.userList);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new UDTeamMessageModel());
        }
        adapter = new UdTeamMessageAdapter(UDTeamMessageActivity.this, R.layout.udteam_message_item, list);
        userList.setAdapter(adapter);

    }
}
