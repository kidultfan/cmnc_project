package com.demo.manage;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.qiniu.android.common.FixedZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {

    private ArrayList<String> dataList;
    private ArrayList<String> farmlist;

    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> farmadapter;

    private static final int IMAGE = 1;
    String imagePath;
     String token;
    private RequestQueue mQueue;
    EditText name,price,infos;
    UploadManager uploadManager;


    String type;
    String farmname;
    String role;
    Spinner farms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        final Spinner spinner=findViewById(R.id.spinner);
        mQueue = Volley.newRequestQueue(this);
        name=findViewById(R.id.name);
        price=findViewById(R.id.price);
        infos=findViewById(R.id.info);
        dataList = new ArrayList<String>();

        dataList.add("买在农场");
        dataList.add("乐在农场");
        dataList.add("住在农场");
        dataList.add("饮在农场");
        dataList.add("吃在农场");
        role=getIntent().getStringExtra("role");
        final LinearLayout farmsview=findViewById(R.id.farmsview);
        if (role.equals("admin")){
            farmlist = new ArrayList<String>();

            farmadapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,farmlist);

            StringRequest stringRequest = new StringRequest("http://"+getString(R.string.ip)+"/getfarms",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray=new JSONObject(response).getJSONArray("data");
                                Log.i("judgeproduct",jsonArray.toString());
                                for (int i=jsonArray.length()-1;i>=0;i--){
                                    farmlist.clear();
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);

                                    farmlist.add(jsonObject.getString("title"));
                                    farmadapter.notifyDataSetChanged();
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
 farms=findViewById(R.id.farms);
            farms.setAdapter(farmadapter);

        }

        else {
            farmsview.setVisibility(View.GONE);
            farmname=getIntent().getStringExtra("role");
        }

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,dataList);

        //为适配器设置下拉列表下拉时的菜单样式。
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //为spinner绑定我们定义好的数据适配器
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){


                    case 0:type="buy";
                    break;

                    case 1:type="play";
                    break;
                    case 2:type="live";
                        break;
                    case 3:type="drink";
                        break;
                    case 4:type="eat";
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ImageView imageView=findViewById(R.id.image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //调用相册
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE);
            }
        });
        Configuration config = new Configuration.Builder()
                .chunkSize(512 * 1024)        // 分片上传时，每片的大小。 默认256K
                .putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
                .connectTimeout(10)           // 链接超时。默认10秒
                .useHttps(true)               // 是否使用https上传域名
                .responseTimeout(60)          // 服务器响应超时。默认60秒
                .zone(FixedZone.zone0)        // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
                .build();
        // 重用uploadManager。一般地，只需要创建一个uploadManager对象
         uploadManager = new UploadManager(config);
        Button post=findViewById(R.id.post);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (name.length()>0&&infos.length()>0&&price.length()>0&&imagePath.length()>0){


                    ProgressBar progressBar=findViewById(R.id.progress);
                    progressBar.setVisibility(View.VISIBLE);
                StringRequest upload = new StringRequest("http://"+getString(R.string.ip)+"/gettoken",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse( String response) {
                                String  data = imagePath;
                                uploadManager.put(data,null, response,
                                        new UpCompletionHandler() {
                                            @Override
                                            public void complete(String key, ResponseInfo info, JSONObject res) {
                                                //res包含hash、key等信息，具体字段取决于上传策略的设置



                                                if(info.isOK()) {
                                                    try {
                                                        if (role.equals("admin")){

                                                            farmname=farms.getSelectedItem().toString();
                                                        }

                                                        Log.i("qiniu", 12+"");
                                                        String imageurl=getString(R.string.qiniu)+res.getString("key");
                                                        StringRequest post = new StringRequest("http://" + getString(R.string.ip) + "/addproduct?title="+name.getText().toString()+"&price="+price.getText().toString()+"&info="+infos.getText().toString()+"&image="+imageurl+"&type="+spinner.getSelectedItem().toString()+"&farm="+farmname
                                                                , new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {

                                                            if (response.equals("success！")){

                                                                Toast.makeText(AddActivity.this,"上传成功",Toast.LENGTH_SHORT).show();
                                                                AddActivity.this.finish();

                                                            }

                                                        }
                                                    }, new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {

                                                        }
                                                    });
                                                    mQueue.add(post);


                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }

//


                                                } else {
                                                    Log.i("qiniu", "Upload Fail");
                                                    //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                                                }
                                                Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                                            }
                                        }, null);

//                            getActivity().runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {

//                            });




                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", error.getMessage(), error);
                    }
                });

                mQueue.add(upload);

                }
                else {

                    Toast.makeText(AddActivity.this,"缺少信息",Toast.LENGTH_SHORT).show();
                }






            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径
        if (requestCode == IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
             imagePath = c.getString(columnIndex);


            showImage(imagePath);
            c.close();
        }
    }

    //加载图片
    private void showImage(String imaePath){
        Bitmap bm = BitmapFactory.decodeFile(imaePath);
        ((ImageView)findViewById(R.id.image)).setImageBitmap(bm);
    }

    private String gettoken(){


        return  token;



    }


}
