package com.demo.manage.farm;

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
import com.demo.manage.R;

public class AddtagActivity extends AppCompatActivity {
    RequestQueue mQueue;
    EditText tag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtag);
        mQueue = Volley.newRequestQueue(this);
          tag=findViewById(R.id.tag);
        Button post=findViewById(R.id.post);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tag.getText().toString().length()==0){

                    Toast.makeText(AddtagActivity.this,"不能为空",Toast.LENGTH_SHORT).show();
                }
                else {

                StringRequest stringRequest = new StringRequest("http://"+getString(R.string.ip)+"/addtag?tag="+tag.getText().toString(),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                if (response.equals("success!")){
setResult(RESULT_OK);
              AddtagActivity.this.finish();

                                }
                                else if (response.equals("exist!")){

                                    Toast.makeText(AddtagActivity.this,"已存在此语义",Toast.LENGTH_SHORT).show();

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
            }
            }
        });
    }
}
