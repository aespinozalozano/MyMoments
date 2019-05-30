package com.fernandopretell.mymoments.feature.register;

import com.fernandopretell.mymoments.base.ui.BasePresenter;
import com.fernandopretell.mymoments.data.remote.model.Register;
import com.fernandopretell.mymoments.utils.Configuration;

/**
 * Created by Fernando Pretell on 27/08/2017.
 */

public class RegisterPresenter<V extends RegisterContract.RegisterView> extends BasePresenter<V>
        implements RegisterContract.RegisterPresenter<V>, RegisterContract.OnRegisterListener {

    private RegisterContract.RegisterInteractor mInteractor;

    public RegisterPresenter(V mView) {
        super(mView);
        mInteractor = new RegisterInteractor();
    }

    @Override
    public void onDetachView() {
        super.onDetachView();
        mInteractor.onUnsubscribe();
    }

    /* ======= START PRESENTER ======= */

    @Override
    public void startRegister(Register register) {
        //mView.hideLoading();
        mView.showLoading("Validando Credenciales");
        if(Configuration.CONNECTED_INTERNET){
            mInteractor.AuthRegister(register, this);
        }else{
            //mInteractor.callApiLocalAuthLogin(this, register);
            mView.onErrorMessage("no tienes internet");
        }
    }

    @Override
    public void onViewInitialized() {
        //mInteractor.callPreferencesCheckAuth(this);
    }


    /* ======= START LISTENER ======= */

    @Override
    public void onRegisterSuccess(Register register) {
        mView.hideLoading();
        mView.openLoginActivity(register);
        mView.showMessage("Registro exitoso");
    }

    @Override
    public void onRegisterError(String message) {
        mView.hideLoading();
        mView.onErrorMessage(message);
    }


}
