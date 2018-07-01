package com.example.wangtao.day21_monitiyue.model;


import com.example.wangtao.day21_monitiyue.utils.OkhttpUtils;

import java.util.Map;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2018/05/29
 * Description:
 */
public class NewsModel {

    /**
     * 请求数据
     * @param getUrl
     */
    public void getData(String getUrl, Map<String,String> params,final ResponseCallback responseCallback) {

        OkhttpUtils.getInstance().postData(getUrl,params, new OkhttpUtils.ICallback() {
            @Override
            public void success(String result) {
                responseCallback.success(result);

            }

            @Override
            public void fail(String msg) {

                responseCallback.fail(msg);
            }
        });
    }

    public interface  ResponseCallback{
        void success(String result);
        void fail(String msg);
    }
}
