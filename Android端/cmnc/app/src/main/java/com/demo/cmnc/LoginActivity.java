package com.demo.cmnc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class LoginActivity extends AppCompatActivity {
TextView regist;
Button  login;
EditText id,pwd;
    RequestQueue mQueue;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         mQueue = Volley.newRequestQueue(this);
        id=findViewById(R.id.userid);
        pwd=findViewById(R.id.userpwd);
login=findViewById(R.id.btn_login);
         editor = getSharedPreferences("User",MODE_PRIVATE).edit();

        login.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        final String id_text=id.getText().toString();
        String pwd_text=pwd.getText().toString();

        StringRequest stringRequest = new StringRequest("http://"+getString(R.string.ip)+"/login?id="+id_text+"&&pwd="+pwd_text,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

if (response.equals("success!")){
    setResult(RESULT_OK);
    LoginActivity.this.finish();
editor.putString("userid",id_text);
editor.apply();


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
});
        regist=findViewById(R.id.text_regist);
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegistActivity.class));
            }
        });
    }
}
