package com.lee.mvpsimple;

import com.android.baselib.mvp.BasePresenter;

/**
 * @author lee
 * @title: MainPresenter
 * @description: TODO
 * @date 2021/1/19  14:06
 */
public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    public MainPresenter(MainContract.View view) {
        super(view);
    }
}
