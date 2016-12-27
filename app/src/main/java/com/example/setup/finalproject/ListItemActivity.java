package com.example.setup.finalproject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

public class ListItemActivity extends Activity {

    public static final String LOG_TAG = ListItemActivity.class.getName();

    private TextView collegeName = null;
    private String url = null;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);

        collegeName = (TextView) findViewById(R.id.name);
        String[] info = getIntent().getStringArrayExtra("INFO");
        collegeName.setText(info[0]);
        url = "http://" + info[1];
        Log.i(LOG_TAG, url);

        webView = (WebView)findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);

    }
}