package com.demo.cmnc.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
import com.demo.cmnc.JudgeActivity;
import com.demo.cmnc.R;
import com.demo.cmnc.fragments.dummy.Judge;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CPFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CPFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PJFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    MyRecyclerAdapter myRecyclerAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    private List<Judge> judgeList=new ArrayList();
FloatingActionButton fab;
    private OnFragmentInteractionListener mListener;

    public PJFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CPFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CPFragment newInstance(String param1, String param2) {
        CPFragment fragment = new CPFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pj, container, false);
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView=getActivity().findViewById(R.id.pj_rv);
        myRecyclerAdapter=new MyRecyclerAdapter(getActivity());
fab=getActivity().findViewById(R.id.fab);
fab.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(getActivity(),JudgeActivity.class).putExtra("type","farm").putExtra("farmid",FarmActivity.farm));
    }
});
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        RequestQueue mQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest("http://"+getString(R.string.ip)+"/getjudge?farmid=test"+"&type=farm",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray=new JSONObject(response).getJSONArray("data");

                            Log.i("test",jsonArray.toString());
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                Log.i("test111",jsonObject.toString());
                                Judge judge =new Judge();

                                judge.setUser(jsonObject.getString("userid"));
                                judge.setGrade1(jsonObject.getString("grade1"));
                                judge.setGrade2(jsonObject.getString("grade2"));

                                judge.setGrade3(jsonObject.getString("grade3"));
                                judge.setGrade4(jsonObject.getString("grade4"));
                                judge.setPrice(jsonObject.getString("price"));
                                judge.setWord(jsonObject.getString("word"));
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
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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

}
