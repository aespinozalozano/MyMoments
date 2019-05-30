package com.fernandopretell.mymoments.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fernandopretell.mymoments.R;


/**
 * Created by Fernando Pretell on 27/08/2017.
 */

public class ViewUtils {

    public static ProgressDialog showProgressDialog(Context context, @Nullable String message) {

        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.dlg_progress, null);

        ((TextView) view.findViewById(R.id.txtMensaje)).setText(message != null ? message : context.getResources().getString(R.string.progressdialog_cargando));

        ProgressDialog progressDialog = new ProgressDialog(context);

        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(view);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
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

    public static void showSnackBar(Context context, String message) {
        Snackbar snackbar = Snackbar.make(((Activity) context).findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        TextView textView = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setTextColor(ContextCompat.getColor(context, R.color.white));
        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        snackbar.show();
    }

    public static void showToast(Context context, int duration, String message) {
        Toast toast = Toast.makeText(((Activity) context).findViewById(android.R.id.content).getContext(), message, duration);
        toast.show();
    }

    public static void showAlert(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        if(title!=null){
            builder.setTitle(title);
        }
        builder.setCancelable(false);
        builder.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
