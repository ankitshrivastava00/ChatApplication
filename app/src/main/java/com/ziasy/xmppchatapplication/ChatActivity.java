package com.ziasy.xmppchatapplication;

import android.content.ContentProviderOperation;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.RawContacts;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.devlomi.record_view.OnBasketAnimationEnd;
import com.devlomi.record_view.OnRecordListener;
import com.devlomi.record_view.RecordButton;
import com.devlomi.record_view.RecordView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.gson.JsonObject;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.PlaceAutocomplete;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.model.PlaceOptions;
import com.mindorks.paracamera.Camera;
//import com.vincent.videocompressor.VideoCompress;


import com.ziasy.xmppchatapplication.ChatModulSocket.ChatApplication;
import com.ziasy.xmppchatapplication.activity.CareerContactListActivity;
import com.ziasy.xmppchatapplication.activity.EmojiActivity;
import com.ziasy.xmppchatapplication.activity.ForwardActivity;
import com.ziasy.xmppchatapplication.attachment.activity.AudioFileList;
import com.ziasy.xmppchatapplication.attachment.activity.FileList;
import com.ziasy.xmppchatapplication.attachment.activity.VideoFileList;
import com.ziasy.xmppchatapplication.common.AlertDialogHelper;
import com.ziasy.xmppchatapplication.common.Confiq;
import com.ziasy.xmppchatapplication.common.DrawableClickListener;
import com.ziasy.xmppchatapplication.common.Permission;
import com.ziasy.xmppchatapplication.common.RecyclerItemClickListener;
import com.ziasy.xmppchatapplication.common.SessionManagement;
import com.ziasy.xmppchatapplication.converstion.ActivityResult;
import com.ziasy.xmppchatapplication.database.DBUtil;
import com.ziasy.xmppchatapplication.localDB.LocalDBHelper;
import com.ziasy.xmppchatapplication.localDB.LocalFileManager;
import com.ziasy.xmppchatapplication.media_tools_magic.EditImageActivity;
import com.ziasy.xmppchatapplication.model.JsonModelForChat;
import com.ziasy.xmppchatapplication.multiimage.helpers.Constants;
import com.ziasy.xmppchatapplication.multiimage.models.Image;
import com.ziasy.xmppchatapplication.network.ApiClass;
import com.ziasy.xmppchatapplication.network.ApiInterface;

import com.ziasy.xmppchatapplication.util.ui.CustomEditText;
import com.ziasy.xmppchatapplication.video_compressor.videocompression.MediaController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import github.ankushsachdeva.emojicon.EmojiconEditText;

import java.io.File;
import java.util.concurrent.TimeUnit;

import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import java.net.URISyntaxException;

import android.widget.PopupWindow.OnDismissListener;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.ziasy.xmppchatapplication.common.Permission.EMOJI_RESULTS;
import static com.ziasy.xmppchatapplication.video_compressor.Util.getFilePath;

public class ChatActivity extends AppCompatActivity implements OnClickListener, AlertDialogHelper.AlertDialogListener, GoogleApiClient.OnConnectionFailedListener {
    // AWS CREADANTIAL
    private AmazonS3Client s3;
    File rootAudio;
    private BasicAWSCredentials credentials;
    private TransferUtility transferUtility;
    private TransferObserver observer;
    // Recording Component
// Camera Picker For Images
    private static final String TAG = "ChatActivity";
    private Camera camera;
    private ImageView imageViewRecord, imageViewStop;
    private MediaRecorder mRecorder;
    private String fileName = null;
    private String firstDate = "";
    private static final int PLACE_PICKER_REQUEST = 169;
    private static final int REQUEST_CODE_AUTOCOMPLETE = 170;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_IMAGE = 201;
    private static final int MY_PERMISSIONS_GET_AUDIO_PERMISSION = 202;
    private static final int VIDEO_CAPTURED = 203;
    private static final int MY_PERMISSIONS_GET_VIDEO = 204;
    private static final int MY_PERMISSIONS_CONTACT_SHARE = 205;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private String videoPath = null;
    private EmojiconEditText msg_edittext;
    private RecordButton recordButton;
    private List<JsonModelForChat> list;
    private String rid, username;
    private ArrayList<String> forwardString = null, strChatType = null;
    private static ChatAdapter chatAdapter;
    private RecyclerView msgListView;
    private LinearLayoutManager layoutManager;
    private ConnectionDetector cd;
    private SessionManagement sd;
    private String did = null;
    //private ProgressDialog progressDialog;
    private Toolbar mToolbar;
    private ApiInterface apiInterface;
    private TextView txt_name, txt_online_status;
    private ImageView smileyBtn, ImageAttachment, ImageSetting, imageBack, sendButton, sendVoiceRecording;
    private StringBuffer selectedImgFile;
    private StringBuffer selectedImgFilePath;
    private PopupWindow popupWindow, popupWindowForSetting;
    private RelativeLayout chatName;
    private CircleImageView chatProfilePic;
    private LinearLayout hideEditText;
    private ImageView backImg;
    // PopLayoutForAddAttachment
    private LinearLayout SellPost, BuyPost, MomentPick, ImageUpload, TakePicture, Video, TakeVideo, MediaTools, Location, ContactInfo, FileUpload, AudioFile;
    //PopLayoutForChatSetting
    private LinearLayout clearChatLinear, addContactLinear, searchLinear, mediaLinear, notificationLinear, wallpaperLinear;
    //MEDIA PLAYER
    private LinearLayout searchLayout;
    // Socket IO
    private boolean mTyping = false;
    private Handler mTypingHandler = new Handler();
    private Socket mSocket;
    private TextView typing_txt;
    private static final int TYPING_TIMER_LENGTH = 600;
    private String imgDecodableString;
    private boolean isMultiSelect = false;
    private AlertDialogHelper alertDialogHelper;
    //  TOOLBAR CHANGE
    private LinearLayout linear_chat_option;
    private ImageView btnForwordCab, btndeleteCab, cab_bomb_btn, cab_copy_btn, btnReplyCab;
    private ArrayList<JsonModelForChat> multiselect_list;
    private ArrayList<String> copy_list;
    private ClipboardManager myClipboard;
    private ClipData myClip;
    private TextView cancelSearch;
    private CustomEditText searchEt;
    private ProgressDialog progressDialog;
    private LocalFileManager localFileManager;
    private LocalDBHelper localDBHelper;
    private String localPath= null,mode= "online";

    public void compressImage(File mainFile) {

        new Compressor(this)
                .compressToFileAsFlowable(mainFile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<File>() {
                    @Override
                    public void accept(File file) {
                        //   compressedImage = file;
                        // setCompressedImage();
                        UploadFile(file, sd.getKeyId(), ".jpg", "img");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        throwable.printStackTrace();
                        //     showError(throwable.getMessage());
                        Log.e("swerewrwe", throwable.getMessage());
                    }
                });
    }

