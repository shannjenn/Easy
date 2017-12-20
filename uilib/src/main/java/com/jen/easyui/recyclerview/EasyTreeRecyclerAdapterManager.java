package com.jen.easyui.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jen.easy.log.EasyUILog;

import java.util.List;

/**
 * 树形模式
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */

abstract class EasyTreeRecyclerAdapterManager<T extends EasyTreeItem> extends EasyRecyclerBaseAdapterManager<T> {
    private final String TAG = EasyTreeRecyclerAdapterManager.class.getSimpleName() + " ";
//    protected Context context;
//    private boolean showTopLevel = true;//展示的最高等级
//    protected T tree;
//    protected List<T> expadData = new ArrayList<>();
//    private EasyAdapterClickEvent easyAdapterClickEvent;

    /**
     * @param context
     * @param data
     */
    protected EasyTreeRecyclerAdapterManager(Context context, List<T> data) {
        super(context, data);
//        this.context = context;
//        this.tree = tree;
//        showTopLevel = showTopLevel();
//        initExpadData();
    }

    /*@Override
    public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
//        initExpadData();
        super.registerAdapterDataObserver(observer);
    }*/

    /**
     * 展示最高等级等级(默认最高等级0)
     *
     * @return
     */
//    protected abstract boolean showTopLevel();

    /*private void initExpadData() {
        if (tree == null) {
            EasyUILog.e(TAG + "tree is null");
            return;
        }
        if (showTopLevel) {
            expadData.add(tree);
        }
        expadData.addAll(getAllExpandItem(tree));
    }*/

    /*private List<T> getAllExpandItem(T item) {
        List<T> list = new ArrayList<>();
        List<T> chilren = item.getChildren();
        if (chilren == null || !item.isExpand()) {
            return list;
        } else {
            list.addAll(chilren);
            int size = chilren.size();
            for (int i = 0; i < size; i++) {
                list.addAll(getAllExpandItem(chilren.get(i)));
            }
            return list;
        }
    }*/

/*    @Override
    public int getItemCount() {
        int count = 0;
        if (expadData == null) {
            return count;
        }
        count = expadData.size();
        return count;
    }*/
    @Override
    public int getItemViewType(int position) {
        return getViewType(position);
    }

    /**
     * 获取布局类型
     *
     * @param position 下标
     * @return
     */
    protected abstract int getViewType(int position);

    @Override
    public EasyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int[] layouts = onBindLayout();
        if (layouts == null) {
            EasyUILog.e("布局为空");
            return null;
        }
        if (viewType < 0 || layouts.length > viewType) {
            EasyUILog.e("viewType：" + viewType + "错误");
            return null;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(layouts[viewType], parent, false);
        if (view == null) {
            EasyUILog.e("找不到该值对应item布局R.layout.id：" + layouts[viewType]);
            return null;
        }
        return new EasyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        EasyTreeItem data = mData.get(position);
        EasyTreeItem parent = data.getParent();
        if (parent != null && parent.isExpand()) {//展开显示
            super.onBindViewHolder(holder, position);
        }
    }

    protected abstract int[] onBindLayout();

    /*@Override
    public int getItemViewType(int position) {
        EasyTreeItem item = expadData.get(position);
        if (item != null) {
            return item.getLevel();
        } else {
            return -1;
        }
    }*/

    /*@Override
    public EasyHloderManager onCreateViewHolder(ViewGroup parent, int viewType) {
        int[] layouts = onBindLevelLayout();
        if (layouts == null) {
            EasyUILog.e("布局为空");
            return null;
        }
        if (viewType < 0 || layouts.length > viewType) {
            EasyUILog.e("viewType：" + viewType + "错误");
            return null;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(layouts[viewType], parent, false);
        if (view == null) {
            EasyUILog.e("找不到该值对应item布局R.layout.id：" + layouts[viewType]);
            return null;
        }
        float size = itemSpaceSize();
        view.setPadding(EasyDensityUtil.dip2px(context, size), 0, 0, 0);
        EasyHloderManager hloderImp = onCreateEasyHolder(view);
        hloderImp.setAdapterClickEvent(easyAdapterClickEvent);
        return hloderImp;
    }*/

    /**
     * @return 绑定布局，第一个为第一层，第二个为第二层
     */
//    protected abstract int[] onBindLevelLayout();

    /**
     * @return 每一层缩进大小
     */
    protected abstract float itemSpaceSize();

}