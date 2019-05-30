package com.fernandopretell.mymoments.feature.main;

import com.fernandopretell.mymoments.base.ui.BasePresenter;
import com.fernandopretell.mymoments.data.remote.model.Login;
import com.fernandopretell.mymoments.data.remote.model.Place;
import com.fernandopretell.mymoments.utils.Configuration;

import java.util.List;

/**
 * Created by Fernando Pretell.
 */

public class MainPresenter<V extends MainContract.MainView> extends BasePresenter<V>
        implements MainContract.MainPresenter<V>, MainContract.OnMainListener {

    private MainContract.MainInteractor mInteractor;

    public MainPresenter(V mView) {
        super(mView);
        mInteractor = new MainInteractor();
    }

    @Override
    public void onDetachView() {
        super.onDetachView();
        mInteractor.onUnsubscribe();
    }

    /* ======= START PRESENTER ======= */

    @Override
    public void startShowListPost() {
        mInteractor.authListPost(this);
    }


    /* ======= START LISTENER ======= */


    @Override
    public void onShowListPostSuccess(List<Place> list) {
        mView.showListPostActivity(list);
    }

    @Override
    public void onShowListPostError(String message) {
        mView.hideLoading();
        mView.onError(message);
    }



}
