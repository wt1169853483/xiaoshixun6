package com.example.wangtao.day21_monitiyue.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wangtao.day21_monitiyue.MainActivity;
import com.example.wangtao.day21_monitiyue.utils.AppUtil;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2018/05/30
 * Description:
 */
public class ThreeColorView extends ViewGroup {

    public ThreeColorView(Context context) {
        super(context);
    }

    public ThreeColorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ThreeColorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 把此view的最终的宽度和高度定下来
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int totalHeight = 0;//此控件的高度
        int totalWidth = 0;//此控件的宽度
        //得到子view数量
        int child = getChildCount();
        if (child > 0) {
            for (int i = 0; i < child; i++) {//遍历子控件
                View view = getChildAt(i);//得到此容器所有的子view
                totalHeight += view.getMeasuredHeight();
                measureChild(view,widthMeasureSpec,heightMeasureSpec);
//                view.measure(widthMeasureSpec, heightMeasureSpec);
            }
        }
        totalWidth = AppUtil.screenWidth(getContext());
        System.out.println("width:"+totalWidth);
        System.out.println("height:"+totalHeight);


        //设置宽度和高度给当前view，通过下面这个方法
        setMeasuredDimension(totalWidth, totalHeight);

    }

    @Override
    protected void onLayout(boolean bo, int left, int top, int right, int bottom) {

        int l = 0;
        int t = 0;
        int r = 0;
        int b = 0;

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {

            View view = getChildAt(i);//得到每一个view的对象

            view.layout(l, t, l + view.getMeasuredWidth(), t + view.getMeasuredHeight());

            l += view.getMeasuredWidth();

            System.out.println("llll:"+l);

            t += view.getMeasuredHeight();

            if (l+view.getMeasuredWidth()>AppUtil.screenWidth(getContext())){
                l = 0;
            }


//            if (AppUtil.screenWidth(getContext()) - l < view.getMeasuredWidth()) {
//                l = 0;
//            }

            //点击事件
            final int finalI = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(getContext(), finalI +"：点击位置", Toast.LENGTH_SHORT).show();

                    TextView textView = (TextView) view;

                    Toast.makeText(getContext(), textView.getText().toString() +"文本", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.putExtra("id",textView.getText().toString());
                    getContext().startActivity(intent);

                }
            });

            view.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Toast.makeText(getContext(), finalI +"：长按位置", Toast.LENGTH_SHORT).show();

                    removeView(view);
                    return true;
                }
            });



        }


    }
}
