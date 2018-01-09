package com.jen.easytest.fragment;

import com.jen.easytest.R;
import com.jen.easytest.base.BaseFragment;

/**
 * Created by Administrator on 2017/10/31.
 */

public class EmptyFragmentRed extends BaseFragment {


    @Override
    protected int inflateLayout() {
        return R.layout.fragment_empty_red;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void loadDataAfterView() {

    }

    @Override
    public void httpSuccess(int flagCode, String flag, Object response) {

    }

    @Override
    public void httpFail(int flagCode, String flag, String msg) {

    }
}
