package com.demo.manage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class AddKeywordActivity extends AppCompatActivity {
    RequestQueue mQueue;
    EditText keyword;
    String tag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_keyword);
        mQueue = Volley.newRequestQueue(this);
        tag=getIntent().getStringExtra("tag");
        Log.i("tag",tag);
        keyword=findViewById(R.id.keyword);
        Button post=findViewById(R.id.post);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest stringRequest = new StringRequest("http://"+getString(R.string.ip)+"/addkeyword?tag="+tag+"&keyword="+keyword.getText().toString(),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                if (response.equals("success!")){
                                    setResult(RESULT_OK);
                                    AddKeywordActivity.this.finish();

                                }
                                else if (response.equals("exist!")){

                                    Toast.makeText(AddKeywordActivity.this,"已存在此关键词",Toast.LENGTH_SHORT).show();

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
                Log.i("tag",stringRequest.getUrl());
            }
        });


    }
}
