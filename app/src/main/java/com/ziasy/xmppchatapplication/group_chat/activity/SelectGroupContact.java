package com.ziasy.xmppchatapplication.group_chat.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
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
import com.ziasy.xmppchatapplication.ChatUserListActivity;
import com.ziasy.xmppchatapplication.ConnectionDetector;
import com.ziasy.xmppchatapplication.R;
import com.ziasy.xmppchatapplication.User;
import com.ziasy.xmppchatapplication.common.Confiq;
import com.ziasy.xmppchatapplication.common.DrawableClickListener;
import com.ziasy.xmppchatapplication.common.RecyclerItemClickListener;
import com.ziasy.xmppchatapplication.common.SessionManagement;
import com.ziasy.xmppchatapplication.common.SingleRecyclerItemClickListener;
import com.ziasy.xmppchatapplication.group_chat.adapter.SelectGroupContactAdapter;
import com.ziasy.xmppchatapplication.localDB.LocalDBHelper;
import com.ziasy.xmppchatapplication.util.ui.CustomEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SelectGroupContact extends AppCompatActivity {
    private AmazonS3Client s3;
    private BasicAWSCredentials credentials;
    private TransferUtility transferUtility;
    private TransferObserver observer;

    // Recording Component
    private RecyclerView recyclerView;
    private SelectGroupContactAdapter adapter;
    private ArrayList<User> list;
    private ArrayList<User> multiselect_list;
    private boolean isMultiSelect = false;
    private SessionManagement sessionManager;
    private ProgressDialog progressDialog;
    private RelativeLayout view_career;
    private LinearLayout linearTop;
    private TextView userNumber;
    private ConnectionDetector cd;
    private Socket mSocket;
    private String strGroupName, strDescription;
    private StringBuffer selectedImgFile;
    private RelativeLayout back_relative;
    private int count = 0;
    private CustomEditText searchEt;
    private String eid, number, status, name, imagePath;
    private SessionManagement sd;
    private CircleImageView iv_profilePic;
    LocalDBHelper mlocalDBHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contact_list);
        mlocalDBHelper= new LocalDBHelper(this);
        sessionManager = new SessionManagement(SelectGroupContact.this);
        cd = new ConnectionDetector(SelectGroupContact.this);
        sd = new SessionManagement(SelectGroupContact.this);
        progressDialog = new ProgressDialog(SelectGroupContact.this);
        progressDialog.setMessage("Create a Group");
        progressDialog.setCancelable(false);
        ChatApplication app = (ChatApplication) getApplication();
        mSocket = app.getSocket();
        mSocket.on("employeeListDetail", onSendMyMessage);
        selectedImgFile = new StringBuffer();
        back_relative = (RelativeLayout) findViewById(R.id.back_relative);
        searchEt = (CustomEditText) findViewById(R.id.searchEt);
        iv_profilePic = (CircleImageView) findViewById(R.id.iv_profilePic);

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
        selectedImgFile.append(sessionManager.getUserName());
        back_relative.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        strGroupName = getIntent().getStringExtra("group_name");
        strDescription = getIntent().getStringExtra("group_description");
        imagePath = getIntent().getStringExtra("imagePath");
        if (imagePath.equalsIgnoreCase("No Image Found")) {
            iv_profilePic.setImageResource(R.drawable.team);
        }else {
            Glide.with(SelectGroupContact.this).load(imagePath).into(iv_profilePic);
        }
        view_career = (RelativeLayout) findViewById(R.id.view_career);
        linearTop = (LinearLayout) findViewById(R.id.linearTop);
        userNumber = (TextView) findViewById(R.id.userNumber);
        userNumber.setText("0 Member Select");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        view_career.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (multiselect_list.size() > 0) {

                    ArrayList<String> stringBuilderTpye = new ArrayList<>();
                    ArrayList<String> stringBuilderMessage = new ArrayList<String>();
                    for (int i = 0; i < multiselect_list.size(); i++) {
                        stringBuilderTpye.add(multiselect_list.get(i).getName());

                    }

                    userNumber.setText("0 Member Select");
                    isMultiSelect = false;
                    multiselect_list = new ArrayList<User>();
                    adapter.selected_usersList = multiselect_list;
                    adapter.notifyDataSetChanged();
                    if (imagePath.equalsIgnoreCase("No Image Found")) {
                        createContact(imagePath, stringBuilderTpye);
                    } else {
                        UploadFile(imagePath, sd.getKeyId(), ".jpg", stringBuilderTpye);
                    }  //  Toast.makeText(getApplicationContext(), "Forward", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Please Select Member", Toast.LENGTH_SHORT).show();
                }
                // Log.e("CONTACTS_STRING", contacts.toString() + "");
                // Log.e("CONTACT_LIST", contactList.toString() + "");

            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new

                DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation()));
        list = new ArrayList<>();
        multiselect_list = new ArrayList<>();

        recyclerView.addOnItemTouchListener(new SingleRecyclerItemClickListener(this, recyclerView, new SingleRecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
              //  Toast.makeText(SelectGroupContact.this,"onItemClick",Toast.LENGTH_SHORT).show();
                //
                 /*   if (!isMultiSelect) {
                        multiselect_list = new ArrayList<User>();
                        isMultiSelect = true;
                    }*/
              /*  if (isMultiSelect)
                    multi_select(position);*/
                if (!isMultiSelect) {
                    multiselect_list = new ArrayList<User>();
                    isMultiSelect = true;
                    multi_select(position);
                }else {
                multi_select(position);
            }
            }

            @Override
            public void onItemLongClick(View view, int position) {
              /*  if (!isMultiSelect) {
                    multiselect_list = new ArrayList<User>();
                    isMultiSelect = true;
                }
                multi_select(position);*/

              //  Toast.makeText(SelectGroupContact.this,"onItemLongClick",Toast.LENGTH_SHORT).show();
            }
        }));
        if (!cd.isConnectingToInternet()) {
            Snackbar.make(findViewById(android.R.id.content), "Internet Connection not available..", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
            list = new ArrayList<>();
            list=mlocalDBHelper.getAllUsersList();
            adapter = new SelectGroupContactAdapter(SelectGroupContact.this, R.layout.group_contact_item, mlocalDBHelper.getAllUsersList(), multiselect_list);
            recyclerView.setAdapter(adapter);
        } else {
            if (mSocket != null) {
                mSocket.connect();
                JsonObject jsonObject1 = new JsonObject();
                jsonObject1.addProperty("senderid", sessionManager.getKeyId());
                mSocket.emit("employeeList", jsonObject1);
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("id", sessionManager.getKeyId());
                jsonObject.addProperty("isChatEnable", "false");
                jsonObject.addProperty("isDelivered", "false");
                jsonObject.addProperty("isReaded", "false");
                jsonObject.addProperty("isOnlineStatus", "true");
                mSocket.emit("userid", jsonObject);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSocket == null) {
            ChatApplication app = (ChatApplication) getApplication();
            mSocket = app.getSocket();
            mSocket.on("employeeListDetail", onSendMyMessage);
        }
        if (mSocket != null) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", sessionManager.getKeyId());
            jsonObject.addProperty("isOnlineStatus", "false");
            mSocket.emit("disableUser", jsonObject);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void multi_select(int position) {
        if (multiselect_list.contains(list.get(position))) {
            multiselect_list.remove(list.get(position));
        } else {
            multiselect_list.add(list.get(position));
        }
        if (multiselect_list.size() > 0) {
            userNumber.setText("0 Member Select");
        } else {
            isMultiSelect = false;
            multiselect_list = new ArrayList<User>();
        }
        refreshAdapter();
    }

    public void refreshAdapter() {
        userNumber.setText(multiselect_list.size() + " Member Select");
        adapter.selected_usersList = multiselect_list;
        adapter.chatMessageList = list;
        adapter.notifyDataSetChanged();
    }

    private Emitter.Listener onSendMyMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        list.clear();
                        //pd.dismiss();
                        JSONObject data = (JSONObject) args[0];
                        Log.d("USERLISTDETA", data.toString());
                        JSONArray arr = data.getJSONArray("result");
                        for (int i = 0; i < arr.length(); i++) {
                            User allPrdctData = new User();
                            JSONObject obj1 = arr.getJSONObject(i);
                            String id = obj1.getString("id");
                            String name = obj1.getString("name");
                            String deviceid = obj1.getString("deviceid");
                            String photo = obj1.getString("photo");
                            String path = obj1.getString("path");
                            //   String datetime = obj1.getString("datetime");
                            if (!id.equalsIgnoreCase(sd.getKeyId()) && !name.trim().equalsIgnoreCase("")) {
                                allPrdctData.setId(id);
                                allPrdctData.setName(name);
                                allPrdctData.setDid(deviceid);
                                allPrdctData.setCount("0");
                                allPrdctData.setDatetime("");
                                allPrdctData.setImageUrl(path + photo);
                                list.add(allPrdctData);
                                mlocalDBHelper.insertUserList(allPrdctData);
                            }
                        }
                        Collections.sort(list, User.StuNameComparator);
                        adapter = new SelectGroupContactAdapter(SelectGroupContact.this, R.layout.group_contact_item, list, multiselect_list);
                        recyclerView.setAdapter(adapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                        // pd.dismiss();
                    }
                }
            });
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contact, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_create_chat) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createContact(String GroupIcon, ArrayList<String> users) {
        try {
            if (!cd.isConnectingToInternet()) {
                Snackbar.make(findViewById(android.R.id.content), "You can't create group because you are offline.", Snackbar.LENGTH_SHORT).show();
            } else {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = df.format(c.getTime());
                for (int i = 0, l = users.size(); i < l; i++) {
                    String path = users.get(i);
                    if (selectedImgFile.toString().isEmpty()) {
                        selectedImgFile.append(path);
                    } else {
                        selectedImgFile.append("," + path);
                    }
                }
                Log.e("STRING_VALUE_BUFFER", selectedImgFile.toString());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://128.199.222.145/reg/greg.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject object = new JSONObject(response.trim());
                                    JSONArray jsonArray = object.getJSONArray("result");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object1 = jsonArray.getJSONObject(i);
                                        eid = object1.getString("g_id");
                                        number = object1.getString("number");
                                        status = object1.getString("status");
                                        name = object1.getString("name");

                                    }
                                    progressDialog.dismiss();
                                    if (status.equalsIgnoreCase("Registered")) {
                                        attemptSend(sd.getUserName(), eid, "added");
                                        for (int j = 0; j < users.size(); j++) {
                                            attemptSend(users.get(j), eid, "added");
                                        }
                                        Intent i = new Intent(SelectGroupContact.this, ChatUserListActivity.class);
                                        startActivity(i);
                                        ActivityCompat.finishAffinity(SelectGroupContact.this);
                                    }
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
                        params.put("name", strGroupName);
                        params.put("num", String.valueOf(selectedImgFile));
                        params.put("admin", sessionManager.getUserName());
                        params.put("uid", strDescription);
                        params.put("photo", GroupIcon);
                        params.put("dt", formattedDate);
                        return params;
                    }
                };
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                RequestQueue requestQueue = Volley.newRequestQueue(SelectGroupContact.this);
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

    private void attemptSend(String message, String rid, String type) {
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

    public String UploadFile(String path, String UserId, String Extention, ArrayList<String> users) {
        progressDialog.show();
        String FileName = UserId + "_" + getSaltString() + Extention;
        String key = "JEOWPS5PEY4E5X6J42F4";
        String secret = "U4Se+JKztU5tz8Vjz/SMpUNJwd3Xz+Z2VBlPe8ANrAg";
        credentials = new BasicAWSCredentials(key, secret);
        s3 = new AmazonS3Client(credentials);
        s3.setEndpoint("sgp1.digitaloceanspaces.com");
        transferUtility = new TransferUtility(s3, SelectGroupContact.this);
        File file = new File(path);
        Log.d("AUDIO_PATH", path + " : " + file);
        if (!file.exists()) {
            Toast.makeText(SelectGroupContact.this, "File Not Found!", Toast.LENGTH_SHORT).show();
            return null;
        }
        observer = transferUtility.upload(
                "udtalks",
                FileName,
                file,
                CannedAccessControlList.PublicRead
        );
        observer.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                if (state.COMPLETED.equals(observer.getState())) {
                    //  Toast.makeText(SelectGroupContact.this, "File Upload Complete", Toast.LENGTH_SHORT).show();
                    createContact(Confiq.DOCUMNMENT_URL_AWS + FileName, users);
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                long _bytesCurrent = bytesCurrent;
                long _bytesTotal = bytesTotal;
                float percentage = ((float) _bytesCurrent / (float) _bytesTotal * 100);
                Log.d("percentage", "" + percentage);
                //  pb.setProgress((int) percentage);
                //  _status.setText(percentage + "%");
            }

            @Override
            public void onError(int id, Exception ex) {
                progressDialog.dismiss();
                Toast.makeText(SelectGroupContact.this, "" + ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return FileName;
    }

}