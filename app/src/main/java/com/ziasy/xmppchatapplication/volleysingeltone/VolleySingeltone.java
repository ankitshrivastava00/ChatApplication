package com.ziasy.xmppchatapplication.volleysingeltone;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import java.io.File;
import java.util.HashMap;
import java.util.Map;



/**
 * Created by shubham on 10/6/2017.
 */

public class VolleySingeltone {
    private Context context;
    private ReturnVolleyResult returnVolleyResult;
    String filePath;
    String url, userId, description, type, title;
    MultipartEntityBuilder entityBuilder;
    private static final VolleySingeltone ourInstance = new VolleySingeltone();

    public static VolleySingeltone getInstance() {
        return ourInstance;
    }

    private VolleySingeltone() {

    }

    public void callGetApi(Context context, final ReturnVolleyResult returnVolleyResult, String url, final HashMap<String, String> param) {
        this.returnVolleyResult = returnVolleyResult;
        this.context = context;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response", response);
                        try {
                             returnVolleyResult.returnResult(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.toString());
                        try {
                            returnVolleyResult.returnResult(error.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                if (param != null) {
                    Map<String, String> params = new HashMap<String, String>(param);
                    return params;
                }
                return null;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }


    // public void callMultipartApi(Context context, String url, String filePath, String userId, String desc, String type, final ReturnVolleyResult returnVolleyResult) {
    public void callMultipartApi(MultipartEntityBuilder entityBuilder, String url,final ReturnVolleyResult returnVolleyResult) {
        this.url=url;
        this.returnVolleyResult = returnVolleyResult;
        this.entityBuilder = entityBuilder;
        new MyTask4().execute();
    }

    public class MyTask4 extends AsyncTask<Void, Void, Void> {

        String result = "";

        @Override
        protected Void doInBackground(Void... params) {
            try {
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(url);
                entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                HttpEntity entity = entityBuilder.build();
                post.setEntity(entity);
                HttpResponse response = client.execute(post);
                HttpEntity httpEntity = response.getEntity();
                result = EntityUtils.toString(httpEntity);
                //session.setKeyIsVerify(false);
              //  returnVolleyResult.returnResult(result);

            } catch (Exception e) {
                e.printStackTrace();
                try {
                    returnVolleyResult.returnResult(e.toString());
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.e("", "doInBackground: " + result);
            try {
                returnVolleyResult.returnResult(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.e("post execution", "");
        }

    }
}





