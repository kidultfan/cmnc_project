package com.demo.manage;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.manage.farm.BannerActivity;
import com.demo.manage.farm.TagsActivity;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SelfFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SelfFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelfFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RelativeLayout login;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    String userid;
    TextView userhint;
    TextView username;
    private MyGridView gridView1;
    String role;
    private int resultCode;

    Button quit;
    MyListView listView,listView1;
    private String[] names = { "我的信息", "钱包","意见反馈" ,"联系客服"};
    private String[] names2 = { "关于崇明农场", "上海农林职业技术学院"};
    ListAdapter listAdapter,listAdapter1;

    MyAdapter myAdapter=new MyAdapter();
    private int[] images = { R.drawable.tags,R.drawable.banner};

    private String[] titles = { "语法管理","广告管理"};

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SelfFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelfFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelfFragment newInstance(String param1, String param2) {
        SelfFragment fragment = new SelfFragment();
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
        login=getActivity().findViewById(R.id.login);
        userhint=getActivity().findViewById(R.id.userhint);
        sp=getActivity().getSharedPreferences("User",Context.MODE_PRIVATE);

        quit=getActivity().findViewById(R.id.quit);
        listAdapter=new ListAdapter(names);
        listAdapter1=new ListAdapter(names2);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(),LoginActivity.class),resultCode);
            }
        });
        quit=getActivity().findViewById(R.id.quit);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor=sp.edit();
                editor.putString("userid","");
                editor.putString("farmname","");
                editor.putString("role","");
                editor.apply();
                username.setText("点击登录/注册");
                gridView1.setVisibility(View.GONE);
                quit.setVisibility(View.GONE);
                userhint.setVisibility(View.VISIBLE);
            }
        });

        gridView1 = (MyGridView)getActivity().findViewById(R.id.mineSelfview);
        gridView1.setAdapter(myAdapter);
        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){

                    case 0:
                        startActivity(new Intent(getActivity(),TagsActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getActivity(),BannerActivity.class));
                        break;

                }
            }
        });
        update();



        listView = (MyListView) getActivity().findViewById(R.id.list);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        startActivity(new Intent(getActivity(),ProfileActivity.class));

                        break;
                    case 1:
                        startActivity(new Intent(getActivity(),PocketActivity.class));
                        break;

                    case 2:
                        startActivity(new Intent(getActivity(),AdviceActivity.class));

                        break;

                    case 3:

                        diallPhone("400-8208820");


                        break;


                }
            }
        });


        listView1 = (MyListView) getActivity().findViewById(R.id.list_two);
        listView1.setAdapter(listAdapter1);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:startActivity(new Intent(getActivity(),AboutActivity.class));
                        break;
                    case 1:startActivity(new Intent(getActivity(),WebActivity.class));
                        break;

                }
            }
        });


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_self, container, false);
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
    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 2;
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
            View gridview_item = View.inflate(getActivity(), R.layout.item, null);

            ImageView iv_icon = (ImageView) gridview_item.findViewById(R.id.image);
            TextView tv_title = (TextView) gridview_item.findViewById(R.id.title);

            iv_icon.setBackgroundResource(images[position]);
            tv_title.setText(titles[position]);

            return gridview_item;
        }




    }
    class ListAdapter extends BaseAdapter {
        String[] titless;
        public ListAdapter(  String[]  titles){

            this.titless=titles;

        }
        @Override
        public int getCount() {
            return titless.length;
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
            View gridview_item = View.inflate(getActivity(), R.layout.list_item, null);
            TextView tv_name = (TextView) gridview_item.findViewById(R.id.list_name);
            tv_name.setText(titless[position]);

//            ImageView iv_icon = (ImageView) gridview_item.findViewById(R.id.image);
//            TextView tv_title = (TextView) gridview_item.findViewById(R.id.title);
//
//            iv_icon.setBackgroundResource(images[position]);
//            tv_title.setText(titles[position]);

            return gridview_item;
        }


    }

    public void diallPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

    public void update() {


        sp = getActivity().getSharedPreferences("User", Context.MODE_PRIVATE);

        role = sp.getString("role", "");
        userid = sp.getString("userid", "");

        Log.i("role", role);

        if (role.length() > 0) {

            if (role.equals("admin")) {
                username = getActivity().findViewById(R.id.username);
                gridView1.setVisibility(View.VISIBLE);


                if (userid.length() > 0) {

                    username.setText(userid);


                }


            } else if (role.equals("farm")) {

                userid = sp.getString("userid", "");
                String farmname = sp.getString("farmname", "");
                gridView1.setVisibility(View.GONE);

                username = getActivity().findViewById(R.id.username);

                if (userid.length() > 0) {

                    username.setText(farmname);


                }
            }
            userhint.setVisibility(View.INVISIBLE);
            quit.setVisibility(View.VISIBLE);

            Log.i("update", "1");
        }
    }

}
