package com.android.baselib.mvp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.baselib.base.BaseFragment;

public abstract class MVPFragment<P extends IPresenter> extends BaseFragment implements IView {
    /************************ MVP 定义*************************/
    protected P mPresenter;

    protected View mView;

    protected abstract P initPresenter();

    @LayoutRes
    protected abstract int layoutId();

    protected abstract void initView();

    protected abstract void initAction();

    protected abstract void initData();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        initBaseView(getActivity());
        mPresenter = initPresenter();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBaseView(getActivity());
        mPresenter = initPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(layoutId(), container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        ButterKnife.bind(this, mView);
        initView();
        initAction();
        initData();
    }

    /************************ 通用 定义*************************/
    @Override
    public void showDialog(String msg) {
        super.showDialog(msg);
    }

    @Override
    public void dismissWaiting() {
        super.dismissWaiting();
    }

    @Override
    public void showWaiting(String msg) {
        super.showWaiting(msg);
    }

    @Override
    public void showWaiting(String msg, int delayTime) {
        super.showWaiting(msg, delayTime);
    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }

    @Override
    public void debugShowToast(String msg) {
        super.showToast(msg);
    }

}