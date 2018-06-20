package com.demo.cmnc.fragments.like;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.demo.cmnc.R;
import com.demo.cmnc.activities.one.FarmActivity;
import com.demo.cmnc.fragments.dummy.Farm;
import com.demo.cmnc.widgets.MyGridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class farm_like extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
 SecondAdapter secondAdapter=new SecondAdapter();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SharedPreferences sp;
    private String userid;
    private OnFragmentInteractionListener mListener;
    private MyGridView gridView2;
    private List<Farm> farmlist=new ArrayList();

    public farm_like() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NCFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static farm_like newInstance(String param1, String param2) {
        farm_like fragment = new farm_like();
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
        return inflater.inflate(R.layout.fragment_nc, container, false);
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        gridView2 = (MyGridView) getActivity().findViewById(R.id.NCView);
        sp = getActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
        userid=sp.getString("userid","");
        gridView2.setAdapter(secondAdapter);
        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getActivity(), FarmActivity.class).putExtra("farm",farmlist.get(i).getTitle()));
            }
        });
        RequestQueue mQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest("http://"+getString(R.string.ip)+"/getlike?userid="+userid+"&type=farm",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            Log.i("farmresult",response);

                            JSONArray jsonArray=new JSONObject(response).getJSONArray("data");
                            if (jsonArray.length()>0){
                                Log.i("test222",jsonArray.toString());
                                for (int i=0;i<jsonArray.length();i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    Log.i("test111", jsonObject.toString());

                                    Farm farm = new Farm();
                                    farm.setPic(jsonObject.getString("image"));
                                    farm.setTitle(jsonObject.getString("title"));

                                    farmlist.add(farm);
                                    Log.i("test111", farmlist.size() + "");

                                    secondAdapter.notifyDataSetChanged();
                                }


                            }

                            else {



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
    class SecondAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return farmlist.size();
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
            View gridview_item = View.inflate(getActivity(), R.layout.farms_item, null);

            Farm farm=farmlist.get(position);

            ImageView iv_icon = (ImageView) gridview_item.findViewById(R.id.farm_image);
            TextView tv_title = (TextView) gridview_item.findViewById(R.id.farm_title);
            Glide.with(getActivity())
                    .load(farm.getPic())
                    .into(iv_icon);
            tv_title.setText(farm.getTitle());
            TextView grade = (TextView) gridview_item.findViewById(R.id.farm_grade);




//
//            iv_icon.setBackgroundResource(series_img[position]);
//            tv_title.setText(series_title[position]);
//            tv_text.setText(series_text[position]);

            return gridview_item;
        }




    }
}
