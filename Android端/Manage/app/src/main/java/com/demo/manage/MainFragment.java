package com.demo.manage;

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
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Product> productslist=new ArrayList();

    private OnFragmentInteractionListener mListener;
    private MyRecyclerAdapter myRecyclerAdapter;
    private RecyclerView recyclerView;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        myRecyclerAdapter=new MyRecyclerAdapter(getActivity());
        RequestQueue mQueue = Volley.newRequestQueue(getActivity());
       String url="http://"+getString(R.string.ip)+"/products/type/all";
        recyclerView=getActivity().findViewById(R.id.rv_list);
        FloatingActionButton fab=getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),AddActivity.class));
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
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

                                product.setGrade1(jsonObject.getString("grade1"));
                                product.setGrade2(jsonObject.getString("grade2"));

                                product.setGrade3(jsonObject.getString("grade3"));

                                product.setGrade4(jsonObject.getString("grade4"));

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
        public void onBindViewHolder(MyHolder holder, final int position) {
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
            Glide.with(getActivity())
                    .load(product.getPic())
                    .into(holder.imageView);
            Log.i("glide", product.getPic());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
            


                    startActivity(new Intent(getActivity(), DetailsActivity.class).putExtra("id", product.getId()));
                }
            });

        }

        @Override
        // 重写onCreateViewHolder方法，返回一个自定义的ViewHolder
        public MyHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
            // 填充布局
            View view = LayoutInflater.from(mContext).inflate(R.layout.products_item, null);
            MyHolder holder = new MyHolder(view);
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

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
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
}
