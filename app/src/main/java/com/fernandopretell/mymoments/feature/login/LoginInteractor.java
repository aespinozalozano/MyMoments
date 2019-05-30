package com.fernandopretell.mymoments.feature.login;

import com.fernandopretell.mymoments.base.ui.BaseInteractor;
import com.fernandopretell.mymoments.data.remote.model.Login;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Fernando Pretell on 28/08/2017.
 */

public class LoginInteractor extends BaseInteractor implements LoginContract.LoginInteractor {

    public LoginInteractor() {
        super();
    }


    @Override
    public void AuthLogin(Login login, final LoginContract.OnLoginListener onLoginListener) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithEmailAndPassword(login.getUsuario(),login.getContrasenia())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        onLoginListener.onLoginSuccess();

                    }else{
                        onLoginListener.onLoginError("Credenciales invalidas");
                    }
                });





    }








}
