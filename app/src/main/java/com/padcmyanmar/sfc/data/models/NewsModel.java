package com.padcmyanmar.sfc.data.models;

import com.padcmyanmar.sfc.SFCNewsApp;
import com.padcmyanmar.sfc.data.vo.NewsVO;
import com.padcmyanmar.sfc.events.RestApiEvents;
import com.padcmyanmar.sfc.network.MMNewsDataAgent;
import com.padcmyanmar.sfc.network.MMNewsDataAgentImpl;
import com.padcmyanmar.sfc.network.reponses.GetNewsResponse;
import com.padcmyanmar.sfc.utils.AppConstants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by aung on 12/3/17.
 */

public class NewsModel {

    private static NewsModel objInstance;

    private List<NewsVO> mNews;
    private int mmNewsPageIndex = 1;

    private NewsModel() {
        EventBus.getDefault().register(this);
        mNews = new ArrayList<>();
    }

    public static NewsModel getInstance() {
        if (objInstance == null) {
            objInstance = new NewsModel();
        }
        return objInstance;
    }

    public void startLoadingMMNews(final PublishSubject<GetNewsResponse> publishSubject) {
//        MMNewsDataAgentImpl.getInstance().loadMMNews(AppConstants.ACCESS_TOKEN, mmNewsPageIndex);
        Observable<GetNewsResponse> responseObservable = SFCNewsApp.getMmNewsAPI().loadMMNews(mmNewsPageIndex, AppConstants.ACCESS_TOKEN);
        responseObservable.
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GetNewsResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GetNewsResponse value) {
//                        RestApiEvents.NewsDataLoadedEvent newsDataLoadedEvent = new RestApiEvents.NewsDataLoadedEvent(
//                                value.getPageNo(), value.getNewsList());
//                        EventBus.getDefault().post(newsDataLoadedEvent);
                        publishSubject.onNext(value);
                    }

                    @Override
                    public void onError(Throwable e) {
//                        RestApiEvents.ErrorInvokingAPIEvent errorEvent
//                                = new RestApiEvents.ErrorInvokingAPIEvent("No data could be loaded for now. Pls try again later.");
//                        EventBus.getDefault().post(errorEvent);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onNewsDataLoaded(RestApiEvents.NewsDataLoadedEvent event) {
        mNews.addAll(event.getLoadNews());
        mmNewsPageIndex = event.getLoadedPageIndex() + 1;
    }
}
