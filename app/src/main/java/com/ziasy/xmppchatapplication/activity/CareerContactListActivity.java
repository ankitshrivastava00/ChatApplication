package com.ziasy.xmppchatapplication.activity;

/**
 * Created by Khushvinders on 15-Nov-16.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.ziasy.xmppchatapplication.ChatModulSocket.ChatApplication;
import com.ziasy.xmppchatapplication.ConnectionDetector;
import com.ziasy.xmppchatapplication.R;
import com.ziasy.xmppchatapplication.User;
import com.ziasy.xmppchatapplication.UserListAdapter;
import com.ziasy.xmppchatapplication.common.SessionManagement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class CareerContactListActivity extends AppCompatActivity implements OnClickListener {

    List<User> users;
    ListView userList;
    private ConnectionDetector cd;
    private SessionManagement sd;
    private ProgressDialog pd;
    private TextView tv_toolbar_title;
    private Toolbar toolbar;
    private Socket mSocket;
    private ImageView iv_back;
    private LinearLayout searchLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userlist_layout);
        toolbar = (Toolbar) findViewById(R.id.tb_base);
        tv_toolbar_title = (TextView) findViewById(R.id.tv_toolbar_title);
        tv_toolbar_title.setText("Share Contact");
        iv_back = (ImageView) findViewById(R.id.iv_back);
        searchLayout = (LinearLayout) findViewById(R.id.searchLayout);
        searchLayout.setVisibility(View.GONE);
        iv_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        userList = (ListView) findViewById(R.id.userList);
        users = new ArrayList<User>();
        cd = new ConnectionDetector(CareerContactListActivity.this);
        sd = new SessionManagement(CareerContactListActivity.this);
        pd = new ProgressDialog(CareerContactListActivity.this);
        pd.setCancelable(false);
        pd.setMessage("Please Wait");

        //  pd.show();
        if (!cd.isConnectingToInternet()) {
            Snackbar.make(findViewById(android.R.id.content), "Internet Connection not available..", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
        } else {
            ChatApplication app = (ChatApplication) getApplication();
            mSocket = app.getSocket();
            mSocket.on("employeeListDetail", onSendMyMessage);

       userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              // Toast.makeText(CareerContactListActivity.this, "Position " + users.get(i), Toast.LENGTH_SHORT).show();
               StringBuffer stringBuffer = new StringBuffer();
               stringBuffer.append(users.get(i).getName());
               stringBuffer.append("," + users.get(i).getImageUrl());
               stringBuffer.append("," + users.get(i).getId());
               stringBuffer.append("," + users.get(i).getDid());
               Intent intent = new Intent();
               intent.putExtra("share", (CharSequence) stringBuffer);
               setResult(RESULT_OK, intent);
               finish();
           }
       });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", sd.getKeyId());
        jsonObject.addProperty("isOnlineStatus", "false");
        mSocket.emit("disableUser", jsonObject);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!cd.isConnectingToInternet()) {
            Snackbar.make(findViewById(android.R.id.content), "Internet Connection not available..", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
        } else {
            mSocket.connect();
            JsonObject jsonObject1 = new JsonObject();
            jsonObject1.addProperty("senderid", sd.getKeyId());
            mSocket.emit("employeeList", jsonObject1);

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", sd.getKeyId());
            jsonObject.addProperty("isChatEnable", "false");
            jsonObject.addProperty("isDelivered", "false");
            jsonObject.addProperty("isReaded", "false");
            jsonObject.addProperty("isOnlineStatus", "true");

            mSocket.emit("userid", jsonObject);
        }
    }

    private Emitter.Listener onSendMyMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        users.clear();
                        pd.dismiss();
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
                            if (!id.equalsIgnoreCase(sd.getKeyId())) {
                                allPrdctData.setId(id);
                                allPrdctData.setName(name);
                                allPrdctData.setCount("0");
                                allPrdctData.setDatetime("");
                                allPrdctData.setImageUrl(path + photo);
                                users.add(allPrdctData);
                            }
                        }
                        Collections.sort(users, User.StuNameComparator);
                        adapter = new AddContatctListAdapter(CareerContactListActivity.this, users);
                        userList.setAdapter(adapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                        pd.dismiss();
                    }
                }
            });

        }
    };
    RequestQueue requestQueue;
    AddContatctListAdapter adapter;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void callBack(int i) {
        Toast.makeText(CareerContactListActivity.this, "Position " + users.get(i), Toast.LENGTH_SHORT).show();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(users.get(i).getName());
        stringBuffer.append("," + users.get(i).getImageUrl());
        Intent intent = new Intent();
        intent.putExtra("share", (CharSequence) stringBuffer);
        setResult(RESULT_OK, intent);
        finish();
    }
/*
    public void getUsrList() {
        requestQueue = Volley.newRequestQueue(CareerContactListActivity.this);
        try {
            if (!cd.isConnectingToInternet()) {
                Snackbar.make(findViewById(android.R.id.content), "No Internet Connection", Snackbar.LENGTH_SHORT).show();
            } else {
                pd.show();
                StringRequest strReq = new StringRequest(Request.Method.GET, "http://" + getString(R.string.server) + "/plugins/restapi/v1/users", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response : ", response);
                        // UserResponse userResponse = new Gson().fromJson(response, UserResponse.class);
                        // String [] y={"newuser","newuser"};\
                        pd.dismiss();
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray("users");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                User adminModel = new User();
                                JSONObject obj1 = jsonArray.getJSONObject(i);
                                String username = obj1.getString("username");
                                String name = obj1.getString("name");
                                //String email = obj1.getString("email");
                                if (!sd.getUserName().equalsIgnoreCase(username)) {
                                    adminModel.setUsername(username);
                                    adminModel.setName(name);
                                    adminModel.setEmail(username+"@gmail.com");

                                    users.add(adminModel);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter = new UserListAdapter(CareerContactListActivity.this, users);
                        userList.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        pd.dismiss();
                    }
                }) {

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Authorization", "E6jhLtJRA4QowlKx");
                        headers.put("Accept", "application/json");
                        return headers;
                    }
                };
                requestQueue.add(strReq);
            }
        } catch (Exception e) {
            e.printStackTrace();
            pd.dismiss();
        }
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

}