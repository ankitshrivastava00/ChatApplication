package com.ziasy.xmppchatapplication.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.ziasy.xmppchatapplication.R;

import java.util.ArrayList;

public class PlayerActivityTest extends AppCompatActivity {
    ArrayList<Integer> arrayList;
    ListView listView;
    PlayerListTestAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userlist_layout);
        arrayList=new ArrayList<>();
        for (int i=0;i<=20;i++){
            arrayList.add(i);
        }
        listView=(ListView)findViewById(R.id.userList);
        adapter=new PlayerListTestAdapter(PlayerActivityTest.this,R.layout.outgoing_audio,arrayList);
        listView.setAdapter(adapter);


    }
}
