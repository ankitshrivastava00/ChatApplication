package com.ziasy.xmppchatapplication.group_chat.activity;

/**
 * Created by Khushvinders on 15-Nov-16.
 */

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
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
import com.ziasy.xmppchatapplication.common.DrawableClickListener;
import com.ziasy.xmppchatapplication.common.SessionManagement;
import com.ziasy.xmppchatapplication.group_chat.adapter.AddUserGroupAdapter;
import com.ziasy.xmppchatapplication.util.ui.CustomEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class AddUserGroupActivity extends AppCompatActivity implements OnClickListener {

    private ArrayList<User> users;
    private ListView userList;
    private ConnectionDetector cd;
    private SessionManagement sd;
    private ProgressDialog pd;
    private TextView tv_toolbar_title;
    private Toolbar toolbar;
    private CustomEditText searchEt;
    private ImageView iv_back;
    private Socket mSocket;
    private LinearLayout linearHorizontal;
    private String rid;
    private ArrayList<String>userArrayList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userlist_layout);
        toolbar = (Toolbar) findViewById(R.id.tb_base);
        tv_toolbar_title = (TextView) findViewById(R.id.tv_toolbar_title);
        tv_toolbar_title.setText("Add User");
        users = new ArrayList<User>();
        userArrayList = new ArrayList<String>();
        iv_back = (ImageView) findViewById(R.id.iv_back);
        searchEt = (CustomEditText) findViewById(R.id.searchEt);
        linearHorizontal = (LinearLayout) findViewById(R.id.linearHorizontal);
        linearHorizontal.setVisibility(View.GONE);
        rid = getIntent().getStringExtra("rid");
        userArrayList = getIntent().getStringArrayListExtra("user");

        iv_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        userList = (ListView) findViewById(R.id.userList);

        cd = new ConnectionDetector(AddUserGroupActivity.this);
        sd = new SessionManagement(AddUserGroupActivity.this);
        pd = new ProgressDialog(AddUserGroupActivity.this);
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
            //getUsrList();
        }
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                loginSocket(users.get(i).getName());
            }
        });

        searchEt.setDrawableClickListener(new DrawableClickListener() {


            public void onClick(DrawablePosition target) {
                switch (target) {
                    case RIGHT:
                        //Do something here
                        searchEt.setText("");
                        if (TextUtils.isEmpty(searchEt.getText().toString().trim())) {
                            if (adapter != null) {
                                adapter.filter("");
                            }
                        }
                        break;

                    default:
                        break;
                }
            }

        });

        searchEt.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(searchEt.getText().toString().trim())) {
                    if (adapter != null) {
                        adapter.filter("");
                    }
                } else {
                    if (adapter != null) {
                        adapter.filter(searchEt.getText().toString().trim());
                    }
                }
            }

        });

    }

    private void loginSocket(String user) {
        try {
            if (!cd.isConnectingToInternet()) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "No Internet Connection", Snackbar.LENGTH_LONG)
                        .setAction("Ok", null);
                View sbView = snackbar.getView();
                sbView.setBackgroundColor(Color.BLUE);
                snackbar.show();

            } else {
                pd.show();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://128.199.222.145/reg/addu.php",
                        // StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://13.59.184.203/reg/reg.php",
                        //            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://35.243.191.92/reg/reg.php",
                        //   StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://35.232.185.242:8090/getchat.php ",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                String result = null;
                                try {

                                    JSONObject object = new JSONObject(response);
                                    result = object.getString("result");

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                if (result.equalsIgnoreCase("Success")){
                                   // finish();
                                    attemptSend(user,"added");

                                //    attemptSendTest(user,"firsttest");
                                    finish();
                                }

                                pd.dismiss();


                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                pd.dismiss();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("id", rid);
                        params.put("num", user);

                        return params;
                    }
                };

                stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                RequestQueue requestQueue = Volley.newRequestQueue(AddUserGroupActivity.this);
                requestQueue.add(stringRequest);
            }
        } catch (Exception e) {
            e.printStackTrace();
            pd.dismiss();

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
        }
    }

    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    private void attemptSend(String message, String type) {
        ChatApplication app = (ChatApplication) getApplication();
        mSocket = app.getSocket();
        mSocket.connect();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        JsonObject object = new JsonObject();
        object.addProperty("reciverid", rid);
        object.addProperty("message", message);
        object.addProperty("dtype", type);
        object.addProperty("sname", sd.getUserName());
        object.addProperty("did", "asdfasdf");
        object.addProperty("uid", getSaltString());
        object.addProperty("datetime", formattedDate);
        object.addProperty("isread", "false");
        object.addProperty("deliver", "false");
        object.addProperty("senderid", sd.getUserName());
        mSocket.emit("sendMessageGroup", object);
        Log.d("ADDUSERGROUP", object.toString());

    }
    private void attemptSendTest(String message, String type) {
        ChatApplication app = (ChatApplication) getApplication();
        mSocket = app.getSocket();
        mSocket.connect();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        JsonObject object = new JsonObject();
        object.addProperty("reciverid", rid);
        object.addProperty("message", message);
        object.addProperty("dtype", type);
        object.addProperty("sname", message);
        object.addProperty("did", "asdfasdf");
        object.addProperty("uid", getSaltString());
        object.addProperty("datetime", formattedDate);
        object.addProperty("isread", "false");
        object.addProperty("deliver", "false");
        object.addProperty("senderid", message);
        mSocket.emit("sendMessageGroup", object);
        Log.d("ADDUSERGROUP", object.toString());

    }

    private Emitter.Listener onSendMyMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        users.clear();
                        pd.show();
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
                            String deviceid = obj1.getString("deviceid");
                           // String datetime = obj1.getString("datetime");
                            if (!userArrayList.contains(id)){
                            if (!id.equalsIgnoreCase(sd.getKeyId())) {
                                allPrdctData.setId(id);
                                allPrdctData.setName(name);
                                allPrdctData.setDid(deviceid);
                                allPrdctData.setCount("0");
                                allPrdctData.setDatetime("");
                                allPrdctData.setImageUrl(path + photo);
                                users.add(allPrdctData);
                            }
                        }
                        }
                        pd.dismiss();
                        Collections.sort(users, User.StuNameComparator);
                        adapter = new AddUserGroupAdapter(AddUserGroupActivity.this, users);
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
    AddUserGroupAdapter adapter;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

}