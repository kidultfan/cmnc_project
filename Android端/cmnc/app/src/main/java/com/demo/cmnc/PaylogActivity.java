package com.demo.cmnc;

import android.content.Context;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PaylogActivity extends AppCompatActivity {

    private List<Pay> payList=new ArrayList();
    private SharedPreferences sp;
    String userid;
    private MyRecyclerAdapter myRecyclerAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paylog);
myRecyclerAdapter=new MyRecyclerAdapter(this);

        RequestQueue mQueue = Volley.newRequestQueue(this);
        Log.i("ip",getString(R.string.ip));
        sp = getSharedPreferences("User", Context.MODE_PRIVATE);
        getSupportActionBar().setTitle("消费记录");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userid=sp.getString("userid","");
      String  url="http://"+getString(R.string.ip)+"/getpay?farm="+userid;
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
                                Pay pay=new Pay();

                                pay.setPrice(jsonObject.getString("price"));
                                pay.setTime(jsonObject.getString("time"));
                                pay.setTitle(jsonObject.getString("title"));


                                payList.add(pay);

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

        recyclerView=findViewById(R.id.rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myRecyclerAdapter);
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
            return payList.size();
        }

        @Override
        // 填充onCreateViewHolder方法返回的holder中的控件
        public void onBindViewHolder(MyRecyclerAdapter.MyHolder holder, final int position) {
            // TODO Auto-generated method stub
//            holder.imageView.setImageResource(mDatas.get(position));
            final Pay pay=payList.get(position);
//            Glide.with(holder.imageView).load(product.ge)
            holder.title.setText(pay.getTitle());
            holder.price.setText("-"+pay.getPrice());
            holder.time.setText(pay.getTime());


        }

        @Override
        // 重写onCreateViewHolder方法，返回一个自定义的ViewHolder
        public MyRecyclerAdapter.MyHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
            // 填充布局
            View view = LayoutInflater.from(mContext).inflate(R.layout.pay_item, null);
            MyRecyclerAdapter.MyHolder holder = new MyRecyclerAdapter.MyHolder(view);
            return holder;
        }

        // 定义内部类继承ViewHolder
        class MyHolder extends RecyclerView.ViewHolder {

            private TextView title,time,price;

            public MyHolder(View view) {
                super(view);
                title=view.findViewById(R.id.title);
                time=view.findViewById(R.id.time);
                price=view.findViewById(R.id.price);

            }

        }


    }

    public class Pay{

        private String title;
        private String time;

        private String price;


        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }




        public String  getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }




    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
