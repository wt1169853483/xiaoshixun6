package com.example.wangtao.day21_monitiyue.utils;

import android.os.Handler;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2018/05/29
 * Description:
 */
public class OkhttpUtils {
    private static OkhttpUtils okhttpUtils;
    private OkHttpClient okHttpClient;
    private Handler handler;

    private OkhttpUtils() {
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        handler = new Handler();

    }

    public static OkhttpUtils getInstance() {

        if (okhttpUtils == null) {
            okhttpUtils = new OkhttpUtils();
        }

        return okhttpUtils;

    }

    /**
     * get方式
     */
    public void getData(String url, final ICallback callback){

        final Request request = new Request.Builder()
                .url(url).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                if (callback!=null){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.fail("请求失败");
                        }
                    });

                }

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (callback!=null){
                    if (response.isSuccessful()&&response.code()==200){

                        final String result = response.body().string();


                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.success(result);
                            }
                        });
                    }
                }
            }
        });



    }


    /**
     * post方式
     */
    public void postData(String url, Map<String,String> params, final ICallback callback){

        FormBody.Builder builder = new FormBody.Builder();

        for (Map.Entry<String, String> bean : params.entrySet()) {
            builder.add(bean.getKey(),bean.getValue());

        }

        final Request request = new Request.Builder()
                .url(url).post(builder.build()).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                if (callback!=null){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.fail("请求失败");
                        }
                    });

                }

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (callback!=null){
                    if (response.isSuccessful()&&response.code()==200){

                        final String result = response.body().string();


                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.success(result);
                            }
                        });
                    }
                }
            }
        });
    }


    public interface ICallback{
        void success(String result);
        void fail(String msg);
    }



}
