package com.demo.manage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MainFragment mainFragment;
    private SelfFragment selfFragment;
    private FragmentTransaction transaction;
    private String mParam1;
    private String mParam2;
    private List<Product> productslist=new ArrayList();
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
    private String[] names = { "意见反馈" ,"联系客服"};
    private String[] names2 = { "关于崇明农场", "上海农林职业技术学院"};
    ListAdapter listAdapter,listAdapter1;

    private int[] images1 = { R.drawable.tags,R.drawable.banner,R.drawable.product,R.drawable.select};
    private int[] images2 = { R.drawable.product,R.drawable.profile,R.drawable.order,R.drawable.pocket};

    private String[] titles1 = { "语法管理","广告管理","产品管理","推荐管理"};
    private String[] titles2 = { "产品管理","资料修改","查看订单","我的钱包"};

    private MainFragment.OnFragmentInteractionListener mListener;
    private MainFragment.MyRecyclerAdapter myRecyclerAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login=findViewById(R.id.login);
        userhint=findViewById(R.id.userhint);
        sp=getSharedPreferences("User", Context.MODE_PRIVATE);

        quit=findViewById(R.id.quit);
        listAdapter=new ListAdapter(names);
        listAdapter1=new ListAdapter(names2);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this,LoginActivity.class),resultCode);
            }
        });
        quit=findViewById(R.id.quit);
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

        gridView1 = (MyGridView)findViewById(R.id.mineSelfview);


        update();



        listView = (MyListView)findViewById(R.id.list);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){


                    case 0:
                        startActivity(new Intent(MainActivity.this,AdviceActivity.class));

                        break;

                    case 1:

                        diallPhone("400-8208820");


                        break;


                }
            }
        });


        listView1 = (MyListView)findViewById(R.id.list_two);
        listView1.setAdapter(listAdapter1);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:startActivity(new Intent(MainActivity.this,AboutActivity.class));
                        break;
                    case 1:startActivity(new Intent(MainActivity.this,WebActivity.class));
                        break;

                }
            }
        });

    }





    class MyAdapter extends BaseAdapter {
        int[] images;
        String [] titles;

        public MyAdapter(     int[] images,
                String [] titles){
            this.images=images;
            this.titles=titles;

        }

        @Override
        public int getCount() {
            return titles.length;
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
            View gridview_item = View.inflate(MainActivity.this, R.layout.item, null);

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
            View gridview_item = View.inflate(MainActivity.this, R.layout.list_item, null);
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


        sp =getSharedPreferences("User", Context.MODE_PRIVATE);

        role = sp.getString("role", "");
        userid = sp.getString("userid", "");

        Log.i("role", role);

        if (role.length() > 0) {

            if (role.equals("admin")) {
                username = findViewById(R.id.username);
                gridView1.setVisibility(View.VISIBLE);


                if (userid.length() > 0) {

                    username.setText(userid);


                }
                MyAdapter myAdapter= new MyAdapter(images1,titles1);

                gridView1.setAdapter(myAdapter);
                gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        switch (i){

                            case 0:
                                startActivity(new Intent(MainActivity.this,TagsActivity.class));
                                break;
                            case 1:
                                startActivity(new Intent(MainActivity.this,BannerActivity.class));
                                break;
                            case 2:
                                startActivity(new Intent(MainActivity.this,ProductsActivity.class).putExtra("type","all"));
                                break;
                            case 3:

                                startActivity(new Intent(MainActivity.this,SelectedActivity.class));
        break;
                        }
                    }
                });

            } else if (role.equals("farm")) {

                userid = sp.getString("userid", "");
                final String farmname = sp.getString("farmname", "");

                username =findViewById(R.id.username);

                if (userid.length() > 0) {

                    username.setText(farmname);


                }
                MyAdapter myAdapter= new MyAdapter(images2,titles2);
                Log.i("update", "2");

                gridView1.setAdapter(myAdapter);
                gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        switch (i){

                            case 0:
                                startActivity(new Intent(MainActivity.this,ProductsActivity.class).putExtra("type","farm").putExtra("farmname",farmname));
                                break;
                            case 1:
                                startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                                break;

                            case 2:
                                startActivity(new Intent(MainActivity.this,PaylogActivity.class).putExtra("farm",farmname));

                                break;
                            case 3:
                                startActivity(new Intent(MainActivity.this,PocketActivity.class));
                                break;
                        }
                    }
                });
            }
            userhint.setVisibility(View.INVISIBLE);
            quit.setVisibility(View.VISIBLE);


            Log.i("update", "1");
        }
    }

    @Override

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        Log.i("ok", "oddsfda");

        switch (resultCode)

        {
            case RESULT_OK:
                selfFragment.update();
                break;
        }



    }
}
