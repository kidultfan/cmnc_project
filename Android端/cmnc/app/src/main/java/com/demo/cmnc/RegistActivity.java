package com.demo.cmnc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class RegistActivity extends AppCompatActivity implements TextWatcher {
EditText id,name,pwd,pwd2;
Button regist;
    RequestQueue mQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         mQueue = Volley.newRequestQueue(this);

        setContentView(R.layout.activity_regist);
        id=findViewById(R.id.edit_id);
        name=findViewById(R.id.edit_name);
        pwd=findViewById(R.id.edit_pwd);
        pwd2=findViewById(R.id.edit_pwd2);
        id.addTextChangedListener(this);
        name.addTextChangedListener(this);
        pwd.addTextChangedListener(this);
        pwd2.addTextChangedListener(this);
        regist=findViewById(R.id.btn_regist);
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest stringRequest = new StringRequest("http://"+R.string.ip+"/regist/?id="+id.getText().toString()+"&name="+name.getText().toString()+"&pwd="+pwd.getText().toString(),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Log.i("result",response);
                               if (response.equals("success!")){
                                   Toast.makeText(RegistActivity.this,"注册成功",Toast.LENGTH_SHORT).show();

                                   new Thread() {
                                       @Override
                                       public void run() {
                                           super.run();
                                           try {
                                               Thread.sleep(1000);//休眠3秒
                                               RegistActivity.this.finish();
                                           } catch (InterruptedException e) {
                                               e.printStackTrace();
                                           }
                                           /**
                                            * 要执行的操作
                                            */
                                       }
                                   }.start();
                               }
                               else {
                                   Toast.makeText(RegistActivity.this,"注册失败",Toast.LENGTH_SHORT).show();

                               }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(RegistActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
                        Log.e("TAG", error.getMessage(), error);
                    }
                });
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0, 1.0f));

                mQueue.add(stringRequest);

            }
        });

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (id.getText().length()>0&&name.getText().length()>0&&pwd.getText().length()>0&&pwd2.getText().length()>0&&pwd.getText().toString().equals(pwd2.getText().toString())){
            regist.setEnabled(true);
            regist.setBackgroundColor(getResources().getColor(R.color.cornflowerblue));


        }

    }
}
