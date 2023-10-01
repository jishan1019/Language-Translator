package com.dhakadevcraft.globallanguagetranslator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PrivacyActivity extends AppCompatActivity {

    WebView browser_view;
//    public static String browser_view_url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);

        browser_view = findViewById(R.id.browser_view);

        browser_view.loadUrl(getResources().getString(R.string.browser_view_url));
        browser_view.setWebViewClient(new WebViewClient());
        browser_view.getSettings().setJavaScriptEnabled(true);
        browser_view.getSettings().setBuiltInZoomControls(true);
        browser_view.getSettings().setUseWideViewPort(true);
        browser_view.getSettings().setLoadsImagesAutomatically(true);
        browser_view.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


    @Override
    public void onBackPressed() {
        if (browser_view.canGoBack()){
            browser_view.goBack();
        } else {
            super.onBackPressed();
        }
    }
}