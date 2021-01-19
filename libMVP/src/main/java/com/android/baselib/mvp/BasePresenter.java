package com.android.baselib.mvp;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import okhttp3.Call;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class BasePresenter<V extends IView> implements IPresenter<V> {
    protected Activity mContext;
    //双层保证 不会出现内存泄漏
    //1、软引用  在内存不足时会回收   如果Activity意外终止 使用该方式能保证
    private WeakReference<V> mRefView;
    //2、通过手动的方式直接释放  activity销毁时直接回收

    public BasePresenter(V view) {
        attach(view);
    }

    public V getView() {
        if (mRefView == null) {
            return null;
        }
        return mRefView.get();
    }

    @Override
    public void attach(V view) {
        if (view instanceof Activity) {
            mContext = (Activity) view;
        } else if (view instanceof Fragment) {
            mContext = ((Fragment) view).getActivity();
        }
        mRefView = new WeakReference<>(view);
    }

    @Override
    public void stop() {
        if (calls != null) {
            for (Call call : calls) {
                if (call != null && !call.isCanceled()) {
                }
            }
        }
    }

    @Override
    public void detach() {
        //先中断异步操作,再清除view
        if (calls != null) {
            for (Call call : calls) {
                if (call != null && !call.isCanceled()) {
                    call.cancel();
                }
            }
        }
        calls = null;
        mRefView.clear();
    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void addSubscribe(Disposable subscription) {
        mCompositeDisposable.add(subscription);
    }

    public void showToast(String msg) {
        getView().showToast(msg);
    }

    List<Call> calls = new ArrayList<>();

    protected void addCall(Call post) {
        if (post == null) return;
        if (calls != null) {
            calls.add(post);
        }
    }
}