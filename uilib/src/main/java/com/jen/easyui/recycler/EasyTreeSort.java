package com.jen.easyui.recycler;

import java.util.ArrayList;
import java.util.List;

/**
 * EasyTree数据树形重构排序，0为最高级
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */

public abstract class EasyTreeSort {

    /**
     * 树形转到平铺(要先设置好children值：parent.setChildren(children))
     *
     * @param topParent 最高层（没有传空）
     * @param startLev  开始层级如：0，1
     * @return
     */
    public static List<EasyTreeItem> treeToTile(EasyTreeItem topParent, int startLev) {
        List<EasyTreeItem> list = new ArrayList<>();
        if (topParent == null) {
            return list;
        }
        List<EasyTreeItem> trees = topParent.getChildren();
        return treeToTile(topParent, trees, startLev);
    }

    /**
     * 树形转到平铺(要先设置好children值：parent.setChildren(children))
     *
     * @param trees
     * @param startLev 开始层级如：0，1
     * @return
     */
    public static List<EasyTreeItem> treeToTile(List<EasyTreeItem> trees, int startLev) {
        return treeToTile(null, trees, startLev);
    }

    /**
     * 树形转到平铺(要先设置好children值：parent.setChildren(children))
     *
     * @param topParent 最高层（没有传空）
     * @param trees     要先设置好children值：parent.setChildren(children)
     * @param startLev  开始层级如：0，1
     * @return
     */
    public static List<EasyTreeItem> treeToTile(EasyTreeItem topParent, List<EasyTreeItem> trees, int startLev) {
        List<EasyTreeItem> list = new ArrayList<>();
        if (topParent != null) {
            topParent.setChildren(trees);
            topParent.setLevel(startLev);
            list.add(topParent);
        }
        if (trees == null) {
            return list;
        }
        for (int i = 0; i < trees.size(); i++) {
            EasyTreeItem parent = trees.get(i);
            parent.setParent(topParent);
            parent.setLevel(topParent == null ? startLev : topParent.getLevel() + 1);
            if (parent == null) {
                continue;
            }
            list.add(parent);
            List<EasyTreeItem> children = parent.getChildren();
            int childrenSize = children.size();
            if (children == null || childrenSize == 0) {
                continue;
            }
            parent.setChildren(children);
            for (int j = 0; j < childrenSize; j++) {
                EasyTreeItem childrenJ = children.get(j);
                childrenJ.setParent(parent);
                childrenJ.setLevel(parent.getLevel() + 1);
                list.add(childrenJ);

                List<EasyTreeItem> children2 = childrenJ.getChildren();
                int children2Size = children2.size();
                if (children2 != null || children2Size > 0) {
                    childrenJ.setChildren(children2);
                    for (int k = 0; k < children2Size; k++) {
                        children2.get(k).setParent(childrenJ);
                    }
                    List<EasyTreeItem> children2Sort = treeToTile(null, children2, childrenJ.getLevel() + 1);
                    list.addAll(children2Sort);
                }
            }
        }
        return list;
    }
}
