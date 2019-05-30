package com.fernandopretell.mymoments.feature.login;

import com.fernandopretell.mymoments.base.ui.BasePresenter;
import com.fernandopretell.mymoments.data.remote.model.Login;
import com.fernandopretell.mymoments.utils.Configuration;

/**
 * Created by Fernando Pretell.
 */

public class LoginPresenter<V extends LoginContract.LoginView> extends BasePresenter<V>
        implements LoginContract.LoginPresenter<V>, LoginContract.OnLoginListener {

    private LoginContract.LoginInteractor mInteractor;

    public LoginPresenter(V mView) {
        super(mView);
        mInteractor = new LoginInteractor();
    }

    @Override
    public void onDetachView() {
        super.onDetachView();
        mInteractor.onUnsubscribe();
    }

    /* ======= START PRESENTER ======= */

    @Override
    public void startLogin(Login login) {
        //mView.hideLoading();
        mView.showLoading("Validando Credenciales");
        if(Configuration.CONNECTED_INTERNET){
            mInteractor.AuthLogin(login, this);
        }else{
            mView.showMessage("No tienes conexión a internet");
        }
    }

    @Override
    public void onViewInitialized() {
        //mInteractor.callPreferencesCheckAuth(this);
    }

    @Override
    public void showLoading(String message) {
        mView.showLoadingActivity(message);
    }


    /* ======= START LISTENER ======= */

    @Override
    public void onLoginSuccess() {
        mView.hideLoading();
        mView.openMainActivity();
    }

    @Override
    public void onLoginError(String message) {
        mView.hideLoading();
        mView.onErrorMessage(message);
    }





}
