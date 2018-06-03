package com.demo.cmnc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.demo.cmnc.widgets.MyGridView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {
    private RequestQueue mQueue;
    private String farm;
    private MyGridView gridView;
    MyAdapter myAdapter;

    List<String> pics=new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        farm=getIntent().getStringExtra("farm");

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gallery);
        gridView=findViewById(R.id.GalleryView);
        myAdapter=new MyAdapter();
        gridView.setAdapter(myAdapter);
        mQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest("http://"+getString(R.string.ip)+"/getpics?title="+farm+"",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray=new JSONArray(response);

                            Log.i("test",jsonArray.toString());
                            for (int i=0;i<jsonArray.length();i++){
                                String url=jsonArray.getString(i);
                                pics.add(url);

myAdapter.notifyDataSetChanged();
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

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            Log.i("test",pics.size()+"");

            return pics.size();

        }

        @Override
        public Object getItem(int i) {
            return null;
        }


        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            //将布局文件转换成View
            View gridview_item = View.inflate(GalleryActivity.this, R.layout.gallery_item, null);

            ImageView img = (ImageView) gridview_item.findViewById(R.id.image);
            Glide.with(GalleryActivity.this).load(pics.get(position)).into(img);
            return gridview_item;
        }




    }
}
