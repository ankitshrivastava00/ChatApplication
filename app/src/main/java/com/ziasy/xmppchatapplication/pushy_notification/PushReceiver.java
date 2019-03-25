package com.ziasy.xmppchatapplication.pushy_notification;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.Context;
import android.app.PendingIntent;
import android.media.RingtoneManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.ziasy.xmppchatapplication.ChatActivity;
import com.ziasy.xmppchatapplication.ChatUserListActivity;
import com.ziasy.xmppchatapplication.R;
import com.ziasy.xmppchatapplication.common.SessionManagement;
import com.ziasy.xmppchatapplication.group_chat.activity.GroupChatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PushReceiver extends BroadcastReceiver {
    private String pusddid = null,reciverid = null, image = null, description = null, admin = null, chattype = null, message = null, dtype = null, senderid = null, isread = null, deliver = null, sname = null, did = null, datetime = null;
    SessionManagement sd;
    Intent intent;

    @Override
    public void onReceive(Context context, Intent intent) {
        String notificationTitle = "MyApp";
        String notificationText = "Test notification";
        sd = new SessionManagement(context);
        if (sd.getLoginStatus().equalsIgnoreCase("true")) {
            // Attempt to extract the "message" property from the payload: {"message":"Hello World!"}
            if (intent.getStringExtra("message") != null) {
                notificationText = intent.getStringExtra("message");
            }

            // Log.e("MessageseartsertBody", notificationText.toString());
            if (!TextUtils.isEmpty(notificationText)) {
                JSONObject customJson = null;
//                Log.d("JDONVJDBDFD",customJson.toString());
                try {
                    customJson = new JSONObject(notificationText);
                    Log.d("JDONVJDBDFD",customJson.toString());
                    //  if (!customJson.isNull("obj")) {
                    image = customJson.optString("image");
                    description = customJson.optString("description");
                    admin = customJson.optString("admin");
                    chattype = customJson.optString("chattype");
                    reciverid = customJson.optString("reciverid");
                    pusddid = customJson.optString("pusddid");

                    message = customJson.getString("message");
                    dtype = customJson.getString("dtype");
                    sname = customJson.getString("sname");
                    did = customJson.getString("did");
                    datetime = customJson.getString("datetime");
                    isread = customJson.getString("isread");
                    deliver = customJson.getString("deliver");
                    senderid = customJson.getString("senderid");

                    Log.d("Message_per ", reciverid + " : " + dtype + sname + did + datetime + isread + deliver + senderid + message);
                    // }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            try {
                sendNotification(Integer.parseInt(senderid), sname, message, context, dtype, chattype);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Notification_Expre", e.toString());

            }
        }
    }

    private void sendNotification(int id, String Name, String messageBody, Context context, String Type, String ChatType) {
        try {

            Log.e("Message_Body", messageBody.toString());


            if (ChatType.equalsIgnoreCase("group")) {
                intent = new Intent(context, GroupChatActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent.putExtra("image", image);
                intent.putExtra("description", description);
                intent.putExtra("admin", admin);
            } else if (ChatType.equalsIgnoreCase("indivisual")) {
                intent = new Intent(context, ChatActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            }
            intent.putExtra("name", sname);
            intent.putExtra("rid", senderid);
            intent.putExtra("chattype", dtype);
            intent.putExtra("did", pusddid);
            intent.putStringArrayListExtra("forwardString", new ArrayList<>());

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.app_icon);
            if (Type.equalsIgnoreCase("msg")) {
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

                notificationManager.notify(id, notificationBuilder.build());
                //  ((Activity)context).finish();
            } else if (Type.equalsIgnoreCase("img")) {

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
                notificationManager.notify(id, notificationBuilder.build());
            } else if (Type.equalsIgnoreCase("pdf")) {
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.app_icon)
                        .setContentTitle(Name)
                        .setContentText("Document")
                        .setColor(context.getResources().getColor(R.color.colorPrimary))
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("Document"))
                        .setLargeIcon(bm)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);
                NotificationManager notificationManager =
                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(id, notificationBuilder.build());
            } else if (Type.equalsIgnoreCase("video")) {
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.app_icon)
                        .setContentTitle(Name)
                        .setContentText("Video")
                        .setColor(context.getResources().getColor(R.color.colorPrimary))
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("Video"))
                        .setLargeIcon(bm)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);
                NotificationManager notificationManager =
                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(id, notificationBuilder.build());
            } else if (Type.equalsIgnoreCase("share")) {
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.app_icon)
                        .setContentTitle(Name)
                        .setContentText("Contact Details")
                        .setColor(context.getResources().getColor(R.color.colorPrimary))
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("Contact Details"))
                        .setLargeIcon(bm)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);
                NotificationManager notificationManager =
                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(id, notificationBuilder.build());
            } else if (Type.equalsIgnoreCase("audio")) {
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.app_icon)
                        .setContentTitle(Name)
                        .setContentText("Audio File")
                        .setColor(context.getResources().getColor(R.color.colorPrimary))

                        .setStyle(new NotificationCompat.BigTextStyle().bigText("Audio File"))
                        .setLargeIcon(bm)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

                NotificationManager notificationManager =
                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                notificationManager.notify(id, notificationBuilder.build());
            } else if (Type.equalsIgnoreCase("location")) {
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.app_icon)
                        .setContentTitle(Name)
                        .setContentText("Location")
                        .setColor(context.getResources().getColor(R.color.colorPrimary))

                        .setStyle(new NotificationCompat.BigTextStyle().bigText("Location"))
                        .setLargeIcon(bm)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

                NotificationManager notificationManager =
                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                notificationManager.notify(id, notificationBuilder.build());
            }else if (Type.equalsIgnoreCase("emoji")) {
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.app_icon)
                        .setContentTitle(Name)
                        .setContentText("Emoji")
                        .setColor(context.getResources().getColor(R.color.colorPrimary))

                        .setStyle(new NotificationCompat.BigTextStyle().bigText("Emoji"))
                        .setLargeIcon(bm)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

                NotificationManager notificationManager =
                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                notificationManager.notify(id, notificationBuilder.build());
            }

        } catch (Exception e) {
            e.printStackTrace();
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


}