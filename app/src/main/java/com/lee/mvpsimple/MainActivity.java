package com.lee.mvpsimple;

import com.android.baselib.mvp.MVPActivity;

public class MainActivity extends MVPActivity<MainPresenter> implements MainContract.View {

    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void loadData() {

    }
}