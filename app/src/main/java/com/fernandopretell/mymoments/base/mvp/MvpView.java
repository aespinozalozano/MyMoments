package com.fernandopretell.mymoments.base.mvp;

import android.support.annotation.StringRes;

/**
 * Created by Fernando Pretell.
 */

public interface MvpView {

    void showLoading(String message);

    void hideLoading();

    void showMessage(String message);

    void showMessage(@StringRes int resId);

    void onError(String message);

    void onError(@StringRes int resId);

    boolean isNetworkConnected();

    void hideKeyboard();
}
