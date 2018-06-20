package com.demo.manage;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {
    private List<Product> productslist=new ArrayList();

    private MyRecyclerAdapter myRecyclerAdapter;
    private RecyclerView recyclerView;
    String type;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        myRecyclerAdapter=new MyRecyclerAdapter(this);
        RequestQueue mQueue = Volley.newRequestQueue(this);
        recyclerView=findViewById(R.id.rv_list);
        FloatingActionButton fab=findViewById(R.id.fab);
         type=getIntent().getStringExtra("type");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type.equals("all")) {
                    startActivity(new Intent(ProductsActivity.this, AddActivity.class).putExtra("role", "admin"));
                }
                else {

                    startActivity(new Intent(ProductsActivity.this, AddActivity.class).putExtra("role", getIntent().getStringExtra("farmname")));

                }
            }
        });

        if (type.equals("all")){

            url="http://"+getString(R.string.ip)+"/products/type/all";

        }
        else {

            String farmname=getIntent().getStringExtra("farmname");
            url="http://"+getString(R.string.ip)+"/products/farm/"+farmname;
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myRecyclerAdapter);
        StringRequest stringRequest = new StringRequest(url,
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
            return productslist.size();
        }

        @Override
        // 填充onCreateViewHolder方法返回的holder中的控件
        public void onBindViewHolder(MyRecyclerAdapter.MyHolder holder, final int position) {
            // TODO Auto-generated method stub
//            holder.imageView.setImageResource(mDatas.get(position));
            final Product product = productslist.get(position);
            Log.i("test", product.toString());
//            Glide.with(holder.imageView).load(product.ge)
            holder.title.setText(product.getTitle());
            holder.text.setText("来自：" + product.getFrom());
            holder.price.setText("￥" + product.getPrice());
            if (product.getGrade() < 20) {

                holder.grade.setText("⭐");

            } else if (product.getGrade() < 40) {
                holder.grade.setText("⭐⭐");

            } else if (product.getGrade() < 60) {
                holder.grade.setText("⭐⭐⭐");

            } else if (product.getGrade() < 80) {
                holder.grade.setText("⭐⭐⭐⭐");

            } else {
                holder.grade.setText("⭐⭐⭐⭐⭐");

            }
            Glide.with(ProductsActivity.this)
                    .load(product.getPic())
                    .into(holder.imageView);
            Log.i("glide", product.getPic());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (type.equals("all")) {
                        startActivity(new Intent(ProductsActivity.this, DetailsActivity.class).putExtra("role", "admin").putExtra("id", product.getId()));
                    }
                    else {

                        startActivity(new Intent(ProductsActivity.this, DetailsActivity.class).putExtra("role", getIntent().getStringExtra("farmname")).putExtra("id", product.getId()));

                    }

                }
            });

        }

        @Override
        // 重写onCreateViewHolder方法，返回一个自定义的ViewHolder
        public MyRecyclerAdapter.MyHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
            // 填充布局
            View view = LayoutInflater.from(mContext).inflate(R.layout.products_item, null);
            MyRecyclerAdapter.MyHolder holder = new MyRecyclerAdapter.MyHolder(view);
            return holder;
        }

        // 定义内部类继承ViewHolder
        class MyHolder extends RecyclerView.ViewHolder {

            private RoundedImageView imageView;
            private TextView title, text, grade, price;

            public MyHolder(View view) {
                super(view);
                imageView = view.findViewById(R.id.pd_image);
                title = view.findViewById(R.id.pd_title);
                text = view.findViewById(R.id.pd_text);
                grade = view.findViewById(R.id.pd_grade);
                price = view.findViewById(R.id.pd_price);

            }
        }
    }
}
