package com.jen.easy.bind.imp;

import android.app.Activity;
import android.view.View;

/**
 * Created by Jen on 2017/8/9.
 */

public interface BindImp {
    /**
     * 绑定
     *
     * @param activity
     */
    void bind(Activity activity);

    /**
     * 注入
     *
     * @param obj
     * @param parent
     */
    void inject(Object obj, View parent);

    /**
     * 解除绑定
     *
     * @param activity
     */
    void unbind(Activity activity);
}
