package com.ziasy.xmppchatapplication.group_chat.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
//import com.squareup.picasso.Picasso;
import com.ziasy.xmppchatapplication.ChatActivity;
import com.ziasy.xmppchatapplication.ChatModulSocket.ChatApplication;
import com.ziasy.xmppchatapplication.ChatUserListActivity;
import com.ziasy.xmppchatapplication.ConnectionDetector;
import com.ziasy.xmppchatapplication.R;
import com.ziasy.xmppchatapplication.common.Permission;
import com.ziasy.xmppchatapplication.common.SessionManagement;
import com.ziasy.xmppchatapplication.group_chat.adapter.GroupDetailAdapter;
import com.ziasy.xmppchatapplication.group_chat.model.GroupUserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import io.socket.client.Socket;

public class GroupProfileActivity extends AppCompatActivity {
    private TextView tv_toolbar_title;
    private Toolbar toolbar;
    private ImageView iv_back;
    private String imageUrl, description;
    private String userName, rid, did;
    private ListView groupList;
    private ArrayList<GroupUserModel> list;
    private ArrayList<String> arrayListName;
    private ConnectionDetector cd;
    private SessionManagement sd;
    private ProgressDialog progressDialog;
    private GroupDetailAdapter adapter;
    private Socket mSocket;
    private ImageView linear;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_detail_list);
        sd = new SessionManagement(GroupProfileActivity.this);
        cd = new ConnectionDetector(GroupProfileActivity.this);
        progressDialog = new ProgressDialog(GroupProfileActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        toolbar = (Toolbar) findViewById(R.id.tb_base);
        setSupportActionBar(toolbar);
        tv_toolbar_title = (TextView) findViewById(R.id.tv_toolbar_title);
        linear = (ImageView) findViewById(R.id.linear);
        groupList = (ListView) findViewById(R.id.groupList);
        list = new ArrayList<>();
        arrayListName = new ArrayList<>();
        tv_toolbar_title.setText("Group Info.");
        iv_back = (ImageView) findViewById(R.id.iv_back);
        userName = getIntent().getStringExtra("name");
        imageUrl = getIntent().getStringExtra("image");
        description = getIntent().getStringExtra("description");
        rid = getIntent().getStringExtra("rid");
        did = getIntent().getStringExtra("did");

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        createContact();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (linear != null) {
            linear.setImageResource(sd.getGroupWallpaper());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);

    }

    private void createContact() {
        try {
            if (!cd.isConnectingToInternet()) {
                Snackbar.make(findViewById(android.R.id.content), "No Internet Connection", Snackbar.LENGTH_SHORT).show();
            } else {
                progressDialog.show();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://128.199.222.145/reg/getg.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject object = new JSONObject(response.trim());
                                    String Admin = object.getString("admin");
                                    JSONArray jsonArray = object.getJSONArray("result");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object1 = jsonArray.getJSONObject(i);
                                        String id = object1.getString("id");
                                        String name = object1.getString("name");
                                        String num = object1.getString("num");
                                        String device = object1.getString("device");
                                        String photo = object1.getString("photo");
                                        arrayListName.add(id);
                                        list.add(new GroupUserModel(id, name, num,photo,device));
                                    }
                                    adapter = new GroupDetailAdapter(GroupProfileActivity.this, R.layout.group_contact_item, list,Admin);
                                    groupList.setAdapter(adapter);
                                    progressDialog.dismiss();
                                    //      txtCOubt.setText(list.size());
                                    LayoutInflater inflaterHeader = getLayoutInflater();
                                    View header = getLayoutInflater().inflate(R.layout.group_profile_layout, null,false);

                                    groupList.addHeaderView(header);
                                    CircleImageView iv_profilePic = (CircleImageView) header.findViewById(R.id.iv_profilePic);
                                    TextView tv_company = (TextView) header.findViewById(R.id.tv_company);
                                    TextView tv_name = (TextView) header.findViewById(R.id.tv_name);
                                    TextView txtDescription = (TextView) header.findViewById(R.id.txtDescription);
                                    TextView txtCOubt = (TextView) header.findViewById(R.id.txtCOubt);
                                    RelativeLayout wallpaperRelative = (RelativeLayout) header.findViewById(R.id.wallpaperRelative);
                                    wallpaperRelative.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent i = new Intent(GroupProfileActivity.this, GroupWallpaperActivity.class);
                                            startActivity(i);
                                        }
                                    });
                                    tv_name.setText(Admin);
                                    txtDescription.setText(description);
                                    tv_company.setText(userName);
                                    txtCOubt.setText(list.size() + "");

                                    Glide.with(GroupProfileActivity.this).load(imageUrl).into(iv_profilePic);

                                    View footer = getLayoutInflater().inflate(R.layout.groupdetail_footer, null,false);
                                    groupList.addFooterView(footer);
                                    Button btnAdd = (Button) footer.findViewById(R.id.addGroup);
                                    Button exitGroup = (Button) footer.findViewById(R.id.exitGroup);
                                    Button exitGroupForSingle = (Button) footer.findViewById(R.id.exitGroupForSingle);
                                    if (Admin.equalsIgnoreCase(sd.getUserName())) {
                                        btnAdd.setVisibility(View.VISIBLE);
                                        exitGroup.setVisibility(View.VISIBLE);
                                        exitGroupForSingle.setVisibility(View.GONE);
                                    } else {
                                        btnAdd.setVisibility(View.GONE);
                                        exitGroup.setVisibility(View.GONE);
                                        exitGroupForSingle.setVisibility(View.VISIBLE);
                                    }
                                    btnAdd.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent i = new Intent(GroupProfileActivity.this, AddUserGroupActivity.class);
                                            i.putExtra("rid", rid);
                                            i.putStringArrayListExtra("user", arrayListName);
                                            startActivity(i);
                                            finish();
                                        }
                                    });
                                    exitGroup.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            leftGroup();
                                        }
                                    });
                                    exitGroupForSingle.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            leftGroup();
                                        }
                                    });
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
                        params.put("id", rid);

                        return params;
                    }
                };
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                RequestQueue requestQueue = Volley.newRequestQueue(GroupProfileActivity.this);
                requestQueue.add(stringRequest);
            }
        } catch (Exception e) {
            e.printStackTrace();
            progressDialog.dismiss();
        }
    }

    public void leftGroup() {
        try {
            if (!cd.isConnectingToInternet()) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "No Internet Connection", Snackbar.LENGTH_LONG)
                        .setAction("Ok", null);
                View sbView = snackbar.getView();
                sbView.setBackgroundColor(Color.BLUE);
                snackbar.show();

            } else {
                progressDialog.show();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://128.199.222.145/reg/delu.php",
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
                                progressDialog.dismiss();
                                if (result.equalsIgnoreCase("Sucess")) {
                                    // finish();
                                    attemptSend(sd.getUserName(), "left");
                                    Intent i = new Intent(GroupProfileActivity.this, ChatUserListActivity.class);
                                    startActivity(i);
                                    ActivityCompat.finishAffinity((Activity) GroupProfileActivity.this);
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
                        params.put("id", rid);
                        params.put("num", sd.getUserName());

                        return params;
                    }
                };
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                RequestQueue requestQueue = Volley.newRequestQueue(GroupProfileActivity.this);
                requestQueue.add(stringRequest);
            }
        } catch (Exception e) {
            e.printStackTrace();
            progressDialog.dismiss();
        }
    }
    public void removeGroup(String id,String name) {
        try {
            if (!cd.isConnectingToInternet()) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "No Internet Connection", Snackbar.LENGTH_LONG)
                        .setAction("Ok", null);
                View sbView = snackbar.getView();
                sbView.setBackgroundColor(Color.BLUE);
                snackbar.show();

            } else {
                progressDialog.show();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://128.199.222.145/reg/delu.php",
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
                                progressDialog.dismiss();
                                if (result.equalsIgnoreCase("Sucess")) {
                                    // finish();
                                    attemptSend(name, "remove");
                                    Intent i = new Intent(GroupProfileActivity.this, ChatUserListActivity.class);
                                    startActivity(i);
                                    ActivityCompat.finishAffinity((Activity) GroupProfileActivity.this);
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
                        params.put("id", rid);
                        params.put("num", id);

                        return params;
                    }
                };
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                RequestQueue requestQueue = Volley.newRequestQueue(GroupProfileActivity.this);
                requestQueue.add(stringRequest);
            }
        } catch (Exception e) {
            e.printStackTrace();
            progressDialog.dismiss();
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
        Log.d("SEND_SINGLE", object.toString());

    }
}