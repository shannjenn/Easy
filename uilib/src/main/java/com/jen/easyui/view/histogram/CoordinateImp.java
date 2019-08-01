package com.jen.easyui.view.histogram;

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
