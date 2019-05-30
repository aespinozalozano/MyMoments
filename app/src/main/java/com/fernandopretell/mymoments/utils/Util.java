package com.fernandopretell.mymoments.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.fernandopretell.mymoments.R;

import java.text.DecimalFormat;
import java.util.Calendar;

import retrofit2.Response;

//import com.tivit.task.talma.ui.activity.TicketViewActivity;

public class Util {

    public static final int TOAST = 1;
    public static final int SNACK = 2;
    public static final int PICK_FROM_CAMERA = 3;
    public static final int PHOTO_SEND = 1;

    public static String getClassName(Class cl) {
        Class<?> enclosingClass = cl.getEnclosingClass();
        if (enclosingClass != null) {
            return enclosingClass.getName();
        } else {
            return cl.getName();
        }
    }

    public static String getFileExtension(String path) {
        return path.substring(path.lastIndexOf(".") + 1).toLowerCase().trim();
    }

    public static void showMessage(Context context, String message, int length, int type) {

        if (type == SNACK) {
            final Snackbar snack = Snackbar.make(((Activity) context).findViewById(android.R.id.content), message, length);
            View view = snack.getView();
            TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
            snack.setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            snack.show();
            new CountDownTimer(Configuration.PARAM_SNACK_MORE_DURATION_MAX, Configuration.PARAM_SNACK_MORE_DURATION_MIN) {
                public void onTick(long millisUntilFinished) {snack.show();}
                public void onFinish() {snack.show();}
            }.start();
        } else if (type == TOAST) {
            final Toast toast = Toast.makeText(((Activity) context).findViewById(android.R.id.content).getContext(), message, length);
            toast.show();
            new CountDownTimer(Configuration.PARAM_TOAST_MORE_DURATION_MAX, Configuration.PARAM_TOAST_MORE_DURATION_MIN) {
                public void onTick(long millisUntilFinished) {toast.show();}
                public void onFinish() {toast.show();}
            }.start();
        }
    }

    public static ProgressDialog showProgressDialog(Context context, String mensaje) {
        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.dlg_progress, null);
        ((TextView) view.findViewById(R.id.txtMensaje)).setText(mensaje != null ? mensaje : context.getResources().getString(R.string.progressdialog_cargando));

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        progressDialog.setContentView(view);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);
        return progressDialog;
    }

    public static ProgressDialog showProgressDialogSimple(Context context, String mensaje) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(mensaje);
        progressDialog.show();
        progressDialog.setCancelable(false);
        return progressDialog;
    }

    public static ProgressDialog showProgressDialogBar(Context context, String mensaje) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage(mensaje);
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        return progressDialog;
    }

    public static void showAlert(Context context, String message, String title) {
        AlertDialog.Builder bld = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        bld.setMessage(message);
        if(title!=null){
            bld.setTitle(title);
        }
        bld.setPositiveButton("OK",null);
        bld.create().show();
    }

    public static void openKeyboard(EditText txt, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(txt, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void closeKeyboard(EditText txt, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(txt.getWindowToken(), 0);
    }

    static public boolean isOnline(Context context) {
        if(context == null) return true;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public static Calendar calculateDateFromTimestamp (long timestamp){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp*1000);
        return cal;
    }

    public static String getEmailError(String value, Context context) {
        if (value.length() == 0) {
            return context.getString(R.string.error_enter_email);
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            return context.getString(R.string.error_invalid_email);
        }
        return null;
    }

    public static String getSizeString(long size){
        String sizeString = "";
        DecimalFormat decf = new DecimalFormat("###.##");

        float KB = 1024;
        float MB = KB * 1024;
        float GB = MB * 1024;
        float TB = GB * 1024;

        if (size < KB){
            sizeString = size + " B";
        }
        else if (size < MB){
            sizeString = decf.format(size/KB) + " KB";
        }
        else if (size < GB){
            sizeString = decf.format(size/MB) + " MB";
        }
        else if (size < TB){
            sizeString = decf.format(size/GB) + " GB";
        }
        else{
            sizeString = decf.format(size/TB) + " TB";
        }

        return sizeString;
    }

    public static void showMessageError(Context context, Response<?> response) {
        int code = response.code();
        if (code == 401) {
            showMessage(context, "Code:" + code + " Unauthenticated", Snackbar.LENGTH_INDEFINITE, SNACK);
        } else if (code >= 400 && code < 500) {
            showMessage(context, "Code:" + code + " Client Error", Snackbar.LENGTH_INDEFINITE, SNACK);
        } else if (code >= 500 && code < 600) {
            showMessage(context, "Code:" + code + " Server Error", Snackbar.LENGTH_INDEFINITE, SNACK);
        } else {
            showMessage(context, new RuntimeException("Unexpected response " + response).getMessage(), Snackbar.LENGTH_INDEFINITE, SNACK);
        }
    }

}
