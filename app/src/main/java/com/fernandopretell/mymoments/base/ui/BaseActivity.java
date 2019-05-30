package com.fernandopretell.mymoments.base.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.fernandopretell.mymoments.PeruappsApplication;
import com.fernandopretell.mymoments.R;
import com.fernandopretell.mymoments.base.mvp.MvpView;
import com.fernandopretell.mymoments.utils.CommonUtils;
import com.fernandopretell.mymoments.utils.KeyboardUtils;
import com.fernandopretell.mymoments.utils.ViewUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Fernando Pretell.
 */

public abstract class BaseActivity extends AppCompatActivity implements MvpView{

    public Activity mActivity;
    private ProgressDialog mProgressDialog;
    private Unbinder mUnBinder;

    protected Drawable emptyDrawable = ContextCompat.getDrawable(PeruappsApplication.getAppContext(), R.mipmap.ic_empty);
    protected Drawable errorDrawable = ContextCompat.getDrawable(PeruappsApplication.getAppContext(), R.mipmap.ic_error);


    protected abstract void createPresenter();
    protected abstract void setUp();

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        mActivity = this;
        mUnBinder = ButterKnife.bind(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
    }

    @Override
    public void showLoading(String message) {

        if (mProgressDialog != null && mProgressDialog.isShowing())
            hideLoading();
        mProgressDialog = ViewUtils.showProgressDialog(this, message);
    }

    @Override
    public void hideLoading() {

        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    @Override
    public void showMessage(String message) {
        if (message != null) {
            ViewUtils.showToast(this, Toast.LENGTH_LONG, message);
        } else {
            ViewUtils.showToast(this, Toast.LENGTH_LONG, getString(R.string.error));
        }
    }

    @Override
    public void showMessage(@StringRes int resId) {
        showMessage(getString(resId));
    }

    @Override
    public void onError(String message) {
        if (message != null) {
            ViewUtils.showSnackBar(this, message);
        } else {
            ViewUtils.showSnackBar(this, getString(R.string.error));
        }
    }

    @Override
    public void onError(@StringRes int resId) {
        onError(getString(resId));
    }

    @Override
    public boolean isNetworkConnected() {
        return CommonUtils.isNetworkConnected(getApplicationContext());
    }

    public void hideKeyboard() {
        KeyboardUtils.hideSoftInput(this);
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransitionExit();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransitionEnter();
    }

    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.animator.slide_from_right, R.animator.slide_to_left);
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
    protected void overridePendingTransitionExit() {
        overridePendingTransition(R.animator.slide_from_left, R.animator.slide_to_right);
    }

    protected void next(Bundle bundle, Class<?> activity, boolean destroy) {
        Intent intent= new Intent(this,activity);
        if(bundle!=null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
        if(destroy)
            finish();
    }

}
