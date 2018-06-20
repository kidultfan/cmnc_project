package com.demo.cmnc;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class UpdateActivity extends AppCompatActivity {
    RequestQueue mQueue;
    LinearLayout hint;
    TextView number;
    String word;
    String productname,farmname;
    String userid;
    Button post;
    EditText judgeword;
    String jid;
    RatingBar bar1,bar2,bar3,bar4;
    String grade1="",grade2="",grade3="",grade4="",type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
word=getIntent().getStringExtra("word");
        type=getIntent().getStringExtra("type");
        mQueue = Volley.newRequestQueue(this);

        judgeword=findViewById(R.id.judgeword);
        number=findViewById(R.id.number);
        post=findViewById(R.id.post);
        judgeword.setText(getIntent().getStringExtra("word"));
        bar1=findViewById(R.id.bar1);
        bar2=findViewById(R.id.bar2);
        bar3=findViewById(R.id.bar3);
        bar4=findViewById(R.id.bar4);
        grade1=(getIntent().getStringExtra("grade1"));
        grade2=(getIntent().getStringExtra("grade2"));
        grade3=(getIntent().getStringExtra("grade3"));
        grade4=(getIntent().getStringExtra("grade4"));
        jid=(getIntent().getStringExtra("jid"));
bar1.setRating(        Float.parseFloat(grade1));
        bar2.setRating(        Float.parseFloat(grade2));
        bar3.setRating(        Float.parseFloat(grade3));
        bar4.setRating(        Float.parseFloat(grade4));
        hint=findViewById(R.id.hint);
        int n=10-word.length();
        number.setText(n+"");

        if (word.length()>=10){
            hint.setVisibility(View.INVISIBLE);
            post.setEnabled(true);
            post.setBackgroundColor(getResources().getColor(R.color.colorAccent));

        }
        else {

            hint.setVisibility(View.VISIBLE);
            post.setEnabled(false);
            post.setBackgroundColor(getResources().getColor(R.color.lightgray));

        }

        bar1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                grade1=String.valueOf(rating);
            }
        });

        bar2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                grade2=String.valueOf(rating);

            }
        });

        bar3.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                grade3=String.valueOf(rating);

            }
        });

        bar4.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                grade4=String.valueOf(rating);

            }
        });
        judgeword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void afterTextChanged(Editable editable) {



                word=editable.toString();
                int n=10-word.length();
                number.setText(n+"");

                if (word.length()>=10){
                    hint.setVisibility(View.INVISIBLE);
                    post.setEnabled(true);
                    post.setBackgroundColor(getResources().getColor(R.color.colorAccent));

                }
                else {

                    hint.setVisibility(View.VISIBLE);
                    post.setEnabled(false);
                    post.setBackgroundColor(getResources().getColor(R.color.lightgray));

                }


            }
        });


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url;
                    url="http://"+getString(R.string.ip)+"/changejudges?userid="+"&grade1="+grade1+"&grade2="+grade2+"&grade3="+grade3+"&grade4="+grade4+"&word="+word+"&type="+type;



                    url="http://"+getString(R.string.ip)+"/changejudges?jid="+jid+"&grade1="+grade1+"&grade2="+grade2+"&grade3="+grade3+"&grade4="+grade4+"&word="+word+"&type="+type;



                Log.i("url",url);

                StringRequest stringRequest = new StringRequest(url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Log.d("TAG", response);
                                if (response.equals("success!"))
                                {

                                    Toast.makeText(UpdateActivity.this,"修改成功！",Toast.LENGTH_SHORT).show();
                                    UpdateActivity.this.finish();

                                }

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

    }
}
