package com.ziasy.xmppchatapplication.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ziasy.xmppchatapplication.R;
import com.ziasy.xmppchatapplication.adapter.ContactAdapter;
import com.ziasy.xmppchatapplication.common.Permission;
import com.ziasy.xmppchatapplication.model.ContactModel;

import java.util.ArrayList;
import java.util.List;

public class GetContactFromMobileActivity extends AppCompatActivity {
    private ListView contactList;
    private List<ContactModel> list;
    private ContactAdapter adapter;
    private TextView txtName;
    private ImageView iv_back;
    private LinearLayout searchLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userlist_layout);
        contactList = (ListView) findViewById(R.id.userList);
        txtName = (TextView) findViewById(R.id.tv_toolbar_title);
        searchLayout = (LinearLayout) findViewById(R.id.searchLayout);
        searchLayout.setVisibility(View.GONE);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        list = new ArrayList<>();

        GetContactsIntoArrayList();
        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("CONTATC_LISTDD","CLICK");
                // Toast.makeText(EmojiActivity.this, "Position " + i  +"EMOJI RESOURCE ID "+list.get(i), Toast.LENGTH_SHORT).show();
                /*Intent intent = new Intent();
                intent.putExtra("image", list.get(i));
                setResult(RESULT_OK, intent);
                finish();*/
            }
        });
       /* contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              //  Toast.makeText(GetContactFromMobileActivity.this, "POSITOIN" + list.get(i), Toast.LENGTH_SHORT).show();
                Log.e("CONTATC_LISTDD","CLICK");
            }
        });*/
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        txtName.setText("Contact");
    }

    private void GetContactsIntoArrayList() {

        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        while (cursor.moveToNext()) {


            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            String phonenumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            list.add(new ContactModel(name, phonenumber, ""));
        }
        adapter = new ContactAdapter(GetContactFromMobileActivity.this, R.layout.contact_item, list);
        contactList.setAdapter(adapter);
        cursor.close();

    }

}
