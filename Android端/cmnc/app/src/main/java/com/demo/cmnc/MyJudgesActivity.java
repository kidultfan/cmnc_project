package com.demo.cmnc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

public class MyJudgesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_judges);
//
//        recyclerView=getActivity().findViewById(R.id.pj_rv);
//        myRecyclerAdapter=new PJFragment.MyRecyclerAdapter(getActivity());
//        RequestQueue mQueue = Volley.newRequestQueue(getActivity());
//        StringRequest stringRequest = new StringRequest("http://"+getString(R.string.ip)+"/getjudge?farmname="+farm+"&type=farm",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            judgeList.clear();
//                            JSONArray jsonArray=new JSONObject(response).getJSONArray("data");
//
//                            Log.i("test",jsonArray.toString());
//                            for (int i=jsonArray.length()-1;i>0;i--){
//                                JSONObject jsonObject=jsonArray.getJSONObject(i);
//                                Log.i("test111",jsonObject.toString());
//                                Judge judge =new Judge();
//
//                                judge.setUser(jsonObject.getString("userid"));
//                                judge.setGrade1(jsonObject.getString("grade1"));
//                                judge.setGrade2(jsonObject.getString("grade2"));
//
//                                judge.setGrade3(jsonObject.getString("grade3"));
//                                judge.setGrade4(jsonObject.getString("grade4"));
//                                judge.setPrice(jsonObject.getString("price"));
//                                judge.setWord(jsonObject.getString("word"));
//                                judgeList.add(judge);
//                                myRecyclerAdapter.notifyDataSetChanged();
//
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                        Log.d("TAG", response);
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("TAG", error.getMessage(), error);
//            }
//        });
//        mQueue.add(stringRequest);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(myRecyclerAdapter);
//    }
//
//    public class MyRecyclerAdapter extends RecyclerView.Adapter<PJFragment.MyRecyclerAdapter.MyHolder> {
//
//        private Context mContext;
//
//        public MyRecyclerAdapter(Context context) {
//            super();
//            this.mContext = context;
//        }
//
//        @Override
//        public int getItemCount() {
//            // TODO Auto-generated method stub
//
//            return judgeList.size();
//        }
//
//        @Override
//        // 填充onCreateViewHolder方法返回的holder中的控件
//        public void onBindViewHolder(PJFragment.MyRecyclerAdapter.MyHolder holder, int position) {
//            // TODO Auto-generated method stub
//
//            Judge judge=judgeList.get(position);
//
//            holder.grade1.setText(judge.getGrade1());
//
//            holder.grade2.setText(judge.getGrade2());
//            holder.grade3.setText(judge.getGrade3());
//            holder.grade4.setText(judge.getGrade4());
//            holder.price.setText(judge.getPrice());
//            holder.word.setText(judge.getWord());
//            holder.name.setText(judge.getUser());
//
//
////            holder.imageView.setImageResource(mDatas.get(position));
////            Product product=productslist.get(position);
////            Log.i("test1",product.toString());
//////            Glide.with(holder.imageView).load(product.ge)
////            holder.title.setText(product.getTitle());
////            holder.text.setText("来自："+product.getFrom());
////            if (product.getGrade()<20){
////
////                holder.grade.setText("⭐");
////
////            }
////
////            else if (product.getGrade()<40){
////                holder.grade.setText("⭐⭐");
////
////            }
////
////
////            else if(product.getGrade()<60){
////                holder.grade.setText("⭐⭐⭐");
////
////            }
////            else if(product.getGrade()<80){
////                holder.grade.setText("⭐⭐⭐⭐");
////
////            }
////            else {
////                holder.grade.setText("⭐⭐⭐⭐⭐");
////
////            }
////            Glide.with(getActivity())
////                    .load(product.getPic())
////                    .into(holder.imageView);
////            Log.i("glide",product.getPic());
////            holder.itemView.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View view) {
////                    startActivity(new Intent(getActivity(),DetailsActivity.class));
////                }
////            });
//
//        }
//
//        @Override
//        // 重写onCreateViewHolder方法，返回一个自定义的ViewHolder
//        public PJFragment.MyRecyclerAdapter.MyHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
//            // 填充布局
//            View view = LayoutInflater.from(mContext).inflate(R.layout.pj_item, null);
//            PJFragment.MyRecyclerAdapter.MyHolder holder = new PJFragment.MyRecyclerAdapter.MyHolder(view);
//            return holder;
//        }
//
//        // 定义内部类继承ViewHolder
//        class MyHolder extends RecyclerView.ViewHolder {
//
//            private RoundedImageView imageView;
//            private TextView name,price,grade1,grade2,grade3,grade4,word;
//
//            public MyHolder(View view) {
//                super(view);
//
//
//
//                imageView = view.findViewById(R.id.pd_image);
//                name=view.findViewById(R.id.pj_name);
//                price=view.findViewById(R.id.price);
//                grade1=view.findViewById(R.id.grade1);
//                grade2=view.findViewById(R.id.grade2);
//                grade3=view.findViewById(R.id.grade3);
//                grade4=view.findViewById(R.id.grade4);
//                word=view.findViewById(R.id.pj_text);
//
//            }
//
//        }


    }

}