    private BroadcastReceiver onNotice = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("Internet_check", "onReceive called");
            if (intent.getStringExtra("key").equalsIgnoreCase("NO")) {
                mSocket.disconnect();
            } else {
                mSocket.connect();
                sendOfflineMessage();
            }
        }
    };

    private void sendOfflineMessage() {
      //  progressDialog.show();
        List<JsonModelForChat> offlineList=localDBHelper.getAllGroupMessage(rid);
        for(int i=0;i<offlineList.size();i++){
            if (offlineList.get(i).getStatus().equals("offline")){
                attemptSend(offlineList.get(i).getMessage(),offlineList.get(i).getType());
            }
        }
       // progressDialog.dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);
        //  Toast.makeText(ChatActivity.this, "onCreate()", Toast.LENGTH_SHORT).show();
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        Mapbox.getInstance(this, getString(R.string.access_token));
        alertDialogHelper = new AlertDialogHelper(ChatActivity.this);
        progressDialog = new ProgressDialog(ChatActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        ChatApplication app = (ChatApplication) getApplication();
        mSocket = app.getSocket();
        IntentFilter iff = new IntentFilter("INTERNET");
        LocalBroadcastManager.getInstance(this).registerReceiver(onNotice, iff);
        backImg = (ImageView) findViewById(R.id.backImg);
        final View rootView = findViewById(R.id.personalChatLayout);
        hideEditText = (LinearLayout) findViewById(R.id.hideSliding);
        searchLayout = (LinearLayout) findViewById(R.id.searchLayout);
        searchLayout.setVisibility(View.GONE);
        btnForwordCab = (ImageView) findViewById(R.id.btnForwordCab);
        btndeleteCab = (ImageView) findViewById(R.id.btndeleteCab);
        cancelSearch = (TextView) findViewById(R.id.cancelSearch);
        searchEt = (CustomEditText) findViewById(R.id.searchEt);
        cab_bomb_btn = (ImageView) findViewById(R.id.cab_bomb_btn);
        cab_copy_btn = (ImageView) findViewById(R.id.cab_copy_btn);
        btnReplyCab = (ImageView) findViewById(R.id.btnReplyCab);
        localDBHelper= new LocalDBHelper(this);
        //   btnForwordCab, btndeleteCab, cab_bomb_btn, cab_copy_btn ;

        btnReplyCab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ChatActivity.this, "On Progress", Toast.LENGTH_SHORT).show();
            }
        });

        btnForwordCab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (multiselect_list.size() > 0) {

                    ArrayList<String> stringBuilderTpye = new ArrayList<>();
                    ArrayList<String> stringBuilderMessage = new ArrayList<String>();
                    for (int i = 0; i < multiselect_list.size(); i++) {
                        stringBuilderTpye.add(multiselect_list.get(i).getType());
                        stringBuilderMessage.add(multiselect_list.get(i).getMessage());
                    }

                    linear_chat_option.setVisibility(View.GONE);
                    isMultiSelect = false;
                    multiselect_list = new ArrayList<JsonModelForChat>();
                    chatAdapter.selected_usersList = multiselect_list;
                    chatAdapter.notifyDataSetChanged();

                    Intent intent = new Intent(ChatActivity.this, ForwardActivity.class);
                    intent.putStringArrayListExtra("forwardString", stringBuilderMessage);
                    intent.putStringArrayListExtra("type", stringBuilderTpye);

                    startActivityForResult(intent, 88);

                    Toast.makeText(getApplicationContext(), "Forward", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Kya Hua", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btndeleteCab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (multiselect_list.size() > 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < multiselect_list.size(); i++) {
                        stringBuilder.append("," + "'" + multiselect_list.get(i).getMid() + "'");
                        list.remove(multiselect_list.get(i));
                    }
                    JsonObject jsonObject1 = new JsonObject();
                    jsonObject1.addProperty("id", sd.getKeyId());
                    jsonObject1.addProperty("uid", String.valueOf(stringBuilder));


                    Log.d("MIDMID", jsonObject1 + "");
                    mSocket.emit("deleteMessage", jsonObject1);
                    linear_chat_option.setVisibility(View.GONE);
                    isMultiSelect = false;
                    multiselect_list = new ArrayList<JsonModelForChat>();
                    chatAdapter.selected_usersList = multiselect_list;
                    chatAdapter.notifyDataSetChanged();


                    Toast.makeText(getApplicationContext(), "Delete", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Kya Hua", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cab_bomb_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogHelper.showAlertDialog("", "Delete Chat", "Delete For Everyone", "CANCEL", 1, false);

            }
        });

        cab_copy_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder stringBuilder = new StringBuilder();
                for (String s : copy_list) {
                    if (stringBuilder.length() == 0) {
                        stringBuilder.append(s);
                    } else {
                        stringBuilder.append("\n" + s);
                    }
                }
                myClip = ClipData.newPlainText("text", stringBuilder);
                myClipboard.setPrimaryClip(myClip);
                linear_chat_option.setVisibility(View.GONE);
                isMultiSelect = false;
                multiselect_list = new ArrayList<JsonModelForChat>();
                chatAdapter.selected_usersList = multiselect_list;
                chatAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Copy", Toast.LENGTH_SHORT).show();
                Log.d("COPYYY", copy_list.toString());
            }
        });
        cancelSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                searchLayout.setVisibility(View.GONE);
                searchEt.setText("");
                if (TextUtils.isEmpty(searchEt.getText().toString().trim())) {
                    if (chatAdapter != null) {
                        chatAdapter.filter("");
                    }
                }
            }
        });
        searchEt.setDrawableClickListener(new DrawableClickListener() {
            @Override
            public void onClick(DrawablePosition target) {
                switch (target) {
                    case RIGHT:
                        //Do something here

                        if (TextUtils.isEmpty(searchEt.getText().toString().trim())) {
                            if (chatAdapter != null) {
                                chatAdapter.filter("");
                            }
                        } else {
                            if (chatAdapter != null) {
                                chatAdapter.filter(searchEt.getText().toString().trim());
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        });
        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        // FOR ANIMATION RECORDING
        RecordView recordView = (RecordView) findViewById(R.id.record_view);
        recordButton = (RecordButton) findViewById(R.id.record_button);
        recordButton.setRecordView(recordView);
        recordView.setCancelBounds(8);
        recordView.setSmallMicColor(Color.parseColor("#c2185b"));
        //prevent recording under one Second
        recordView.setLessThanSecondAllowed(false);
        recordView.setSlideToCancelText("Slide To Cancel");
        recordView.setCustomSounds(R.raw.record_start, R.raw.record_finished, 0);
        recordView.setOnRecordListener(new OnRecordListener() {
            @Override
            public void onStart() {
                hideEditText.setVisibility(View.GONE);
                recordView.setVisibility(View.VISIBLE);
                boolean attached_file6 = Permission.checkReadExternalStoragePermission(ChatActivity.this);
                boolean recording = Permission.checkRecordPermission(ChatActivity.this);
                boolean recordingWrite = Permission.permissionForExternal(ChatActivity.this);
                if (attached_file6) {
                    if (recordingWrite) {
                        if (recording) {
                            try {
                                startRecording();
                                Log.d("RecordView", "onStart");
                                //       Toast.makeText(ChatActivity.this, "OnStartRecord", Toast.LENGTH_SHORT).show();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancel() {
                hideEditText.setVisibility(View.VISIBLE);
                recordView.setVisibility(View.GONE);
                boolean attached_file6 = Permission.checkReadExternalStoragePermission(ChatActivity.this);
                boolean recording = Permission.checkRecordPermission(ChatActivity.this);
                boolean recordingWrite = Permission.permissionForExternal(ChatActivity.this);
                if (attached_file6) {
                    if (recordingWrite) {
                        if (recording) {
                            Log.d("RecordView", "onCancel");
                            cancelRecording();

                        }
                    }
                }
            }

            @Override
            public void onFinish(long recordTime) {
                hideEditText.setVisibility(View.VISIBLE);
                recordView.setVisibility(View.GONE);
                boolean attached_file6 = Permission.checkReadExternalStoragePermission(ChatActivity.this);
                boolean recording = Permission.checkRecordPermission(ChatActivity.this);
                boolean recordingWrite = Permission.permissionForExternal(ChatActivity.this);
                if (attached_file6) {
                    if (recordingWrite) {
                        if (recording) {
                            String time = getHumanTimeText(recordTime);
                            // Toast.makeText(ChatActivity.this, "onFinishRecord - Recorded Time is: " + time, Toast.LENGTH_SHORT).show();
                            Log.d("RecordView", "onFinish");

                            Log.d("RecordTime", time);
                            stopRecording();
                        }
                    }
                }
            }

            @Override
            public void onLessThanSecond() {
                hideEditText.setVisibility(View.VISIBLE);
                recordView.setVisibility(View.GONE);
                boolean attached_file6 = Permission.checkReadExternalStoragePermission(ChatActivity.this);
                boolean recording = Permission.checkRecordPermission(ChatActivity.this);
                boolean recordingWrite = Permission.permissionForExternal(ChatActivity.this);
                if (attached_file6) {
                    if (recordingWrite) {
                        if (recording) {
                            //  Toast.makeText(ChatActivity.this, "OnLessThanSecond", Toast.LENGTH_SHORT).show();

                            cancelRecording();
                            Log.d("RecordView", "onLessThanSecond");
                        }
                    }
                }
            }
        });

        multiselect_list = new ArrayList<JsonModelForChat>();
        copy_list = new ArrayList<String>();

        recordView.setOnBasketAnimationEndListener(new OnBasketAnimationEnd() {
            @Override
            public void onAnimationEnd() {
                hideEditText.setVisibility(View.VISIBLE);
                recordView.setVisibility(View.GONE);
                boolean attached_file6 = Permission.checkReadExternalStoragePermission(ChatActivity.this);
                boolean recording = Permission.checkRecordPermission(ChatActivity.this);
                boolean recordingWrite = Permission.permissionForExternal(ChatActivity.this);
                if (attached_file6) {
                    if (recordingWrite) {
                        if (recording) {
                            Log.d("RecordView", "Basket Animation Finished");

                        }
                    }
                }
            }
        });
        // FOR RECORDING END
        selectedImgFile = new StringBuffer();
        selectedImgFilePath = new StringBuffer();
        sd = new SessionManagement(ChatActivity.this);
        rid = getIntent().getStringExtra("rid");
        username = getIntent().getStringExtra("name");
        forwardString = new ArrayList<>();
        strChatType = new ArrayList<>();
        forwardString = getIntent().getStringArrayListExtra("forwardString");
        linear_chat_option = (LinearLayout) findViewById(R.id.linear_chat_option);
        txt_name = (TextView) findViewById(R.id.nameTv);
        chatProfilePic = (CircleImageView) findViewById(R.id.chatProfilePic);
        smileyBtn = (ImageView) findViewById(R.id.smileyBtn);
        ImageAttachment = (ImageView) findViewById(R.id.attachmentBtn);
        imageBack = (ImageView) findViewById(R.id.imageBack);
        ImageSetting = (ImageView) findViewById(R.id.settingIv);
        chatName = (RelativeLayout) findViewById(R.id.chatName);
        txt_online_status = (TextView) findViewById(R.id.statusTv);
        txt_name.setText(username);
        typing_txt = (TextView) findViewById(R.id.typing_txt);
        // Socket IO
        mSocket.on("myMessage", onConnect);
        mSocket.on("onTyping", onTyping);
        mSocket.on("onStopTyping", onStopTyping);
        mSocket.on("recieveMessage", onNewMessage);
        mSocket.on("userMessage", AllMyMessage);
        mSocket.on("userOnline", onOnlineStatus);
        mSocket.on("userOffline", onOfflineStatus);
        mSocket.on("disabledUser", onDisable);
        mSocket.on("toggledUser", onReadStatus);
        mSocket.on("mySendMessage", onSendMyMessage);
        //Socket IO

        imageBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ImageSetting.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customViewSetting = layoutInflater.inflate(R.layout.setting_lauyout, null);
                // Create popup window.
                ImageSetting.setImageResource(R.drawable.setting_active);
                //close the popup window on button click

                popupWindowForSetting = new PopupWindow(customViewSetting, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

                // Set popup window animation style.
                popupWindowForSetting.setAnimationStyle(R.style.popup_window_animation_sms);
                popupWindowForSetting.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                popupWindowForSetting.setFocusable(true);
                popupWindowForSetting.setOutsideTouchable(true);
                popupWindowForSetting.update();
                popupWindowForSetting.showAsDropDown(mToolbar, 1, 1);
                popupWindowForSetting.update();

                // Show popup window offset 1,1 to smsBtton.
                popupWindowForSetting.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        ImageSetting.setImageResource(R.drawable.setting_inactive);
                    }
                });

                addContactLinear = (LinearLayout) customViewSetting.findViewById(R.id.addContactLinear);
                searchLinear = (LinearLayout) customViewSetting.findViewById(R.id.searchLinear);
                mediaLinear = (LinearLayout) customViewSetting.findViewById(R.id.mediaLinear);
                notificationLinear = (LinearLayout) customViewSetting.findViewById(R.id.notificationLinear);
                wallpaperLinear = (LinearLayout) customViewSetting.findViewById(R.id.wallpaperLinear);
                clearChatLinear = (LinearLayout) customViewSetting.findViewById(R.id.clearChatLinear);
                Switch switchOne = (Switch) customViewSetting.findViewById(R.id.switchNotification);

                switchOne.setOnCheckedChangeListener(
                        new CompoundButton.OnCheckedChangeListener() {
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    Toast.makeText(ChatActivity.this,
                                            "Switch On", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ChatActivity.this,
                                            "Switch Off", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                addContactLinear.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindowForSetting.dismiss();
                        // Toast.makeText(ChatActivity.this,"OnWorking",Toast.LENGTH_SHORT).show();
                        boolean cantact = Permission.checkPermisionForREAD_CONTACTS(ChatActivity.this);
                        if (cantact) {
                            addContact(username, rid);
                        }
                    }
                });
                searchLinear.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindowForSetting.dismiss();
                        searchLayout.setVisibility(View.VISIBLE);

                    }
                });
                mediaLinear.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindowForSetting.dismiss();

                    }
                });

                notificationLinear.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindowForSetting.dismiss();
                    }
                });
                wallpaperLinear.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindowForSetting.dismiss();
                        Intent i = new Intent(ChatActivity.this, WallpaperActivity.class);
                        startActivity(i);
                    }
                });
                clearChatLinear.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindowForSetting.dismiss();
                        new DeleteAllChatTask().execute();
                    }
                });
            }
        });

        ImageAttachment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.popup, null);
                // Create popup window.

                //close the popup window on button click
                ImageAttachment.setImageResource(R.drawable.menu_active);
                popupWindow = new PopupWindow(customView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

                // Set popup window animation style.
                popupWindow.setAnimationStyle(R.style.popup_window_animation_sms);

                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

                popupWindow.setFocusable(true);

                popupWindow.setOutsideTouchable(true);

                popupWindow.update();
                // Show popup window offset 1,1 to smsBtton.
                popupWindow.showAsDropDown(mToolbar, 1, 1);

                popupWindow.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        ImageAttachment.setImageResource(R.drawable.menu_inactive);
                    }
                });

                SellPost = (LinearLayout) customView.findViewById(R.id.SellPost);
                BuyPost = (LinearLayout) customView.findViewById(R.id.BuyPost);
                MomentPick = (LinearLayout) customView.findViewById(R.id.MomentPick);
                ImageUpload = (LinearLayout) customView.findViewById(R.id.ImageUpload);
                TakePicture = (LinearLayout) customView.findViewById(R.id.TakePicture);
                Video = (LinearLayout) customView.findViewById(R.id.Video);
                TakeVideo = (LinearLayout) customView.findViewById(R.id.TakeVideo);
                MediaTools = (LinearLayout) customView.findViewById(R.id.MediaTools);
                Location = (LinearLayout) customView.findViewById(R.id.Location);
                ContactInfo = (LinearLayout) customView.findViewById(R.id.ContactInfo);
                FileUpload = (LinearLayout) customView.findViewById(R.id.FileUpload);
                AudioFile = (LinearLayout) customView.findViewById(R.id.AudioFile);

                SellPost.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
