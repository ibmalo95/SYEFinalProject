package com.example.setup.finalproject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

/*
 * The college view; where the individual college info can be seen
 */
public class ListItemActivity extends Activity {

    public static final String LOG_TAG = ListItemActivity.class.getName();

    private TextView collegeName = null;
    private String url = null;
    private WebView webView;
    private String addressText = null;
    private TextView address = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);

        collegeName = (TextView) findViewById(R.id.name);
        String[] info = getIntent().getStringArrayExtra("INFO");
        collegeName.setText(info[0]);
        url = "http://" + info[1];
        addressText = info[2] + ", " + info[3] + ", " + info[4];
        address = (TextView) findViewById(R.id.address);
        address.setText(addressText);

        webView = (WebView)findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);

    }
}
