package com.demo.cmnc.activities.one;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.demo.cmnc.R;
import com.demo.cmnc.fragments.dummy.Product;
import com.jayfang.dropdownmenu.DropDownMenu;
import com.jayfang.dropdownmenu.OnMenuSelectedListener;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {
    private DropDownMenu mMenu;
    private ListView mList;
RecyclerView recyclerView;
    MyRecyclerAdapter myRecyclerAdapter;
    private int city_index;
    private int sex_index;
    private int age_index;
    private List<String> data;
    private List<Integer> datas;
    private List <Product>productslist=new ArrayList();
    private List <Product>productslist2=new ArrayList();
    final String[] arr1=new String[]{"全部农场","泰生示范农场","嘉仕有机农场","态申农庄","一亩田有机农场","瀛鼎生态农庄"};
    final String[] arr2=new String[]{"所有区间","100以下","100——500","500——1000","1000以上"};
    final String[] arr3=new String[]{"按产品评价","按旅游评价","按环境评价","按服务评价"};
    final String[] strings=new String[]{"选择农场","选择价格","排序方式"};
    private SharedPreferences sp;
    String url;
 String  history;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent getIntent = getIntent();
        String type = getIntent.getStringExtra("type");
        String tag = getIntent.getStringExtra("tag");

        String like = getIntent.getStringExtra("like");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        sp=getSharedPreferences("User",Context.MODE_PRIVATE);

        history = sp.getString("history","[]");

        Log.i("history",history);

myRecyclerAdapter=new MyRecyclerAdapter(this);
        RequestQueue mQueue = Volley.newRequestQueue(this);



        if (tag!=null&&tag.equals("empty")){


            ImageView imageView =(ImageView)findViewById(R.id.empty);
            imageView.setVisibility(View.VISIBLE);

        }

        else {





            if (type!=null) {

                if (type.equals("buy")) {

                    getSupportActionBar().setTitle("买在农场");
                } else if (type.equals("play")) {

                    getSupportActionBar().setTitle("乐在农场");
                } else if (type.equals("live")) {

                    getSupportActionBar().setTitle("住在农场");
                } else if (type.equals("drink")) {

                    getSupportActionBar().setTitle("饮在农场");
                } else if (type.equals("eat")) {

                    getSupportActionBar().setTitle("吃在农场");
                }

                else {

                    getSupportActionBar().setTitle("全部产品");

                }

                url="http://"+getString(R.string.ip)+"/products/type/"+type;
            }

            else  if(like!=null) {

                url="http://"+getString(R.string.ip)+"/products/like/"+like;
                getSupportActionBar().setTitle("搜索结果");

            }
            else {


                url = "http://" + getString(R.string.ip) + "/products/tag/" + tag;
                Log.i("url", url);
                getSupportActionBar().setTitle("搜索结果");

            }






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
                                    productslist2.add(product);
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

        mMenu=(DropDownMenu)findViewById(R.id.menu);
recyclerView=findViewById(R.id.rv_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
recyclerView.setAdapter(myRecyclerAdapter);
        mMenu.setmMenuCount(3);
        mMenu.setmShowCount(6);
        mMenu.setShowCheck(true);
        mMenu.setmMenuTitleTextSize(16);
        mMenu.setmMenuTitleTextColor(Color.parseColor("#777777"));
        mMenu.setmMenuListTextSize(16);
        mMenu.setmMenuListTextColor(Color.BLACK);
        mMenu.setmMenuBackColor(Color.parseColor("#eeeeee"));
        mMenu.setmMenuPressedBackColor(Color.WHITE);
        mMenu.setmMenuPressedTitleTextColor(Color.BLACK);

        mMenu.setmCheckIcon(R.drawable.ico_make);

        mMenu.setmUpArrow(R.drawable.arrow_up);
        mMenu.setmDownArrow(R.drawable.arrow_down);

        mMenu.setDefaultMenuTitle(strings);


        mMenu.setShowDivider(false);
        mMenu.setmMenuListBackColor(getResources().getColor(R.color.white));
        mMenu.setmMenuListSelectorRes(R.color.white);
        mMenu.setmArrowMarginTitle(20);

        mMenu.setMenuSelectedListener(new OnMenuSelectedListener() {
            @Override
            public void onSelected(View listview, int RowIndex, int ColumnIndex) {
                Log.i("MainActivity", "select " + ColumnIndex + " column and " + RowIndex + " row");
                if (ColumnIndex == 0) {

                        if(RowIndex== 0) {
                            productslist.clear();

                            for (Product product : productslist2) {
                                productslist.add(product);
                            }
                            myRecyclerAdapter.notifyDataSetChanged();
                        }
                        else {
                            productslist.clear();


                            for (Product product : productslist2) {
                                if (product.getFrom().equals(arr1[RowIndex]))
                                    productslist.add(product);
                            }
                            myRecyclerAdapter.notifyDataSetChanged();


                        }
                    city_index = RowIndex;
                } else if (ColumnIndex == 1) {
                    switch (RowIndex){
                        case 0:
                            productslist.clear();

                            for (Product product : productslist2) {
                                productslist.add(product);
                            }
                            myRecyclerAdapter.notifyDataSetChanged();
                            break;
                        case 1:


                            productslist.clear();

                            for (Product product : productslist2) {
                                if (Integer.parseInt(product.getPrice())<100){
                                    productslist.add(product);


                                }
                            }
                            myRecyclerAdapter.notifyDataSetChanged();

                            break;

                        case 2:


                            productslist.clear();

                            for (Product product : productslist2) {

                                if (100<Integer.parseInt(product.getPrice())&&Integer.parseInt(product.getPrice())<500){
                                    productslist.add(product);


                                }
                            }
                            myRecyclerAdapter.notifyDataSetChanged();

                            break;
                        case 3:


                            productslist.clear();

                            for (Product product : productslist2) {
                                if (500<Integer.parseInt(product.getPrice())&&Integer.parseInt(product.getPrice())<1000){
                                    productslist.add(product);


                                }                            }
                            myRecyclerAdapter.notifyDataSetChanged();

                            break;


                        case 4:


                            productslist.clear();

                            for (Product product : productslist2) {
                                if (Integer.parseInt(product.getPrice())>1000){
                                    productslist.add(product);


                                }                            }
                            myRecyclerAdapter.notifyDataSetChanged();

                            break;
                    }






                    sex_index = RowIndex;
                } else {
switch (RowIndex){

    case 0:                        Collections.sort(productslist, new SortByProduct());
        myRecyclerAdapter.notifyDataSetChanged();

        break;
    case 1:
        Collections.sort(productslist, new SortByTrip());
        myRecyclerAdapter.notifyDataSetChanged();

        break;

    case 2:
        Collections.sort(productslist, new SortByPlace());
        myRecyclerAdapter.notifyDataSetChanged();

        break;
    case 3:
        Collections.sort(productslist, new SortByService());
        myRecyclerAdapter.notifyDataSetChanged();

        break;


}               }
                //过滤筛选
//                setFilter();
            }
        });
        List<String[]> items = new ArrayList<>();
        items.add(arr1);
        items.add(arr2);
        items.add(arr3);
        mMenu.setmMenuItems(items);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                List<String[]> items = new ArrayList<>();
//                items.add(arr1);
//                items.add(arr2);
//                items.add(arr3);
//                mMenu.setmMenuItems(items);
//
//            }
//        }, 1000);

        mMenu.setIsDebug(false);

//        data=getData();
//        mList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, data));

    }

//    private void setFilter(){
//        List<String> temp=new ArrayList<String>();
//        for (int i=0;i<getData().size();i++){
//            boolean city=((city_index==0)?true:data.get(i).contains(arr1[city_index]));
//            boolean sex=((sex_index==0)?true:data.get(i).contains(arr2[sex_index]));
//            boolean age=((age_index==0)?true:data.get(i).contains(arr3[age_index]));
//            if(city && sex && age){
//                temp.add(data.get(i));
//            }
//        }
//        mList.setAdapter(new ArrayAdapter<String>(ProductsActivity.this, android.R.layout.simple_expandable_list_item_1,temp));
//    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
            final Product product=productslist.get(position);
            Log.i("test",product.toString());
//            Glide.with(holder.imageView).load(product.ge)
            holder.title.setText(product.getTitle());
            holder.text.setText("来自："+product.getFrom());
            holder.price.setText("￥"+product.getPrice());
            if (product.getGrade()<20){

                holder.grade.setText("⭐");

            }

            else if (product.getGrade()<40){
                holder.grade.setText("⭐⭐");

            }


            else if(product.getGrade()<60){
                holder.grade.setText("⭐⭐⭐");

            }
            else if(product.getGrade()<80){
                holder.grade.setText("⭐⭐⭐⭐");

            }
            else {
                holder.grade.setText("⭐⭐⭐⭐⭐");

            }
            Glide.with(ProductsActivity.this)
                    .load(product.getPic())
                    .into(holder.imageView);
            Log.i("glide",product.getPic());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        JSONArray jsonArray=new JSONArray(history);

                        JSONObject  jsonObject=new JSONObject();
                        jsonObject.put("title",product.getTitle());
                        jsonObject.put("farm",product.getFrom());
                        jsonObject.put("price",product.getPrice());
                        jsonObject.put("grade",product.getGrade());
                        jsonObject.put("pics",product.getPic());
                        jsonObject.put("id",product.getId());
                     jsonArray.put(jsonObject);
                        SharedPreferences.Editor editor = sp.edit();
                        Log.i("jsonarray",jsonArray.toString());
                        editor.putString("history",jsonArray.toString());
                        editor.apply();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    startActivity(new Intent(ProductsActivity.this,DetailsActivity.class).putExtra("id",product.getId()));
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
            private TextView title,text,grade,price;

            public MyHolder(View view) {
                super(view);
                imageView = view.findViewById(R.id.pd_image);
                title=view.findViewById(R.id.pd_title);
                text=view.findViewById(R.id.pd_text);
                grade=view.findViewById(R.id.pd_grade);
                price=view.findViewById(R.id.pd_price);

            }

        }


    }
    class SortByProduct implements Comparator {
        public int compare(Object o1, Object o2) {
            Product s1 = (Product) o1;
            Product s2 = (Product) o2;
            return (Double.compare(Double.parseDouble(s1.getGrade1()),Double.parseDouble(s2.getGrade1())));

        }
    }

    class SortByPlace implements Comparator {
        public int compare(Object o1, Object o2) {
            Product s1 = (Product) o1;
            Product s2 = (Product) o2;
            return (Double.compare(Double.parseDouble(s1.getGrade2()),Double.parseDouble(s2.getGrade2())));

        }
    }

    class SortByTrip implements Comparator {
        public int compare(Object o1, Object o2) {
            Product s1 = (Product) o1;
            Product s2 = (Product) o2;
            return (Double.compare(Double.parseDouble(s1.getGrade3()),Double.parseDouble(s2.getGrade3())));

        }
    }

    class SortByService implements Comparator {
        public int compare(Object o1, Object o2) {
            Product s1 = (Product) o1;
            Product s2 = (Product) o2;

            return (Double.compare(Double.parseDouble(s1.getGrade4()),Double.parseDouble(s2.getGrade4())));


        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

}