//                        attachment = false;
//                        ImageAttachment.setImageResource(R.drawable.menu_inactive);
                        // Toast.makeText(ChatActivity.this,"OnWorking",Toast.LENGTH_SHORT).show();
                    }
                });
                BuyPost.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        // attachment = false;
                        // ImageAttachment.setImageResource(R.drawable.menu_inactive);
                    }
                });
                MomentPick.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        //  attachment = false;
                        // ImageAttachment.setImageResource(R.drawable.menu_inactive);
                    }
                });
                ImageUpload.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        //  attachment = false;
                        // ImageAttachment.setImageResource(R.drawable.menu_inactive);
                        boolean attached_file1 = Permission.checkReadExternalStoragePermission(ChatActivity.this);
                        boolean image_permission_write = Permission.permissionForExternal(ChatActivity.this);
                        if (attached_file1) {
                            if (image_permission_write) {
                                try {
                                   /* Intent intent = new Intent(ChatActivity.this, AlbumSelectActivity.class);
                                    intent.putExtra(Constants.INTENT_EXTRA_LIMIT, Constants.DEFAULT_LIMIT);
                                    startActivityForResult(intent, Constants.REQUEST_CODE_IMAGE);*/

                                    Intent galleryintent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    startActivityForResult(galleryintent, 143);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });
                TakePicture.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        // attachment = false;
                        //   ImageAttachment.setImageResource(R.drawable.menu_inactive);
                        boolean attached_file4 = Permission.checkReadExternalStoragePermission(ChatActivity.this);
                        boolean camera_permission = Permission.checkCameraPermission(ChatActivity.this);
                        boolean camera_permission_write = Permission.permissionForExternal(ChatActivity.this);
                        if (camera_permission_write) {
                            if (camera_permission) {
                                if (attached_file4) {
                                    try {
                                        cameraUpView();
                                        //cameraIntent();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                });
                Video.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        boolean attached_file8 = Permission.checkReadExternalStoragePermission(ChatActivity.this);
                        boolean video_permission_write = Permission.permissionForExternal(ChatActivity.this);
                        if (video_permission_write) {
                            if (attached_file8) {
                                try {
                                    selectVideoFromGallery();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });
                TakeVideo.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean recording = Permission.checkRecordPermission(ChatActivity.this);
                        boolean attached_file4 = Permission.checkReadExternalStoragePermission(ChatActivity.this);
                        boolean camera_permission = Permission.checkCameraPermission(ChatActivity.this);
                        boolean camera_permission_write = Permission.permissionForExternal(ChatActivity.this);
                        if (camera_permission_write) {
                            if (camera_permission) {
                                if (attached_file4) {
                                    if (recording) {
                                        try {
                                            Intent captureVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                                            captureVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 50000);
                                            startActivityForResult(captureVideoIntent, VIDEO_CAPTURED);
                                            //cameraIntent();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                        popupWindow.dismiss();
                    }
                });
                MediaTools.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        boolean attached_file1 = Permission.checkReadExternalStoragePermission(ChatActivity.this);
                        boolean image_permission_write = Permission.permissionForExternal(ChatActivity.this);
                        if (attached_file1) {
                            if (image_permission_write) {
                                try {
                                  /*  Intent intent = new Intent(ChatActivity.this, AlbumSelectActivity.class);
                                    intent.putExtra(Constants.INTENT_EXTRA_LIMIT, Constants.DEFAULT_LIMIT);
                                    startActivityForResult(intent, Constants.REQUEST_CODE_IMAGE);*/
                                    Intent i = new Intent(ChatActivity.this, EditImageActivity.class);

                                    startActivityForResult(i, 420);
                                   /* Intent galleryintent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    startActivityForResult(galleryintent, MEDIA_TOOL_REQUEST);*/
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                      /*  Intent i=new Intent(ChatActivity.this,EditImageActivity.class);
                        startActivity(i);*/
                    }
                });
                Location.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        //  attachment = false;
                        //  ImageAttachment.setImageResource(R.drawable.menu_inactive);
                        boolean attached_file3 = Permission.permissionAccessNetwork(ChatActivity.this);
                        if (attached_file3) {
                        /*    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                            try {
                                startActivityForResult(builder.build(ChatActivity.this), PLACE_PICKER_REQUEST);
                            } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                                e.printStackTrace();
                            }
*/
                            Intent intent = new PlaceAutocomplete.IntentBuilder()
                                    .accessToken(Mapbox.getAccessToken())
                                    .placeOptions(PlaceOptions.builder()
                                            .backgroundColor(Color.parseColor("#EEEEEE"))
                                            .limit(10)
                                            // .addInjectedFeature(home)
                                            // .addInjectedFeature(work)
                                            .build(PlaceOptions.MODE_CARDS))
                                    .build(ChatActivity.this);
                            startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
                        }
                    }
                });
                ContactInfo.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        popupWindow.dismiss();
                        Intent i = new Intent(ChatActivity.this, CareerContactListActivity.class);
                        startActivityForResult(i, MY_PERMISSIONS_CONTACT_SHARE);
                    }
                });

                FileUpload.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //  attachment = false;
                        popupWindow.dismiss();

                        if (!checkPermission()) {
                            openActivity();
                        } else {
                            if (checkPermission()) {
                                requestPermissionAndContinue();
                            } else {
                                openActivity();
                            }
                        }
                    }
                });

                AudioFile.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        boolean attached_file7 = Permission.checkReadExternalStoragePermission(ChatActivity.this);
                        boolean record_permission_write = Permission.permissionForExternal(ChatActivity.this);
                        if (attached_file7) {
                            if (record_permission_write) {
                                Intent intent = new Intent(ChatActivity.this, AudioFileList.class);
                                startActivityForResult(intent, MY_PERMISSIONS_GET_AUDIO_PERMISSION);
                            }
                        }
                    }
                });
            }
        });

        apiInterface = ApiClass.getRetrofitObjectForImage();
        cd = new ConnectionDetector(ChatActivity.this);

        did = getIntent().getStringExtra("did");
        msg_edittext = (EmojiconEditText) findViewById(R.id.msgEditText);
        msgListView = (RecyclerView) findViewById(R.id.msgListView);
        scrollTodown();


        msgListView.addOnItemTouchListener(new RecyclerItemClickListener(this, msgListView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (isMultiSelect)
                    multi_select(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                if (!isMultiSelect) {
                    multiselect_list = new ArrayList<JsonModelForChat>();
                    copy_list = new ArrayList<String>();
                    isMultiSelect = true;
                    linear_chat_option.setVisibility(View.VISIBLE);
                }
                multi_select(position);
            }
        }));

        sendButton = (ImageView) findViewById(R.id.sendMessageBtn);
        sendVoiceRecording = (ImageView) findViewById(R.id.sendVoiceRecording);
        sendButton.setOnClickListener(this);
        sendVoiceRecording.setOnClickListener(this);
        list = new ArrayList<JsonModelForChat>();
        if (!cd.isConnectingToInternet()) {
            mode="offline";
            list= new ArrayList<>();
            list=localDBHelper.getAllSingleChat(rid);
            chatAdapter = new ChatAdapter(ChatActivity.this, msgListView, R.layout.chat_list_item, list, multiselect_list,mode);
            msgListView.setAdapter(chatAdapter);
            Snackbar.make(findViewById(android.R.id.content), "No Internet Connection- Offline Mode", Snackbar.LENGTH_SHORT).show();
        }
        msg_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!mTyping) {
                    mTyping = true;
                    JsonObject object = new JsonObject();
                    object.addProperty("reciverid", rid);
                    object.addProperty("senderid", sd.getKeyId());
                    mSocket.emit("typing", object);
                }
                mTypingHandler.removeCallbacks(onTypingTimeout);
                mTypingHandler.postDelayed(onTypingTimeout, TYPING_TIMER_LENGTH);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (msg_edittext.getText().toString().trim().isEmpty()) {
                    sendButton.setVisibility(View.GONE);
                    recordButton.setVisibility(View.VISIBLE);

                } else {
                    sendButton.setVisibility(View.VISIBLE);
                    recordButton.setVisibility(View.GONE);
                }
            }
        });

