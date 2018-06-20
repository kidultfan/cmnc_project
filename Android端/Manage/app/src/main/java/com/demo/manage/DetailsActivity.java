package com.demo.manage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {
LinearLayout fromlayout;
LinearLayout judge;
LinearLayout like;
ImageView like_img;
TextView title,price,farm,grade,likes,info;
String from;
ImageView pic;
    private List<Judge> judgeList=new ArrayList();

    private boolean islike;
    String userid;
SharedPreferences sp;
    private RequestQueue mQueue;
    private String productid,productname;
    Button buy;
    private MyRecyclerAdapter myRecyclerAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        productid = getIntent().getStringExtra("id");

        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("User", Context.MODE_PRIVATE);
userid=sp.getString("userid","");

        getSupportActionBar().setTitle("产品详情");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        setContentView(R.layout.activity_details);
        myRecyclerAdapter=new MyRecyclerAdapter(this);
        title=findViewById(R.id.pt_title);
        price=findViewById(R.id.pt_price);
        pic=findViewById(R.id.pt_pic);
        farm=findViewById(R.id.pt_farm);
        info=findViewById(R.id.pt_info);
        fromlayout=findViewById(R.id.from_view);

        recyclerView=findViewById(R.id.pj_rv);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        StringRequest stringRequest = new StringRequest("http://"+getString(R.string.ip)+"/getjudge_byproduct?productid="+productid+"&type=product",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            judgeList.clear();
                            JSONArray jsonArray=new JSONObject(response).getJSONArray("data");
                            Log.i("judgeproduct",jsonArray.toString());
                            for (int i=jsonArray.length()-1;i>=0;i--){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                Log.i("test111",jsonObject.toString());
                                Judge judge =new Judge();

                                judge.setUser(jsonObject.getString("userid"));
                                judge.setGrade1(jsonObject.getString("grade1"));
                                judge.setGrade2(jsonObject.getString("grade2"));

                                judge.setGrade3(jsonObject.getString("grade3"));
                                judge.setGrade4(jsonObject.getString("grade4"));
                                judge.setWord(jsonObject.getString("word"));
                                judgeList.add(judge);
                                Log.i("judge",judgeList.size()+"");
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

        Log.i("judge",stringRequest.getUrl());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myRecyclerAdapter);
         mQueue = Volley.newRequestQueue(this);

        StringRequest getdetails = new StringRequest("http://"+getString(R.string.ip)+"/getproduct?id="+productid+"",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            Log.i("result",response);
                            final JSONObject jsonObject=new JSONObject(response);
                            Log.i("resultimage",jsonObject.getString("image"));

                            Log.i("title",jsonObject.getString("title"));
                            title.setText(jsonObject.getString("title"));
                            price.setText(jsonObject.getInt("price")+"");
                            info.setText(jsonObject.getString("info"));
                            productname=jsonObject.getString("title");
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




        mQueue.add(getdetails);
        mQueue.add(stringRequest);


        fromlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(DetailsActivity.this,FarmActivity.class).putExtra("farm",from).putExtra("type","product"));
            }
        });





    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_choose) {
            startActivity(new Intent(this,ChooseActivity.class).putExtra("id",productid));
            return true;
        } else if (id == R.id.action_key) {
            startActivity(new Intent(this,KeyActivity.class).putExtra("id",productid));

            return true;
        }

        else if (id==R.id.action_delete){

            StringRequest stringRequest1 = new StringRequest("http://" + getString(R.string.ip) + "/setproductmark?id=" + productid + "&mark=" ,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("success")){

                                Toast.makeText(DetailsActivity.this,"移出成功",Toast.LENGTH_SHORT).show();
                                DetailsActivity.this.finish();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("TAG", error.getMessage(), error);
                }
            });
            mQueue.add(stringRequest1);

        }
        return super.onOptionsItemSelected(item);
    }

//    private void showPopupWindow() {
//        //设置contentView
//        View contentView = LayoutInflater.from(DetailsActivity.this).inflate(R.layout.popuplayout, null);
//        mPopWindow = new PopupWindow(contentView,
//                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
//        mPopWindow.setContentView(contentView);
//        //设置各个控件的点击响应
//        TextView tv1 = (TextView)contentView.findViewById(R.id.pop_computer);
//        TextView tv2 = (TextView)contentView.findViewById(R.id.pop_financial);
//        TextView tv3 = (TextView)contentView.findViewById(R.id.pop_manage);
//        tv1.setOnClickListener(this);
//        tv2.setOnClickListener(this);
//        tv3.setOnClickListener(this);
//        //显示PopupWindow
//        View rootview = LayoutInflater.from(MainActivity.this).inflate(R.layout.main, null);
//        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
//
//    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
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
            Log.i("judge",judgeList.size()+"");

            return judgeList.size();
        }

        @Override
        // 填充onCreateViewHolder方法返回的holder中的控件
        public void onBindViewHolder(MyRecyclerAdapter.MyHolder holder, int position) {
            // TODO Auto-generated method stub

            Judge judge=judgeList.get(position);

            holder.grade1.setText(judge.getGrade1());

            holder.grade2.setText(judge.getGrade2());
            holder.grade3.setText(judge.getGrade3());
            holder.grade4.setText(judge.getGrade4());
            holder.price.setText(judge.getPrice());
            holder.word.setText(judge.getWord());
            holder.name.setText(judge.getUser());


//            holder.imageView.setImageResource(mDatas.get(position));
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
//            Glide.with(getActivity())
//                    .load(product.getPic())
//                    .into(holder.imageView);
//            Log.i("glide",product.getPic());
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    startActivity(new Intent(getActivity(),DetailsActivity.class));
//                }
//            });

        }

        @Override
        // 重写onCreateViewHolder方法，返回一个自定义的ViewHolder
        public MyRecyclerAdapter.MyHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
            // 填充布局
            View view = LayoutInflater.from(mContext).inflate(R.layout.pj_item, null);
            MyRecyclerAdapter.MyHolder holder = new MyRecyclerAdapter.MyHolder(view);
            return holder;
        }

        // 定义内部类继承ViewHolder
        class MyHolder extends RecyclerView.ViewHolder {

            private RoundedImageView imageView;
            private TextView name,price,grade1,grade2,grade3,grade4,word;

            public MyHolder(View view) {
                super(view);



                imageView = view.findViewById(R.id.pd_image);
                name=view.findViewById(R.id.pj_name);
                price=view.findViewById(R.id.price);
                grade1=view.findViewById(R.id.grade1);
                grade2=view.findViewById(R.id.grade2);
                grade3=view.findViewById(R.id.grade3);
                grade4=view.findViewById(R.id.grade4);
                word=view.findViewById(R.id.pj_text);

            }

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (getIntent().getBooleanExtra("select",false)) {
            getMenuInflater().inflate(R.menu.menu_select, menu);

        }

        else if (getIntent().getStringExtra("role").equals("admin")){
            getMenuInflater().inflate(R.menu.menu_product, menu);

        }


        return true;
    }

}
