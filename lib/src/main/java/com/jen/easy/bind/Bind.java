package com.jen.easy.bind;

import android.app.Activity;
import android.view.View;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：控件ID绑定类（结合注释@EasyMouse.BIND使用）
 */
public class Bind extends BindManager {
    /**
     * 绑定
     *
     * @param activity
     */
    @Override
    public void bind(Activity activity) {
        super.bind(activity);
    }

    /**
     * 注入
     *
     * @param obj    注入对象(如：类)
     * @param parent 布局
     */
    @Override
    public void inject(Object obj, View parent) {
        super.inject(obj, parent);
    }

    /**
     * 解除绑定
     *
     * @param activity
     */
    @Override
    public void unbind(Activity activity) {
        super.unbind(activity);
    }
}
