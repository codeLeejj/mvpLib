package com.android.baselib.base;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.android.baselib.BuildConfig;
import com.android.baselib.mvp.IView;

import me.jessyan.autosize.internal.CustomAdapt;

public class BaseFragment extends Fragment implements IView, CustomAdapt {

    @Override
    public boolean isBaseOnWidth() {
        return true;
    }

    @Override
    public float getSizeInDp() {
        return Integer.parseInt(BuildConfig.designWidth);
    }

    private BaseView mBaseView;

    protected void initBaseView(Activity activity) {
        mBaseView = new BaseView(activity);
    }

    @Override
    public void onDestroy() {
        dismissWaiting();
        dismissDialog();
        mBaseView = null;
        super.onDestroy();
    }

    @Override
    public void dismissWaiting() {
        if (mBaseView != null)
            mBaseView.dismissWaiting();
    }

    @Override
    public void dismissDialog() {
        if (mBaseView != null)
            mBaseView.dismissDialog();
    }

    @Override
    public void showDialog(String msg) {
        if (mBaseView != null) mBaseView.showDialog(msg);
    }

    @Override
    public void showWaiting(String msg) {
        if (mBaseView != null) mBaseView.showWaiting(msg);
    }

    @Override
    public void showWaiting(String msg, int delayTime) {
        if (mBaseView != null) mBaseView.showWaiting(msg, delayTime);
    }

    @Override
    public void showToast(String msg) {
        if (mBaseView != null) mBaseView.showToast(msg);
    }

    @Override
    public void debugShowToast(String msg) {
        if (mBaseView != null) mBaseView.debugShowToast(msg);
    }

    @Override
    public void hintKeyboard() {
        if (mBaseView != null) mBaseView.hintKeyboard();
    }

    public void startActivity(Class clazz) {
        if (mBaseView != null) mBaseView.startActivity(clazz);
    }
    /**
     * 扩展函数
     */

}