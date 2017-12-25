package com.jen.easyui.recyclerview;

import java.util.List;

/**
 * 0为最高级
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */

public abstract class EasyTreeItem<T extends EasyTreeItem> {
    /**
     * 父级
     */
    private T parent;

    /**
     * 持有的子数据(注意：不可以同时为父数据)
     */
    private List<T> children;
    /**
     * 是否展开
     */
    private boolean expand;

    /**
     * 层级,0为最顶层
     */
    private int level;

    public T getParent() {
        return parent;
    }

    public void setParent(T parent) {
        this.parent = parent;
    }

    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
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

    /**
     * 是否是展开状态，
     */
    public boolean isParentExpand() {
        if (parent == null) {
            return true;
        } else if (!parent.isExpand()) {
            return false;
        } else {
            return parent.isParentExpand();
        }
    }
}
