package com.demo.cmnc.fragments;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.demo.cmnc.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about); getSupportActionBar().setTitle("关于崇明农场");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
