package com.jen.easytest.fragment;

import com.jen.easytest.R;
import com.jen.easyui.base.EasyFragment;

/**
 * Created by Administrator on 2017/10/31.
 */

public class EmptyFragmentRed extends EasyFragment {


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
    public void success(int flagCode, String flag, Object response) {

    }

    @Override
    public void fail(int flagCode, String flag, String msg) {

    }
}
