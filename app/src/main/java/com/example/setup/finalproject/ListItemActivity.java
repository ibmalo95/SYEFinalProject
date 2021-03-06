package com.example.setup.finalproject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

/*
 * The college view; where the individual college info can be seen
 */
public class ListItemActivity extends Activity {

    public static final String LOG_TAG = ListItemActivity.class.getName();

    private TextView collegeName;
    private String url;
    private WebView webView;
    private String addressText;
    private TextView address;
    private TextView admission;
    private TextView size;
    private TextView in_state;
    private TextView out_state;
    private TextView debt;
    private TextView completion;
    private TextView retention;
    private ProgressBar progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);

        // set the views
        collegeName = (TextView) findViewById(R.id.name);
        ArrayList<String> data = (ArrayList<String>) getIntent().getSerializableExtra("INFO");
        collegeName.setText(data.get(0));

        url = "http://" + data.get(1);

        addressText = data.get(2);
        address = (TextView) findViewById(R.id.address);
        address.setText(addressText);

        admission = (TextView) findViewById(R.id.admission);
        String ad_deci = data.get(3);
        long avalue = Math.round(Double.parseDouble(ad_deci) * 100) * 100;
        String ad_percent = Double.toString(avalue/100);
        String ad_text = admission.getText().toString() + " %" + ad_percent;
        admission.setText(ad_text);

        size = (TextView) findViewById(R.id.size);
        String size_text = size.getText().toString() + " " + data.get(4);
        size.setText(size_text);

        in_state = (TextView) findViewById(R.id.in);
        String in_text = in_state.getText().toString() + " $" + data.get(5);
        in_state.setText(in_text);

        out_state = (TextView) findViewById(R.id.out);
        String out_text = out_state.getText().toString() + " $" + data.get(6);
        out_state.setText(out_text);

        completion = (TextView) findViewById(R.id.completion);
        String comp_deci = data.get(7);
        long cvalue = Math.round((Double.parseDouble(comp_deci) * 100) * 100);
        String comp_percent = Long.toString(cvalue/100);
        String comp_text = completion.getText().toString() + " %" + comp_percent;
        completion.setText(comp_text);

        retention = (TextView) findViewById(R.id.retention);
        String reten_deci = data.get(8);
        long rvalue = Math.round((Double.parseDouble(reten_deci) * 100) * 100);
        String reten_percent = Double.toString(rvalue/100);
        String reten_text = retention.getText().toString() + " %" + reten_percent;
        retention.setText(reten_text);

        debt = (TextView) findViewById(R.id.debt);
        String debt_text = debt.getText().toString() + " $" + data.get(9);
        debt.setText(debt_text);

        webView = (WebView)findViewById(R.id.webview);
        progress_bar = (ProgressBar) findViewById(R.id.progress);
        webView.getSettings().setJavaScriptEnabled(true);

        // Change visibility of progress bar on web view
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if(progress < 100 && progress_bar.getVisibility() == ProgressBar.GONE){
                    progress_bar.setVisibility(ProgressBar.VISIBLE);
                }

                progress_bar.setProgress(progress);
                if (progress == 100) {
                    progress_bar.setVisibility(ProgressBar.GONE);
                }
            }
        });
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }
}
