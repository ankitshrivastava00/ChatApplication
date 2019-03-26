package com.ziasy.xmppchatapplication;

/**
 * Created by Khushvinders on 15-Nov-16.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.ziasy.xmppchatapplication.ChatModulSocket.ChatApplication;
import com.ziasy.xmppchatapplication.common.AlwaysRunning;
import com.ziasy.xmppchatapplication.common.DrawableClickListener;
import com.ziasy.xmppchatapplication.common.SessionManagement;
import com.ziasy.xmppchatapplication.group_chat.activity.CreateGroupActivity;
import com.ziasy.xmppchatapplication.localDB.LocalDBHelper;
import com.ziasy.xmppchatapplication.util.ui.CustomEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import me.pushy.sdk.Pushy;

public class ChatUserListActivity extends AppCompatActivity implements OnClickListener {

    private List<ChatUserList> users;
    ChatUserList chatUserList;

    private boolean clickStatus = true;
    private ListView userList;
    private ConnectionDetector cd;
    private SessionManagement sd;
    private ProgressDialog pd;
    private ChatUserListAdapter chatAdapter;
    private RelativeLayout ll_emoji;
    private Toolbar toolbar;
    private ImageView iv_tab_user, iv_emoji, iv_search;
    private LinearLayout userLayout;
    private String type, msg;
    private PopupWindow popupWindow;
    private View back_view;
    private LinearLayout addUserLinear, addGroupLinear;
    private CustomEditText searchEt;
    //  private TextView tv_contacts_count;
    private Socket mSocket;
    private LinearLayout addGroup, addUserFromUD, addUserFromPhone, scanUser, searchLayout;
    LocalDBHelper localDBHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_list);
        Intent i=new Intent(ChatUserListActivity.this, AlwaysRunning.class);
        startService(i);
        Pushy.listen(ChatUserListActivity.this);
        localDBHelper= new LocalDBHelper(ChatUserListActivity.this);
        toolbar = (Toolbar) findViewById(R.id.tb_chatlist);
        iv_tab_user = (ImageView) findViewById(R.id.iv_add_user);
        searchEt = (CustomEditText) findViewById(R.id.searchEt);
        iv_search = (ImageView) findViewById(R.id.iv_search);
        iv_emoji = (ImageView) findViewById(R.id.iv_emoji);
        back_view = (View) findViewById(R.id.back_view);
        userList = (ListView) findViewById(R.id.userList);
        userLayout = (LinearLayout) findViewById(R.id.userLayout);
        searchLayout = (LinearLayout) findViewById(R.id.searchLayout);
        ll_emoji = (RelativeLayout) findViewById(R.id.ll_emoji);
        //   tv_contacts_count = (TextView) findViewById(R.id.tv_contacts_count);
        users = new ArrayList<ChatUserList>();
        cd = new ConnectionDetector(ChatUserListActivity.this);
        sd = new SessionManagement(ChatUserListActivity.this);
        pd = new ProgressDialog(ChatUserListActivity.this);
        pd.setCancelable(false);
        pd.setMessage("Please Wait");
        if (!cd.isConnectingToInternet()) {
            Snackbar.make(findViewById(android.R.id.content), "Internet Connection not available..", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
            Log.d("No_internet_data", localDBHelper.getAllChatList().toString());
            users=localDBHelper.getAllChatList();
            chatAdapter = new ChatUserListAdapter(ChatUserListActivity.this, R.layout.chat_list_item, localDBHelper.getAllChatList());
            userList.setAdapter(chatAdapter);
            ll_emoji.setVisibility(View.GONE);
            userList.setVisibility(View.VISIBLE);
        } else {
            ChatApplication app = (ChatApplication) getApplication();
            mSocket = app.getSocket();
            mSocket.on("recieveMessage", onNewMessage);
            mSocket.on("chatEmployeeList", onSendMyMessage);
            mSocket.on("chatCountListDetails", onChatCount);
        }
        iv_search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickStatus) {
                    clickStatus = false;
                    iv_search.setImageResource(R.drawable.search_activte);
                    searchLayout.setVisibility(View.VISIBLE);

                } else {
                    iv_search.setImageResource(R.drawable.search_inactive);
                    searchLayout.setVisibility(View.GONE);
                    clickStatus = true;
                    searchEt.setText("");
                    if (TextUtils.isEmpty(searchEt.getText().toString().trim())) {
                        if (chatAdapter != null) {
                            chatAdapter.filter("");
                        }
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
                            if (chatAdapter != null) {
                                chatAdapter.filter("");
                            }
                        }
                        break;

                    default:
                        break;
                }
            }

        });


        iv_tab_user.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.add_user, null,false);
                // Create popup window.

                //close the popup window on button click
                iv_tab_user.setImageResource(R.drawable.dot_activite);
                iv_search.setImageResource(R.drawable.search_inactive);
                searchLayout.setVisibility(View.GONE);

                clickStatus = true;
                back_view.setVisibility(View.VISIBLE);
                popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                // Set popup window animation style.
                popupWindow.setAnimationStyle(R.style.popup_window_animation_sms);

                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

                popupWindow.setFocusable(true);

                popupWindow.setOutsideTouchable(true);

                popupWindow.update();
                // Show popup window offset 1,1 to smsBtton.
                popupWindow.showAsDropDown(toolbar, 1, 1);

                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        back_view.setVisibility(View.GONE);
                        iv_tab_user.setImageResource(R.drawable.dot_inactivity);
                        iv_search.setImageResource(R.drawable.search_inactive);
                        searchLayout.setVisibility(View.GONE);
                        clickStatus = true;
                    }
                });

                addGroup = (LinearLayout) customView.findViewById(R.id.addGroup);
                addUserFromUD = (LinearLayout) customView.findViewById(R.id.addUserFromUD);
                addUserFromPhone = (LinearLayout) customView.findViewById(R.id.addUserFromPhone);
                scanUser = (LinearLayout) customView.findViewById(R.id.scanUser);

                addGroup.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        Intent i = new Intent(ChatUserListActivity.this, CreateGroupActivity.class);
                        startActivity(i);
                    }
                });
                addUserFromUD.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        Intent i = new Intent(ChatUserListActivity.this, UserListActivity.class);
                        startActivity(i);
                    }
                });
                addUserFromPhone.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                scanUser.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
            }
        });
        userLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChatUserListActivity.this, UDTeamMessageActivity.class);
                startActivity(i);
            }
        });
        // getUserList();
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
                    if (chatAdapter != null) {
                        chatAdapter.filter("");
                    }
                } else {
                    if (chatAdapter != null) {
                        chatAdapter.filter(searchEt.getText().toString().trim());
                    }
                }
            }

        });
    }

    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSocket!=null) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", sd.getKeyId());
            jsonObject.addProperty("isOnlineStatus", "false");
            mSocket.emit("disableUser", jsonObject);
            mSocket.disconnect();
        }
    }
    /*
    @Override
    protected void onPause() {
        super.onPause();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", sd.getKeyId());
        jsonObject.addProperty("isOnlineStatus", "false");
        mSocket.emit("disableUser", jsonObject);
    }

    @Override
    protected void onStop() {
        super.onStop();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", sd.getKeyId());
        jsonObject.addProperty("isOnlineStatus", "false");
        mSocket.emit("disableUser", jsonObject);
    }*/

    @Override
    protected void onStart() {
        super.onStart();
        if (!cd.isConnectingToInternet()) {
            Snackbar.make(findViewById(android.R.id.content), "Internet Connection not available..", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
        } else {
            if (mSocket==null) {
                ChatApplication app = (ChatApplication) getApplication();
                mSocket = app.getSocket();
            }
            mSocket.connect();
            JsonObject jsonObject1 = new JsonObject();
            jsonObject1.addProperty("senderid", sd.getKeyId());
            mSocket.emit("ChatemployeeListDetail", jsonObject1);
            //   mSocket.emit("chatCountList", jsonObject1);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", sd.getKeyId());
            jsonObject.addProperty("isChatEnable", "false");
            jsonObject.addProperty("isDelivered", "false");
            jsonObject.addProperty("isReaded", "false");
            jsonObject.addProperty("isOnlineStatus", "true");
            mSocket.emit("userid", jsonObject);
            //convertDrawableToByte();
        }
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            JsonObject jsonObject1 = new JsonObject();
            jsonObject1.addProperty("senderid", sd.getKeyId());
            mSocket.emit("ChatemployeeListDetail", jsonObject1);
        }
    };

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
                       // Log.d("RESULT_LIST_RESPONCE", data + "");
                        // Log.e("RESULT_LIST_RESPONCE", data + "");
                        JSONArray arr = data.getJSONArray("result");

                        for (int i = 0; i < arr.length(); i++) {
                            ChatUserList allPrdctData = new ChatUserList();
                            JSONObject obj1 = arr.getJSONObject(i);
                            String id = obj1.getString("id");
                            String name = obj1.getString("name");
                            String message = obj1.getString("message");
                            String photo = obj1.getString("photo");
                            String description = obj1.optString("description");
                            String admin = obj1.optString("admin");
                            String userstatus = obj1.getString("userstatus");
                            String datetime = obj1.getString("datetime");
                            String dtype = obj1.getString("dtype");
                            String chattype = obj1.getString("chattype");
                            String did = obj1.getString("deviceid");

                          //  if (!id.equalsIgnoreCase(sd.getKeyId()) && !id.equalsIgnoreCase("0")&&!chattype.equalsIgnoreCase("group")) {
                                allPrdctData.setId(id);
                                allPrdctData.setName(name);
                                allPrdctData.setDescription(description);
                                allPrdctData.setLastMessage(message);
                                allPrdctData.setDatetime(datetime);
                                allPrdctData.setIsOnline(userstatus);
                                allPrdctData.setTime(timeConverter(datetime));
                                allPrdctData.setImageUrl(photo);
                                allPrdctData.setType(dtype);
                                allPrdctData.setData(message);
                                allPrdctData.setChattype(chattype);
                                allPrdctData.setDid(did);
                                allPrdctData.setAdmin(admin);
                                users.add(allPrdctData);

                                try{
                                    localDBHelper.insertChatList(allPrdctData);

                                }catch (Exception e){
                                    Log.d("Sqlite_Exception",e.toString());
                                }

                            Log.d("ANIKET_LOCALDB", allPrdctData.toString());
                          //  }
                        }
                        pd.dismiss();
                        if (users.size() == 0) {
                            ll_emoji.setVisibility(View.VISIBLE);
                            userList.setVisibility(View.GONE);
                            //  tv_contacts_count.setText(users.size()+" Chat");
                        } else {
                            ll_emoji.setVisibility(View.GONE);
                            userList.setVisibility(View.VISIBLE);
                            Collections.sort(users, ChatUserList.StuNameComparator);
                            //   tv_contacts_count.setText(users.size()+" Chat");

                            chatAdapter = new ChatUserListAdapter(ChatUserListActivity.this, R.layout.chat_list_item, users);
                            userList.setAdapter(chatAdapter);
                        }

