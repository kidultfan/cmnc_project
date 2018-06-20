package com.demo.manage.farm;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.demo.manage.R;
import com.qiniu.android.common.FixedZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONException;
import org.json.JSONObject;

public class BannerActivity extends AppCompatActivity {
EditText text1,text2,text3,text4;
    EditText img1,img2,img3,img4;
    Button post1,post2,post3,post4;
    private RequestQueue mQueue;
    private static final int IMAGE = 1;
    private String imagePath;
    private String imagePath1;
    private String imagePath2;
    private String imagePath3;
    private String imagePath4;

    int now;
    private UploadManager uploadManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        text1=findViewById(R.id.text1);
        text2=findViewById(R.id.text2);
        text3=findViewById(R.id.text3);

        text4=findViewById(R.id.text4);

        post1=findViewById(R.id.post1);
        post2=findViewById(R.id.post2);
        post3=findViewById(R.id.post3);
        post4=findViewById(R.id.post4);
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

        ImageView imageView1=findViewById(R.id.image1);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //调用相册
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE);
                now=1;
            }
        });

        ImageView imageView2=findViewById(R.id.image2);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //调用相册
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE);
                now=2;
            }
        });

        ImageView imageView3=findViewById(R.id.image3);
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //调用相册
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE);
                now=3;
            }
        });

        ImageView imageView4=findViewById(R.id.image4);
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //调用相册
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE);
                now=4;
            }
        });
        mQueue = Volley.newRequestQueue(this);


        post1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringRequest upload = new StringRequest("http://"+getString(R.string.ip)+"/gettoken",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse( String response) {
                                String  data = imagePath1;
                                uploadManager.put(data,null, response,
                                        new UpCompletionHandler() {
                                            @Override
                                            public void complete(String key, ResponseInfo info, JSONObject res) {
                                                //res包含hash、key等信息，具体字段取决于上传策略的设置



                                                if(info.isOK()) {
                                                    try {

                                                        Log.i("qiniu", 12+"");
                                                        String imageurl=getString(R.string.qiniu)+res.getString("key");
                                                        StringRequest post = new StringRequest("http://" + getString(R.string.ip) + "/setbanners?farm="+text1.getText().toString()+"&image="+imageurl+"&id=1"
                                                                , new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String response) {

                                                                if (response.equals("success！")){

                                                                    Toast.makeText(BannerActivity.this,"上传成功",Toast.LENGTH_SHORT).show();
                                                                    BannerActivity.this.finish();

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
        });

        post2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringRequest upload = new StringRequest("http://"+getString(R.string.ip)+"/gettoken",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse( String response) {
                                String  data = imagePath2;
                                uploadManager.put(data,null, response,
                                        new UpCompletionHandler() {
                                            @Override
                                            public void complete(String key, ResponseInfo info, JSONObject res) {
                                                //res包含hash、key等信息，具体字段取决于上传策略的设置



                                                if(info.isOK()) {
                                                    try {

                                                        Log.i("qiniu", 12+"");
                                                        String imageurl=getString(R.string.qiniu)+res.getString("key");
                                                        StringRequest post = new StringRequest("http://" + getString(R.string.ip) + "/setbanners?farm="+text1.getText().toString()+"&image="+imageurl+"&id=2"
                                                                , new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String response) {

                                                                if (response.equals("success！")){

                                                                    Toast.makeText(BannerActivity.this,"上传成功",Toast.LENGTH_SHORT).show();
                                                                    BannerActivity.this.finish();

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
        });
        post3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringRequest upload = new StringRequest("http://"+getString(R.string.ip)+"/gettoken",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse( String response) {
                                String  data = imagePath3;
                                uploadManager.put(data,null, response,
                                        new UpCompletionHandler() {
                                            @Override
                                            public void complete(String key, ResponseInfo info, JSONObject res) {
                                                //res包含hash、key等信息，具体字段取决于上传策略的设置



                                                if(info.isOK()) {
                                                    try {

                                                        Log.i("qiniu", 12+"");
                                                        String imageurl=getString(R.string.qiniu)+res.getString("key");
                                                        StringRequest post = new StringRequest("http://" + getString(R.string.ip) + "/setbanners?farm="+text1.getText().toString()+"&image="+imageurl+"&id=3"
                                                                , new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String response) {

                                                                if (response.equals("success！")){

                                                                    Toast.makeText(BannerActivity.this,"上传成功",Toast.LENGTH_SHORT).show();
                                                                    BannerActivity.this.finish();

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
        });
        post4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringRequest upload = new StringRequest("http://"+getString(R.string.ip)+"/gettoken",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse( String response) {
                                String  data = imagePath4;
                                uploadManager.put(data,null, response,
                                        new UpCompletionHandler() {
                                            @Override
                                            public void complete(String key, ResponseInfo info, JSONObject res) {
                                                //res包含hash、key等信息，具体字段取决于上传策略的设置



                                                if(info.isOK()) {
                                                    try {

                                                        Log.i("qiniu", 12+"");
                                                        String imageurl=getString(R.string.qiniu)+res.getString("key");
                                                        StringRequest post = new StringRequest("http://" + getString(R.string.ip) + "/setbanners?farm="+text1.getText().toString()+"&image="+imageurl+"&id=4"
                                                                , new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String response) {

                                                                if (response.equals("success！")){

                                                                    Toast.makeText(BannerActivity.this,"上传成功",Toast.LENGTH_SHORT).show();
                                                                    BannerActivity.this.finish();

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

            switch (now) {


                case 1:
                    imagePath1 = imagePath;
                    break;
                case 2:
                    imagePath2 = imagePath;
                    break;
                case 3:
                    imagePath3 = imagePath;
                    break;
                case 4:
                    imagePath4 = imagePath;
                    break;

            }
            showImage(imagePath);
            c.close();
        }
    }


    //加载图片
    private void showImage(String imaePath){
        Bitmap bm = BitmapFactory.decodeFile(imaePath);

        switch (now){


            case 1:
                ((ImageView)findViewById(R.id.image1)).setImageBitmap(bm);
                break;

            case 2:
                ((ImageView)findViewById(R.id.image2)).setImageBitmap(bm);
                break;

            case 3:
                ((ImageView)findViewById(R.id.image3)).setImageBitmap(bm);
                break;

            case 4:
                ((ImageView)findViewById(R.id.image4)).setImageBitmap(bm);
                break;
        }

    }



}
