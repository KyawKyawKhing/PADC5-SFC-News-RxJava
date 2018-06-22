package com.padcmyanmar.sfc.mvp.presenters;

import com.padcmyanmar.sfc.data.models.NewsModel;
import com.padcmyanmar.sfc.data.vo.NewsVO;
import com.padcmyanmar.sfc.mvp.views.NewsDetailsView;

/**
 * Created by kkk on 6/17/2018.
 */

public class NewsDetailsPresenter extends BasePresenter<NewsDetailsView> {

    public NewsDetailsPresenter(NewsDetailsView mNewsDetailsView) {
        super(mNewsDetailsView);
    }


    public void onFinishUIComponentSetup(String newsId) {
        NewsVO newsVO = NewsModel.getInstance().getNewsById(newsId);
        mView.displayNewsDetail(newsVO);
    }
}
