package com.fernandopretell.mymoments.feature.register;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fernandopretell.mymoments.R;
import com.fernandopretell.mymoments.base.ui.BaseActivity;
import com.fernandopretell.mymoments.data.remote.model.Register;
import com.fernandopretell.mymoments.feature.login.LoginActivity;
import com.fernandopretell.mymoments.utils.Configuration;
import com.fernandopretell.mymoments.utils.KeyboardUtils;
import com.fernandopretell.mymoments.utils.Util;

import butterknife.BindView;

/**
 * Created by Fernando Pretell.
 */

public class RegisterActivity extends BaseActivity implements RegisterContract.RegisterView, View.OnClickListener {

    private RegisterContract.RegisterPresenter mPresenter;


    // VIEWS

    @BindView(R.id.et_nombre)
    EditText etNombre;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_nacimiento)
    EditText etNacimiento;
    @BindView(R.id.btn_registrar)
    Button btnRegistrar;


    /* ======= START ACTIVITY ======= */

    private BroadcastReceiver wifiStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            ConnectivityManager connectivity =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info_wifi = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo info_datos = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if(String.valueOf(info_wifi.getState()).equals("CONNECTED")){
                Configuration.CONNECTED_INTERNET = true;
                //lyOffline.setVisibility(View.GONE);
            }else{
                if(String.valueOf(info_datos.getState()).equals("CONNECTED")) {
                    Configuration.CONNECTED_INTERNET = true;
                    //lyOffline.setVisibility(View.GONE);
                }else{
                    Configuration.CONNECTED_INTERNET = false;
                    //lyOffline.setVisibility(View.VISIBLE);
                    showMessage("Perdiste conexión a internet");
                }
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(wifiStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));

    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(wifiStateReceiver, intentFilter);
    }

    @Override
    public void onStop() {
        super.onStop();
        unregisterReceiver(wifiStateReceiver);
    }

    ///////////////////////////////

    @Override
    protected void createPresenter() {
        mPresenter = new RegisterPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setUp();
    }

    @Override
    protected void setUp() {
        setUpToolbar();
        btnRegistrar.setOnClickListener(this);
        mPresenter.onViewInitialized();
    }

    private void setUpToolbar() {
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar_register);
        toolbar.setTitle("Registro de usuario");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.black));
        setSupportActionBar(toolbar);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_registrar :
                authRegister();

                break;
        }
    }

    private void authRegister() {

        boolean isValid = true;
        View focusView = null;

        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String nacimiento = etNacimiento.getText().toString().trim();
        String nombre = etNombre.getText().toString().trim();

        if(TextUtils.isEmpty(username) && TextUtils.isEmpty(password)) {
            isValid = false;
            focusView = etPassword;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Contraseña requerida");
            focusView = etPassword;
            isValid = false;
        }

        if (TextUtils.isEmpty(username)) {
            etUsername.setError("Usuario requerido");
            focusView = etNombre;
            isValid = false;
        }

        if(!isValid) {
            focusView.requestFocus();

        } else {
            KeyboardUtils.hideSoftInput(this);

            showLoadingActivity("Registrando usuario");
            Util.closeKeyboard(etPassword, this);
            Register register = new Register(nombre, nacimiento,username,password);
            mPresenter.startRegister(register);
        }

    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetachView();
        super.onDestroy();
    }


    @Override
    public void openLoginActivity(Register register) {
        Bundle bundle = new Bundle();
        bundle.putString("mail",register.getUsuario());
        bundle.putString("pass",register.getContrasenia());
        next(bundle, LoginActivity.class, true);
        overridePendingTransitionEnter();
    }

    @Override
    public void showLoginView() {

    }

    public void showLoadingActivity(String msj){
        showLoading(msj);
    }

    @Override
    public void onErrorMessage(String message) {

    }


}
