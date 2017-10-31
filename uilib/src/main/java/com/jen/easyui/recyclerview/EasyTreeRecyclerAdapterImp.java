package com.jen.easyui.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jen.easy.log.EasyUILog;
import com.jen.easyui.util.EasyDensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */

abstract class EasyTreeRecyclerAdapterImp<T extends EasyTreeItem> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String TAG = EasyTreeRecyclerAdapterImp.class.getSimpleName() + " ";
    protected Context context;
    private boolean showTopLevel = true;//展示的最高等级
    protected T tree;
    protected List<T> expadData = new ArrayList<>();
    private EasyItemOnClickEvent itemOnClickEvent;

    /**
     * @param tree 数据
     */
    protected EasyTreeRecyclerAdapterImp(Context context, T tree) {
        this.context = context;
        this.tree = tree;
        showTopLevel = showTopLevel();
        initExpadData();
    }

    @Override
    public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        initExpadData();
        super.registerAdapterDataObserver(observer);
    }

    /**
     * 展示最高等级等级(默认最高等级0)
     *
     * @return
     */
    protected abstract boolean showTopLevel();

    private void initExpadData() {
        if (tree == null) {
            EasyUILog.e(TAG + "tree is null");
            return;
        }
        if (showTopLevel) {
            expadData.add(tree);
        }
        expadData.addAll(getAllExpandItem(tree));
    }

    private List<T> getAllExpandItem(T item) {
        List<T> list = new ArrayList<>();
        List<T> chilren = item.getChildren();
        if (chilren == null || !item.isExpand()) {
            return list;
        } else {
            list.addAll(chilren);
            for (int i = 0; i < chilren.size(); i++) {
                list.addAll(getAllExpandItem(chilren.get(i)));
            }
            return list;
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (expadData == null) {
            return count;
        }
        count = expadData.size();
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        EasyTreeItem item = expadData.get(position);
        if (item != null) {
            return item.getLevel();
        } else {
            return -1;
        }
    }

    @Override
    public EasyHloderImp onCreateViewHolder(ViewGroup parent, int viewType) {
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
        EasyHloderImp hloderImp = onCreateEasyHolder(view);
        hloderImp.setItemOnClickEvent(itemOnClickEvent);
        return hloderImp;
    }

    protected abstract int[] onBindLevelLayout();

    protected abstract float itemSpaceSize();

    /**
     * Holder
     *
     * @return
     */
    protected abstract EasyHloderImp onCreateEasyHolder(View view);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder == null) {
            return;
        }
        T t = expadData.get(position);
        ((EasyHloderImp) holder).onBindViewHolder(t, position);
    }

    public void setItemOnClickEvent(EasyItemOnClickEvent itemOnClickEvent) {
        this.itemOnClickEvent = itemOnClickEvent;
    }
}