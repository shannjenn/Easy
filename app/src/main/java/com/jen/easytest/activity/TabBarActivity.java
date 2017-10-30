package com.jen.easytest.activity;

import android.os.Bundle;

import com.jen.easy.EasyMouse;
import com.jen.easytest.BaseActivity;
import com.jen.easytest.R;
import com.jen.easyui.tabbar.EasyTabBarTxtImg;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public class TabBarActivity extends BaseActivity {

    @EasyMouse.BIND.ID(R.id.easy_tabbar)
    EasyTabBarTxtImg easy_tabbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbar);
    }

}
