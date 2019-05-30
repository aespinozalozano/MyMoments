package com.fernandopretell.mymoments.base.mvp;

/**
 * Created by Fernando Pretell.
 */

public interface MvpPresenter<V extends MvpView>{

    void onAttachView(V view);

    void onDetachView();

    boolean isViewAttached();

}
