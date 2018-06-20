package com.demo.manage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class ChooseActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private ArrayList<String> dataList;
    private Spinner spinner;
Button post;

String mark,productid;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        spinner = (Spinner) findViewById(R.id.spinner);
         productid=getIntent().getStringExtra("id");
        mQueue = Volley.newRequestQueue(this);

        dataList = new ArrayList<String>();
        dataList.add("新品上市");
        dataList.add("精品推荐");
        dataList.add("特价产品");
        dataList.add("热销产品");
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,dataList);

        //为适配器设置下拉列表下拉时的菜单样式。
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //为spinner绑定我们定义好的数据适配器
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mark=spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        post=findViewById(R.id.post);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mark!=null) {
                    StringRequest stringRequest1 = new StringRequest("http://" + getString(R.string.ip) + "/setproductmark?id=" + productid + "&mark=" + mark,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (response.equals("success")){

                                        Toast.makeText(ChooseActivity.this,"设置成功",Toast.LENGTH_SHORT).show();
                                        ChooseActivity.this.finish();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("TAG", error.getMessage(), error);
                        }
                    });
                    mQueue.add(stringRequest1);
                }

            }
        });
       ;

    }
}
