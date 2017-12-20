package com.jen.easyui.recyclerview;

import java.util.List;

/**
 * 0为最高级
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */

public abstract class EasyTreeItem {
    /**
     * 父级
     */
    private EasyTreeItem parent;

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

    public EasyTreeItem getParent() {
        return parent;
    }

    public void setParent(EasyTreeItem parent) {
        this.parent = parent;
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
