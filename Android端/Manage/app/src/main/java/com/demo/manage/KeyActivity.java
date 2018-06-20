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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class KeyActivity extends AppCompatActivity {

    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> dataList;
    private RequestQueue mQueue;
String tag,productid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key);
        spinner = (Spinner) findViewById(R.id.spinner);
        dataList = new ArrayList<String>();
        mQueue = Volley.newRequestQueue(this);
 productid=getIntent().getStringExtra("id");
        StringRequest stringRequest = new StringRequest("http://"+getString(R.string.ip)+"/gettags",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray jsonArray=new JSONObject(response).getJSONArray("data");
                            Log.i("judgeproduct",jsonArray.toString());
                            for (int i=jsonArray.length()-1;i>=0;i--){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);

dataList.add(jsonObject.getString("name"));

adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.d("TAG", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
   mQueue.add(stringRequest);

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,dataList);

        //为适配器设置下拉列表下拉时的菜单样式。
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //为spinner绑定我们定义好的数据适配器
        spinner.setAdapter(adapter);
spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        tag=spinner.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
});
        Button post=findViewById(R.id.post);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

if (tag!=null) {
    StringRequest stringRequest1 = new StringRequest("http://" + getString(R.string.ip) + "/setproducttag?id=" + productid + "&tag=" + tag,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("success")){

                        Toast.makeText(KeyActivity.this,"设置成功",Toast.LENGTH_SHORT).show();

                        KeyActivity.this.finish();
                    }
                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("TAG", error.getMessage(), error);
        }
    });

    Log.i("tag",stringRequest1.getUrl());
    mQueue.add(stringRequest1);
}

            }
        });
    }
}
