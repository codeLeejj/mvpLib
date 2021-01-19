package com.lee.mvpsimple;

import com.android.baselib.mvp.IPresenter;
import com.android.baselib.mvp.IView;

/**
 * @author lee
 * @title: MainContract
 * @description: TODO
 * @date 2021/1/19  14:07
 */
public interface MainContract {
    interface View extends IView {
    }

    interface Presenter extends IPresenter<View> {
    }
}
