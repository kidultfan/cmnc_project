package com.demo.cmnc;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.demo.cmnc.tools.ZxingUtils;

public class ZxingActivity extends AppCompatActivity {
ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String userid=getIntent().getStringExtra("userid");
        setContentView(R.layout.activity_zxing);
imageView=findViewById(R.id.zxing);
        Bitmap bitmap = ZxingUtils.createBitmap(userid);
        imageView.setImageBitmap(bitmap);
    }
}