/*                        chatAdapter = new ChatUserListAdapter(ChatUserListActivity.this,R.layout.showuser_swipe_row, users);
                        userList.setAdapter(chatAdapter);*/
                    } catch (Exception e) {
                        e.printStackTrace();
                        pd.dismiss();
                    }
                }
            });
            Log.d("COUNT", "COUNTING");
            JsonObject jsonObject1 = new JsonObject();
            jsonObject1.addProperty("senderid", sd.getKeyId());
            mSocket.emit("chatCountList", jsonObject1);
        }
    };

    public void deleteUser(String Id,int position) {
        try {
            if (!cd.isConnectingToInternet()) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "No Internet Connection", Snackbar.LENGTH_LONG)
                        .setAction("Ok", null);
                View sbView = snackbar.getView();
                sbView.setBackgroundColor(Color.BLUE);
                snackbar.show();

            } else {
                pd.show();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://128.199.222.145/reg/delchat.php",
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
                                pd.dismiss();
                                if (result.equalsIgnoreCase("Success")){
                                    // finish();
                                 /*   users.remove(position);
                                    chatAdapter.notifyDataSetChanged();*/
                                    JsonObject jsonObject1 = new JsonObject();
                                    jsonObject1.addProperty("senderid", sd.getKeyId());
                                    mSocket.emit("ChatemployeeListDetail", jsonObject1);
                                }
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
                        params.put("sid", sd.getKeyId());
                        params.put("rid", Id);

                        return params;
                    }
                };

                stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                RequestQueue requestQueue = Volley.newRequestQueue(ChatUserListActivity.this);
                requestQueue.add(stringRequest);
            }
        } catch (Exception e) {
            e.printStackTrace();
            pd.dismiss();

        }
    }

    public void leftGroup(String Id) {
        try {
            if (!cd.isConnectingToInternet()) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "No Internet Connection", Snackbar.LENGTH_LONG)
                        .setAction("Ok", null);
                View sbView = snackbar.getView();
                sbView.setBackgroundColor(Color.BLUE);
                snackbar.show();

            } else {
                pd.show();
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
                                pd.dismiss();
                                if (result.equalsIgnoreCase("Sucess")){
                                    // finish();
                                    attemptSend(sd.getUserName(),"remove",Id);
                                }
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
                        params.put("id", Id);
                        params.put("num", sd.getUserName());

                        return params;
                    }
                };

                stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                RequestQueue requestQueue = Volley.newRequestQueue(ChatUserListActivity.this);
                requestQueue.add(stringRequest);
            }
        } catch (Exception e) {
            e.printStackTrace();
            pd.dismiss();

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

    private void attemptSend(String message, String type,String rid) {
        ChatApplication app = (ChatApplication) getApplication();
        mSocket = app.getSocket();
        mSocket.connect();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        JsonObject object = new JsonObject();
        object.addProperty("reciverid",rid);
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

    private Emitter.Listener onChatCount = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    Log.d("OFFLINE_DATA", data + "");
                    try {
                        JSONArray jsonArray = data.getJSONArray("result");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj1 = jsonArray.getJSONObject(i);
                            String id = obj1.getString("id");
                            String count = obj1.getString("count");

                            for (ChatUserList model : users) {
                                if (model.getId().equalsIgnoreCase(id)) {
                                    model.setCount(count);
                                } else {
                                    model.setCount("0");
                                }
                            }
                            if (chatAdapter != null) {
                                chatAdapter.notifyDataSetChanged();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    private String timeConverter(String input) {
        String output = null;
        DateFormat outputformat = null;
        Date date = null;
        if (!input.equalsIgnoreCase("null")) {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat currnetDate = new SimpleDateFormat("yyyy-MM-dd");
            String today = currnetDate.format(c.getTime());
            c.add(Calendar.DATE, -1);
            String yesterday = currnetDate.format(c.getTime());

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //Date/time pattern of desired output date
            outputformat = new SimpleDateFormat("yyyy-MM-dd");

            try {
                //Conversion of input String to date
                date = df.parse(input);
                output = outputformat.format(date);
                if (today.equalsIgnoreCase(output)) {
                    outputformat = new SimpleDateFormat("hh:mm aa");
                    output = outputformat.format(date);
                } else if (yesterday.equalsIgnoreCase(output)) {
                    output = "YESTERDAY";
                } else {
                    output = outputformat.format(date);
                }

                //old date format to new date format

                System.out.println(output);
                Log.d("OUTPUT_DATE", output);
            } catch (ParseException pe) {
                pe.printStackTrace();
            }
        }
        return output;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

}