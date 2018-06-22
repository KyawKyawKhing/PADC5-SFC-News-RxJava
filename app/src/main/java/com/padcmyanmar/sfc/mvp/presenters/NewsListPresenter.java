package com.padcmyanmar.sfc.mvp.presenters;

import android.support.design.widget.Snackbar;
import android.util.Log;

import com.padcmyanmar.sfc.data.models.NewsModel;
import com.padcmyanmar.sfc.data.vo.NewsVO;
import com.padcmyanmar.sfc.delegates.NewsItemDelegate;
import com.padcmyanmar.sfc.mvp.views.NewsListView;
import com.padcmyanmar.sfc.network.reponses.GetNewsResponse;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.Callable;

import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by kkk on 6/17/2018.
 */

public class NewsListPresenter extends BasePresenter<NewsListView> implements NewsItemDelegate {

    public NewsListPresenter(NewsListView newsListView) {
        super(newsListView);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startNewsLoading();
    }

    private void startNewsLoading() {
        PublishSubject<GetNewsResponse> publishSubject = PublishSubject.create();
        NewsModel.getInstance().startLoadingMMNews(publishSubject);
        publishSubject.subscribe(new Observer<GetNewsResponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(GetNewsResponse value) {
                if (value != null)
                    if (value.getNewsList() != null && value.getNewsList().size() > 0)
                        mView.displayNewsList(value.getNewsList());
            }

            @Override
            public void onError(Throwable e) {
                mView.displayErrorMsg(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
        getPrimeNumbers();
    }

    private void getPrimeNumbers() {
        Single<Integer[]> single = Single.fromCallable(new Callable<Integer[]>() {
            @Override
            public Integer[] call() throws Exception {
                return calculatePrimeNumbers("str1,str2,str3");
            }
        });
        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Integer[]>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Integer[] values) {
                        for (Integer value : values) {
                            Log.d("result value ", value + "");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    private Integer[] calculatePrimeNumbers(String str) {
        String[] strings = str.split(",");
        int count = strings.length;
        int index = 0;
        Integer[] results = new Integer[count];
        for (int i = 2; i < 1000; i++) {
            if ((i % 2) != 0 && (i % 3) != 0) {
                results[index] = i;
                index++;
                if (index == count)
                    break;
            }
        }
        return results;
    }

    @Override
    public void onTapComment() {

    }

    @Override
    public void onTapSendTo() {

    }

    @Override
    public void onTapFavorite() {

    }

    @Override
    public void onTapStatistics() {

    }

    @Override
    public void onTapNews(NewsVO newsVO) {
        mView.launchNewsDetailScreen(newsVO.getNewsId());
    }
}
