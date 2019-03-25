package com.ziasy.xmppchatapplication.group_chat.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.ziasy.xmppchatapplication.ChatModulSocket.ChatApplication;
import com.ziasy.xmppchatapplication.ChatUserList;
import com.ziasy.xmppchatapplication.ChatUserListActivity;
import com.ziasy.xmppchatapplication.ChatUserListAdapter;
import com.ziasy.xmppchatapplication.ConnectionDetector;
import com.ziasy.xmppchatapplication.R;
import com.ziasy.xmppchatapplication.User;
import com.ziasy.xmppchatapplication.UserListActivity;
import com.ziasy.xmppchatapplication.UserListAdapter;
import com.ziasy.xmppchatapplication.adapter.EmojiAdapter;
import com.ziasy.xmppchatapplication.common.GridItemView;
import com.ziasy.xmppchatapplication.common.SessionManagement;
import com.ziasy.xmppchatapplication.group_chat.GridItemViewForGroup;
import com.ziasy.xmppchatapplication.group_chat.adapter.ContactRecyclerAdapter;
import com.ziasy.xmppchatapplication.group_chat.adapter.CreateGroupAdapter;
import com.ziasy.xmppchatapplication.group_chat.model.Contact;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class CreateGroupChat extends AppCompatActivity {
    private GridView gridView;
    private ArrayList<Integer> selectedStrings;
    private CreateGroupAdapter adapter;
    private ArrayList<User> users;
    private SessionManagement sessionManager;
    private ProgressDialog progressDialog;
    private ConnectionDetector cd;
    private Socket mSocket;
    private int count = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.setVisibility(View.GONE);chat);

        selectedStrings = new ArrayList<>();
        users = new ArrayList<>();

        sessionManager = new SessionManagement(CreateGroupChat.this);
        cd = new ConnectionDetector(CreateGroupChat.this);
        progressDialog = new ProgressDialog(CreateGroupChat.this);
        progressDialog.setMessage("Create a Group");
        progressDialog.setCancelable(false);
        ChatApplication app = (ChatApplication) getApplication();
        mSocket = app.getSocket();
        mSocket.on("employeeListDetail", onSendMyMessage);


        gridView = (GridView) findViewById(R.id.grid_view_id);
        gridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Toast.makeText(EmojiActivity.this, "Position " + i  +"EMOJI RESOURCE ID "+list.get(i), Toast.LENGTH_SHORT).show();
                /*Intent intent = new Intent();
                intent.putExtra("image", list.get(i));
                setResult(RESULT_OK, intent);
                finish();*/
                int selectedIndex = adapter.selectedPositions.indexOf(i);

                if (selectedIndex > -1) {
                    adapter.selectedPositions.remove(selectedIndex);
                    ((GridItemViewForGroup) view).display(false);
                    selectedStrings.remove( (Integer) adapterView.getItemAtPosition(i));
                } else {

                    adapter.selectedPositions.add(i);
                    ((GridItemViewForGroup) view).display(true);
                    selectedStrings.add((Integer) adapterView.getItemAtPosition(i));

                }
            }
        });

    }

    private Emitter.Listener onSendMyMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        users.clear();
                        progressDialog.dismiss();
                        JSONObject data = (JSONObject) args[0];
                        JSONArray arr = data.getJSONArray("result");
                        for (int i = 0; i < arr.length(); i++) {
                            User allPrdctData = new User();
                            JSONObject obj1 = arr.getJSONObject(i);
                            String id = obj1.getString("id");
                            String name = obj1.getString("name");
                            //  String count = obj1.getString("count");
                            String photo = obj1.getString("photo");
                            String path = obj1.getString("path");
                            //   String datetime = obj1.getString("datetime");
                            if (!id.equalsIgnoreCase(sessionManager.getKeyId())) {
                                allPrdctData.setId(id);
                                allPrdctData.setName(name);
                                allPrdctData.setCount("0");
                                allPrdctData.setDatetime("");
                                allPrdctData.setImageUrl(path + photo);
                                users.add(allPrdctData);
                            }
                        }
                        Collections.sort(users, User.StuNameComparator);
                        adapter = new CreateGroupAdapter(CreateGroupChat.this,R.layout.group_contact_item, users);
                        gridView.setAdapter(adapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
            });

        }
    };
}
