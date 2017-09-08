package com.jen.easytest.demo;

import com.jen.easy.http.BaseParamRequest;
import com.jen.easytest.R;
import com.jen.easyui.listview.ItemSource;

/**
 * 创建人：ShannJenn
 * 时间：2017/8/20.
 */

public class Student extends BaseParamRequest {

    @ItemSource(text = R.id.tv_1)
    public String id;

    @ItemSource(text = R.id.tv_2)
    public String name;

    @ItemSource(isViewType = true)
    public String viewType;

}
