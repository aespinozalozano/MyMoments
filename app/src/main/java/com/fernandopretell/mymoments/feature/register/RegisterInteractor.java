package com.fernandopretell.mymoments.feature.register;

import android.support.annotation.NonNull;

import com.fernandopretell.mymoments.base.ui.BaseInteractor;
import com.fernandopretell.mymoments.data.remote.model.Register;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Created by Fernando Pretell .
 */

public class RegisterInteractor extends BaseInteractor implements RegisterContract.RegisterInteractor {

    public RegisterInteractor() {
        super();
    }


    @Override
    public void AuthRegister(Register register, RegisterContract.OnRegisterListener registerListener) {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(register.getUsuario(),register.getContrasenia()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                FirebaseFirestore database = FirebaseFirestore.getInstance();
                database.collection("users").document(firebaseUser.getUid()).set(register).addOnSuccessListener(aVoid -> {
                    registerListener.onRegisterSuccess(register);
                }).addOnFailureListener(e -> registerListener.onRegisterError(e.getMessage()));


            }else{
                registerListener.onRegisterError("problemas de registrar el mail");
            }
        });
    }
}


