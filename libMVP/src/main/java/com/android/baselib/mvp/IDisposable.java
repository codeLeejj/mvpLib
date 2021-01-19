package com.android.baselib.mvp;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author Lee
 * @date 2019/1/4 9:47
 * @Description
 */
public interface IDisposable {
    CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    void unSubscribe();

    void addSubscribe(Disposable subscription);
}