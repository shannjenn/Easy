package com.jen.easyui.recyclerview;

import java.util.List;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */

public abstract class EasyTreeItem<D> {
    /**
     * 当前item的数据
     */
    private D data;

    /**
     * 持有的子数据(注意：不可以同时为父数据)
     */
    private List<EasyTreeItem> children;
    /**
     * 是否展开
     */
    private boolean expand;

    /**
     * 层级,0为最顶层
     */
    private int level;

    public EasyTreeItem(D data) {
        this.data = data;
    }

    /**
     * 是否持有子数据
     *
     * @return
     */
    public boolean isParent() {
        return children != null && children.size() > 0;
    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }

    public List<EasyTreeItem> getChildren() {
        return children;
    }

    public void setChildren(List<EasyTreeItem> children) {
        this.children = children;
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
