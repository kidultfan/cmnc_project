package com.demo.cmnc;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AdviceActivity extends AppCompatActivity {

    private TextView adviceword;
    String word;
    LinearLayout hint;
    TextView number;
    Button post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);
        hint=findViewById(R.id.hint);
        number=findViewById(R.id.number);
        post=findViewById(R.id.post);

        adviceword=findViewById(R.id.adviceword);
        adviceword.addTextChangedListener(new TextWatcher() {
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
        Toast.makeText(AdviceActivity.this,"感谢反馈！",Toast.LENGTH_SHORT).show();
        AdviceActivity.this.finish();
    }
});
    }



}
