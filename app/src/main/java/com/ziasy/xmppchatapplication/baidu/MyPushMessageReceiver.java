package com.ziasy.xmppchatapplication.baidu;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.android.pushservice.PushMessageReceiver;
import com.microsoft.windowsazure.messaging.NotificationHub;
import com.ziasy.xmppchatapplication.ChatUserListActivity;
import com.ziasy.xmppchatapplication.R;
import com.ziasy.xmppchatapplication.common.SessionManagement;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MyPushMessageReceiver extends PushMessageReceiver {

    public static final String TAG = MyPushMessageReceiver.class
            .getSimpleName();
    private String reciverid = null, message = null, dtype = null, senderid = null, isread = null, deliver = null, sname = null, did = null, datetime = null;
    public static NotificationHub hub = null;
    public static String mChannelId, mUserId;
    private SessionManagement sessionManagement;

    @Override
    public void onBind(Context context, int errorCode, String appid,
                       String userId, String channelId, String requestId) {

        String responseString = "onBind errorCode=" + errorCode + " appid="
                + appid + " userId=" + userId + " channelId=" + channelId
                + " requestId=" + requestId;
        Log.d(TAG, responseString);
        sessionManagement = new SessionManagement(context);
        sessionManagement.setUserFcmId(channelId);

        if (errorCode == 0) {
            // Binding successful
            Log.d(TAG, " Binding successful");
        }
        try {
            if (hub == null) {
                hub = new NotificationHub(
                        ConfigurationSettings.NotificationHubName,
                        ConfigurationSettings.NotificationHubConnectionString,
                        context);
                Log.i(TAG, "Notification hub initialized");
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        mChannelId = channelId;
        mUserId = userId;

        registerWithNotificationHubs();
    }

    private void registerWithNotificationHubs() {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    hub.registerBaidu(mUserId, mChannelId);
                    Log.i(TAG, "Registered with Notification Hub - '"
                            + ConfigurationSettings.NotificationHubName + "'"
                            + " with UserId - '"
                            + mUserId + "' and Channel Id - '"
                            + mChannelId + "'");
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
                return null;
            }
        }.execute(null, null, null);
    }

    @Override
    public void onMessage(Context context, String message,
                          String customContentString) {
        String messageString = " onMessage=\"" + message
                + "\" customContentString=" + customContentString;
        Log.d(TAG, messageString);
        if (!TextUtils.isEmpty(customContentString)) {
            JSONObject customJson = null;
            try {
                customJson = new JSONObject(customContentString);
                String myvalue = null;
                if (!customJson.isNull("mykey")) {
                    myvalue = customJson.getString("mykey");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onNotificationArrived(Context context, String title, String description, String customContentString) {
        String notifyString = " Notice Arrives onNotificationArrived  title=\"" + title
                + "\" description=\"" + description + "\" customContent="
                + customContentString;
        Log.d(TAG, notifyString);
        if (!TextUtils.isEmpty(description)) {
            JSONObject customJson = null;
            try {
                customJson = new JSONObject(description);

                if (!customJson.isNull("obj")) {
                    reciverid = customJson.getJSONObject("obj").getString("reciverid");
                    message = customJson.getJSONObject("obj").getString("message");
                    dtype = customJson.getJSONObject("obj").getString("dtype");
                    sname = customJson.getJSONObject("obj").getString("sname");
                    did = customJson.getJSONObject("obj").getString("did");
                    datetime = customJson.getJSONObject("obj").getString("datetime");
                    isread = customJson.getJSONObject("obj").getString("isread");
                    deliver = customJson.getJSONObject("obj").getString("deliver");
                    senderid = customJson.getJSONObject("obj").getString("senderid");

                    Log.d("Message ", reciverid + " : " + dtype + sname + did + datetime + isread + deliver + senderid + message);
                }
                sendNotification(sname,message, context, dtype);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }

    private void sendNotification(String Name,String messageBody, Context context, String Type) {
        Log.e("Message_Body", messageBody.toString());
        Intent intent = new Intent(context, ChatUserListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.app_icon);
        if (dtype.equalsIgnoreCase("msg")) {
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.app_icon)
                    .setContentTitle(Name)
                    .setContentText(messageBody)
                    .setColor(context.getResources().getColor(R.color.colorPrimary))

                    .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                    .setLargeIcon(bm)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, notificationBuilder.build());
        } else {
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.app_icon)
                    .setContentTitle(Name)
                    //.setContentText(messageBody)
                    .setColor(context.getResources().getColor(R.color.colorPrimary))

                    .setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(getBitmapfromUrl(messageBody)))
                    .setLargeIcon(bm)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, notificationBuilder.build());
        }
    }


    @Override
    public void onNotificationClicked(Context context, String title, String description, String customContentString) {
        String notifyString = " onNotificationClicked title=\"" + title + "\" description=\""
                + description + "\" customContent=" + customContentString;
        Log.d(TAG, notifyString);
        Intent intent = new Intent(context.getApplicationContext(), ChatUserListActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("description", description);
        intent.putExtra("isFromNotify", true);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(intent);

    }

    @Override
    public void onSetTags(Context context, int errorCode,
                          List<String> successTags, List<String> failTags, String requestId) {
        String responseString = "onSetTags errorCode=" + errorCode
                + " successTags=" + successTags + " failTags=" + failTags
                + " requestId=" + requestId;
        Log.d(TAG, responseString);

    }

    @Override
    public void onDelTags(Context context, int errorCode,
                          List<String> successTags, List<String> failTags, String requestId) {
        String responseString = "onDelTags errorCode=" + errorCode
                + " successTags=" + successTags + " failTags=" + failTags
                + " requestId=" + requestId;
        Log.d(TAG, responseString);

    }

    @Override
    public void onListTags(Context context, int errorCode, List<String> tags,
                           String requestId) {
        String responseString = "onListTags errorCode=" + errorCode + " tags="
                + tags;
        Log.d(TAG, responseString);

    }

    @Override
    public void onUnbind(Context context, int errorCode, String requestId) {
        String responseString = "onUnbind errorCode=" + errorCode
                + " requestId = " + requestId;
        Log.d(TAG, responseString);

        if (errorCode == 0) {
            // Unbinding is successful
            Log.d(TAG, " Unbinding is successful ");
        }
    }
}
