package com.demo.cmnc.activities.one;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.demo.cmnc.GalleryActivity;
import com.demo.cmnc.R;
import com.demo.cmnc.fragments.farm.CPFragment;
import com.demo.cmnc.fragments.farm.JSFragment;
import com.demo.cmnc.fragments.farm.PJFragment;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class FarmActivity extends AppCompatActivity implements PJFragment.OnFragmentInteractionListener,CPFragment.OnFragmentInteractionListener,JSFragment.OnFragmentInteractionListener {

    private TabLayout mTabLayout;
ViewPager mViewPager;
JSFragment js;
PJFragment pj;
CPFragment cp;
public static String farm;
    private List<Fragment> list_fragment;                                //定义要装fragment的列表
    private List<String> list_title;                                     //tab名称列表
    private FragmentPagerAdapter fAdapter;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton fab;
    private boolean islike;
    String userid;
    SharedPreferences sp;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         farm=getIntent().getStringExtra("farm");
        setContentView(R.layout.activity_farm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(farm);
        sp = getSharedPreferences("User", Context.MODE_PRIVATE);
        userid=sp.getString("userid","");

        collapsingToolbarLayout=findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FarmActivity.this, GalleryActivity.class).putExtra("farm",farm));
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        StringRequest getislike = new StringRequest("http://"+getString(R.string.ip)+"/islike?userid="+userid+"&farmname="+farm+"&type=farm",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("islike",response);
                        if (response.equals("true")){

                            islike=true;
                            fab.setImageResource(R.drawable.likee);


//                            fab.(R.drawable.likee);
                        }
                        else {
                            fab.setImageResource(R.drawable.like);

//                            fab.setBackgroundResource(R.drawable.like);

                            islike=false;
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        mQueue = Volley.newRequestQueue(this);

        mQueue.add(getislike);


        mViewPager=findViewById(R.id.main_vp_container);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                // arg0是当前选中的页面的Position
                Log.e(TAG, "onPageSelected------>"+arg0);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // arg0 :当前页面，及你点击滑动的页面；arg1:当前页面偏移的百分比；arg2:当前页面偏移的像素位置
                Log.e(TAG, "onPageScrolled------>arg0："+arg0+"\nonPageScrolled------>arg1:"+arg1+"\nonPageScrolled------>arg2:"+arg2);
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                //arg0 ==1的时表示正在滑动，arg0==2的时表示滑动完毕了，arg0==0的时表示什么都没做。
//                if(arg0 == 0){
//                    Log.e(TAG, "onPageScrollStateChanged------>0");
//                }else
                if(arg0 == 1){
                    Log.e(TAG, "onPageScrollStateChanged------>1");
                }else if(arg0 == 2){
                    Log.e(TAG, "onPageScrollStateChanged------>2");
                }

            }
        });
        mTabLayout= (TabLayout) findViewById(R.id.tablayout);
        mTabLayout.setupWithViewPager(mViewPager);
        list_title = new ArrayList<>();
        list_title.add("信息");

        list_title.add("产品");
        list_title.add("评价");
        list_fragment = new ArrayList<>();
        js=new JSFragment();
        pj=new PJFragment();
        cp=new CPFragment();
        list_fragment.add(js);
        list_fragment.add(cp);

        list_fragment.add(pj);

        fAdapter = new Find_tab_Adapter(getSupportFragmentManager(),list_fragment,list_title);

        mViewPager.setAdapter(fAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userid.length()==0){


                    Toast.makeText(FarmActivity.this,"请先登录",Toast.LENGTH_SHORT).show();
                }


                else {
                    if (!islike) {
                        fab.setImageResource(R.drawable.likee);

                        StringRequest addlike = new StringRequest("http://" + getString(R.string.ip) + "/addlike?userid=" + userid + "&farmname=" + farm +"&type=farm"
                                ,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        if (response.equals("success!")) {

                                            islike = true;
                                            Toast.makeText(FarmActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("TAG", error.getMessage(), error);
                            }
                        });

                        Log.i("addlike", addlike.getUrl());

                        mQueue.add(addlike);

                    } else {

                        fab.setImageResource(R.drawable.like);
                        islike = false;
//
                        StringRequest dislike = new StringRequest("http://" + getString(R.string.ip) + "/dislike?userid=" + userid + "&farmname=" + farm+"&type=farm" ,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        if (response.equals("success!")) {

                                            islike = false;
                                            Toast.makeText(FarmActivity.this, "取消成功", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("TAG", error.getMessage(), error);
                            }
                        });

                        Log.i("dislike", dislike.getUrl());
                        mQueue.add(dislike);
                    }

                }
                fab.setImageResource(R.drawable.likee);
                Snackbar.make(view, "收藏成功", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }



    public class Find_tab_Adapter extends FragmentPagerAdapter {

        private List<Fragment> list_fragment;                         //fragment列表
        private List<String> list_Title;                              //tab名的列表



        public Find_tab_Adapter(FragmentManager fm, List<Fragment> list_fragment, List<String> list_Title) {
            super(fm);
            this.list_fragment = list_fragment;
            this.list_Title = list_Title;
        }

        @Override
        public Fragment getItem(int position) {
            return list_fragment.get(position);
        }

        @Override
        public int getCount() {
            return list_Title.size();
        }

        //此方法用来显示tab上的名字
        @Override
        public CharSequence getPageTitle(int position) {

            return list_Title.get(position % list_Title.size());
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }



}
