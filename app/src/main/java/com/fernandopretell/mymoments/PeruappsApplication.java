package com.fernandopretell.mymoments;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import timber.log.Timber;

public class PeruappsApplication extends MultiDexApplication {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        mContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return mContext;
    }

}
