package com.jen.easyui.view.histogram;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;

import com.jen.easyui.util.EasyDensityUtil;

/**
 * 这是一个示例
 * 作者：ShannJenn
 * 时间：2019/5/20.
 */
public class CoordinateImp<T extends Float> extends Coordinate<T> {

    @Override
    public String xText(T t) {
        return "2019-05-21";
    }

    @Override
    public float xNextValue() {
        return 25f;
    }

    @Override
    public int ySpaceSize() {
        return 5;
    }
}
