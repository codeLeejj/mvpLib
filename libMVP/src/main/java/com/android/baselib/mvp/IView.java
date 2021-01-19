package com.android.baselib.mvp;

public interface IView {
    void dismissWaiting();

    void showDialog(String msg);

    void dismissDialog();

    void showWaiting(String msg);

    void showWaiting(String msg, int delayTime);

    void showToast(String msg);

    void debugShowToast(String msg);

    void hintKeyboard();

    void startActivity(Class clazz);
}
