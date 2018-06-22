package com.padcmyanmar.sfc.mvp.presenters;

import com.padcmyanmar.sfc.mvp.views.BaseView;

/**
 * Created by kkk on 6/17/2018.
 */

public abstract class BasePresenter<V extends BaseView> {

    protected V mView;

    public BasePresenter(V mView) {
        this.mView = mView;
    }

    public void onStart() {
    }

    public void onCreate() {
    }

    public void onResume() {
    }

    public void onPause() {
    }

    public void onStop() {
    }

    public void onDestory() {
    }
}
