package com.demo.cmnc.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.demo.cmnc.JudgeActivity;
import com.demo.cmnc.R;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailsActivity extends AppCompatActivity {
LinearLayout fromlayout;
LinearLayout judge;
LinearLayout like;
ImageView like_img;
TextView title,price,farm,grade,likes,info;
String from;
ImageView pic;
    private boolean islike;
    String userid;
SharedPreferences sp;
    private RequestQueue mQueue;
    private String productid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final String ptitle = getIntent().getStringExtra("title");

        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("User", Context.MODE_PRIVATE);
//userid=sp.getString("userid","2");
        userid="2";
 productid="2";




        setContentView(R.layout.activity_details);
        title=findViewById(R.id.pt_title);
        price=findViewById(R.id.pt_price);
        pic=findViewById(R.id.pt_pic);
        farm=findViewById(R.id.pt_farm);
        info=findViewById(R.id.pt_info);
        fromlayout=findViewById(R.id.from_view);
        judge=findViewById(R.id.judge);
        like=findViewById(R.id.like);
        likes=findViewById(R.id.pt_like);
        like_img=findViewById(R.id.like_img);
         mQueue = Volley.newRequestQueue(this);

        StringRequest getdetails = new StringRequest("http://"+getString(R.string.ip)+"/getproduct?title="+ptitle+"",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            Log.i("result",response);
                            JSONObject jsonObject=new JSONObject(response);
                            Log.i("resultimage",jsonObject.getString("image"));

                            Log.i("title",jsonObject.getString("title"));
                            title.setText(jsonObject.getString("title"));
                            price.setText(jsonObject.getInt("price")+"");
                            likes.setText(jsonObject.getInt("like")+"");
                            info.setText(jsonObject.getString("info"));
                            farm.setText(jsonObject.getString("farm"));

                            Glide.with(DetailsActivity.this)
                                    .load(jsonObject.getString("image"))
                                    .into(pic);
from=jsonObject.getString("farm");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });

        StringRequest getislike = new StringRequest("http://"+getString(R.string.ip)+"/islike?userid="+userid+"&productid="+productid+"&type=product",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("islike",response);
                        if (response.equals("true")){

                            islike=true;
                            like_img.setBackgroundResource(R.drawable.likee);
                        }
                        else {
                            like_img.setBackgroundResource(R.drawable.like);

                            islike=false;
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        Log.i("islike",getislike.getUrl());


        mQueue.add(getdetails);
mQueue.add(getislike);

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!islike) {
                    like_img.setBackgroundResource(R.drawable.likee);
                    like_img.startAnimation(AnimationUtils.loadAnimation(
                            DetailsActivity.this, R.anim.like)
                    );

                    StringRequest addlike = new StringRequest("http://"+getString(R.string.ip)+"/addlike?userid="+userid+"&productid="+productid,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                if (response.equals("success!"))
                                {

                                    islike=true;
                                    Toast.makeText(DetailsActivity.this,"收藏成功",Toast.LENGTH_SHORT).show();

                                }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("TAG", error.getMessage(), error);
                        }
                    });

                    Log.i("addlike",addlike.getUrl());

                    mQueue.add(addlike);

                }

                else {

                    like_img.setBackgroundResource(R.drawable.like);
                    like_img.startAnimation(AnimationUtils.loadAnimation(
                            DetailsActivity.this, R.anim.like)
                    );
                    islike=false;
//
                    StringRequest dislike = new StringRequest("http://"+getString(R.string.ip)+"/dislike?userid="+userid+"&productid="+productid,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    if (response.equals("success!"))
                                    {

                                        islike=false;
                                        Toast.makeText(DetailsActivity.this,"取消成功",Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("TAG", error.getMessage(), error);
                        }
                    });

Log.i("dislike",dislike.getUrl());
                    mQueue.add(dislike);

                }
            }
        });
        judge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailsActivity.this,JudgeActivity.class).putExtra("productid",productid).putExtra("type","product"));

            }
        });
        fromlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailsActivity.this,FarmActivity.class).putExtra("farm",from).putExtra("type","product"));
            }
        });
    }
}
