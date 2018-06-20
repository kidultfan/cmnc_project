package com.demo.cmnc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class OtherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
       TextView time= (TextView)findViewById(R.id.time_o);
       time.setText(getIntent().getStringExtra("time"));
        TextView scope= (TextView)findViewById(R.id.scope_o);
        scope.setText(getIntent().getStringExtra("scope"));
        getSupportActionBar().setTitle("其他信息");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

}
