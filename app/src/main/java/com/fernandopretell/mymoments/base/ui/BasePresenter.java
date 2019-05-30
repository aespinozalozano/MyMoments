package com.fernandopretell.mymoments.base.ui;

import com.fernandopretell.mymoments.base.mvp.MvpPresenter;
import com.fernandopretell.mymoments.base.mvp.MvpView;

/**
 * Created by Fernando Pretell.
 */

public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    protected V mView;

    public BasePresenter(V view) {
        onAttachView(view);
    }

    @Override
    public void onAttachView(V view) {
        this.mView = view;
    }

    @Override
    public void onDetachView() {
        this.mView = null;
    }

    @Override
    public boolean isViewAttached() {
        return mView != null;
    }

}

