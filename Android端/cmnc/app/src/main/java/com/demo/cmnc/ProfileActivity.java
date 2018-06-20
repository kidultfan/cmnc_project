package com.demo.cmnc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
Spinner spinner;
    private ArrayList<String> dataList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        spinner =  findViewById(R.id.spinner);
        getSupportActionBar().setTitle("修改信息");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dataList = new ArrayList<String>();
        dataList.add("男");
        dataList.add("女");
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,dataList);

        //为适配器设置下拉列表下拉时的菜单样式。
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //为spinner绑定我们定义好的数据适配器
        spinner.setAdapter(adapter);




    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
