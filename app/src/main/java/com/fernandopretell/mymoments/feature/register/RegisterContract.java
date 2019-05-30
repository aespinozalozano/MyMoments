package com.fernandopretell.mymoments.feature.register;

import com.fernandopretell.mymoments.base.mvp.MvpInteractor;
import com.fernandopretell.mymoments.base.mvp.MvpPresenter;
import com.fernandopretell.mymoments.base.mvp.MvpView;
import com.fernandopretell.mymoments.data.remote.model.Login;
import com.fernandopretell.mymoments.data.remote.model.Register;
import com.fernandopretell.mymoments.feature.login.LoginContract;

import java.util.List;

/**
 * Created by Fernando Pretell .
 */

public interface RegisterContract {

    interface RegisterView extends MvpView {
        void openLoginActivity(Register register);
        void showLoginView();
        void showLoadingActivity(String message);
        void onErrorMessage(String message);
    }

    interface RegisterPresenter<V extends RegisterContract.RegisterView> extends MvpPresenter<V> {

        void startRegister(Register register);
        void onViewInitialized();

    }

    interface RegisterInteractor extends MvpInteractor {

        void AuthRegister(Register register, RegisterContract.OnRegisterListener registerListener);
    }

    interface OnRegisterListener {
        void onRegisterSuccess(Register register);
        void onRegisterError(String message);
    }
}
