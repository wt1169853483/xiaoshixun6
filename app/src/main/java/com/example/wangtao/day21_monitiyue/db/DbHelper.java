package com.example.wangtao.day21_monitiyue.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2018/05/30
 * Description:
 */
public class DbHelper extends SQLiteOpenHelper {
    //数据库文件名称
    private static final String DB_NAME = "news.db";
    public static final String NEWS_TABLE_NAME = "news";
    private static final int VERSION = 1;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "create table " + NEWS_TABLE_NAME + " (_id Integer PRIMARY KEY ,json text)";

        sqLiteDatabase.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {



    }
}
