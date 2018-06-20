package com.demo.cmnc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class JudgeActivity extends AppCompatActivity {
RatingBar bar1,bar2,bar3,bar4;
String grade1="0.0",grade2="0.0",grade3="0.0",grade4="0.0",word;
Button post;
EditText judgeword;
    RequestQueue mQueue;
    LinearLayout hint;
    TextView number;
     String type;
     String productname,farmname,productid;
    String userid;
    SharedPreferences sp;
   EditText price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    type=getIntent().getStringExtra("type");
        setContentView(R.layout.activity_judge);

        sp = getSharedPreferences("User", Context.MODE_PRIVATE);
        userid=sp.getString("userid","");

        if (type.equals("product")){
        productname=getIntent().getStringExtra("productname");
            productid=getIntent().getStringExtra("productid");
            LinearLayout priceview=  (LinearLayout)findViewById(R.id.price_view);
            priceview.setVisibility(View.GONE);

    }
else {
        farmname=getIntent().getStringExtra("farmname");
          LinearLayout priceview=  (LinearLayout)findViewById(R.id.price_view);
            priceview.setVisibility(View.VISIBLE);
        price=findViewById(R.id.price);

    }
        getSupportActionBar().setTitle("添加评价");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         mQueue = Volley.newRequestQueue(this);
         hint=findViewById(R.id.hint);
        judgeword=findViewById(R.id.judgeword);
        number=findViewById(R.id.number);
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

         post=findViewById(R.id.post);
         post.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                     String url;
                     if (type.equals("product")){
                         url="http://"+getString(R.string.ip)+"/addjudges?userid="+userid+"&productname="+productname+"&productid="+productid+"&grade1="+grade1+"&grade2="+grade2+"&grade3="+grade3+"&grade4="+grade4+"&word="+word+"&type="+type;

                     }
                     else {


                         url="http://"+getString(R.string.ip)+"/addjudges?userid="+userid+"&farmname="+farmname+"&grade1="+grade1+"&grade2="+grade2+"&grade3="+grade3+"&grade4="+grade4+"&word="+word+"&type="+type+"&price="+price.getText().toString();

                     }

                     Log.i("url",url);

                     StringRequest stringRequest = new StringRequest(url,
                             new Response.Listener<String>() {
                                 @Override
                                 public void onResponse(String response) {

                                     Log.d("TAG", response);
                                     if (response.equals("success!"))
                                     {

                                         Toast.makeText(JudgeActivity.this,"评价成功！",Toast.LENGTH_SHORT).show();
                                         JudgeActivity.this.finish();

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




        bar1=findViewById(R.id.bar1);
        bar2=findViewById(R.id.bar2);
        bar3=findViewById(R.id.bar3);
        bar4=findViewById(R.id.bar4);
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

    }





//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle presses on the action bar items
//        switch (item.getItemId()) {
//            case R.id.navigation_home:
//                // 处理返回逻辑
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
