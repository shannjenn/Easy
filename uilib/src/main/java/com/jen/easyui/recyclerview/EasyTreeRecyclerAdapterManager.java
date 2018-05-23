package com.jen.easyui.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jen.easy.log.EasyLog;
import com.jen.easyui.util.EasyDensityUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 树形模式(数据平铺:如level0 position=0，level1 position=1)
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */

abstract class EasyTreeRecyclerAdapterManager<T extends EasyTreeItem> extends EasyRecyclerBaseAdapterManager<T> {
    private final String TAG = EasyTreeRecyclerAdapterManager.class.getSimpleName() + " ";
    private int spaceSize;
    private Map<Integer, int[]> mLayoutParam = new HashMap<>();

    /**
     * @param context
     * @param data
     */
    EasyTreeRecyclerAdapterManager(Context context, List<T> data) {
        super(context, data);
        spaceSize = EasyDensityUtil.dip2px(mContext, itemLeftSpace());
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = super.getItemViewType(position);
        switch (viewType) {
            case VIEW_TYPE_HEAD:
            case VIEW_TYPE_FOOT:
                return viewType;
        }
        int level = mData.get(position - mHeadItemCount).getLevel();
        return getViewType(level);
    }

    /**
     * 获取布局类型
     *
     * @param level 层级
     * @return
     */
    protected abstract int getViewType(int level);

    @Override
    public EasyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_HEAD:
            case VIEW_TYPE_FOOT:
                return super.onCreateViewHolder(parent, viewType);
        }
        int[] layouts = onBindLayout();
        if (layouts == null) {
            EasyLog.w("布局为空");
            return super.onCreateViewHolder(parent, viewType);
        }
        if (viewType < 0 || layouts.length <= viewType) {
            EasyLog.w("viewType：" + viewType + "错误");
            return super.onCreateViewHolder(parent, viewType);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(layouts[viewType], parent, false);
        if (view == null) {
            EasyLog.w("找不到该值对应item布局R.layout.id：" + layouts[viewType]);
            return super.onCreateViewHolder(parent, viewType);
        }
        return new EasyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder == null) {
            return;
        }
        if (position == 0 && mHeaderLayout != 0) {
            super.onBindViewHolder(holder, position);
            return;
        }
        if (isFootPosition(position) && mFootLayout != 0) {
            super.onBindViewHolder(holder, position);
            return;
        }

        EasyTreeItem data = mData.get(position - mHeadItemCount);
        if (!mLayoutParam.containsKey(data.getLevel())) {
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            int height = layoutParams.height;
            int width = layoutParams.width;
            mLayoutParam.put(data.getLevel(), new int[]{height, width});
        }
        holder.itemView.setVisibility(data.isParentExpand() ? View.VISIBLE : View.GONE);
        setVisibility(data.getLevel(), holder.itemView);
        if (data.isParentExpand()) {//展开显示
            View view = holder.itemView;
            int space = data.getLevel() * spaceSize;
            view.setPadding(space, view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
            super.onBindViewHolder(holder, position);
        }
    }

    private void setVisibility(int level, View itemView) {
        RecyclerView.LayoutParams param = (RecyclerView.LayoutParams) itemView.getLayoutParams();
        if (itemView.getVisibility() == View.VISIBLE) {
//            param.height = RelativeLayout.LayoutParams.WRAP_CONTENT;// 这里注意使用自己布局的根布局类型
//            param.width = RelativeLayout.LayoutParams.MATCH_PARENT;// 这里注意使用自己布局的根布局类型
            param.height = mLayoutParam.get(level)[0];
            param.width = mLayoutParam.get(level)[1];
        } else {
            param.height = 0;
            param.width = 0;
        }
        itemView.setLayoutParams(param);
    }

    protected abstract int[] onBindLayout();

    /**
     * 单位db
     *
     * @return
     */
    protected abstract float itemLeftSpace();

    @Override
    protected void onBindHeaderView(View view) {

    }

    @Override
    protected void onBindFooterView(View view) {

    }
}