package com.example.wangtao.day21_monitiyue.bean;

import java.util.List;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2018/05/29
 * Description:
 */
public class News {
    public String stat;
    public List<Data> data;

    public class Data {
        public String topic;
        public String source;
        public List<IMG> miniimg;

        public class IMG {
            public String src;
        }

    }
}
