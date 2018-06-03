package com.demo.cmnc;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.demo.cmnc.widgets.MyGridView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NCFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NCFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NCFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
 SecondAdapter secondAdapter=new SecondAdapter();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private MyGridView gridView2;

    public NCFragment() {
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
    public static NCFragment newInstance(String param1, String param2) {
        NCFragment fragment = new NCFragment();
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

        gridView2.setAdapter(secondAdapter);}
   
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
            return 200;
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

//            ImageView iv_icon = (ImageView) gridview_item.findViewById(R.id.series_image);
//            TextView tv_title = (TextView) gridview_item.findViewById(R.id.series_title);
//            TextView tv_text = (TextView) gridview_item.findViewById(R.id.series_text);
//
//            iv_icon.setBackgroundResource(series_img[position]);
//            tv_title.setText(series_title[position]);
//            tv_text.setText(series_text[position]);

            return gridview_item;
        }




    }
}
