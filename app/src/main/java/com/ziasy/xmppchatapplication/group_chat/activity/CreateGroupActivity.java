package com.ziasy.xmppchatapplication.group_chat.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ziasy.xmppchatapplication.ChatActivity;
import com.ziasy.xmppchatapplication.R;
import com.ziasy.xmppchatapplication.common.Permission;
import com.ziasy.xmppchatapplication.multiimage.activities.AlbumSelectActivity;
import com.ziasy.xmppchatapplication.multiimage.helpers.Constants;

import de.hdodenhof.circleimageview.CircleImageView;

public class CreateGroupActivity extends AppCompatActivity {
    private EditText EdtNameGroup, EdtDescriptionGroup;
    private Button btnAddGroup;
    private String imagePath = null;
    private CircleImageView groupIcon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_group_name);
        EdtNameGroup = (EditText) findViewById(R.id.EdtNameGroup);
        btnAddGroup = (Button) findViewById(R.id.btnAddGroup);
        groupIcon = (CircleImageView) findViewById(R.id.groupIcon);
        EdtDescriptionGroup = (EditText) findViewById(R.id.EdtDescriptionGroup);
        groupIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean attached_file1 = Permission.checkReadExternalStoragePermission(CreateGroupActivity.this);
                boolean image_permission_write = Permission.permissionForExternal(CreateGroupActivity.this);
                if (attached_file1) {
                    if (image_permission_write) {
                        try {

                            Intent galleryintent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(galleryintent, 143);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        btnAddGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strName = EdtNameGroup.getText().toString().trim();
                String strDescription = EdtDescriptionGroup.getText().toString().trim();
                if (strName.isEmpty()) {
                    EdtNameGroup.setError("Please Enter Group Name");
                } else if (strDescription.isEmpty()) {
                    EdtDescriptionGroup.setError("Please Enter Description");

                } else
                {
                    if (imagePath==null ) {
                        imagePath="No Image Found";
                            }
                    Intent i = new Intent(CreateGroupActivity.this, SelectGroupContact.class);
                    i.putExtra("group_name", strName);
                    i.putExtra("group_description", strDescription);
                    i.putExtra("imagePath", imagePath);
                    startActivity(i);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 143 && resultCode == RESULT_OK
                && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            // Move to first row
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            imagePath = cursor.getString(columnIndex);
            groupIcon.setImageURI(selectedImage);
            cursor.close();
            //Log.d("onActivityResult",imgDecodableString);
            // sendImage(imgDecodableString);
            Log.d("imgDecodable", imagePath);


        }
    }
}
