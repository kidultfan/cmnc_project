package com.demo.cmnc.fragments.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.demo.cmnc.R;
import com.demo.cmnc.activities.one.SelectedActivity;
import com.demo.cmnc.activities.one.FarmActivity;
import com.demo.cmnc.activities.one.ProductsActivity;
import com.demo.cmnc.fragments.dummy.Farm;
import com.demo.cmnc.widgets.MyGridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;


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
    MyAdapter myAdapter=new MyAdapter();
    SecondAdapter secondAdapter=new SecondAdapter();
    private List<View> viewList;
    private List<View> ADList;
    private Spinner spinner;
    private List<Farm> farmList=new ArrayList();
    private List<banner> banenrlist=new ArrayList();

    private  View view;
    private  View AD;

    ViewPager viewPager;
    private ImageView[] mBottomImages;//底部只是当前页面的小圆点
    Handler handler;
    Timer itimer;
    int now=0;
    private String[] titles = { "买在农场", "乐在农场","住在农场","饮在农场","吃在农场","全部产品" };
    private String[] series_title = { "新品上市", "精品推荐","特价产品","热销产品" };
    private String[] series_text = { "产地直发，新鲜好货", "精挑细选，乐购无忧","限时特价，抢完即止","全城热卖，不容错过" };
    private int[] series_img = { R.drawable.new_p, R.drawable.choose_p,R.drawable.sale_p,R.drawable.hot_p};

    private int[] images = { R.drawable.chinesesnack_u, R.drawable.outing_u,R.drawable.hotel_u,R.drawable.pub_u,R.drawable.food_u,R.drawable.more_u};
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private MyGridView gridView1,gridView2;
    private ViewFlipper viewFlipper;
    private ArrayList<String> dataList;
    private ArrayAdapter<String> adapter;
    private List<Integer> datas;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewList=new ArrayList<>();
        ADList=new ArrayList<>();

        RequestQueue mQueue = Volley.newRequestQueue(getActivity());
        Log.i("ip",getString(R.string.ip));
        StringRequest stringRequest = new StringRequest("http://"+getString(R.string.ip)+"/getfarms",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray=new JSONObject(response).getJSONArray("data");

                            Log.i("test",jsonArray.toString());
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                Log.i("test",jsonObject.toString());

                                Farm farm= new Farm();
                                farm.setPic(jsonObject.getString("image"));
                                farm.setTitle(jsonObject.getString("title"));
                                farm.setPlace_grade(jsonObject.getInt("place_grade"));
                                farm.setProduct_grade(jsonObject.getInt("product_grade"));
                                farm.setService_grade(jsonObject.getInt("service_grade"));
                                farm.setTrip_grade(jsonObject.getInt("trip_grade"));
                                Log.i("test",jsonObject.toString());

                                farmList.add(farm);
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
        StringRequest getbanners= new StringRequest("http://"+getString(R.string.ip)+"/getbanners",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray=new JSONObject(response).getJSONArray("data");

                            Log.i("test",jsonArray.toString());
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                Log.i("test",jsonObject.toString());

                               banner b=new banner();
                               b.setName(jsonObject.getString("farm"));
                               b.setImage(jsonObject.getString("img"));
                               banenrlist.add(b);
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
        mQueue.add(getbanners);


        viewFlipper = (ViewFlipper) getActivity().findViewById(R.id.viewFlipper);
        spinner = (Spinner)getActivity(). findViewById(R.id.spinner);
        dataList = new ArrayList<String>();
        dataList.add("农产品排序");
        dataList.add("旅游排序");
        dataList.add("环境排序");
        dataList.add("服务排序");
        myRecyclerAdapter=new MyRecyclerAdapter(getActivity(),datas);

        recyclerView=getActivity().findViewById(R.id.product_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myRecyclerAdapter);
        /*为spinner定义适配器，也就是将数据源存入adapter，这里需要三个参数
        1. 第一个是Context（当前上下文），这里就是this
        2. 第二个是spinner的布局样式，这里用android系统提供的一个样式
        3. 第三个就是spinner的数据源，这里就是dataList*/
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,dataList);

        //为适配器设置下拉列表下拉时的菜单样式。
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //为spinner绑定我们定义好的数据适配器
        spinner.setAdapter(adapter);

        //为spinner绑定监听器，这里我们使用匿名内部类的方式实现监听器
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){

                    case 0:
                        Collections.sort(farmList, new SortByProduct());
myRecyclerAdapter.notifyDataSetChanged();
break;
                    case 1:
                        Collections.sort(farmList, new SortByTrip());
                        myRecyclerAdapter.notifyDataSetChanged();
                        break;
                    case 2:
                        Collections.sort(farmList, new SortByPlace());
                        myRecyclerAdapter.notifyDataSetChanged();
                        break;
                    case 3:
                        Collections.sort(farmList, new SortByService());
                        myRecyclerAdapter.notifyDataSetChanged();
                        break;

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

      
        handler=new Handler();
        for (int i=0;i<4;i++){
            view=getActivity().getLayoutInflater().inflate(R.layout.view_image,null);
            ImageView img=   (ImageView)view.findViewById(R.id.large_image);
            switch (i){
                case 0:
                    img.setImageResource(R.drawable.slider_one);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(getActivity(),FarmActivity.class).putExtra("farm","泰生示范农场"));
                        }
                    });
                    break;
                case 1:
                    img.setImageResource(R.drawable.slider_two);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(getActivity(),FarmActivity.class).putExtra("farm","嘉仕有机农场"));
                        }
                    });
                    break;
                case 2:
                    img.setImageResource(R.drawable.slider_three);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(getActivity(),FarmActivity.class).putExtra("farm","瀛鼎生态农庄"));
                        }
                    });
                    break;
                case 3:
                    img.setImageResource(R.drawable.slider_four);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(getActivity(),FarmActivity.class).putExtra("farm","一亩田有机农场"));
                        }
                    });
                    break;

            }


            viewList.add(view);

        }







        for (int i=0;i<4;i++){
            view=getActivity().getLayoutInflater().inflate(R.layout.view_ad,null);


            viewFlipper.addView(view);

        }
        mBottomImages = new ImageView[4];

        for (int i=0;i<4;i++) {

            LinearLayout circle=(LinearLayout)getActivity().findViewById(R.id.circle);

            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(40, 40);
            params.setMargins(5, 0, 5, 0);
            imageView.setLayoutParams(params);
            if (i == 0) {
                imageView.setBackgroundResource(R.drawable.select);
            } else {
                imageView.setBackgroundResource(R.drawable.unselect);
            }

            mBottomImages[i] = imageView;

            //把指示作用的原点图片加入底部的视图中
            circle.addView(mBottomImages[i]);
        }
        viewPager = (ViewPager)getActivity(). findViewById(R.id.viewpager);

        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int total = mBottomImages.length;
                for (int i=0;i<total;i++) {
                    if (i==position) {
                        mBottomImages[i].setBackgroundResource(R.drawable.select);
                    }
                    else {mBottomImages[i].setBackgroundResource(R.drawable.unselect);}
                    now=position;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        TimerTask();
        gridView1 = (MyGridView) getActivity().findViewById(R.id.mineMainview);

        gridView1.setAdapter(myAdapter);
        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(),ProductsActivity.class);

                switch (i){
                    case 0:
                        intent.putExtra("type","buy");

                        getActivity().startActivity(intent);

                    break;
                    case 1:
                        intent.putExtra("type","play");

                        getActivity().startActivity(intent);

                        break;  case 2:
                        intent.putExtra("type","live");

                        getActivity().startActivity(intent);

                        break;  case 3:
                        intent.putExtra("type","drink");

                        getActivity().startActivity(intent);

                        break;  case 4:
                        intent.putExtra("type","eat");

                        getActivity().startActivity(intent);

                        break;  case 5:
                        intent.putExtra("type","all");

                        getActivity().startActivity(intent);

                        break;
                }
            }
        });
        gridView2 = (MyGridView) getActivity().findViewById(R.id.SecondView);
        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(),SelectedActivity.class);

                switch (i){
                    case 0:
                        intent.putExtra("mark","new");
                        intent.putExtra("name","新品上市");

                        getActivity().startActivity(intent);

                        break;
                    case 1:
                        intent.putExtra("mark","choose");
                        intent.putExtra("name","精品推荐");

                        getActivity().startActivity(intent);

                        break;
                        case 2:
                        intent.putExtra("mark","sale");
                            intent.putExtra("name","特价产品");
                        getActivity().startActivity(intent);

                        break;  case 3:
                        intent.putExtra("mark","hot");
                        intent.putExtra("name","热销产品");
                        getActivity().startActivity(intent);

                        break;  case 4:
                        intent.putExtra("type","eat");

                        getActivity().startActivity(intent);

                        break;  case 5:
                        intent.putExtra("type","more");

                        getActivity().startActivity(intent);

                        break;
                }
            }
        });

        gridView2.setAdapter(secondAdapter);

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
            return 6;
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
    class SecondAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 4;
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
            View gridview_item = View.inflate(getActivity(), R.layout.series_item, null);

            ImageView iv_icon = (ImageView) gridview_item.findViewById(R.id.series_image);
            TextView tv_title = (TextView) gridview_item.findViewById(R.id.series_title);
            TextView tv_text = (TextView) gridview_item.findViewById(R.id.series_text);

            iv_icon.setBackgroundResource(series_img[position]);
            tv_title.setText(series_title[position]);
            tv_text.setText(series_text[position]);

            return gridview_item;
        }




    }
    PagerAdapter pagerAdapter = new PagerAdapter() {

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return viewList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            // TODO Auto-generated method stub
            container.removeView(viewList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // TODO Auto-generated method stub
            container.addView(viewList.get(position));
            return viewList.get(position);


        }
    };
    public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyHolder> {

        private Context mContext;
        private List<Integer> mDatas;

        public MyRecyclerAdapter(Context context, List<Integer> datas) {
            super();
            this.mContext = context;
            this.mDatas = datas;
        }

        @Override
        public int getItemCount() {
            // TODO Auto-generated method stub
            return farmList.size();
        }

        @Override
        // 填充onCreateViewHolder方法返回的holder中的控件
        public void onBindViewHolder(MyRecyclerAdapter.MyHolder holder, int position) {
            // TODO Auto-generated method stub
            final Farm farm=farmList.get(position);
            Glide.with(getActivity())
                    .load(farm.getPic())
                    .into(holder.imageView);//

            holder.title.setText(farm.getTitle());
            holder.itemView.setOnClickListener(new
                                                       View.OnClickListener() {
                                                           @Override
                                                           public void onClick(View view) {
                                                               startActivity(new Intent(getActivity(),FarmActivity.class).putExtra("farm",farm.getTitle()));
                                                           }
                                                       });

            //          holder.imageView.setImageResource(mDatas.get(position));
        }

        @Override
        // 重写onCreateViewHolder方法，返回一个自定义的ViewHolder
        public MyRecyclerAdapter.MyHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
            // 填充布局
            View view = LayoutInflater.from(mContext).inflate(R.layout.farms_item, null);
           MyRecyclerAdapter.MyHolder holder = new MyRecyclerAdapter.MyHolder(view);
            return holder;
        }

        // 定义内部类继承ViewHolder
        class MyHolder extends RecyclerView.ViewHolder {

            private ImageView imageView;
            TextView title;

            public MyHolder(View view) {
                super(view);
                imageView = (ImageView) view.findViewById(R.id.farm_image);
                title=view.findViewById(R.id.farm_title);
            }

        }


    }
    class SortByProduct implements Comparator {
        public int compare(Object o1, Object o2) {
            Farm s1 = (Farm) o1;
            Farm s2 = (Farm) o2;
            return Integer.compare(s1.getPlace_grade(),s2.getProduct_grade());

        }
    }

    class SortByPlace implements Comparator {
        public int compare(Object o1, Object o2) {
            Farm s1 = (Farm) o1;
            Farm s2 = (Farm) o2;
            return Integer.compare(s1.getPlace_grade(),s2.getPlace_grade());
        }
    }

    class SortByTrip implements Comparator {
        public int compare(Object o1, Object o2) {
            Farm s1 = (Farm) o1;
            Farm s2 = (Farm) o2;
            return Integer.compare(s1.getTrip_grade(),s2.getTrip_grade());

        }
    }

    class SortByService implements Comparator {
        public int compare(Object o1, Object o2) {
            Farm s1 = (Farm) o1;
            Farm s2 = (Farm) o2;

            return Integer.compare(s1.getService_grade(),s2.getService_grade());

        }
    }
    class banner{




        /**
         * Created by zhangfan on 2018/5/20.
         */


            private String name;


            private String image;
            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String  getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }






    }
//    private void TimerTask() {
//        itimer=new Timer();
//        itimer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//
//                if (now<3){
//                    now=now+1;}
//                else {now=0;}
//
//
//
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (now!=0){
//                            viewPager.setCurrentItem(now);}
//                        else {viewPager.setCurrentItem(now,false);}
//
//                    }
//                });
//            }
//        },0,5000);
//    }
}
