package com.demo.manage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
Spinner spinner;
    private ArrayList<String> dataList;
    private ArrayAdapter<String> adapter;
    SharedPreferences sp;
    String farmname;
    private RequestQueue mQueue;
    TextView name;
EditText  scope,time,address,info,phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle("修改信息");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sp=getSharedPreferences("User", Context.MODE_PRIVATE);
        farmname = sp.getString("farmname", "");

        mQueue = Volley.newRequestQueue(this);
     address=findViewById(R.id.address);
        scope=findViewById(R.id.scope);
        time=findViewById(R.id.time);
        info=findViewById(R.id.info);
        phone=findViewById(R.id.phone);
name=findViewById(R.id.name);
        name.setText(farmname);


        StringRequest getdetails = new StringRequest("http://"+getString(R.string.ip)+"/getfarm?title="+farmname,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse( String response) {
                        Log.i("result222",response);

//                            getActivity().runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {

                        try {

                            final JSONObject jsonObject=new JSONObject(response);
                            Log.i("result123",jsonObject.getString("phone"));
                            //此时已在主线程中，可以更新UI了
                            address.setText(jsonObject.getString("location"));
                            scope.setText(jsonObject.getString("scope"));
                            time.setText(jsonObject.getString("time"));
                            info.setText(jsonObject.getString("info"));
                            Log.i("result123",jsonObject.getString("phone"));

                            phone.setText(jsonObject.getString("phone"));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                                }
//                            });





                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });

        mQueue.add(getdetails);

        Button post=findViewById(R.id.profile_post);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringRequest getdetails = new StringRequest("http://"+getString(R.string.ip)+"/setfarm?name="+farmname+"&scope="+scope.getText().toString()+"&time="+time
                        .getText().toString()+"&address="+address.getText().toString()+"&info="+info
                        .getText().toString()+"&phone="+phone
                        .getText().toString(),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse( String response) {
                                Log.i("result222",response);

if (response.equals("success")){
    Toast.makeText(ProfileActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
    ProfileActivity.this.finish();
}

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", error.getMessage(), error);
                    }
                });

                mQueue.add(getdetails);
            }
        });


    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
