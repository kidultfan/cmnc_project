package com.demo.cmnc;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.demo.cmnc.activities.one.ProductsActivity;
import com.demo.cmnc.fragments.like.farm_like;
import com.demo.cmnc.fragments.like.product_like;
import com.demo.cmnc.fragments.main.LikeFragment;
import com.demo.cmnc.fragments.main.MainFragment;
import com.demo.cmnc.fragments.main.SelfFragment;
import com.demo.cmnc.tools.JsonParser;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

import static android.os.Build.VERSION;

public class MainActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener, LikeFragment.OnFragmentInteractionListener, SelfFragment.OnFragmentInteractionListener, product_like.OnFragmentInteractionListener, farm_like.OnFragmentInteractionListener {

    private GridView gridView;
    private MainFragment mainFragment;
    private LikeFragment likeFragment;
    private SelfFragment selfFragment;
    RequestQueue mQueue;
    private Toast mToast;
    JSONArray jieba;


    private FragmentTransaction transaction;
    // 语音听写对象
    private SpeechRecognizer mIat;
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();

    // 语音听写UI
    private RecognizerDialog mIatDialog;
    private SearchView searchView;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            hideAllFrag();

            switch (item.getItemId()) {
                case R.id.navigation_home:

                    if (mainFragment == null) {

                        mainFragment = new MainFragment();


                    }
                    addFrag(mainFragment);
                    transaction.show(mainFragment);
                    transaction.commit();

                    return true;
                case R.id.navigation_dashboard:
                    if (likeFragment == null) {

                        likeFragment = new LikeFragment();


                    }
                    addFrag(likeFragment);
                    transaction.show(likeFragment);
                    transaction.commit();

                    return true;
                case R.id.navigation_notifications:
                    if (selfFragment == null) {

                        selfFragment = new SelfFragment();


                    }
                    addFrag(selfFragment);
                    transaction.show(selfFragment);
                    transaction.commit();

                    return true;

            }

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQueue = Volley.newRequestQueue(this);
//        SpeechUtility.createUtility(MainActivity.this, SpeechConstant.APPID +"=12345678");
        mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);

        setContentView(R.layout.activity_main);
        transaction = getSupportFragmentManager().beginTransaction();
        hideAllFrag();//先隐藏所有frag
        setDefaultFragment();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        SpeechUtility.createUtility(MainActivity.this, SpeechConstant.APPID + "=5b0bd538");

        mIat = SpeechRecognizer.createRecognizer(MainActivity.this, mInitListener);

        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
        mIatDialog = new RecognizerDialog(MainActivity.this, mInitListener);

    }

    private void setDefaultFragment() {
        if (mainFragment == null) {

            mainFragment = new MainFragment();


        }
        addFrag(mainFragment);
        transaction.show(mainFragment);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    private void addFrag(Fragment frag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (frag != null && !frag.isAdded()) {

            ft.add(R.id.frame, frag);

        }

        ft.commit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);//指定Toolbar上的视图文件
        MenuItem searchItem = menu.findItem(R.id.action_search);

        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                Log.i("search", query);

               checkquery(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_search:
                Log.i("search", searchView.getQuery().toString());
                break;
            case R.id.action_voice:
//                setParam();
                CheckPermission();
                // 显示听写对话框
                mIatDialog.setListener(mRecognizerDialogListener);
                mIatDialog.show();


                break;
            default:
                break;

        }
        return super.onOptionsItemSelected(item);

    }

    private void hideAllFrag() {
        hideFrag(likeFragment);
        hideFrag(mainFragment);
        hideFrag(selfFragment);

    }

    private void hideFrag(Fragment frag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (frag != null && frag.isAdded()) {
            ft.hide(frag);
        }
        ft.commit();
    }

    private void checkquery( final String query) {

        StringRequest stringRequest = new StringRequest("http://" + getString(R.string.ip) + "/products/like/" + query + "",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("ger",response);
                        if (!response.equals("no")){
                            try {
                                if (new JSONObject(response).getJSONArray("data").length()>0)

                                {

startActivity(new Intent(MainActivity.this,ProductsActivity.class).putExtra("like",query));
                                }else {


         checkjieba(query);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        mQueue.add(stringRequest);

    }
public void  checkjieba(String query)
{

    StringRequest getjieba = new StringRequest("http://" + getString(R.string.ip) + "/getjieba?input="+query,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        jieba =new JSONArray(response);
                        Log.i("jieba",response);
                        StringRequest stringRequest = new StringRequest("http://" + getString(R.string.ip) + "/keywords",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.i("have","123");

                                        JSONArray jsonArray = null;
                                        try {
                                            boolean have=false;
                                            jsonArray = new JSONObject(response).getJSONArray("data");
                                            for (int i = 0; i <jsonArray.length(); i++) {
                                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                                String key = jsonObject.getString("keyword");


                                                for (int y=0;y<jieba.length();y++){

                                                    if (jieba.getString(y).equals(key)){

                                                        have=true;
                                                        Log.i("tag",jsonObject.getString("tag"));
                                                        Intent intent = new Intent(MainActivity.this, ProductsActivity.class);
                                                        intent.putExtra("tag", jsonObject.getString("tag"));
                                                        startActivity(intent);

                                                    }


                                                }
                                                Log.i("have",have+"");



//                                if (stringlist.contains(key)) {
//
//                                    Log.i("tag",jsonObject.getString("tag"));
//                                    Intent intent = new Intent(MainActivity.this, ProductsActivity.class);
//                                    intent.putExtra("tag", jsonObject.getString("tag"));
//                                    startActivity(intent);
//
//
//                                }
                                            }

                                            if (!have){
                                                Log.i("have",have+"");

                                                Intent intent = new Intent(MainActivity.this, ProductsActivity.class);
                                                intent.putExtra("tag", "empty");
                                                startActivity(intent);


                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }


                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("TAG", error.getMessage(), error);
                            }
                        });
                        mQueue.add(stringRequest);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("TAG", error.getMessage(), error);
        }
    });

    mQueue.add(getjieba);




}
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d("12", "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                showTip("初始化失败，错误码：" + code);
            }
        }
    };

    public void setParam() {
        // 清空参数
        mIat.setParameter(SpeechConstant.PARAMS, null);

        // 设置听写引擎
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        // 设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");

        String lag =
                "mandarin";
        if (lag.equals("en_us")) {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
        } else {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            // 设置语言区域
            mIat.setParameter(SpeechConstant.ACCENT, lag);
        }

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS, "4000");

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS, "1000");

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, "1");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mIat.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/iat.wav");
    }


    /**
     * 听写UI监听器
     */
    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        public void onResult(RecognizerResult results, boolean isLast) {
            printResult(results);
        }

        /**
         * 识别回调错误.
         */
        public void onError(SpeechError error) {
            showTip(error.getPlainDescription(true));
        }

    };
    private RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            showTip("开始说话");
        }

        @Override
        public void onError(SpeechError error) {
            // Tips：
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
            // 如果使用本地功能（语记）需要提示用户开启语记的录音权限。
            showTip(error.getPlainDescription(true));
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            showTip("结束说话");
        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            printResult(results);

            if (isLast) {
                // TODO 最后的结果
            }
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            showTip("当前正在说话，音量大小：" + volume);
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };

    private void showTip(final String str) {
        mToast.setText(str);
        mToast.show();
    }

    private void printResult(RecognizerResult results) {
        String text = JsonParser.parseIatResult(results.getResultString());

        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }

        checkquery(resultBuffer.toString());
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


    public void CheckPermission ()   {
        if (VERSION.SDK_INT>=23)       {

            Log.i("permission","1");
            int request= ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
            if (request!= PackageManager.PERMISSION_GRANTED)//缺少权限，进行权限申请
            {
                Log.i("permission","2");

                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},123);
                return;//
            }
            else
            {
                Log.i("permission","3");

                //权限同意，不需要处理,去掉用拍照的方法               Toast.makeText(this,"权限同意",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            //低于23 不需要特殊处理，去掉用拍照的方法
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123) {            //当然权限多了，建议使用Switch，不必纠结于此
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "权限申请成功", Toast.LENGTH_SHORT).show();
            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this, "权限申请失败，用户拒绝权限", Toast.LENGTH_SHORT).show();
            }
        }

    }

}
