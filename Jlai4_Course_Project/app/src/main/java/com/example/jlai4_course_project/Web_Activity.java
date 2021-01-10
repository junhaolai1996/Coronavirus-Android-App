package com.example.jlai4_course_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Web_Activity extends AppCompatActivity {

    WebView getMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_);

        getMap = (WebView) findViewById(R.id.web_map);
        getMap.setWebViewClient(new WebViewClient());
        getMap.loadUrl("https://coronavirus.jhu.edu/map.html");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}