// LOADING PREVIES CHAT
        JsonObject jsonObject2 = new JsonObject();
        jsonObject2.addProperty("senderid", sd.getKeyId());
        jsonObject2.addProperty("reciverid", rid);
        mSocket.emit("loadRecieverChat", jsonObject2);
        mSocket.connect();
        smileyBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatActivity.this, EmojiActivity.class);
                startActivityForResult(intent, EMOJI_RESULTS);
            }
        });
        //list.addAll(DBUtil.fetchAllChatList(ChatActivity.this,sd.getKeyId(),rid));
      //  Log.d("DATAFUCK",list +"");
        //chatAdapter = new ChatAdapter(ChatActivity.this, msgListView, R.layout.chat_list_item, list, multiselect_list);
        ///msgListView.setAdapter(chatAdapter);
        chatName.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChatActivity.this, ProfileActivity.class);
                i.putExtra("name", username);
                i.putExtra("rid", rid);
                i.putExtra("chattype", "indivisual");
                i.putExtra("did", did);
                i.putStringArrayListExtra("forwardString", new ArrayList<>());
                i.putExtra("type", "");
                startActivity(i);
            }
        });
        if (forwardString != null && forwardString.size() != 0) {
            strChatType = getIntent().getStringArrayListExtra("type");
            Log.d("GETSTRING", forwardString + " ; " + strChatType + " : " + rid + " : " + username);

            sendForwardMessage(3000, forwardString, strChatType);
        }
    }

    private void scrollTodown() {
        msgListView.setHasFixedSize(true);
        msgListView.setNestedScrollingEnabled(false);
        msgListView.setItemAnimator(new DefaultItemAnimator());
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setStackFromEnd(true);

        msgListView.setLayoutManager(layoutManager);
    }

    public void multi_select(int position) {
        //  if (mActionMode != null) {


        if (multiselect_list.contains(list.get(position))) {
            multiselect_list.remove(list.get(position));
            copy_list.remove(list.get(position).getMessage());
        } else {

            multiselect_list.add(list.get(position));
            copy_list.add(list.get(position).getMessage());
        }

        if (multiselect_list.size() > 0) {
            linear_chat_option.setVisibility(View.VISIBLE);
            if (multiselect_list.size() == 1) {
                btnReplyCab.setVisibility(View.VISIBLE);
            } else {
                btnReplyCab.setVisibility(View.GONE);
            }
        } else {

            linear_chat_option.setVisibility(View.GONE);
            isMultiSelect = false;
            multiselect_list = new ArrayList<JsonModelForChat>();
            linear_chat_option.setVisibility(View.GONE);
        }

        refreshAdapter();

        // }
    }

    public void refreshAdapter() {
        chatAdapter.selected_usersList = multiselect_list;
        chatAdapter.chatMessageList = list;
        chatAdapter.notifyDataSetChanged();
    }

    // AlertDialog Callback Functions

    @Override
    public void onPositiveClick(int from) {
        if (from == 1) {
            if (multiselect_list.size() > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < multiselect_list.size(); i++) {
                    stringBuilder.append("," + "'" + multiselect_list.get(i).getMid() + "'");
                    list.remove(multiselect_list.get(i));
                }
                JsonObject jsonObject1 = new JsonObject();
                jsonObject1.addProperty("id", sd.getKeyId() + "," + rid);
                jsonObject1.addProperty("uid", String.valueOf(stringBuilder));


                Log.d("MIDMID", jsonObject1 + "");
                mSocket.emit("deleteMessage", jsonObject1);
                linear_chat_option.setVisibility(View.GONE);
                isMultiSelect = false;
                multiselect_list = new ArrayList<JsonModelForChat>();
                chatAdapter.notifyDataSetChanged();


                Toast.makeText(getApplicationContext(), "Delete For Everyone", Toast.LENGTH_SHORT).show();
            }
        } else if (from == 2) {

            linear_chat_option.setVisibility(View.GONE);
            isMultiSelect = false;
            multiselect_list = new ArrayList<JsonModelForChat>();
            linear_chat_option.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "Delete For One", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onNegativeClick(int from) {

    }

    @Override
    public void onNeutralClick(int from) {

    }

    private void sendForwardMessage(int time, ArrayList<String> message, ArrayList<String> type) {

        for (int i = 0; i < message.size(); i++) {
            attemptSend(message.get(i), type.get(i));
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

    public String UploadFile(File file, String UserId, String Extention, String type) {
        String FileName = UserId + "_" + getSaltString() + Extention;
        String key = "JEOWPS5PEY4E5X6J42F4";
        String secret = "U4Se+JKztU5tz8Vjz/SMpUNJwd3Xz+Z2VBlPe8ANrAg";
        credentials = new BasicAWSCredentials(key, secret);
        s3 = new AmazonS3Client(credentials);
        s3.setEndpoint("sgp1.digitaloceanspaces.com");
        transferUtility = new TransferUtility(s3, ChatActivity.this);
        //   File file = new File(path);
        Log.d("AUDIO_PATH", file + " : " + file);
        if (!file.exists()) {
            Toast.makeText(ChatActivity.this, "File Not Found!", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ChatActivity.this, "File Upload Complete", Toast.LENGTH_SHORT).show();
                    attemptSend(Confiq.DOCUMNMENT_URL_AWS + FileName, type);
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
                Toast.makeText(ChatActivity.this, "" + ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return FileName;
    }

    public String UploadFile(String path, String UserId, String Extention, String type) {
        String FileName = UserId + "_" + getSaltString() + Extention;
        String key = "JEOWPS5PEY4E5X6J42F4";
        String secret = "U4Se+JKztU5tz8Vjz/SMpUNJwd3Xz+Z2VBlPe8ANrAg";
        credentials = new BasicAWSCredentials(key, secret);
        s3 = new AmazonS3Client(credentials);
        s3.setEndpoint("sgp1.digitaloceanspaces.com");
        transferUtility = new TransferUtility(s3, ChatActivity.this);
        File file = new File(path);
        Log.d("AUDIO_PATH", path + " : " + file);
        if (!file.exists()) {
            Toast.makeText(ChatActivity.this, "File Not Found!", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ChatActivity.this, "File Upload Complete", Toast.LENGTH_SHORT).show();
                    attemptSend(Confiq.DOCUMNMENT_URL_AWS + FileName, type);
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
                Toast.makeText(ChatActivity.this, "" + ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return FileName;
    }

    public String UploadFileDoc(String path, String paresnt, String UserId, String Extention, String type) {
        Log.d("EXTENSTION", Extention);
        String FileName = UserId + "_" + getSaltString() + Extention;
        String key = "JEOWPS5PEY4E5X6J42F4";
        String secret = "U4Se+JKztU5tz8Vjz/SMpUNJwd3Xz+Z2VBlPe8ANrAg";
        credentials = new BasicAWSCredentials(key, secret);
        s3 = new AmazonS3Client(credentials);
        s3.setEndpoint("sgp1.digitaloceanspaces.com");
        transferUtility = new TransferUtility(s3, ChatActivity.this);
        File file = new File(paresnt, path);
        if (!file.exists()) {
            Toast.makeText(ChatActivity.this, "File Not Found!", Toast.LENGTH_SHORT).show();
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
                    localFileManager = new LocalFileManager(ChatActivity.this);
                    localFileManager.createDocsFolder();
                    try{
                        if (Extention.equals(".mp3")){
                            localFileManager.createAudioFolder();
                            File file1=localFileManager.saveMedia(file,".mp3");
                            localPath=file1.getAbsolutePath();
                            Log.d("Local-docs/audio",file1.getAbsolutePath());
                        }else{
                            File file1=localFileManager.saveDocs(file,path);
                            localPath=file1.getAbsolutePath();
                        }

                    }catch (Exception e){
                        Log.d("Local-docs",e.toString());
                    }
                    Toast.makeText(ChatActivity.this, "File Upload Complete", Toast.LENGTH_SHORT).show();
                    attemptSend(Confiq.DOCUMNMENT_URL_AWS + FileName, type);
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
                Toast.makeText(ChatActivity.this, "" + ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return FileName;
    }

    @Override
    protected void onStart() {
        super.onStart();
        clearNotifications();
        //  Toast.makeText(ChatActivity.this, "OnStart()", Toast.LENGTH_SHORT).show();
        if (mSocket == null) {
            ChatApplication app = (ChatApplication) getApplication();
            mSocket = app.getSocket();
        }
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("id", sd.getKeyId());
        jsonObject.addProperty("messageCount", "0");
        jsonObject.addProperty("isChatEnable", "true");
        jsonObject.addProperty("isDelivered", "true");
        jsonObject.addProperty("isReaded", "true");
        jsonObject.addProperty("isOnlineStatus", "true");
        Log.e("DATA_FOR_USERID", jsonObject + "");

        mSocket.emit("userid", jsonObject);

        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty("id", sd.getKeyId());
        jsonObject1.addProperty("isChatEnable", "true");
        jsonObject1.addProperty("reciverid", rid);
        mSocket.emit("togglewindowstatus", jsonObject1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (backImg != null) {
            backImg.setImageResource(sd.getWallpaper());
            //     Toast.makeText(ChatActivity.this, "onResume()", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", sd.getKeyId());
        jsonObject.addProperty("isChatEnable", "false");
        jsonObject.addProperty("reciverid", rid);
        mSocket.emit("togglewindowstatus", jsonObject);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        chatAdapter.clearMusic();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        chatAdapter.clearMusic();
    }

    private void attemptSend(String message, String type) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        JsonObject object = new JsonObject();
        object.addProperty("reciverid", rid);
        object.addProperty("message", message);
        object.addProperty("dtype", type);
        object.addProperty("sname", sd.getUserName());
        object.addProperty("did", did);
        object.addProperty("pusddid", sd.getUserFcmId());
        object.addProperty("uid", getSaltString());
        object.addProperty("datetime", formattedDate);
        object.addProperty("isread", "false");
        object.addProperty("deliver", "false");
        object.addProperty("senderid", sd.getKeyId());
        mSocket.emit("sendMessage", object);
        Log.d("SEND_SINGLE", object.toString());
    }

    private void attemptSendEmoji(String message, String type) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        JsonObject object = new JsonObject();
        object.addProperty("reciverid", rid);
        object.addProperty("message", message);
        object.addProperty("dtype", type);
        object.addProperty("sname", sd.getUserName());
        object.addProperty("did", did);
        object.addProperty("pusddid", sd.getUserFcmId());
        object.addProperty("uid", getSaltString());
        object.addProperty("datetime", formattedDate);
        object.addProperty("isread", "false");
        object.addProperty("deliver", "false");
        object.addProperty("senderid", sd.getKeyId());
        mSocket.emit("sendMessage", object);
        Log.d("SEND_SINGLE", object.toString());
    }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "connect", Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    private Emitter.Listener onSendMyMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        Log.d("onSendMyMessage", data.toString());
                        String senderid = data.getString("senderid");
                        String reciever_id = data.getString("reciverid");
                        String message = data.getString("message");
                        String datetime = data.getString("datetime");
                        String isread = data.getString("isread");
                        String deliver = data.getString("deliver");
                        String response = data.getString("response");
                        String type = data.getString("dtype");
                        String mid = data.getString("uid");
                        String time = timeConverter(datetime);
                        String date = dateConverter(datetime);
                        if (!date.equalsIgnoreCase(firstDate)) {
                            firstDate = date;
                        } else {
                            date = "";

                        }
                        Log.d("RESPONCE", response);


                        if (reciever_id.equalsIgnoreCase(rid)) {
                            Log.d("DUPLICATE",DBUtil.checkSingleChat(ChatActivity.this,mid)+"");
                          //  if (!DBUtil.checkSingleChat(ChatActivity.this,mid)) {
                              //  list.add( DBUtil.chatInsert(ChatActivity.this,0,senderid,reciever_id,datetime,message,isread,deliver,type,time,date,mid));
                            if ((type.equals("img") || type.equals("video") || type.equals("audio")) && localPath!=null){
                                localDBHelper.insertSingleChat(new JsonModelForChat(localPath, senderid, reciever_id, date, isread, "All", time, deliver, mid, localPath, type, "asdfa", "asdfsda", "asdfsa", datetime, false),rid);
                                localPath=null;
                            }else{
                                localDBHelper.insertSingleChat(new JsonModelForChat(message, senderid, reciever_id, date, isread, "All", time, deliver, mid, message, type, "asdfa", "asdfsda", "asdfsa", datetime, false),rid);
                            }

                            list.add(new JsonModelForChat(message, senderid, reciever_id, date, isread, response, time, deliver, mid, message, type, "asdfa", "asdfsda", "asdfsa", datetime, false));

                            //list.add(DBUtil.chatInsert(ChatActivity.this, 0, senderid, reciever_id, datetime, message, isread, deliver, type, time, date));
                          //  }
                        }
                    } catch (JSONException e) {
                        Log.d(TAG, e.getMessage());
                        return;
                    }
                    if (chatAdapter != null) {
                        chatAdapter.notifyDataSetChanged();

                    } else {

                        chatAdapter = new ChatAdapter(ChatActivity.this, msgListView, R.layout.chat_list_item, list, multiselect_list, mode);
                        msgListView.setAdapter(chatAdapter);

                    }
                    scrollTodown();
                    typing_txt.setVisibility(View.GONE);
                }
            });
        }
    };

    private String timeConverter(String input) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //Date/time pattern of desired output date
        DateFormat outputformat = new SimpleDateFormat("hh:mm aa");
        Date date = null;
        String output = null;
        try {
            //Conversion of input String to date
            date = df.parse(input);
            //old date format to new date format
            output = outputformat.format(date);
            System.out.println(output);
            Log.d("OUTPUT_DATE", output);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        return output;
    }

    private String dateConverter(String input) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat currnetDate = new SimpleDateFormat("yyyy-MM-dd");
        String today = currnetDate.format(c.getTime());
        c.add(Calendar.DATE, -1);
        String yesterday = currnetDate.format(c.getTime());

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //Date/time pattern of desired output date
        DateFormat outputformat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        String output = null;
        try {
            //Conversion of input String to date
            date = df.parse(input);
            //old date format to new date format
            output = outputformat.format(date);
            if (today.equalsIgnoreCase(output)) {
                output = "TODAY";
            }
            if (yesterday.equalsIgnoreCase(output)) {
                output = "YESTERDAY";
            }

            System.out.println(output);
            Log.d("OUTPUT_DATE", output);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        return output;
    }

    private Emitter.Listener AllMyMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {

                        //progressDialog.show();
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
                            String time = timeConverter(datetime);
                            String date = dateConverter(datetime);
                            if (!date.equalsIgnoreCase(firstDate)) {
                                firstDate = date;
                            } else {
                                date = "";

                            }

                            if (mode.equals("offline")){
                                list=localDBHelper.getAllSingleChat(rid);
                                Log.d("No_internet_data", list.toString());
                            }else{
                                list.add(new JsonModelForChat(message, send_id, rcv_id, date, isread, "All", time, deliver, mid, message, type, "asdfa", "asdfsda", "asdfsa", datetime, false));
                            }
                            if ((type.equals("img") || type.equals("video") || type.equals("audio")) && localPath!=null){
                                localDBHelper.insertSingleChat(new JsonModelForChat(localPath, send_id, rcv_id, date, isread, "All", time, deliver, mid, localPath, type, "asdfa", "asdfsda", "asdfsa", datetime, false),rid);
                                localPath=null;
                            }else{
                                localDBHelper.insertSingleChat(new JsonModelForChat(message, send_id, rcv_id, date, isread, "All", time, deliver, mid, message, type, "asdfa", "asdfsda", "asdfsa", datetime, false),rid);

                            }
                            //             list.addAll(DBUtil.fetchAllChatList(ChatActivity.this));
                            //list.add(new ChatModel(message, send_id, rcv_id, time, date, isread, deliver, "All"));
                        }
                      /*  if (chatAdapter != null) {
                            chatAdapter.notifyDataSetChanged();
                        } else {*/
                            chatAdapter = new ChatAdapter(ChatActivity.this, msgListView, R.layout.chat_list_item, list, multiselect_list, mode);
                            msgListView.setAdapter(chatAdapter);
                      //  }

                        ///  progressDialog.dismiss();

                    } catch (JSONException e) {
                        Log.d(TAG, e.getMessage());
                        // progressDialog.dismiss();
                        return;
                    }
                }
            });
        }
    };

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // {"reciverid":"1","message":"hello","dtype":"msg","datetime":"2018-12-01 14:38:00","isread":"false","deliver":"false","senderid":"51"}
                    JSONObject data = (JSONObject) args[0];
                    try {
                        Log.d("MYNEWMESSAGE", data.toString());
                        String senderid = data.getString("senderid");
                        String reciever_id = data.getString("reciverid");
                        String message = data.getString("message");
                        String datetime = data.getString("datetime");
                        String isread = data.getString("isread");
                        String deliver = data.getString("deliver");
                        String dtype = data.getString("dtype");
                        String time = timeConverter(datetime);
                        String mid = data.getString("uid");
                        String date = dateConverter(datetime);

                        if (!date.equalsIgnoreCase(firstDate)) {
                            firstDate = date;
                        } else {
                            date = "";
                        }

                        if (senderid.equalsIgnoreCase(rid)) {
                     //       if (DBUtil.checkSingleChat(ChatActivity.this,mid)) {
                       //         list.add( DBUtil.chatInsert(ChatActivity.this,0,senderid,reciever_id,datetime,message,isread,deliver,dtype,time,date,mid));
                            if ((dtype.equals("img") || dtype.equals("video") || dtype.equals("audio")) && localPath!=null){
                                localDBHelper.insertSingleChat(new JsonModelForChat(localPath, senderid, reciever_id, date, isread, "All", time, deliver, mid, localPath, dtype, "asdfa", "asdfsda", "asdfsa", datetime, false),rid);
                                localPath=null;
                            }else{
                                localDBHelper.insertSingleChat(new JsonModelForChat(message, senderid, reciever_id, date, isread, "All", time, deliver, mid, message, dtype, "asdfa", "asdfsda", "asdfsa", datetime, false),rid);
                            }
                            list.add(new JsonModelForChat(message, senderid, reciever_id, date, isread, "not available", time, deliver, mid, message, dtype, "asdfa", "asdfsda", "asdfsa", datetime, false));

                            //list.add(DBUtil.chatInsert(ChatActivity.this, 0, senderid, reciever_id, datetime, message, isread, deliver, type, time, date));
                         ///   }//                            list.add(new JsonModelForChat(message, senderid, reciever_id, date, isread, "user", time, deliver, mid, message, dtype, "asdfa", "asdfsda", "asdfsa", datetime, false));
                        }
                    } catch (JSONException e) {
                        Log.d(TAG, e.getMessage());
                        return;
                    }

                    if (chatAdapter != null) {
                        chatAdapter.notifyDataSetChanged();
                    } else {
                        chatAdapter = new ChatAdapter(ChatActivity.this, msgListView, R.layout.chat_list_item, list, multiselect_list, mode);
                        msgListView.setAdapter(chatAdapter);
                    }
                    scrollTodown();
                    typing_txt.setVisibility(View.GONE);
                }
            });
        }
    };

    private Emitter.Listener onOnlineStatus = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        JSONArray jsonArray = data.getJSONArray("myId");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj1 = jsonArray.getJSONObject(i);
                            String id = obj1.getString("userId");
                            String onlineStatus = obj1.getString("onlineStatus");
                            if (!id.equalsIgnoreCase(sd.getKeyId())) {
                                if (id.equalsIgnoreCase(rid)) {
                                    if (onlineStatus.equalsIgnoreCase("true")) {
                                        txt_online_status.setVisibility(View.VISIBLE);
                                    } else {
                                        txt_online_status.setVisibility(View.GONE);

                                    }
                                    for (JsonModelForChat model : list) {
                                        if (model.getResponse().equalsIgnoreCase("not avilable")) {
                                            model.setResponse("delivered");
                                        }
                                    }
                                    if (chatAdapter != null) {
                                        chatAdapter.notifyDataSetChanged();
                                    } else {
                                        chatAdapter = new ChatAdapter(ChatActivity.this, msgListView, R.layout.chat_list_item, list, multiselect_list, mode);
                                        msgListView.setAdapter(chatAdapter);
                                    }
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    private Emitter.Listener onOfflineStatus = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    //Log.d("OFFLINE_DATA", data + "");
                    try {
                        JSONArray jsonArray = data.getJSONArray("myId");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj1 = jsonArray.getJSONObject(i);
                            String id = obj1.getString("userId");
                            if (!id.equalsIgnoreCase(sd.getKeyId())) {
                                if (id.equalsIgnoreCase(rid)) {
                                    txt_online_status.setVisibility(View.VISIBLE);
                                } else {
                                    txt_online_status.setVisibility(View.GONE);
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    private Emitter.Listener onDisable = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    Log.d("OFFLINE_DATA", data + "");
                    try {

                        String id = data.getString("userId");
                        String onlineStatus = data.getString("onlineStatus");
                        if (!id.equalsIgnoreCase(sd.getKeyId())) {
                            if (id.equalsIgnoreCase(rid)) {
                                txt_online_status.setVisibility(View.GONE);
                            } else {
                                txt_online_status.setVisibility(View.VISIBLE);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    private Emitter.Listener onReadStatus = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    Log.d("onReadStatus", data + "");
                    try {
                        String id = data.getString("id");
                        String reciverid = data.getString("reciverid");
                        if (id.equalsIgnoreCase(rid) && reciverid.equalsIgnoreCase(sd.getKeyId())) {
                            for (JsonModelForChat model : list) {
                                if (model.getResponse().equalsIgnoreCase("not avilable") || model.getResponse().equalsIgnoreCase("delivered")) {
                                    model.setResponse("read");
                                }
                            }
                            if (chatAdapter != null) {
                                chatAdapter.notifyDataSetChanged();
                            } else {
                                chatAdapter = new ChatAdapter(ChatActivity.this, msgListView, R.layout.chat_list_item, list, multiselect_list, mode);
                                msgListView.setAdapter(chatAdapter);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    private Emitter.Listener onTyping = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        String senderid = data.getString("senderid");
                        String reciever_id = data.getString("reciverid");
                        if (senderid.equalsIgnoreCase(rid)) {
                            typing_txt.setText("Typing..");
                            typing_txt.setVisibility(View.VISIBLE);
                            Log.d("TYPING", "RUNNING TYPING");
                        }
                    } catch (JSONException e) {
                        Log.d(TAG, e.getMessage());
                        return;
                    }
                }
            });
        }
    };

    private Emitter.Listener onStopTyping = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        String senderid = data.getString("senderid");
                        String reciever_id = data.getString("reciverid");
                        if (senderid.equalsIgnoreCase(rid)) {
                            typing_txt.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        Log.d(TAG, e.getMessage());
                        return;
                    }
                }
            });
        }
    };

    private Runnable onTypingTimeout = new Runnable() {
        @Override
        public void run() {
            if (!mTyping) return;
            mTyping = false;
            typing_txt.setVisibility(View.GONE);
            JsonObject object = new JsonObject();
            object.addProperty("reciverid", rid);
            object.addProperty("senderid", sd.getKeyId());
            mSocket.emit("stopTyping", object);
        }
    };

    private String getHumanTimeText(long milliseconds) {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(milliseconds),
                TimeUnit.MILLISECONDS.toSeconds(milliseconds) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds)));
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.sendVoiceRecording:
                boolean attached_file6 = Permission.checkReadExternalStoragePermission(ChatActivity.this);
                boolean recording = Permission.checkRecordPermission(ChatActivity.this);
                boolean recordingWrite = Permission.permissionForExternal(ChatActivity.this);
                if (attached_file6) {
                    if (recordingWrite) {
                        if (recording) {
                            try {
                                recordingAttached();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                break;


            case R.id.imageViewRecord:
                try {
                    prepareforRecording();
                    startRecording();
                } catch (Exception e) {
                    e.printStackTrace();

                }
                break;
            case R.id.imageViewStop:
                prepareforStop();
                stopRecording();
                break;
            case R.id.sendMessageBtn:
                if (!cd.isConnectingToInternet()) {
                    Snackbar.make(findViewById(android.R.id.content), "No Internet Connection", Snackbar.LENGTH_SHORT).show();
                } else {
                    String strMessage = msg_edittext.getText().toString().trim();
                    if (strMessage.length() > 0) {
                        msg_edittext.setText("");

                     /*   JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("data", strMessage);
                        jsonObject.addProperty("type", "msg");
                        jsonObject.addProperty("datetime", currentDateTime());
                        String convertIntoString = jsonObject.toString();
                        sendMessage(convertIntoString, USER_TWO + Confiq.KEY, "send");*/
                        attemptSend(strMessage, "msg");
                    }
                }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Snackbar.make(findViewById(android.R.id.content), connectionResult.getErrorMessage() + "", Snackbar.LENGTH_LONG).show();
    }

    private static String getFileSizeMegaBytes(File file) {
        return (double) file.length() / (1024 * 1024) + "".trim();
    }

    private static String getFileSizeKiloBytes(File file) {

        return (double) file.length() / 1024 + "  kb";
    }

    private static String getFileSizeBytes(File file) {
        return file.length() + " bytes";
    }

    private void recordingAttached() {
        try {
            //   mBuilder = new AlertDialog.Builder(ChatActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.audio_recording, null);

            imageViewRecord = (ImageView) mView.findViewById(R.id.imageViewRecord);
            imageViewStop = (ImageView) mView.findViewById(R.id.imageViewStop);

            imageViewStop.setOnClickListener(this);
            imageViewRecord.setOnClickListener(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String userName(String messageBody) {
        JSONObject typeobject = null;
        String userName = null;
        try {
            typeobject = new JSONObject(messageBody);
            userName = typeobject.getString("type");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userName;
    }

    private Uri getImageUri(Bitmap inImage) {
        Log.d("Image-uri", "call");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            default:
                break;
        }
        return true;
    }

    private void prepareforRecording() {
        //   TransitionManager.beginDelayedTransition(linearLayoutRecorder);
        imageViewRecord.setVisibility(View.GONE);
        imageViewStop.setVisibility(View.VISIBLE);
        //linearLayoutPlay.setVisibility(View.GONE);
    }

    private void startRecording() {
        try {
            mRecorder = new MediaRecorder();
            try {
                //     mRecorder.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

            localFileManager= new LocalFileManager(this);
            rootAudio = localFileManager.createAudioFolder();
            /*
            File file = new File(root.getAbsolutePath() + "/UDTalks/Audios");
            if (!file.exists()) {
                file.mkdirs();
            }*/

            fileName = rootAudio.getAbsolutePath() + "/UDTalks/Audio/" + String.valueOf(System.currentTimeMillis() + ".AMR");
            // Log.d("filename", fileName);
            mRecorder.setOutputFile(fileName);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            try {
                mRecorder.prepare();
                mRecorder.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // making the imageview a stop button
            //starting the chronometer
            // chronometer.setBase(SystemClock.elapsedRealtime());
            // chronometer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void prepareforStop() {
        // TransitionManager.beginDelayedTransition(linearLayoutRecorder);
        imageViewRecord.setVisibility(View.VISIBLE);
        imageViewStop.setVisibility(View.GONE);
        //  linearLayoutPlay.setVisibility(View.VISIBLE);
    }

    private void stopRecording() {

        try {
            mRecorder.stop();
            mRecorder.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mRecorder = null;
        //starting the chronometer
        // chronometer.stop();
        //  chronometer.setBase(SystemClock.elapsedRealtime());
        //showing the play button
        //   mDialog.dismiss();
        String pdfvalue = getFileSizeMegaBytes(new File(fileName));
        Log.d("Value_size", pdfvalue);
        if (sizeValidation(pdfvalue)) {
            Log.d("REAUDIO_PATH", pdfvalue);
            UploadFile(fileName, sd.getKeyId(), ".amr", "audio");

            //   uploadFile(fileName, "audio");
            //Toast.makeText(ChatActivity.this,"Done",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ChatActivity.this, "Audio Size is more to 10 MB", Toast.LENGTH_SHORT).show();
        }


        //  Toast.makeText(this, "Recording saved successfully.", Toast.LENGTH_SHORT).show();
    }

    private void cancelRecording() {
        localFileManager= new LocalFileManager(this);
        localFileManager.deleteAudioFile(fileName);
        try {
            mRecorder.stop();
            mRecorder.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mRecorder = null;

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_AUTOCOMPLETE) {

            // Retrieve selected location's CarmenFeature
            CarmenFeature selectedCarmenFeature = PlaceAutocomplete.getPlace(data);

            // Create a new FeatureCollection and add a new Feature to it using selectedCarmenFeature above
            FeatureCollection featureCollection = FeatureCollection.fromFeatures(
                    new Feature[]{Feature.fromJson(selectedCarmenFeature.toJson())});

            StringBuilder stBuilder = new StringBuilder();


            stBuilder.append(((Point) selectedCarmenFeature.geometry()).latitude());

            stBuilder.append("," + ((Point) selectedCarmenFeature.geometry()).longitude());
            stBuilder.append("," + selectedCarmenFeature.placeName());
            Log.d("LOCATIOP_MANE", stBuilder.toString());
            // Toast.makeText(ChatActivity.this,"OnWorking",Toast.LENGTH_SHORT).show();

            attemptSend(String.valueOf(stBuilder), "location");

            // Retrieve and update the source designated for showing a selected location's symbol layer icon
        /*    GeoJsonSource source = mapboxMap.getSourceAs(geojsonSourceLayerId);
            if (source != null) {
                source.setGeoJson(featureCollection);
            }
*/
            // Move map camera to the selected location
          /*  CameraPosition newCameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(((Point) selectedCarmenFeature.geometry()).latitude(),
                            ((Point) selectedCarmenFeature.geometry()).longitude()))
                    .zoom(14)
                    .build();
            mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(newCameraPosition), 4000);*/
        }

        if (requestCode == PLACE_PICKER_REQUEST) {
            try {
                if (resultCode == RESULT_OK) {
                    try {
                        Place place = PlacePicker.getPlace(data, this);
                        StringBuilder stBuilder = new StringBuilder();
                        String placename = String.format("%s", place.getName());
                        String latitude = String.valueOf(place.getLatLng().latitude);
                        String longitude = String.valueOf(place.getLatLng().longitude);
                        String address = String.format("%s", place.getAddress());
                        //    stBuilder.append("Name: ");
                        //  stBuilder.append(placename);
                        //  stBuilder.append("\n");
                        //  stBuilder.append("Latitude: ");
                        stBuilder.append(latitude);
                        // stBuilder.append("\n");
                        //stBuilder.append("Logitude: ");
                        stBuilder.append("," + longitude);
                        // stBuilder.append("\n");
                        //stBuilder.append("Address: ");
                        stBuilder.append("," + address);
                        Log.d("LOCATIOP_MANE", stBuilder.toString());
                        attemptSend(String.valueOf(stBuilder), "location");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (requestCode == 143 && resultCode == RESULT_OK
                && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            // Move to first row
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            imgDecodableString = cursor.getString(columnIndex);
            cursor.close();

            //Log.d("onActivityResult",imgDecodableString);
            // sendImage(imgDecodableString);
            Log.d("imgDecodable", imgDecodableString);
            File file = new File(imgDecodableString);
            compressImage(file);
            localFileManager= new LocalFileManager(this);
            localFileManager.createImageFolder();
            try {
                File file1=localFileManager.saveMedia(file, ".jpg");
                localPath=file1.getAbsolutePath();
                Log.d("No_internet_data",localPath);
            } catch (IOException e) {
                Log.d("LocalImage",e.toString());
            }
            // UploadFile(imgDecodableString, sd.getKeyId(), ".jpg", "img");

        }
        if (requestCode == 420 && resultCode == RESULT_OK
                && null != data) {
            String dataget = data.getStringExtra("data");

            Log.e("ERERER", data.getStringExtra("data"));
            File file = new File(dataget);
            compressImage(file);
            localFileManager= new LocalFileManager(this);
            localFileManager.createImageFolder();
            try {
                File file1=localFileManager.saveMedia(file, ".jpg");
                localPath=file1.getAbsolutePath();
                Log.d("No_internet_data",localPath);

            } catch (IOException e) {
                Log.d("LocalImage",e.toString());
            }
            //   UploadFile(dataget, sd.getKeyId(), ".jpg", "img");


        }
        if (requestCode == EMOJI_RESULTS && resultCode == RESULT_OK
                && null != data) {
            ArrayList<String> emoji_list = data.getStringArrayListExtra("image");
            for (int i = 0; i < emoji_list.size(); i++) {
                attemptSendEmoji(emoji_list.get(i), "emoji");
                Log.d("EMOJI_ICONE", emoji_list + "");
            }
        }
        if (requestCode == 88 && resultCode == RESULT_OK
                && null != data) {
            finish();
        }

        if (requestCode == MY_PERMISSIONS_CONTACT_SHARE && resultCode == RESULT_OK
                && null != data) {
            String contactDetail = data.getStringExtra("share");
            Log.d("MY_PERMISSIONS", contactDetail + "");
            attemptSend(contactDetail, "share");
        }
        if (requestCode == Constants.REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null) {
            try {

                ArrayList<Image> images = data.getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES);
                StringBuffer stringBuffer = new StringBuffer();

                for (int i = 0, l = images.size(); i < l; i++) {
                    String path = images.get(i).path;
                    String name = path.substring(path.lastIndexOf("/") + 1, path.length());
                    if (selectedImgFile.toString().isEmpty()) {
                        selectedImgFile.append(path);
                        selectedImgFilePath.append(name);
                    } else {
                        selectedImgFile.append("," + path);
                        selectedImgFilePath.append("\n" + name);
                    }
                }
                images.clear();
                //sendDocunmentWithAmazonServer(images.get(0).path, sd.getKeyId(), random(), "img");
                //new MyTask4().execute();
                //Log.d("imgDecodable", imgDecodableString);
                String[] dataFile = selectedImgFile.toString().split(",");
                //Log.d("HTTPECNTITYTT", data + " selectedImgFile " + selectedImgFile);

                for (int i = 0; i < dataFile.length; i++) {
                 /*   File file = new File(dataFile[i]);
                    compressImage(file);*/
                    UploadFile(dataFile[i], sd.getKeyId(), ".jpg", "img");
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ERERERER", e.toString());
            }
        }
        if (resultCode == RESULT_OK && requestCode == VIDEO_CAPTURED) {
            Uri videoUri = data.getData();
            try {
                videoPath = getFilePath(ChatActivity.this, videoUri);
                File file = new File(videoPath);
                localFileManager= new LocalFileManager(this);
                localFileManager.createVideoFoler();
                try {
                    File file1=localFileManager.saveMedia(file,".mp4");
                    localPath=file1.getAbsolutePath();
                } catch (IOException e) {
                    Log.d("LocalVideo",e.toString());
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            File file = new File(videoPath);
            Log.e("before compression", file.getAbsolutePath() + "");
            Log.e("before Compressed size", "" + getFileSizeKiloBytes(file));
            new VideoCompressor().execute();

        }

        ActivityResult activityResult = ActivityResult.of(requestCode, resultCode, data);
        handleActivityResult(activityResult);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void handleActivityResult(ActivityResult activityResult) {
        if (activityResult.resultCode == Activity.RESULT_OK) {
            handlePositiveActivityResult(activityResult.requestCode, activityResult.data);
        } else {
            handleNegativeActivityResult(activityResult.requestCode);
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void handlePositiveActivityResult(int requestCode, final Intent data) {
        switch (requestCode) {


            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_IMAGE:
                try {

                    String file = data.getStringExtra("file");
                    String parent = data.getStringExtra("parent");

                    UploadFileDoc(file, parent, sd.getKeyId(), Confiq.getExtenstion(file), "pdf");

                } catch (Exception e) {
                    e.printStackTrace();

                    Log.d("PDF_FILE_EX", e.toString());
                }

                break;

            case 1:

                if (requestCode == Camera.REQUEST_TAKE_PHOTO) {
                    Bitmap bitmap = camera.getCameraBitmap();
                    if (bitmap != null) {
                        String strStorageCameraUri = getPath(ChatActivity.this, getImageUri(bitmap));
                        String value = getFileSizeMegaBytes(new File(strStorageCameraUri));
                        Log.d("Value_size", value);
                        if (sizeValidation(value)) {
                            //  uploadFile(strStorageCameraUri, "img");
                            File file = new File(strStorageCameraUri);
                            compressImage(file);
                            Log.d("Image-take",file.getAbsolutePath());
                            localFileManager= new LocalFileManager(this);
                            localFileManager.createImageFolder();
                            try {
                                File file1=localFileManager.saveMedia(file,".jpg");
                                localPath=file1.getAbsolutePath();
                                Log.d("No_internet_data",localPath);
                            } catch (IOException e) {
                                Log.d("LocalImage",e.toString());
                            }
                            // UploadFile(strStorageCameraUri, sd.getKeyId(), ".jpg", "img");
                            //Toast.makeText(ChatActivity.this,"Done",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ChatActivity.this, "Image Size is more to 10 MB", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Picture not taken!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case MY_PERMISSIONS_GET_VIDEO:
                try {
                    String file1 = data.getStringExtra("file");
                    String parent = data.getStringExtra("parent");
                    // Uri videoUri = data.getData();
                    try {
                        videoPath = parent + "/" + file1;
                        File file = new File(videoPath);
                        localFileManager= new LocalFileManager(this);
                        localFileManager.createVideoFoler();
                        try {
                            File file11=localFileManager.saveMedia(file,".mp4");
                            localPath=file11.getAbsolutePath();
                        } catch (IOException e) {
                            Log.d("LocalVideo",e.toString());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("before compression", e.toString());
                    }
                    File file = new File(parent + file1);
                    Log.e("before compression", videoPath + "");
                    Log.e("before Compressed size", "" + getFileSizeKiloBytes(file));
                    new VideoCompressor().execute();
                   /* new VideoCompressor().execute();
                    UploadFileDoc(file1, parent, sd.getKeyId(), ".mp4", "video");*/

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case MY_PERMISSIONS_GET_AUDIO_PERMISSION:
                try {
                    String file = data.getStringExtra("file");
                    String parent = data.getStringExtra("parent");

                    UploadFileDoc(file, parent, sd.getKeyId(), ".mp3", "audio");

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("AUDIO_FILE", e.toString());
                }

                break;

        }
    }

    private void clearNotifications() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    private boolean checkPermission() {

        return ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ;
    }

    private void addContact(String Name, String number) {

        ArrayList<ContentProviderOperation> ops =
                new ArrayList<ContentProviderOperation>();

        int rawContactID = ops.size();

// Adding insert operation to operations list
// to insert a new raw contact in the table ContactsContract.RawContacts
        ops.add(ContentProviderOperation.newInsert(RawContacts.CONTENT_URI)
                .withValue(RawContacts.ACCOUNT_TYPE, null)
                .withValue(RawContacts.ACCOUNT_NAME, null)
                .build());

// Adding insert operation to operations list
// to insert display name in the table ContactsContract.Data
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                .withValue(ContactsContract.Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE)
                .withValue(StructuredName.DISPLAY_NAME, Name)
                .build());

// Adding insert operation to operations list
// to insert Mobile Number in the table ContactsContract.Data
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                .withValue(ContactsContract.Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
                .withValue(Phone.NUMBER, Name)
                .withValue(Phone.TYPE, Phone.TYPE_MOBILE)
                .build());

// Adding insert operation to operations list
// to  insert Home Phone Number in the table ContactsContract.Data
    /*    ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                .withValue(ContactsContract.Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
                .withValue(Phone.NUMBER, "78787878")
                .withValue(Phone.TYPE, Phone.TYPE_HOME)
                .build());

// Adding insert operation to operations list
// to insert Home Email in the table ContactsContract.Data
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                .withValue(ContactsContract.Data.MIMETYPE, Email.CONTENT_ITEM_TYPE)
                .withValue(Email.ADDRESS, "Ankit@gmail.vcom")
                .withValue(Email.TYPE, Email.TYPE_HOME)
                .build());

// Adding insert operation to operations list
// to insert Work Email in the table ContactsContract.Data
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                .withValue(ContactsContract.Data.MIMETYPE, Email.CONTENT_ITEM_TYPE)
                .withValue(Email.ADDRESS, "asdfqsdf")
                .withValue(Email.TYPE, Email.TYPE_WORK)
                .build());*/

        try {
// Executing all the insert operations as a single database transaction
            getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
            Toast.makeText(getBaseContext(), "Contact is successfully added", Toast.LENGTH_SHORT).show();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }
    }

    private void requestPermissionAndContinue() {
        if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, WRITE_EXTERNAL_STORAGE)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this, READ_EXTERNAL_STORAGE)) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle("TITLE");
                alertBuilder.setMessage("storage_permission_is_encessary_to_wrote_event");
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(ChatActivity.this, new String[]{WRITE_EXTERNAL_STORAGE
                                , READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                    }
                });
                AlertDialog alert = alertBuilder.create();
                alert.show();
                Log.d("", "permission denied, show dialog");
            } else {
                ActivityCompat.requestPermissions(ChatActivity.this, new String[]{WRITE_EXTERNAL_STORAGE,
                        READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }
        } else {
            openActivity();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (permissions.length > 0 && grantResults.length > 0) {

                boolean flag = true;
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        flag = false;
                    }
                }
                if (flag) {
                    openActivity();
                } else {
                    finish();
                }

            } else {
                finish();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void openActivity() {
        Intent i = new Intent(ChatActivity.this, FileList.class);
        startActivityForResult(i, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_IMAGE);
    }

    public void selectVideoFromGallery() {

        Intent intent = new Intent(ChatActivity.this, VideoFileList.class);
        startActivityForResult(intent, MY_PERMISSIONS_GET_VIDEO);
    }

    private boolean sizeValidation(String fileSize) {

        Double value = Double.valueOf(fileSize);
        if (value > 10.00) {
            return false;
        }
        return true;
    }

    private boolean sizeValidationForVideo(String fileSize) {

        Double value = Double.valueOf(fileSize);
        if (value > 20.00) {
            return false;
        }
        return true;
    }

    private void handleNegativeActivityResult(int requestCode) {
        switch (requestCode) {
            //nothing to do for now
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        try {
            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/"
                                + split[1];
                    }
                } else if (isDownloadsDocument(uri)) {
                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"),
                            Long.valueOf(id));

                    return getDataColumn(context, contentUri, null, null);
                } else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{split[1]};

                    return getDataColumn(context, contentUri, selection,
                            selectionArgs);
                }
            }
            // MediaStore (and general)
            else if ("content".equalsIgnoreCase(uri.getScheme())) {

                // Return the remote address
                if (isGooglePhotosUri(uri))
                    return uri.getLastPathSegment();

                return getDataColumn(context, uri, null, null);
            }
            // File
            else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }


    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }

    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;
    }

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }
// Camera Pickup

    private void cameraUpView() {
        camera = new Camera.Builder()
                .resetToCorrectOrientation(true)
                .setTakePhotoRequestCode(1)
                .setDirectory("pics")
                .setName("ali_" + System.currentTimeMillis())
                .setImageFormat(Camera.IMAGE_JPEG)
                .setCompression(75)
                .setImageHeight(1000)
                .build(this);
        try {
            camera.takePicture();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class VideoCompressor extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                return MediaController.getInstance().convertVideo(videoPath);
            } catch (Exception e) {
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean compressed) {
            super.onPostExecute(compressed);

            if (compressed) {
                Log.e("Compression", "Compression successfully!");
                Log.e("Compressed File Path", "" + MediaController.cachedFile.getPath());
                Log.e("Compressed File Path", "" + getFileSizeKiloBytes(new File(MediaController.cachedFile.getPath())));
                if (sizeValidationForVideo(getFileSizeMegaBytes(new File(MediaController.cachedFile.getPath())))) {
                    String strVideoUri = MediaController.cachedFile.getPath();
                    UploadFile(strVideoUri, sd.getKeyId(), ".mp4", "video");

                } else {
                    Toast.makeText(getApplicationContext(), "Video Max Size Is Only 7MB", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "Video Max Size Is Only 7MB", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "Video Max Size Is Only 7MB", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "File Path Not Found", Toast.LENGTH_SHORT).show();
            }

        }
    }

    class DeleteAllChatTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ChatActivity.this);
            progressDialog.setMessage("Delete All Message");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            if (list.size() > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    stringBuilder.append("," + "'" + list.get(i).getMid() + "'");

                }
                list.removeAll(list);
                JsonObject jsonObject1 = new JsonObject();
                jsonObject1.addProperty("id", sd.getKeyId());
                jsonObject1.addProperty("uid", String.valueOf(stringBuilder));


                Log.d("MIDMID", jsonObject1 + "");
                mSocket.emit("deleteMessage", jsonObject1);
                linear_chat_option.setVisibility(View.GONE);
                isMultiSelect = false;
                multiselect_list = new ArrayList<JsonModelForChat>();
                chatAdapter.selected_usersList = multiselect_list;
                chatAdapter.notifyDataSetChanged();


                Toast.makeText(getApplicationContext(), "Delete", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
            }

        }

    }
}