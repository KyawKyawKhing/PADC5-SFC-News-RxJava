package com.padcmyanmar.sfc.mvp.views;

import com.padcmyanmar.sfc.data.vo.NewsVO;

import java.util.List;

/**
 * Created by kkk on 6/17/2018.
 */

public interface NewsListView extends BaseView{

    void displayNewsList(List<NewsVO> newsVOList);

    void launchNewsDetailScreen(String newsId);
}
