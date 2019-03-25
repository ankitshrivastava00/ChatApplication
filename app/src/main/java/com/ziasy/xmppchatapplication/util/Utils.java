package com.ziasy.xmppchatapplication.util;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

public class Utils {


    public static void downloadFile(Context context, String path, String fileName) {
        try {
            // String str = list.get(position).getPath() + list.get(position).getFile_name();
            //String str = list.get(position).getFile_name();
            String   str = path.replaceAll(" ", "%20");
            Uri source = Uri.parse(str);
            //  Uri source = Uri.parse(list1.get(position).getUrl() + list1.get(position).getFilename());

            DownloadManager.Request request = new DownloadManager.Request(
                    source);
            request.setDescription("Description for the DownloadManager Bar");

            request.setTitle("Upload Download History: " + fileName);
            request.setAllowedOverRoaming(false);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            }
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
            DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            manager.enqueue(request);
          /*  Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
            context.startActivity(intent);*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
