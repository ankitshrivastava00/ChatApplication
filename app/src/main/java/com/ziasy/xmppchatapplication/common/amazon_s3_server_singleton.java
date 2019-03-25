package com.ziasy.xmppchatapplication.common;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

public class amazon_s3_server_singleton {
    private static final amazon_s3_server_singleton ourInstance = new amazon_s3_server_singleton();

    public static amazon_s3_server_singleton getInstance() {
        return ourInstance;
    }

    private amazon_s3_server_singleton() {
    }
    public class MyTask extends AsyncTask<Void, Void, Void> {

        String result = "";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(Void... params) {


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.e("", "doInBackground: " + result);


            Log.e("post execution", "");
        }

    }
}
