package com.ziasy.xmppchatapplication.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ziasy.xmppchatapplication.ConnectionDetector;
import com.ziasy.xmppchatapplication.R;
import com.ziasy.xmppchatapplication.common.PdfViewr;

public class TestActrivity  extends AppCompatActivity {
    WebView webView;
    String url, id;
    ProgressDialog pd;
    ConnectionDetector cd;
    String strurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pdf);
        try {
            cd = new ConnectionDetector(TestActrivity.this);
            if (!cd.isConnectingToInternet()) {
                Snackbar.make(TestActrivity.this.findViewById(android.R.id.content), "Internet Connection not available...", Snackbar.LENGTH_LONG)
                        .setActionTextColor(Color.RED)
                        .show();
            } else {
                pd = new ProgressDialog(TestActrivity.this);
                pd.show();
                pd.setMessage("Please wait...");
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                webView = (WebView) findViewById(R.id.webview);
                url = getIntent().getStringExtra("pdf");
                // Log.e("URL",url);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setAllowFileAccess(true);
                webView.getSettings().setBuiltInZoomControls(true);
                webView.getSettings().setPluginState(WebSettings.PluginState.OFF);
                //  Log.e("path", "");
                //webView.loadRequest("https://udtalks.sgp1.digitaloceanspaces.com/1549651441.pdf");
                //  webView.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + url);
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        pd.dismiss();
                      /*  webView.loadUrl("javascript:(function() { " +
                                "document.querySelector('[role=\"toolbar\"]').remove();})()");*/
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}