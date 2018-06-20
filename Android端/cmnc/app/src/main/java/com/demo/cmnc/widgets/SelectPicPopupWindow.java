package com.demo.cmnc.widgets;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.demo.cmnc.R;

/**
 * Created by zhangfan on 2017/4/19.
 */

public class SelectPicPopupWindow extends PopupWindow {


    public Button pay;
    private View mMenuView;
     public  TextView number,info;
  public   EditText pass;
    Activity context1;
    public SelectPicPopupWindow(Activity context) {
        super(context);
        context1=context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.popup, null);

pay=mMenuView.findViewById(R.id.pay);
        number=mMenuView.findViewById(R.id.number);
        info=mMenuView.findViewById(R.id.info);
        pass=mMenuView.findViewById(R.id.pass);

        this.setBackgroundDrawable(new BitmapDrawable());

        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        this.setAnimationStyle(R.style.Animation);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        //实例化一个ColorDrawable颜色为半透明
        WindowManager.LayoutParams params=context.getWindow().getAttributes();
        params.alpha=0.4f;

        context.getWindow().setAttributes(params);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y=(int) event.getY();
                if(event.getAction()== MotionEvent.ACTION_UP){
                    if(y<height){
                        WindowManager.LayoutParams params=context1.getWindow().getAttributes();
                        params.alpha=1f;
                        context1.getWindow().setAttributes(params);

                        dismiss();
                    }
                }
                return true;
            }
        });
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params=context1.getWindow().getAttributes();
                params.alpha=1f;
                context1.getWindow().setAttributes(params);

            }
        });
    }

}