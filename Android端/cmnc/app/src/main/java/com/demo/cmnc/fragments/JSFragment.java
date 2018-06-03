package com.demo.cmnc.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.demo.cmnc.MapActivity;
import com.demo.cmnc.R;

import org.json.JSONException;
import org.json.JSONObject;

import static com.demo.cmnc.fragments.FarmActivity.farm;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JSFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JSFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JSFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RequestQueue mQueue;

    RelativeLayout farm_addres,farm_phone;
    private OnFragmentInteractionListener mListener;
    TextView address,info,phone,grade1,grade2,grade3,grade4,price;
    public JSFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JSFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JSFragment newInstance(String param1, String param2) {
        JSFragment fragment = new JSFragment();
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
        return inflater.inflate(R.layout.fragment_j, container, false);
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        mQueue = Volley.newRequestQueue(getContext());
      address= (TextView) getActivity().findViewById(R.id.farm_address);
      price=getActivity().findViewById(R.id.price);
      grade1=getActivity().findViewById(R.id.farm_grade1);
        grade2=getActivity().findViewById(R.id.farm_grade2);
        grade3=getActivity().findViewById(R.id.farm_grade3);
        grade4=getActivity().findViewById(R.id.farm_grade4);
        phone=getActivity().findViewById(R.id.phone);

       info=getActivity().findViewById(R.id.farm_info);
        StringRequest getdetails = new StringRequest("http://"+getString(R.string.ip)+"/getfarm?title="+farm,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse( String response) {


//                            getActivity().runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {

                                    try {

                                        final JSONObject jsonObject=new JSONObject(response);
                                        Log.i("result123",jsonObject.getString("phone"));

                                    //此时已在主线程中，可以更新UI了
                                    address.setText(jsonObject.getString("location"));
                                    price.setText(jsonObject.getString("price"));
                                        info.setText(jsonObject.getString("info"));
                                        Log.i("result123",jsonObject.getString("phone"));

                                        phone.setText(jsonObject.getString("phone"));
                                    grade1.setText(jsonObject.getString("product_grade"));
                                    grade2.setText(jsonObject.getString("trip_grade"));
                                    grade3.setText(jsonObject.getString("place_grade"));
                                    grade4.setText(jsonObject.getString("service_grade"));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
//                                }
//                            });





                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });

        mQueue.add(getdetails);
        farm_addres=getActivity().findViewById(R.id.farm_address_layout);
        farm_addres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),MapActivity.class));
            }
        });
        farm_phone=getActivity().findViewById(R.id.farm_phone);
        farm_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
diallPhone(phone.getText().toString());            }
        });
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

    public void diallPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }
}
