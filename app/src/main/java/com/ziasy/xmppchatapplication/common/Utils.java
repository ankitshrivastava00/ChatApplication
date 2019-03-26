package com.ziasy.xmppchatapplication.common;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.SystemClock;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Utils {

    public static long INTERVAL = 5 * 60 * 1000;
    SessionManagement sd;

  /*  public static void scheduleAlarm(Context pContext) {
        Log.e("scheduleAlarm()", "scheduleAlarm()");

     *//*   //Wake up lock0
        PowerManager.WakeLock screenLock = ((PowerManager) pContext.getSystemService(Context.POWER_SERVICE)).newWakeLock(
                PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
        screenLock.acquire();


        CustomLogger.getInsatance(pContext).putLog(" In ScheduleAlarm");*//*


      *//*  if (android.os.Build.VERSION.SDK_INT >= 21) {
            JobScheduler jobScheduler = (JobScheduler) pContext
                    .getSystemService(Context.JOB_SCHEDULER_SERVICE);
            JobInfo.Builder builder = new JobInfo.Builder(1, new ComponentName(
                    pContext.getPackageName(),
                    JobSchedulerService.class.getName()));
            builder.setPeriodic(INTERVAL);
            jobScheduler.schedule(builder.build());
        } else {*//*

      *//*
        AlarmManager alarmManager;
        PendingIntent alarmIntent;
        alarmManager = (AlarmManager) pContext
                .getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(pContext, UpdateLocationService.class);
        alarmIntent = PendingIntent.getService(pContext, 0, intent, 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis(), INTERVAL, alarmIntent);

        //wake up release
        screenLock.release();*//*
        Intent alarm = new Intent(pContext, SampleReceiver.class);
        boolean alarmRunning = (PendingIntent.getBroadcast(pContext, 0, alarm, PendingIntent.FLAG_NO_CREATE) != null);
        if(alarmRunning == false) {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(pContext, 0, alarm, 0);
            AlarmManager alarmManager = (AlarmManager) pContext.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), INTERVAL, pendingIntent);
        }
    }


    public static void cancelAlarm(Context pContext) {

        CustomLogger.getInsatance(pContext).putLog(" In CancelAlarm");
        // if (android.os.Build.VERSION.SDK_INT < 21) {
        Intent intent = new Intent(pContext, UpdateLocationService.class);
        PendingIntent alarmIntent = PendingIntent.getService(pContext, 0,
                intent, 0);
        AlarmManager alarmManager = (AlarmManager) pContext
                .getSystemService(Context.ALARM_SERVICE);
        alarmIntent.cancel();
        alarmManager.cancel(alarmIntent);
       *//* } else {
            JobScheduler mJobScheduler = (JobScheduler) pContext
                    .getSystemService(Context.JOB_SCHEDULER_SERVICE);
            mJobScheduler.cancel(1);
        }*//*

    }*/

    public static String makeHttpURLConnection(String url,
                                               String requestString, Context context) throws IOException {
        Log.e("makeHttpURLConnection()", "makeHttpURLConnection()");

        String responseString = null;
        try {

            URL requestURL = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) requestURL
                    .openConnection();
            setHttpCongiguration(conn);
            if (requestString != null) {
                OutputStreamWriter writer = new OutputStreamWriter(
                        conn.getOutputStream());
                writer.write(requestString);
                writer.flush();
                writer.close();
            }
            int HttpResult = conn.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                InputStream in = new BufferedInputStream(conn.getInputStream());
                responseString = Utils.readStream(in);
                in.close();

            } else {
                Log.e("TAG", conn.getResponseMessage());
            }
            conn.disconnect();
        } catch (Exception e) {

            Log.e("CONNECTION ERROR OCCUR ", e.toString());
            try {

                URL requestURL = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) requestURL
                        .openConnection();
                setHttpCongiguration(conn);
                if (requestString != null) {
                    OutputStreamWriter writer = new OutputStreamWriter(
                            conn.getOutputStream());
                    writer.write(requestString);
                    writer.flush();
                    writer.close();
                }
                int HttpResult = conn.getResponseCode();
                if (HttpResult == HttpURLConnection.HTTP_OK) {
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    responseString = Utils.readStream(in);
                    in.close();

                } else {
                    Log.e("TAG", conn.getResponseMessage());
                }
                conn.disconnect();

            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        //   CustomLogger.getInsatance(context).putLog(":::Response String :::" + responseString);
        return responseString;
    }

    private static String readStream(InputStream in) {
        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    in, "UTF-8"));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        } catch (IOException ioe) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }
    public static boolean autoStartPermission(final Context context, String manufacturer) {

        if ("xiaomi".equalsIgnoreCase(manufacturer)) {
            try {
                final Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setComponent(new ComponentName("com.miui.securitycenter",
                        "com.miui.permcenter.autostart.AutoStartManagementActivity"));
                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        if ("oppo".equalsIgnoreCase(manufacturer)) {
            try {
                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClassName("com.coloros.safecenter",
                        "com.coloros.safecenter.permission.startup.StartupAppListActivity");
                context.startActivity(intent);
            } catch (Exception e) {
                try {
                    Intent intent = new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setClassName("com.oppo.safe",
                            "com.oppo.safe.permission.startup.StartupAppListActivity");
                    context.startActivity(intent);

                } catch (Exception ex) {
                    try {
                        Intent intent = new Intent();
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setClassName("com.coloros.safecenter",
                                "com.coloros.safecenter.startupapp.StartupAppListActivity");
                        context.startActivity(intent);
                    } catch (Exception exx) {

                    }
                }
            }
            return true;
        }
        if ("vivo".equalsIgnoreCase(manufacturer)) {
            try {
                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setComponent(new ComponentName("com.iqoo.secure",
                        "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity"));
                context.startActivity(intent);
            } catch (Exception e) {
                try {
                    Intent intent = new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setComponent(new ComponentName("com.vivo.permissionmanager",
                            "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"));
                    context.startActivity(intent);
                } catch (Exception ex) {
                    try {
                        Intent intent = new Intent();
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setClassName("com.iqoo.secure",
                                "com.iqoo.secure.ui.phoneoptimize.BgStartUpManager");
                        context.startActivity(intent);
                    } catch (Exception exx) {
                        ex.printStackTrace();
                    }
                }
            }
            return true;
        }
        if ("huawei".equalsIgnoreCase(manufacturer)) {
            try {
                final Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity"));
                context.startActivity(intent);
            } catch (Exception e) {

            }
            return true;
        }
        if ("Honor".equalsIgnoreCase(manufacturer)) {
            try {
                final Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity"));
                context.startActivity(intent);
            } catch (Exception e) {

            }
            return true;
        }
        if ("Letv".equalsIgnoreCase(manufacturer)) {
            try {
                final Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setComponent(new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.AutobootManageActivity"));
                context.startActivity(intent);
            } catch (Exception e) {

            }
            return true;
        }
        if ("oneplus".equalsIgnoreCase(manufacturer)) {
            try {
                final Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setComponent(new ComponentName("com.oneplus.security", "com.oneplus.security.chainlaunch.view.ChainLaunchAppListAct‌​ivity"));
                context.startActivity(intent);
            } catch (Exception e) {

            }
            return true;
        }
        return false;
    }
    private static void setHttpCongiguration(HttpURLConnection conn) {
        conn.setReadTimeout(50 * 1000 /* milliseconds */);
        conn.setConnectTimeout(50 * 1000 /* milliseconds */);
        try {
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();
        } catch (ProtocolException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
