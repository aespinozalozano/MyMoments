package com.fernandopretell.mymoments.feature.login;

import com.fernandopretell.mymoments.base.mvp.MvpInteractor;
import com.fernandopretell.mymoments.base.mvp.MvpPresenter;
import com.fernandopretell.mymoments.base.mvp.MvpView;
import com.fernandopretell.mymoments.data.remote.model.Login;

/**
 * Created by Fernando Pretell.
 */

public interface LoginContract {

    interface LoginView extends MvpView {
        void openMainActivity();
        void showLoginView();
        void showLoadingActivity(String message);

        void onErrorMessage(String message);
    }

    interface LoginPresenter<V extends LoginView> extends MvpPresenter<V> {
        void startLogin(Login login);
        void onViewInitialized();

        void showLoading(String message);
    }

    interface LoginInteractor extends MvpInteractor {
        void AuthLogin(Login login, OnLoginListener loginListener);


    }

    interface OnLoginListener {
        void onLoginSuccess();
        void onLoginError(String message);
    }
}
