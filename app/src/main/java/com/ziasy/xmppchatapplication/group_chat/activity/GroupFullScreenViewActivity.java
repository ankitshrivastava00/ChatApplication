package com.ziasy.xmppchatapplication.group_chat.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.ziasy.xmppchatapplication.ChatModulSocket.ChatApplication;
import com.ziasy.xmppchatapplication.ConnectionDetector;
import com.ziasy.xmppchatapplication.R;
import com.ziasy.xmppchatapplication.activity.FullScreenViewActivity;
import com.ziasy.xmppchatapplication.adapter.FullScreenImageAdapter;
import com.ziasy.xmppchatapplication.common.Permission;
import com.ziasy.xmppchatapplication.common.SessionManagement;
import com.ziasy.xmppchatapplication.group_chat.adapter.GroupFullScreenImageAdapter;
import com.ziasy.xmppchatapplication.localDB.LocalDBHelper;
import com.ziasy.xmppchatapplication.model.JsonModelForChat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class GroupFullScreenViewActivity extends Activity {
    private Socket mSocket;
    private List<JsonModelForChat> list;
    private GroupFullScreenImageAdapter adapter;
    private ViewPager viewPager;
    private SessionManagement sd;
    private String position;
    private String rid;
    private static LinearLayout deleteLinear, saveLinear, forwardLinear, scanLinear, ogAppbar, linear_bottom_layout;
    private static boolean isImageFitToScreen = true;
    private String strFileName = null, strFilePath;
    private ImageView imageBack,setting;
    private TextView nameTv;
    LocalDBHelper localDBHelper;
    private ConnectionDetector cd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_view);
        localDBHelper=new LocalDBHelper(this);
        cd= new ConnectionDetector(this);
        sd = new SessionManagement(GroupFullScreenViewActivity.this);
        viewPager = (ViewPager) findViewById(R.id.pager);
        imageBack = (ImageView) findViewById(R.id.imageBack);
        setting = (ImageView) findViewById(R.id.setting);
        nameTv = (TextView) findViewById(R.id.nameTv);
        linear_bottom_layout = (LinearLayout) findViewById(R.id.linear_bottom_layout);
        ogAppbar = (LinearLayout) findViewById(R.id.ogAppbar);
        forwardLinear = (LinearLayout) findViewById(R.id.forwardLinear);
        saveLinear = (LinearLayout) findViewById(R.id.saveLinear);
        deleteLinear = (LinearLayout) findViewById(R.id.deleteLinear);
        scanLinear = (LinearLayout) findViewById(R.id.scanLinear);
        position = getIntent().getStringExtra("id");
        rid = getIntent().getStringExtra("rid");
        list = new ArrayList<>();
        if (!cd.isConnectingToInternet()){
            list= localDBHelper.getAllGroupMessageImages(rid);
            if (list.size()==0){
                finish();
            }else {
                adapter = new GroupFullScreenImageAdapter(GroupFullScreenViewActivity.this,
                        list);
                viewPager.setAdapter(adapter);
            }
            // displaying selected image first
            if (list!=null){
                for (int p = 0 ;p< list.size();p++){
                    if (list.get(p).getMid().equalsIgnoreCase(position)) {
                        viewPager.setCurrentItem(p);
                    }
                }

            }
            Log.d("No_internet_data", list.toString());
        }else{
            ChatApplication app = (ChatApplication) getApplication();

            mSocket = app.getSocket();
            mSocket.connect();
            mSocket.on("userMessageGroup", AllMyMessage);
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("id", sd.getUserName());
            jsonObject.addProperty("messageCount", "0");
            jsonObject.addProperty("isChatEnable", "true");
            jsonObject.addProperty("isDelivered", "true");
            jsonObject.addProperty("isReaded", "true");
            jsonObject.addProperty("isOnlineStatus", "true");

            mSocket.emit("userid", jsonObject);
            JsonObject jsonObject2 = new JsonObject();
            jsonObject2.addProperty("senderid", sd.getUserName());
            jsonObject2.addProperty("reciverid", rid);
            mSocket.emit("loadGroupChat", jsonObject2);

        }

       /* viewPager.setOnClickListener(new View.OnClickListener() {
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
        });*/
        saveLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    boolean storagepermission = Permission.checkReadExternalStoragePermission(GroupFullScreenViewActivity.this);
                    boolean externalStroage = Permission.permissionForExternal(GroupFullScreenViewActivity.this);
                    if (storagepermission) {
                        if (externalStroage) {
                        int i=    viewPager.getCurrentItem();
                        if (adapter!=null){
                          adapter.saveImage(GroupFullScreenViewActivity.this,i);
                            // downloadFile(position);
                         //   Utils.downloadFile(FullScreenViewActivity.this, strImagePath.get(0), strFileName);
                            Toast.makeText(GroupFullScreenViewActivity.this, "Image Saved", Toast.LENGTH_SHORT).show();

                        }
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(GroupFullScreenViewActivity.this, "No space available", Toast.LENGTH_SHORT).show();
                }
            }
        });
        deleteLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = viewPager.getCurrentItem();
           /*     if (adapter!=null){
                    adapter.deleteData(FullScreenViewActivity.this,i,sd.getKeyId(),mSocket);
                    Toast.makeText(FullScreenViewActivity.this,"Delete",Toast.LENGTH_SHORT).show();

                }*/
                JsonObject jsonObject1 = new JsonObject();
                jsonObject1.addProperty("id", sd.getKeyId());
                jsonObject1.addProperty("uid","," + "'" + list.get(i).getMid() + "'");
                Log.d("MIDMID", jsonObject1 + "");

                mSocket.emit("deleteMessage", jsonObject1);
                list.remove(list.get(i));
                Toast.makeText(GroupFullScreenViewActivity.this,"Delete",Toast.LENGTH_SHORT).show();
                if (list.size()==0){
                    finish();
                }else {
                    adapter = new GroupFullScreenImageAdapter(GroupFullScreenViewActivity.this,
                            list);
                    viewPager.setAdapter(adapter);
                }
            }
        });
        forwardLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = viewPager.getCurrentItem();
                if (adapter!=null){
                    adapter.forwardData(i);
                    // downloadFile(position);
                    //   Utils.downloadFile(FullScreenViewActivity.this, strImagePath.get(0), strFileName);
                    Toast.makeText(GroupFullScreenViewActivity.this, "Forward", Toast.LENGTH_SHORT).show();

                }
            }
        });
        scanLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
    public static void clickStatus(){
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 88 && resultCode == RESULT_OK
                && null != data) {
            Intent i = new Intent();
            setResult(Activity.RESULT_OK, i);
            finish();
        }


    }

    private Emitter.Listener AllMyMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {

//                        progressDialog.show();
                        list.clear();
                        JSONObject data = (JSONObject) args[0];
                        Log.d("Rrreeer", data + "");
                        JSONArray jsonArray = data.getJSONArray("result");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj1 = jsonArray.getJSONObject(i);
                            String send_id = obj1.getString("send_id");
                            String rcv_id = obj1.getString("rcv_id");
                            String datetime = obj1.getString("datetime");
                            String message = obj1.getString("message");
                            String isread = obj1.getString("isread");
                            String deliver = obj1.getString("deliver");
                            String type = obj1.getString("type");
                            String mid = obj1.getString("uid");

                            if (!cd.isConnectingToInternet()) {
                                Toast.makeText(GroupFullScreenViewActivity.this, "No work", Toast.LENGTH_SHORT).show();
                                list=localDBHelper.getAllGroupMessageImages(rid);
                                Snackbar.make(findViewById(android.R.id.content), "No Internet Connection", Snackbar.LENGTH_SHORT).show();
                            }else{
                                if (type.equalsIgnoreCase("img")) {
                                    Toast.makeText(GroupFullScreenViewActivity.this, "Work", Toast.LENGTH_SHORT).show();
                                    list.add(new JsonModelForChat(message, send_id, rcv_id, "", isread, "All", "", deliver, mid, message, type, "asdfa", "asdfsda", "asdfsa", datetime, false));
                                    //             list.addAll(DBUtil.fetchAllChatList(ChatActivity.this));
                                    //list.add(new ChatModel(message, send_id, rcv_id, time, date, isread, deliver, "All"));
                                }
                            }
                        }if (list.size()==0){
                            finish();
                        }else {
                            adapter = new GroupFullScreenImageAdapter(GroupFullScreenViewActivity.this,
                                    list);
                            viewPager.setAdapter(adapter);
                        }
                        // displaying selected image first
                        if (list!=null){
                            for (int p = 0 ;p< list.size();p++){
                                if (list.get(p).getMid().equalsIgnoreCase(position)) {
                                    viewPager.setCurrentItem(p);
                                }
                            }

                        }
                        //	chatAdapter = new ChatAdapter(ChatActivity.this, msgListView, R.layout.chat_list_item, list, multiselect_list);
                        //	msgListView.setAdapter(chatAdapter);


                        //  progressDialog.dismiss();

                    } catch (JSONException e) {
                        Log.d("RERRR", e.getMessage());
                        //  progressDialog.dismiss();
                        return;
                    }
                }
            });

        }
    };
}
