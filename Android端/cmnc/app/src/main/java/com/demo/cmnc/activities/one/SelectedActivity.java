package com.demo.cmnc.activities.one;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.demo.cmnc.R;
import com.demo.cmnc.fragments.dummy.Product;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SelectedActivity extends AppCompatActivity {
    private List<Product> productslist=new ArrayList();
RecyclerView rv;
MyRecyclerAdapter myRecyclerAdapter;
    private SharedPreferences sp;
    private String history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setTitle(getIntent().getStringExtra("name"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent getIntent = getIntent();
        sp=getSharedPreferences("User",Context.MODE_PRIVATE);

        history = sp.getString("history","[]");
        String mark = getIntent.getStringExtra("mark");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected);
       myRecyclerAdapter =new MyRecyclerAdapter(SelectedActivity.this);
        Log.i("hello","hello");
        rv=findViewById(R.id.selected_rv);
        RequestQueue mQueue = Volley.newRequestQueue(this);
        Log.i("ip",getString(R.string.ip));
        StringRequest stringRequest = new StringRequest("http://"+getString(R.string.ip)+"/products/mark/"+mark+"",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray=new JSONObject(response).getJSONArray("data");

                            Log.i("test",jsonArray.toString());
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                Log.i("test",jsonObject.toString());

                                Product product=new Product();
                                product.setTitle(jsonObject.getString("title"));
                                product.setFrom(jsonObject.getString("farm"));
                                product.setPrice(jsonObject.getString("price"));
                                product.setGrade(jsonObject.getInt("grade"));
                                product.setPic(jsonObject.getString("image"));
                                product.setId(jsonObject.getString("id"));
                                Log.i("test",jsonObject.toString());

                                productslist.add(product);
                                myRecyclerAdapter.notifyDataSetChanged();

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
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(myRecyclerAdapter);
    }


    public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyHolder> {

        private Context mContext;

        public MyRecyclerAdapter(Context context) {
            super();
            this.mContext = context;
        }

        @Override
        public int getItemCount() {
            // TODO Auto-generated method stub
            Log.i("test1",productslist.size()+"");

            return productslist.size();
        }

        @Override
        // 填充onCreateViewHolder方法返回的holder中的控件
        public void onBindViewHolder(MyRecyclerAdapter.MyHolder holder, int position) {
            // TODO Auto-generated method stub
final Product  product=productslist.get(position);
            Glide.with(SelectedActivity.this)
                    .load(product.getPic())
                    .into(holder.imageView);
            holder.title.setText(product.getTitle());
            holder.itemView.setOnClickListener(new
                                                       View.OnClickListener() {
                                                           @Override
                                                           public void onClick(View view) {

                                                               try {
                                                                   JSONArray jsonArray=new JSONArray(history);

                                                                   JSONObject  jsonObject=new JSONObject();
                                                                   jsonObject.put("title",product.getTitle());
                                                                   jsonObject.put("farm",product.getFrom());
                                                                   jsonObject.put("price",product.getPrice());
                                                                   jsonObject.put("grade",product.getGrade());
                                                                   jsonObject.put("pics",product.getPic());
                                                                   jsonObject.put("id",product.getId());
                                                                   jsonArray.put(jsonObject);
                                                                   SharedPreferences.Editor editor = sp.edit();
                                                                   Log.i("jsonarray",jsonArray.toString());
                                                                   editor.putString("history",jsonArray.toString());
                                                                   editor.apply();

                                                               } catch (JSONException e) {
                                                                   e.printStackTrace();
                                                               }

                                                               startActivity(new Intent(SelectedActivity.this,DetailsActivity.class).putExtra("id",product.getId()));

                                                           }
                                                       });
///
///
/// ////
///
///
///   holder.imageView.setImageResource(mDatas.get(position));
//            Product product=productslist.get(position);
//            Log.i("test1",product.toString());
////            Glide.with(holder.imageView).load(product.ge)
//            holder.title.setText(product.getTitle());
//            holder.text.setText("来自："+product.getFrom());
//            if (product.getGrade()<20){
//
//                holder.grade.setText("⭐");
//
//            }
//
//            else if (product.getGrade()<40){
//                holder.grade.setText("⭐⭐");
//
//            }
//
//
//            else if(product.getGrade()<60){
//                holder.grade.setText("⭐⭐⭐");
//
//            }
//            else if(product.getGrade()<80){
//                holder.grade.setText("⭐⭐⭐⭐");
//
//            }
//            else {
//                holder.grade.setText("⭐⭐⭐⭐⭐");
//
//            }

//            Log.i("glide",product.getPic());
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                    startActivity(new Intent(getActivity(),DetailsActivity.class));
//                }
//            });

        }

        @Override
        // 重写onCreateViewHolder方法，返回一个自定义的ViewHolder
        public MyRecyclerAdapter.MyHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
            // 填充布局
            View view = LayoutInflater.from(arg0.getContext()).inflate(R.layout.selected_item,arg0, false);

            MyRecyclerAdapter.MyHolder holder = new MyRecyclerAdapter.MyHolder(view);
            return holder;
        }

        // 定义内部类继承ViewHolder
        class MyHolder extends RecyclerView.ViewHolder {

            private RoundedImageView imageView;
            private TextView title,text,grade,price;

            public MyHolder(View view) {
                super(view);
                imageView = view.findViewById(R.id.se_image);
                title=view.findViewById(R.id.se_title);

//                text=view.findViewById(R.id.pd_text);
//                grade=view.findViewById(R.id.pd_grade);
//                price=view.findViewById(R.id.pd_price);

            }

        }


    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
