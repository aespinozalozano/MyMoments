package com.fernandopretell.mymoments.feature.newpost;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.Button;

import com.fernandopretell.mymoments.R;
import com.fernandopretell.mymoments.base.ui.BaseActivity;
import com.fernandopretell.mymoments.data.remote.model.Place;
import com.fernandopretell.mymoments.utils.Configuration;

import butterknife.BindView;

public class NewPostActivity extends BaseActivity implements NewPostContract.NewPostView, View.OnClickListener {

    //private NewPostContract.NewPostPresenter mPresenter;
    private Bitmap bmp;
    private Uri outPutfileUri;
    private Uri u;


    // VIEWS
    @BindView(R.id.ibImage1)
    AppCompatImageView imageView1;
    @BindView(R.id.ibImage2)
    AppCompatImageView imageView2;
    @BindView(R.id.btnPublicar)
    Button btnPostear;


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
                    showMessage("Perdiste conexión a internet");
                }
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
       // mPresenter = new NewPostPresenter(this);
    }

    @Override
    protected void setUp() {
        btnPostear.setOnClickListener(this);
        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        setUp();
    }



    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btnPublicar :
                postear();
                break;
            case R.id.ibImage1 :
                showPicker();
                break;
            case R.id.ibImage2 :
                showPicker();
                break;

        }
    }

    private void postear() {





        //mPresenter.startPost(place);
    }


    @Override
    protected void onDestroy() {
        //mPresenter.onDetachView();
        super.onDestroy();
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }


    @Override
    public void showPicker() {

    }

    
}
