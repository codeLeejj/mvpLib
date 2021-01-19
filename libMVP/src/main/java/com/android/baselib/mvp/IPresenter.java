package com.android.baselib.mvp;
public interface IPresenter<V extends IView> extends IDisposable {
    //进行和View层的绑定  activity
    void attach(V view);

    void stop();

    void detach();
}
