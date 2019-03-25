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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.ziasy.xmppchatapplication.ChatModulSocket.ChatApplication;
import com.ziasy.xmppchatapplication.ChatUserList;
import com.ziasy.xmppchatapplication.ConnectionDetector;
import com.ziasy.xmppchatapplication.R;
import com.ziasy.xmppchatapplication.User;
import com.ziasy.xmppchatapplication.adapter.ForwardAdapter;
import com.ziasy.xmppchatapplication.common.DrawableClickListener;
import com.ziasy.xmppchatapplication.common.SessionManagement;
import com.ziasy.xmppchatapplication.group_chat.activity.AddUserGroupActivity;
import com.ziasy.xmppchatapplication.group_chat.activity.GroupProfileActivity;
import com.ziasy.xmppchatapplication.group_chat.activity.GroupWallpaperActivity;
import com.ziasy.xmppchatapplication.group_chat.adapter.GroupDetailAdapter;
import com.ziasy.xmppchatapplication.group_chat.model.GroupUserModel;
import com.ziasy.xmppchatapplication.util.ui.CustomEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class ForwardActivity extends AppCompatActivity implements OnClickListener {

    private ArrayList<ChatUserList> users;
    private ListView userList;
    private ConnectionDetector cd;
    private SessionManagement sd;
    private ForwardAdapter adapter;
    private ProgressDialog pd;
    private TextView tv_toolbar_title;
    private Toolbar toolbar;
    private CustomEditText searchEt;
    private ProgressDialog progressDialog;
    private ImageView iv_back;
    private ArrayList<String> forwardString = null,typeString=null;
   // private Socket mSocket;
    private LinearLayout linearHorizontal;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userlist_layout);
        toolbar = (Toolbar) findViewById(R.id.tb_base);
        progressDialog=new ProgressDialog(ForwardActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        tv_toolbar_title = (TextView) findViewById(R.id.tv_toolbar_title);
        tv_toolbar_title.setText("Forward");
        iv_back = (ImageView) findViewById(R.id.iv_back);
        searchEt = (CustomEditText) findViewById(R.id.searchEt);
        linearHorizontal = (LinearLayout) findViewById(R.id.linearHorizontal);
        linearHorizontal.setVisibility(View.GONE);
        forwardString=new ArrayList<>();
        typeString=new ArrayList<>();
        forwardString = getIntent().getStringArrayListExtra("forwardString");
        typeString = getIntent().getStringArrayListExtra("type");
        Log.e("FORWARDING",forwardString+" : "+typeString);
        iv_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        userList = (ListView) findViewById(R.id.userList);
        users = new ArrayList<ChatUserList>();
        cd = new ConnectionDetector(ForwardActivity.this);
        sd = new SessionManagement(ForwardActivity.this);
        pd = new ProgressDialog(ForwardActivity.this);
        pd.setCancelable(false);
        pd.setMessage("Please Wait");
        //  pd.show();

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
        createContact();
    }

    private void createContact() {
        try {
            if (!cd.isConnectingToInternet()) {
                Snackbar.make(findViewById(android.R.id.content), "No Internet Connection", Snackbar.LENGTH_SHORT).show();
            } else {
                progressDialog.show();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://128.199.222.145/reg/listchat.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject object = new JSONObject(response.trim());

                                    JSONArray jsonArray = object.getJSONArray("result");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        ChatUserList allPrdctData = new ChatUserList();

                                        JSONObject object1 = jsonArray.getJSONObject(i);
                                        String id = object1.getString("id");
                                        String name = object1.getString("name");
                                        String num = object1.getString("num");
                                        String device = object1.getString("device");
                                        String photo = object1.getString("photo");
                                        String description = object1.optString("description");
                                        String admin = object1.optString("admin");
                                        String chattype = object1.getString("chattype");

                                        if (!id.equalsIgnoreCase(sd.getKeyId())){
                                        allPrdctData.setId(id);
                                        allPrdctData.setName(name);
                                        allPrdctData.setDescription(description);
                                        allPrdctData.setImageUrl(photo);
                                        allPrdctData.setChattype(chattype);
                                        allPrdctData.setDid(device);
                                        allPrdctData.setAdmin(admin);
                                        users.add(allPrdctData);
                                    }
                                    }
                                  //  Collections.sort(users, User.StuNameComparator);
                                    adapter = new ForwardAdapter(ForwardActivity.this, users, forwardString,typeString);
                                    userList.setAdapter(adapter);
                                    progressDialog.dismiss();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    progressDialog.dismiss();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("id", sd.getKeyId());

                        return params;
                    }
                };
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                RequestQueue requestQueue = Volley.newRequestQueue(ForwardActivity.this);
                requestQueue.add(stringRequest);
            }
        } catch (Exception e) {
            e.printStackTrace();
            progressDialog.dismiss();
        }
    }


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