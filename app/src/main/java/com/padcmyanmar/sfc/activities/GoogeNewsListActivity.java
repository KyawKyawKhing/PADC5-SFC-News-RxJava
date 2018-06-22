package com.padcmyanmar.sfc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import com.padcmyanmar.sfc.R;
import com.padcmyanmar.sfc.adapters.NewsAdapter;
import com.padcmyanmar.sfc.components.EmptyViewPod;
import com.padcmyanmar.sfc.components.SmartRecyclerView;
import com.padcmyanmar.sfc.data.vo.NewsVO;
import com.padcmyanmar.sfc.mvp.presenters.NewsListPresenter;
import com.padcmyanmar.sfc.mvp.views.NewsListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoogeNewsListActivity extends AppCompatActivity implements NewsListView {

    @BindView(R.id.rv_news)
    SmartRecyclerView rvNews;

    @BindView(R.id.vp_empty_news)
    EmptyViewPod vpEmptyNews;

    private NewsAdapter mNewsAdapter;
    NewsListPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_googe_news_list);
        ButterKnife.bind(this, this);
        mPresenter = new NewsListPresenter(this);
        mPresenter.onCreate();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPresenter = new NewsListPresenter(this);
        mPresenter.onCreate();
        rvNews.setEmptyView(vpEmptyNews);
        rvNews.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        mNewsAdapter = new NewsAdapter(getApplicationContext(), mPresenter);
        rvNews.setAdapter(mNewsAdapter);
    }

    @Override
    public void displayNewsList(List<NewsVO> newsVOList) {
        mNewsAdapter.appendNewData(newsVOList);
    }

    @Override
    public void displayErrorMsg(String errMessage) {
        Snackbar.make(rvNews, errMessage, Snackbar.LENGTH_INDEFINITE).show();
    }

    @Override
    public void launchNewsDetailScreen(String newsId) {
        Intent intent = NewsDetailsActivity.newIntent(getApplicationContext(), newsId);
        startActivity(intent);
    }
}
