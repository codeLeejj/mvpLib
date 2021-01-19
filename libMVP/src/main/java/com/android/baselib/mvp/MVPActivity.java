package com.android.baselib.mvp;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.LayoutRes;

import com.android.baselib.base.BaseActivity;

public abstract class MVPActivity<P extends BasePresenter> extends BaseActivity implements IView {
    protected P mPresenter;

    protected abstract P initPresenter();

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void loadData();

    @Override
    protected void onCreate(Bundle savedActivityState) {
        super.onCreate(savedActivityState);
        setContentView(getLayoutId());
        if (Build.VERSION.SDK_INT == 26) {
            //android 8.0 的bug
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        /**
         * 底部虚拟按钮位置提高
         */
        initBaseView(this);

        mPresenter = initPresenter();

        initView();


        loadData();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mPresenter != null) {
            mPresenter.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detach();
        }
        dismissWaiting();
    }

    /************************ 通用 定义*************************/
    @Override
    public void showDialog(String msg) {
        super.showDialog(msg);
    }

    @Override
    public void showWaiting(String msg) {
        super.showWaiting(msg);
    }

    public void showWaiting(String msg, int delayTime) {
        super.showWaiting(msg, delayTime);
    }

    @Override
    public void dismissWaiting() {
        super.dismissWaiting();
    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }

    @Override
    public void debugShowToast(String msg) {
        super.debugShowToast(msg);
    }
}