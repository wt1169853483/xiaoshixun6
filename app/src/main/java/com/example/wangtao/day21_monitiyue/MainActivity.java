package com.example.wangtao.day21_monitiyue;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.example.wangtao.day21_monitiyue.adapter.NewsAdapter;
import com.example.wangtao.day21_monitiyue.bean.News;
import com.example.wangtao.day21_monitiyue.common.Constants;
import com.example.wangtao.day21_monitiyue.db.DbHelper;
import com.example.wangtao.day21_monitiyue.presenter.NewsPresenter;
import com.example.wangtao.day21_monitiyue.utils.NetWorkUtil;
import com.example.wangtao.day21_monitiyue.view.INews;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements INews {


    private XRecyclerView rv;
    private NewsPresenter presenter;
    private int page = 5010;
    private List<News.Data> data;
    private boolean isRefresh = true;//判断是下啦刷新还是上啦加载
    private NewsAdapter newsAdapter;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        if (getIntent().getExtras() != null) {
            page = 5010 + Integer.parseInt(getIntent().getExtras().getString("id"));
        }

        dbHelper = new DbHelper(this);
        presenter = new NewsPresenter(this);
        data = new ArrayList<>();

        request();

    }

    public void request() {
        if (NetWorkUtil.hasWifiConnection(this) || NetWorkUtil.hasGPRSConnection(this)) {

            Map<String, String> p = new HashMap<>();
            p.put("type", page + "");
            presenter.getData(Constants.GET_URL, p);
        } else {
            Toast.makeText(this, "网络不通畅，请稍后再试！", Toast.LENGTH_SHORT).show();
            String json = null;
//
            //获取数据库对象，可读
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            //获取数据库的游标
            Cursor cursor = db.rawQuery("select * from news", null);
            while (cursor.moveToNext()){
                json = cursor.getString(cursor.getColumnIndex("json"));
            }

            //本地列表刷新
            fillLocalData(json);

        }

    }

    /**
     * 本地列表刷新
     * @param json
     */
    private void fillLocalData(String json) {

        News news = new Gson().fromJson(json,News.class);
        newsAdapter = new NewsAdapter(news.data, this,news);
        rv.setAdapter(newsAdapter);

    }

    private void initView() {
        rv = findViewById(R.id.rv);

        //设置局部刷新动画
        rv.setItemAnimator(new DefaultItemAnimator());

        rv.setPullRefreshEnabled(true);//刷新配置
        rv.setLoadingMoreEnabled(true);//上拉配置
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

//                rv.refreshComplete();
                isRefresh = true;
                //下拉刷新
                page = 5010;
                request();


            }

            @Override
            public void onLoadMore() {

                isRefresh = false;
                page++;
                request();

//                rv.loadMoreComplete();

            }
        });

    }

    @Override
    public void success(News news) {

        //转换json串
        String json = new Gson().toJson(news);



        System.out.println("size:" + news.data.size());




        data = news.data;

        if (isRefresh) {
            newsAdapter = new NewsAdapter(data, this,news);
            rv.setAdapter(newsAdapter);
            rv.refreshComplete();

            //保存json串数据
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("json", json);
            db.insert(DbHelper.NEWS_TABLE_NAME, null, contentValues);



        } else {
            if (newsAdapter != null) {
                //上啦加载更多，刷新
                newsAdapter.loadMore(news.data);
            }
            rv.loadMoreComplete();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }
}
