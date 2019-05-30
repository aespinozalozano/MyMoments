package com.fernandopretell.mymoments.feature.newpost;

import com.fernandopretell.mymoments.base.mvp.MvpInteractor;
import com.fernandopretell.mymoments.base.mvp.MvpPresenter;
import com.fernandopretell.mymoments.base.mvp.MvpView;
import com.fernandopretell.mymoments.data.remote.model.Login;
import com.fernandopretell.mymoments.data.remote.model.Place;

public interface NewPostContract {

    interface NewPostView extends MvpView {
        void showPicker();

    }

    interface NewPostPresenter<V extends NewPostContract.NewPostView> extends MvpPresenter<V> {
        void startPost(Place place);
        void onViewInitialized();

        void showLoading(String message);
    }

    interface NewpostInteractor extends MvpInteractor {
        void AuthCreateNewPost(Login login, NewPostContract.OnNewPostListener newPostListener);


    }

    interface OnNewPostListener {
        void onNewPostSuccess();
        void onNewPostError(String message);
    }
}
