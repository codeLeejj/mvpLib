package com.android.baselib.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AlertDialog;

import com.android.baselib.constants.MyBuildConfig;
import com.android.baselib.mvp.IView;
import com.android.baselib.utils.DialogUtil;
import com.android.libview.utils.ToastTool;
import com.android.libview.view.TimerLoadingDialog;

import java.util.ArrayList;
import java.util.List;

public class BaseView implements IView {
    Activity mActivity;
    List<Dialog> dialogs = new ArrayList<>();

    public BaseView(Activity mActivity) {
        this.mActivity = mActivity;
    }

    /************************ 通用 定义*************************/

    private final long MIN_TIME = 500;//对话框最短显示时间

    private long lastShowTime = 0L;
    private long lastDismissTime = 0L;
    /**
     * 执行关闭时间
     */
    private long execDismissTime = 0L;
    /**
     * 这包装 Dialog 工具类
     */
    TimerLoadingDialog dialog;

    /**
     * 关闭对话框(同时避免一闪而过)
     */
    @Override
    synchronized public void dismissWaiting() {
        lastDismissTime = System.currentTimeMillis();
        long current = lastDismissTime;
        long interval = current - lastShowTime;
        if (MIN_TIME <= interval) {
            if (dialog == null) return;
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            return;
        }
        execDismissTime = lastDismissTime;
        new Handler().postDelayed(() -> {
            if (dialog == null) return;
            if (dialog.isShowing()) {
                //在延时期间重新弹出对话框就不关闭
                if (execDismissTime >= lastShowTime) {
                    dialog.dismiss();
                }
            }
        }, MIN_TIME - interval);

    }

    @Override
    public void showDialog(String msg) {
        AlertDialog dialog = new DialogUtil(mActivity).showAffirm("温馨提示", msg, null);
        dialogs.add(dialog);
    }

    @Override
    public void dismissDialog() {
        for (Dialog dialog : dialogs) {
            if (dialog != null && dialog.isShowing()) {
                try {
                    dialog.dismiss();
                } catch (RuntimeException e) {
                }
            }
        }
    }

    @Override
    public void showWaiting(String msg) {
        showWaiting(msg, 0);
    }

    @Override
    public void showWaiting(String msg, int delayTime) {
        if (dialog == null) {
            dialog = new TimerLoadingDialog(mActivity);
        } else {
            dialog.dismiss();
        }
        dialog.showLoadingDialog(false,
                TextUtils.isEmpty(msg) ? "正在加载…" : msg,
                delayTime, timeDialog -> dialog.dismiss());
        lastShowTime = System.currentTimeMillis();
    }

    @Override
    public void showToast(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        ToastTool.showShort(mActivity, msg);
    }

    @Override
    public void debugShowToast(String msg) {
        if (MyBuildConfig.DEBUG) {
            showToast(msg);
        }
    }

    @Override
    public void hintKeyboard() {
        InputMethodManager manager = ((InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE));
        if (manager != null)
            manager.hideSoftInputFromWindow(mActivity.getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void startActivity(Class clazz) {
        Intent intent = new Intent(mActivity, clazz);
        if (intent.resolveActivity(mActivity.getPackageManager()) != null) {
            mActivity.startActivity(intent);
        }
    }
}