package com.fernandopretell.mymoments.base.ui;

import com.fernandopretell.mymoments.PeruappsApplication;
import com.fernandopretell.mymoments.base.mvp.MvpInteractor;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Fernando Pretell.
 */

public abstract class BaseInteractor implements MvpInteractor {

    protected CompositeDisposable compositeDisposable;
    private DisposableObserver subscriber;

    public BaseInteractor() {
        compositeDisposable = new CompositeDisposable();
    }


    @Override
    public void onUnsubscribe() {
        if(compositeDisposable != null && compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
    }

    protected void addSubscribe(Observable observable, DisposableObserver subscriber) {
        this.subscriber = subscriber;
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add((Disposable) observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(subscriber));
    }

}
