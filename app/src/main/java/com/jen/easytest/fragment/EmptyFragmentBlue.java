package com.jen.easytest.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jen.easytest.R;
import easybase.EasyFragment;

/**
 * Created by Administrator on 2017/10/31.
 */

public class EmptyFragmentBlue extends EasyFragment {


    @Override
    protected int bindLayout() {
        return R.layout.fragment_empty_blue;
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {

    }
}
