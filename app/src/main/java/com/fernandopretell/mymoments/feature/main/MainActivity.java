package com.fernandopretell.mymoments.feature.main;

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
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fernandopretell.mymoments.R;
import com.fernandopretell.mymoments.base.ui.BaseActivity;
import com.fernandopretell.mymoments.data.remote.model.Login;
import com.fernandopretell.mymoments.data.remote.model.Place;
import com.fernandopretell.mymoments.feature.newpost.NewPostActivity;
import com.fernandopretell.mymoments.feature.register.RegisterActivity;
import com.fernandopretell.mymoments.utils.Configuration;
import com.fernandopretell.mymoments.utils.KeyboardUtils;
import com.fernandopretell.mymoments.utils.PostsAdapter;
import com.fernandopretell.mymoments.utils.Util;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Fernando Pretell .
 */

public class MainActivity extends BaseActivity implements MainContract.MainView, View.OnClickListener {

    private MainContract.MainPresenter mPresenter;


    // VIEWS
    @BindView(R.id.rv_list_post)
    RecyclerView rvListPost;
    @BindView(R.id.btn_new_post)
    FloatingActionButton btnNewPost;


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
        registerReceiver(wifiStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

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
        mPresenter = new MainPresenter(this);
    }

    @Override
    protected void setUp() {
        btnNewPost.setOnClickListener(this);
        mPresenter.startShowListPost();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUp();
    }



    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_new_post :
                openNewPostActivity();
                break;

        }
    }


    @Override
    protected void onDestroy() {
        mPresenter.onDetachView();
        super.onDestroy();
    }


    @Override
    public void showListPostActivity(List<Place> placeList) {
        LinearLayoutManager lim = new LinearLayoutManager(mActivity);
        rvListPost.setLayoutManager(lim);
        rvListPost.setAdapter(new PostsAdapter(placeList,MainActivity.this));
    }

    @Override
    public void openNewPostActivity() {
        Bundle bundle = new Bundle();
        next(bundle, NewPostActivity.class, true);
        overridePendingTransitionEnter();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }


}
