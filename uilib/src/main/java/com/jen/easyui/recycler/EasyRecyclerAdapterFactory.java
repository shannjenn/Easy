package com.jen.easyui.recycler;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import com.jen.easyui.recycler.listener.EasyItemListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * baseAdapter
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */

public abstract class EasyRecyclerAdapterFactory<T> extends RecyclerView.Adapter<EasyHolder> {
    protected Context mContext;
    protected List<T> mData;
    EasyItemListener listener;

    public EasyRecyclerAdapterFactory(Context context) {
        this.mContext = context;
        mData = new ArrayList<>();
    }

    /**
     * @param data 数据
     */
    public EasyRecyclerAdapterFactory(Context context, List<T> data) {
        this.mContext = context;
        mData = data;
        if (mData == null) {
            mData = new ArrayList<>();
        }
    }

    /**
     * 竖向列表
     *
     * @param recyclerView .
     */
    public void bindRecycleLinearVertical(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(this);
    }

    /**
     * 横向列表
     *
     * @param recyclerView .
     */
    public void bindRecycleLinearHorizontal(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(this);
    }

    /**
     * 竖向Grid布局
     *
     * @param size 数量
     */
    public void bindRecycleVertical(RecyclerView recyclerView, int size) {
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, size);
        layoutManager.setOrientation(GridLayout.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(this);
    }

    /**
     * 横向Grid布局
     *
     * @param size 数量
     */
    public void bindRecycleHorizontal(RecyclerView recyclerView, int size) {
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, size);
        layoutManager.setOrientation(GridLayout.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(this);
    }

    /**
     * @return 获取数据
     */
    public List<T> getData() {
        return mData;
    }

    /**
     * @param data 设置数据
     */
    public void setData(List<T> data) {
        mData = data;
        if (mData == null) {
            mData = new ArrayList<>();
        }
    }

    @Override
    public int getItemCount() {
        if (mData == null || mData.size() == 0) {
            return 0;
        }
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public EasyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    /**
     * Holder
     */
    @Override
    public void onBindViewHolder(final EasyHolder holder, final int position) {
        if (holder == null) {
            return;
        }
        if (listener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(holder.itemView, position);
                }
            });
        }
        holder.onBindData(holder.itemView, holder.getItemViewType(), position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        boolean isSet = setGridLayoutItemRows(0) <= 0;//如果没设置直接返回
        if (isSet) {
            return;
        }

        //为GridLayoutManager 合并头布局的跨度
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int total = gridLayoutManager.getSpanCount();
                    int rows = setGridLayoutItemRows(position);
                    if (rows > total || rows <= 0) {
                        return total;
                    } else {
                        return rows;
                    }
                }
            });
        }
    }

    /**
     * 设置拖拽排序
     *
     * @param recyclerView .
     */
    public void setItemTouchSortEvent(RecyclerView recyclerView) {
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    /**
     * 拖拽排序
     */
    private ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                final int swipeFlags = 0;
                return makeMovementFlags(dragFlags, swipeFlags);
            } else {
                final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                final int swipeFlags = 0;
                return makeMovementFlags(dragFlags, swipeFlags);
            }
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(mData, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(mData, i, i - 1);
                }
            }
            notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        }
    });

    public void setItemListener(EasyItemListener listener) {
        this.listener = listener;
    }

    /**
     * 设置合并头布局的跨度，只对GridLayoutManager有效
     *
     * @param position 下标
     * @return 跨行
     */
    protected abstract int setGridLayoutItemRows(int position);

    protected abstract EasyHolder bindHolder(View view);
}
