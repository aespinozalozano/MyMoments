package com.fernandopretell.mymoments.feature.login;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fernandopretell.mymoments.R;
import com.fernandopretell.mymoments.base.ui.BaseActivity;
import com.fernandopretell.mymoments.data.remote.model.Login;
import com.fernandopretell.mymoments.feature.main.MainActivity;
import com.fernandopretell.mymoments.feature.register.RegisterActivity;
import com.fernandopretell.mymoments.utils.Configuration;
import com.fernandopretell.mymoments.utils.KeyboardUtils;
import com.fernandopretell.mymoments.utils.Util;

import butterknife.BindView;

/**
 * Created by Fernando Pretell on 29/08/2017.
 */

public class LoginActivity extends BaseActivity implements LoginContract.LoginView, View.OnClickListener {

    private LoginContract.LoginPresenter mPresenter;


    // VIEWS

    @BindView(R.id.etMailUsu)
    EditText etUsername;
    @BindView(R.id.etPass)
    EditText etPassword;
    @BindView(R.id.btn_ingresar)
    Button btnAuthLogin;
    @BindView(R.id.btn_crear_cuenta)
    Button btnCrearCuenta;

    private Button btnprueba;

    /* ======= START ACTIVITY ======= */

    private BroadcastReceiver wifiStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            ConnectivityManager connectivity =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info_wifi = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo info_datos = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if(String.valueOf(info_wifi.getState()).equals("CONNECTED")){
                Configuration.CONNECTED_INTERNET = true;
            }else{
                if(String.valueOf(info_datos.getState()).equals("CONNECTED")) {
                    Configuration.CONNECTED_INTERNET = true;
                }else{
                    Configuration.CONNECTED_INTERNET = false;
                    showMessage("Perdiste conexión a internet");                }
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
        mPresenter = new LoginPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setUp();
        verifyPermissions(this);
    }

    @Override
    protected void setUp() {

        String mail = getIntent().getStringExtra("mail");
        String pass = getIntent().getStringExtra("pass");

        if (mail != null && pass != null) {
            etUsername.setText(mail);
            etPassword.setText(pass);
        }

        btnAuthLogin.setOnClickListener(this);
        btnCrearCuenta.setOnClickListener(this);
        mPresenter.onViewInitialized();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_ingresar :
                authLogin();
                break;
            case R.id.btn_crear_cuenta  :
                register();
                break;
        }
    }

    private void register() {
        Bundle bundle = new Bundle();
        next(bundle, RegisterActivity.class, false);
        overridePendingTransitionEnter();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetachView();
        super.onDestroy();
    }

    private void authLogin() {
        boolean isValid = true;
        View focusView = null;

        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString();

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
            focusView = etUsername;
            isValid = false;
        }

        if(!isValid) {
            focusView.requestFocus();

        } else {
            KeyboardUtils.hideSoftInput(this);

            showLoadingActivity("Validando Credenciales");
            Util.closeKeyboard(etPassword, this);
            Login login = new Login(username, password);

            mPresenter.startLogin(login);
        }
    }

    @Override
    public void openMainActivity() {
        Bundle bundle = new Bundle();
        next(bundle, MainActivity.class, true);
        overridePendingTransitionEnter();
    }

    @Override
    public void showLoginView() {

    }

    public void showLoadingActivity(String msj){
        showLoading(msj);
    }

    public void hideLoadingActivity(String msj){
        hideLoading();
    }


    @Override
    public void onErrorMessage(String message) {
        onError(message);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
                Toast.makeText(getApplicationContext(), "Gracias por darnos los permisos.", Toast.LENGTH_LONG).show();
            } else {
                //runtime_permissions();

            }

        }
    }

    private boolean verifyPermissions(Activity activity) {
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
        };
        int REQUEST_EXTERNAL_STORAGE = 1;
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
            return false;
        }else{
            return true;
        }
    }
}
