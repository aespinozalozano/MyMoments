package com.fernandopretell.mymoments.feature.newpost;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fernandopretell.mymoments.R;
import com.fernandopretell.mymoments.base.ui.BaseActivity;
import com.fernandopretell.mymoments.data.remote.model.Place;
import com.fernandopretell.mymoments.feature.main.MainActivity;
import com.fernandopretell.mymoments.feature.main.MainContract;
import com.fernandopretell.mymoments.feature.main.MainPresenter;
import com.fernandopretell.mymoments.utils.Configuration;
import com.fernandopretell.mymoments.utils.PostsAdapter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

import static com.fernandopretell.mymoments.utils.Util.PHOTO_SEND;
import static com.fernandopretell.mymoments.utils.Util.PICK_FROM_CAMERA;

public class NewPostActivity extends BaseActivity implements NewPostContract.NewPostView, View.OnClickListener {

    private NewPostContract.NewPostPresenter mPresenter;
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
        mPresenter = new NewPostPresenter(this);
    }

    @Override
    protected void setUp() {
        btnPostear.setOnClickListener(this);
        showPicker();
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

        }
    }

    private void postear() {






        mPresenter.startPost(place);
    }


    @Override
    protected void onDestroy() {
        mPresenter.onDetachView();
        super.onDestroy();
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }


    @Override
    public void showPicker() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PHOTO_SEND && resultCode == RESULT_OK){



            if (data != null) {
                u = data.getData();
            }
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), u);
                bmp = scaleDown(bitmap, 1000f, true);
                Glide.with(getApplicationContext()).load(bmp).into(imageView1);
                btnPostear.setEnabled(true);
            } catch (IOException e) {
                e.printStackTrace();
            }




        }else if(requestCode == PICK_FROM_CAMERA && resultCode == RESULT_OK){

            if (data != null) {
                u = data.getData();
            }
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), u);
                bmp = scaleDown(bitmap, 1000f, true);
                Glide.with(getApplicationContext()).load(bmp).into(imageView1);
                btnPostear.setEnabled(true);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

    private void selectImage() {
        final CharSequence[] items = { "Tomar foto", "Elegir en librería",
                "Cancelar" };

        AlertDialog.Builder builder = new AlertDialog.Builder(NewPostActivity.this);
        builder.setTitle("Agregar imagen!");
        builder.setItems(items, (dialog, item) -> {

            StrictMode.VmPolicy.Builder builder1 = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder1.build());

            if (items[item].equals("Tomar foto")) {

                String nombreFoto;//esta porcion de codigo establece un nombre a la imagen tomada basandose en la fecha y hora actual
                Date date = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("SSS.ss-mm-hh-dd-MM-yyyy", Locale.getDefault());
                nombreFoto = simpleDateFormat.format(date);

                Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = new File(Environment.getExternalStorageDirectory(), nombreFoto);
                outPutfileUri = Uri.fromFile(file);
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outPutfileUri);
                startActivityForResult(captureIntent, PICK_FROM_CAMERA);
                dialog.dismiss();


            } else if (items[item].equals("Elegir en librería")) {

                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/jpeg");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
                startActivityForResult(Intent.createChooser(i,"Selecciona una foto"),PHOTO_SEND);
                dialog.dismiss();

            } else if (items[item].equals("Cancelar")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }
}
