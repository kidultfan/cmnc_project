package com.demo.cmnc;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.demo.cmnc.activities.one.DetailsActivity;
import com.demo.cmnc.activities.one.FarmActivity;
import com.demo.cmnc.fragments.dummy.Judge;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PingjiaActivity extends AppCompatActivity {
    String userid;
    SharedPreferences sp;
    private List<Judge> judgeList=new ArrayList();
    RecyclerView recyclerView;
    private MyRecyclerAdapter myRecyclerAdapter;
    RequestQueue mQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pingjia);
        sp =getSharedPreferences("User", Context.MODE_PRIVATE);
        userid=sp.getString("userid","");
        recyclerView=findViewById(R.id.pj_rv);
        myRecyclerAdapter=new MyRecyclerAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        getSupportActionBar().setTitle("评价管理");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView.setAdapter(myRecyclerAdapter);
        if (userid.length() == 0) {


            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
        }
        else {
             mQueue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest("http://" + getString(R.string.ip) + "/getjudges_byuser?userid="+userid,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                judgeList.clear();
                                JSONArray jsonArray = new JSONObject(response).getJSONArray("data");

                                Log.i("test", jsonArray.toString());
                                for (int i = jsonArray.length()-1; i >=0; i--) {
                                    Log.i("2",i+"");
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    Log.i("test111", jsonObject.toString());
                                    Judge judge = new Judge();
                                    judge.setName(jsonObject.getString("name"));

                                    judge.setUser(jsonObject.getString("userid"));
                                    judge.setGrade1(jsonObject.getString("grade1"));
                                    judge.setGrade2(jsonObject.getString("grade2"));
                                    judge.setGrade3(jsonObject.getString("grade3"));
                                    judge.setGrade4(jsonObject.getString("grade4"));
                                    judge.setWord(jsonObject.getString("word"));
                                    judge.setType(jsonObject.getString("type"));
                                    if (jsonObject.getString("type").equals("product")){

                                        judge.setProductid(jsonObject.getString("productid"));

                                    }
                                    judge.setJid(jsonObject.getString("jid"));
                                    judgeList.add(judge);
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
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(myRecyclerAdapter);
        }
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

            return judgeList.size();
        }

        @Override
        // 填充onCreateViewHolder方法返回的holder中的控件
        public void onBindViewHolder(MyRecyclerAdapter.MyHolder holder, final int position) {
            // TODO Auto-generated method stub

            final Judge judge=judgeList.get(position);
           holder.to.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   if (judge.getType().equals("farm")){


                       startActivity(new Intent(PingjiaActivity.this, FarmActivity.class).putExtra("farm",judge.getName()));
                   }
                   else {

                       startActivity(new Intent(PingjiaActivity.this, DetailsActivity.class).putExtra("id",judge.getProductid()));

                   }

               }
           });

            holder.text.setText(judge.getWord());
            holder.name.setText(judge.getName());


//            holder.imageView.setImageResource(mDatas.get(position));
//            Product product=productslist.get(position);
//            Log.i("test1",product.toString());
////            Glide.with(holder.imageView).load(product.ge)
//            holder.title.setText(product.getTitle());
//            holder.text.setText("来自："+product.getFrom());
            if (Double.parseDouble(judge.getGrade1())<2){

                holder.grade.setText("⭐");

            }

            else if (Double.parseDouble(judge.getGrade1())<3){
                holder.grade.setText("⭐⭐");

            }


            else if(Double.parseDouble(judge.getGrade1())<4){
                holder.grade.setText("⭐⭐⭐");

            }
            else if(Double.parseDouble(judge.getGrade1())<5){
                holder.grade.setText("⭐⭐⭐⭐");

            }
            else {
                holder.grade.setText("⭐⭐⭐⭐⭐");

            }

            holder.update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(PingjiaActivity.this,UpdateActivity.class);
                    intent.putExtra("word",judge.getWord());
                    intent.putExtra("grade1",judge.getGrade1());
                    intent.putExtra("grade2",judge.getGrade2());
                    intent.putExtra("grade3",judge.getGrade3());
                    intent.putExtra("grade4",judge.getGrade4());
                    intent.putExtra("type",judge.getType());
                    Log.i("type",judge.getType());

                    intent.putExtra("jid",judge.getJid());
                    startActivity(intent);
                }
            });

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StringRequest stringRequest = new StringRequest("http://" + getString(R.string.ip) + "/deletejudges?jid="+judge.getJid(),
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (response.equals("success!")){

                                        Toast.makeText(getApplicationContext(),"删除成功",Toast.LENGTH_SHORT).show();

                                        judgeList.remove(position) ;
                                        myRecyclerAdapter.notifyDataSetChanged();
                                    }
                                    Log.d("TAG", response);
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("TAG1", error.getMessage(), error);
                        }
                    });
                    mQueue.add(stringRequest);
                }
            });
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
            View view = LayoutInflater.from(arg0.getContext()).inflate(R.layout.pjitem,arg0, false);
           MyHolder holder = new   MyHolder(view);
            return holder;
        }

        // 定义内部类继承ViewHolder
        class MyHolder extends RecyclerView.ViewHolder {

            private ImageView update,delete;
            private TextView name,text,grade,grade2,grade3,grade4,word;
            LinearLayout to;

            public MyHolder(View view) {
                super(view);

to=view.findViewById(R.id.to);
             update=view.findViewById(R.id.update);
             delete=view.findViewById(R.id.delete);

                name=view.findViewById(R.id.name);
                text=view.findViewById(R.id.text);
                grade=view.findViewById(R.id.grade);

            }

        }


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }


}
