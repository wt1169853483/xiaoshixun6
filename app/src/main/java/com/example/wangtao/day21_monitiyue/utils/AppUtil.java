package com.example.wangtao.day21_monitiyue.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2018/05/30
 * Description:
 */
public class AppUtil {
    /**
     *
     * @param context
     * @return 屏幕宽度
     */
    public static int screenWidth(Context context){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return  metrics.widthPixels;
    }
}
