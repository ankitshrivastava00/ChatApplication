package com.ziasy.xmppchatapplication.group_chat.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.ziasy.xmppchatapplication.ChatModulSocket.ChatApplication;
import com.ziasy.xmppchatapplication.ChatUserListActivity;
import com.ziasy.xmppchatapplication.ConnectionDetector;
import com.ziasy.xmppchatapplication.R;
import com.ziasy.xmppchatapplication.common.SessionManagement;
import com.ziasy.xmppchatapplication.group_chat.adapter.ContactRecyclerAdapter;
import com.ziasy.xmppchatapplication.group_chat.model.Contact;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class CreateContactActivity extends AppCompatActivity implements ContactRecyclerAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private ContactRecyclerAdapter adapter;
    private ArrayList<Contact> list;
    private SessionManagement sessionManager;
    private ProgressDialog progressDialog;
    private RelativeLayout view_career;
    private LinearLayout linearTop;
    private TextView userNumber;
    private ConnectionDetector cd;
    private Socket mSocket;
    private String strGroupName,strGroupDescription;
    private StringBuffer selectedImgFile;
    ArrayList<Integer> contacts;
    private int count = 0;
    private String eid, number, status, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        sessionManager = new SessionManagement(CreateContactActivity.this);
        cd = new ConnectionDetector(CreateContactActivity.this);
        progressDialog = new ProgressDialog(CreateContactActivity.this);
        progressDialog.setMessage("Create a Group");
        progressDialog.setCancelable(false);
        ChatApplication app = (ChatApplication) getApplication();
        mSocket = app.getSocket();
        mSocket.on("employeeListDetail", onSendMyMessage);
        selectedImgFile = new StringBuffer();
        selectedImgFile.append(sessionManager.getUserName());

      /*  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        strGroupName = getIntent().getStringExtra("group_name");
        strGroupDescription = getIntent().getStringExtra("group_name");

        view_career = (RelativeLayout) findViewById(R.id.view_career);
        linearTop = (LinearLayout) findViewById(R.id.linearTop);
        userNumber = (TextView) findViewById(R.id.userNumber);
        userNumber.setText(count + " Member Select");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        view_career.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> contacts = new ArrayList<>();
                ArrayList<String> contactsId = new ArrayList<>();
                ArrayList<Contact> contactList = new ArrayList<>();
                for (Contact contact : adapter.getDatas()) {
                    if (contact.isSelected()) {
                        contacts.add(contact.getName());
                        contactsId.add(contact.getId());
                        contactList.add(contact);
                    }
                }
                Log.e("CONTACTS_STRING", contacts.toString() + "");
                Log.e("CONTACT_LIST", contactList.toString() + "");
                createContact(contacts, contactsId, contactList);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation()));
        list = new ArrayList<>();
        contacts = new ArrayList<>();
        adapter = new ContactRecyclerAdapter(this, list, this);
        recyclerView.setAdapter(adapter);
        if (!cd.isConnectingToInternet()) {
            Snackbar.make(findViewById(android.R.id.content), "Internet Connection not available..", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
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
        //  getContacts();
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

    private Emitter.Listener onSendMyMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        list.clear();
                        //progressDialog.dismiss();
                        JSONObject data = (JSONObject) args[0];
                  //      Log.e("DATATA", data.toString());
                        JSONArray arr = data.getJSONArray("result");
                        for (int i = 0; i < arr.length(); i++) {
                            Contact allPrdctData = new Contact();
                            JSONObject obj1 = arr.getJSONObject(i);
                            String id = obj1.getString("id");
                            String name = obj1.getString("name");
                            //  String count = obj1.getString("count");
                            String photo = obj1.getString("photo");
                            String path = obj1.getString("path");
                            //   String datetime = obj1.getString("datetime");
                            if (!id.equalsIgnoreCase(sessionManager.getKeyId())&&!name.trim().equalsIgnoreCase("")) {
                                allPrdctData.setId(id);
                                allPrdctData.setName(name);
                                allPrdctData.setUsername(name);
                                //allPrdctData.set("");
                                //allPrdctData.setImageUrl(path + photo);
                                list.add(allPrdctData);
                            }
                        }
                        // Collections.sort(users, User.StuNameComparator);
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                        //pd.dismiss();
                    }
                }
            });
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(
                R.menu.menu_contact, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_create_chat) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(View view, int position) {
        // Toast.makeText(CreateContactActivity.this,"Position " +position,Toast.LENGTH_SHORT).show();
        adapter.getItem(position).setSelected(!adapter.getItem(position).isSelected());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCheckedChange(CompoundButton buttonView, boolean isChecked, int position) {
        adapter.getItem(position).setSelected(isChecked);
        adapter.notifyDataSetChanged();
        if (isChecked) {
            count++;
            userNumber.setText(count + " Member Select");
        } else {
            try {
                count--;
                userNumber.setText(count + " Member Select");
            } catch (Exception e) {
                e.printStackTrace();
                userNumber.setText(count + " Member Select");
            }
        }

    }

    private void createContact(ArrayList<String> users, ArrayList<String> usersId, final ArrayList<Contact> contactList) {
        try {
            if (!cd.isConnectingToInternet()) {
                Snackbar.make(findViewById(android.R.id.content), "No Internet Connection", Snackbar.LENGTH_SHORT).show();
            } else {
                progressDialog.show();

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
                                        Intent i = new Intent(CreateContactActivity.this, ChatUserListActivity.class);
                                        startActivity(i);
                                        ActivityCompat.finishAffinity(CreateContactActivity.this);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
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
                       // params.put("uid", );
                        return params;
                    }
                };
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                RequestQueue requestQueue = Volley.newRequestQueue(CreateContactActivity.this);
                requestQueue.add(stringRequest);
            }
        } catch (Exception e) {
            e.printStackTrace();
            progressDialog.dismiss();
        }
    }
}