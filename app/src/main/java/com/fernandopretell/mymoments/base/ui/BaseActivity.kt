package com.fernandopretell.mymoments.base.ui

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

import com.fernandopretell.mymoments.PeruappsApplication
import com.fernandopretell.mymoments.R
import com.fernandopretell.mymoments.base.mvp.MvpView
import com.fernandopretell.mymoments.utils.CommonUtils
import com.fernandopretell.mymoments.utils.KeyboardUtils
import com.fernandopretell.mymoments.utils.ViewUtils

import butterknife.ButterKnife
import butterknife.Unbinder

/**
 * Created by Fernando Pretell.
 */

abstract class BaseActivity : AppCompatActivity(), MvpView {

    var mActivity: Activity? = null
    private var mProgressDialog: ProgressDialog? = null
    private var mUnBinder: Unbinder? = null

    protected var emptyDrawable = ContextCompat.getDrawable(PeruappsApplication.appContext, R.mipmap.ic_empty)
    protected var errorDrawable = ContextCompat.getDrawable(PeruappsApplication.appContext, R.mipmap.ic_error)


    protected abstract fun createPresenter()
    protected abstract fun setUp()

    override fun setContentView(@LayoutRes layoutResID: Int) {
        super.setContentView(layoutResID)
        mActivity = this
        mUnBinder = ButterKnife.bind(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createPresenter()
    }

    override fun onDestroy() {
        super.onDestroy()
        mUnBinder!!.unbind()
    }

    override fun showLoading(message: String) {

        if (mProgressDialog != null && mProgressDialog!!.isShowing)
            hideLoading()
        mProgressDialog = ViewUtils.showProgressDialog(this, message)
    }

    override fun hideLoading() {

        if (mProgressDialog != null) {
            mProgressDialog!!.dismiss()
            mProgressDialog = null
        }
    }

    override fun showMessage(message: String?) {
        if (message != null) {
            ViewUtils.showToast(this, Toast.LENGTH_LONG, message)
        } else {
            ViewUtils.showToast(this, Toast.LENGTH_LONG, getString(R.string.error))
        }
    }

    override fun showMessage(@StringRes resId: Int) {
        showMessage(getString(resId))
    }

    override fun onError(message: String?) {
        if (message != null) {
            ViewUtils.showSnackBar(this, message)
        } else {
            ViewUtils.showSnackBar(this, getString(R.string.error))
        }
    }

    override fun onError(@StringRes resId: Int) {
        onError(getString(resId))
    }

    override fun isNetworkConnected(): Boolean {
        return CommonUtils.isNetworkConnected(applicationContext)
    }

    override fun hideKeyboard() {
        KeyboardUtils.hideSoftInput(this)
    }


    override fun finish() {
        super.finish()
        overridePendingTransitionExit()
    }

    override fun startActivity(intent: Intent) {
        super.startActivity(intent)
        overridePendingTransitionEnter()
    }

    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    protected fun overridePendingTransitionEnter() {
        overridePendingTransition(R.animator.slide_from_right, R.animator.slide_to_left)
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
    protected fun overridePendingTransitionExit() {
        overridePendingTransition(R.animator.slide_from_left, R.animator.slide_to_right)
    }

    protected fun next(bundle: Bundle?, activity: Class<*>, destroy: Boolean) {
        val intent = Intent(this, activity)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
        if (destroy)
            finish()
    }

}
