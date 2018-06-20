package com.demo.manage.farm;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.demo.manage.AddKeywordActivity;
import com.demo.manage.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class KeywordsActivity extends AppCompatActivity {

    private int resultCode;

    private ArrayAdapter<String> adapter;
    private ArrayList<String> dataList;
    private RequestQueue mQueue;

     String tag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keywords);
        mQueue = Volley.newRequestQueue(this);
        dataList = new ArrayList<String>();

         tag=getIntent().getStringExtra("tag");
        Log.i("tag",tag);
        final ListView listView=findViewById(R.id.list);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        refresh();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,final int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(KeywordsActivity.this);
                builder.setTitle("是否删除？");

                builder.setNeutralButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {




                        StringRequest stringRequest = new StringRequest("http://"+getString(R.string.ip)+"/deletekeyword?keyword=" +dataList.get(position)+"&tag="+tag,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                       if (response.equals("success")){

                                           Toast.makeText(KeywordsActivity.this,"删除成功",Toast.LENGTH_SHORT).show();

                                           refresh();
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
                builder.show();

            }
        });

        FloatingActionButton fab=findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(KeywordsActivity.this,AddKeywordActivity.class).putExtra("tag",tag),resultCode);
            }
        });

    }

    public void refresh(){

        StringRequest stringRequest = new StringRequest("http://"+getString(R.string.ip)+"/keywords_bytag?tag="+tag,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
dataList.clear();
                            JSONArray jsonArray=new JSONObject(response).getJSONArray("data");
                            Log.i("judgeproduct",jsonArray.toString());
                            for (int i=jsonArray.length()-1;i>=0;i--){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);

                                dataList.add(jsonObject.getString("keyword"));

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
    }
    @Override

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        Log.i("ok", "oddsfda");

        switch (resultCode)

        {
            case RESULT_OK:
                refresh();
                break;
        }



    }
}
