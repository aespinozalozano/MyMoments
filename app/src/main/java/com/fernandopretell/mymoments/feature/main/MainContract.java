package com.fernandopretell.mymoments.feature.main;

import com.fernandopretell.mymoments.base.mvp.MvpInteractor;
import com.fernandopretell.mymoments.base.mvp.MvpPresenter;
import com.fernandopretell.mymoments.base.mvp.MvpView;
import com.fernandopretell.mymoments.data.remote.model.Place;

import java.util.List;

/**
 * Created by Fernando Pretell.
 */

public interface MainContract {

    interface MainView extends MvpView {
        void showListPostActivity(List<Place> list);
        void openNewPostActivity();
    }

    interface MainPresenter<V extends MainView> extends MvpPresenter<V> {
        void startShowListPost();
    }

    interface MainInteractor extends MvpInteractor {
        void authListPost(OnMainListener mainListener);


    }

    interface OnMainListener {
        void onShowListPostSuccess(List<Place> list);
        void onShowListPostError(String message);
    }
}
