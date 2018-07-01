package com.example.wangtao.day21_monitiyue.presenter;

import android.text.TextUtils;


import com.example.wangtao.day21_monitiyue.bean.News;
import com.example.wangtao.day21_monitiyue.model.NewsModel;
import com.example.wangtao.day21_monitiyue.view.INews;
import com.google.gson.Gson;

import java.util.Map;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2018/05/29
 * Description:p
 */
public class NewsPresenter {

    private INews iNews;
    private NewsModel model;

    public NewsPresenter(INews iNews) {

        model = new NewsModel();
        attach(iNews);
    }

    /**
     * 绑定view
     * @param iNews
     */
    public void attach(INews iNews){
        this.iNews = iNews;
    }
    /**
     * 获取数据的方法
     *
     * @param getUrl
     */
    public void getData(String getUrl, Map<String ,String> params) {

        model.getData(getUrl, params,new NewsModel.ResponseCallback() {
            @Override
            public void success(String result) {
                if (!TextUtils.isEmpty(result)) {
                    String s = result.replace("null(","")
                            .replace(")","");

                    News news = new Gson().fromJson(s, News.class);
                    iNews.success(news);
                }
            }

            @Override
            public void fail(String msg) {


            }
        });

//        OkhttpUtils.getInstance().getData(getUrl, new OkhttpUtils.ICallback() {
//            @Override
//            public void success(String result) {
//
//            }
//
//            @Override
//            public void fail(String msg) {
//
//            }
//        });
    }

    /**
     * 解绑
     */
    public void detach(){
        this.iNews = null;
    }
}
