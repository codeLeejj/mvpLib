package com.android.baselib.mvvp;

import android.content.Context;

import androidx.annotation.NonNull;

import com.android.baselib.base.BaseFragment;
import com.android.baselib.mvp.IPresenter;
import com.android.baselib.mvp.IView;

public abstract class MVVPFragment<P extends IPresenter> extends BaseFragment {
    /************************ MVP 定义*************************/
    protected P mPresenter;

    protected abstract P initPresenter();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        initBaseView(getActivity());
        mPresenter = initPresenter();
    }
}

