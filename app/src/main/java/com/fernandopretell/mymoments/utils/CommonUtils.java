package com.fernandopretell.mymoments.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.fernandopretell.mymoments.PeruappsApplication;
import com.fernandopretell.mymoments.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Fernando Pretell on 29/08/2017.
 */

public class CommonUtils {

    public static final SimpleDateFormat DATETIME_FORMAT_DEFAULT = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
    public static final SimpleDateFormat DATE_FORMAT_DEFAULT = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat TIME_FORMAT_DEFAULT = new SimpleDateFormat("hh:mm a");

    public static final SimpleDateFormat DATETIME_FORMAT_DEFAULT24 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    //public static final SimpleDateFormat INTEGER_FORMAT_DEFAULT = new SimpleDateFormat("yyyyMMddHHmmss");
    public static final SimpleDateFormat INTEGER_FORMAT_DEFAULT = new SimpleDateFormat("HHmmss");

    private CommonUtils() {
        // This utility class is not publicly instantiable
    }

    public static String getClassName(Class cl) {
        Class<?> enclosingClass = cl.getEnclosingClass();
        if (enclosingClass != null) {
            return enclosingClass.getName();
        } else {
            return cl.getName();
        }
    }

    public static String dateConvertLongToDateTime(Long date) {
        Date itemDate = new Date(date * 1000);
        String text = DATETIME_FORMAT_DEFAULT.format(itemDate);
        return text;
    }

    public static String dateConvertLongToDate(Long date) {
        Date itemDate = new Date(date * 1000);
        String text = DATE_FORMAT_DEFAULT.format(itemDate);
        return text;
    }

    public static String dateConvertLongToTime(Long date) {
        Date itemDate = new Date(date * 1000);
        String text = TIME_FORMAT_DEFAULT.format(itemDate);
        return text;
    }

    public static String dateConvertString(Date date) {
        Date itemDate = date;
        String text = DATETIME_FORMAT_DEFAULT24.format(itemDate);
        return text;
    }

    public static int dateConvertInteger(Date date) {
        Date itemDate = date;
        String text;
        try {
            text = INTEGER_FORMAT_DEFAULT.format(itemDate);
        }catch(Exception e){e.printStackTrace(); text = "0";}
        return Integer.parseInt(text);
    }

    public static Calendar dateCurrentDateTime(){
        Calendar cal = Calendar.getInstance();
        return cal;
    }

    public static Long dateConvertDateToLong(Date time) {
        return time.getTime() / 1000;
    }

    public static boolean isEmailValid(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String getFileExtension(String path) {
        return path.substring(path.lastIndexOf(".") + 1).toLowerCase().trim();
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static boolean isInternetOnline(Context context) {
        if(!isNetworkConnected(PeruappsApplication.getAppContext())) {
            ViewUtils.showToast(context, Toast.LENGTH_LONG, PeruappsApplication.getAppContext().getResources().getString(R.string.network_not_connection));
            return false;
        }
        return true;
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

}

