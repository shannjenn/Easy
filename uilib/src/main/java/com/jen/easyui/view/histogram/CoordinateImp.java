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
        return null;
    }

    @Override
    public float yUnitValue() {
        return 0;
    }

    @Override
    public int xUnitSize() {
        return 0;
    }
}
