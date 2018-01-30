package com.jen.easytest.fragment;

import com.jen.easytest.R;
import com.jen.easyui.base.EasyFragment;

/**
 * Created by Administrator on 2017/10/31.
 */

public class EmptyFragmentBlue extends EasyFragment {


    @Override
    protected int inflateLayout() {
        return R.layout.fragment_empty_blue;
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
