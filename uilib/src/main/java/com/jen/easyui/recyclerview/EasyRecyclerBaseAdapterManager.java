package com.jen.easyui.recyclerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

/**
 * baseAdapter
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */

public abstract class EasyRecyclerBaseAdapterManager<T> extends RecyclerView.Adapter<EasyHolder> {
    protected Context mContext;
    protected List<T> mData;//不包含header、footer
    protected EasyAdapterOnClickListener easyAdapterOnClickListener;

    protected int mHeaderLayout;
    protected int mFootLayout;
    protected int mHeadItemCount;
    protected int mFootItemCount;
    private View mHeaderView;
    private View mFootView;
    private int mHeaderHeight;
    private int mFootHeight;

    /**
     * @param data 数据
     */
    EasyRecyclerBaseAdapterManager(Context context, List<T> data) {
        this.mContext = context;
        mData = data;
    }

    @Override
    public int getItemCount() {
        if (mData == null || mData.size() == 0) {
            return 0;
        }
        int count = 0;
        mHeadItemCount = 0;
        mFootItemCount = 0;
        if (mHeaderLayout != 0) {
            count++;
            mHeadItemCount = 1;
        }
        if (mFootLayout != 0) {
            count++;
            mFootItemCount = 1;
        }
        count += mData.size();
        return count;
    }

    protected boolean isFootPosition(int position) {
        return position - mHeadItemCount > mData.size() - 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && mHeaderLayout != 0) {
            return EasyItemType.HEAD.getType();
        }
        if (isFootPosition(position) && mFootLayout != 0) {
            return EasyItemType.FOOT.getType();
        }
        return super.getItemViewType(position - mHeadItemCount);
    }

    @Override
    public EasyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == EasyItemType.HEAD.getType()) {
            mHeaderView = LayoutInflater.from(parent.getContext()).inflate(mHeaderLayout, parent, false);
            if (mHeaderView != null) {
                mHeaderHeight = mHeaderView.getLayoutParams().height;
                setHeaderVisible(false);
                return bindHolder(mHeaderView, EasyItemType.HEAD);
            }
        } else if (viewType == EasyItemType.FOOT.getType()) {
            mFootView = LayoutInflater.from(parent.getContext()).inflate(mFootLayout, parent, false);
            if (mFootView != null) {
                mFootHeight = mFootView.getLayoutParams().height;
                setFootVisible(false);
                return bindHolder(mFootView, EasyItemType.FOOT);
            }
        }
        return null;
    }

    /**
     * Holder
     *
     * @return
     */
    @Override
    public void onBindViewHolder(EasyHolder holder, final int position) {
        if (holder == null) {
            return;
        }
        if (position == 0 && mHeaderLayout != 0) {
            holder.onBindHeadData(holder.itemView);
            return;
        }
        if (isFootPosition(position) && mFootLayout != 0) {
            holder.onBindFootData(holder.itemView);
            return;
        }
        holder.onBindData(holder.itemView, holder.getItemViewType(), position - mHeadItemCount);
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
     * 设置下拉显示/隐藏
     *
     * @param visible
     */
    protected void setHeaderVisible(boolean visible) {
        if (mHeaderView != null) {
            if (visible) {
                mHeaderView.getLayoutParams().height = mHeaderHeight;
                mHeaderView.setVisibility(View.VISIBLE);
            } else {
                mHeaderView.getLayoutParams().height = 1;
                mHeaderView.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 设置上拉显示/隐藏
     *
     * @param visible
     */
    protected void setFootVisible(boolean visible) {
        if (mFootView != null) {
            if (visible) {
                mFootView.getLayoutParams().height = mFootHeight;
                mFootView.setVisibility(View.VISIBLE);
            } else {
                mFootView.getLayoutParams().height = 1;
                mFootView.setVisibility(View.GONE);
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mFootView.setVisibility(View.INVISIBLE);
                        mFootView.getLayoutParams().height = mFootHeight;
                    }
                }, 5);
            }
        }
    }

    /**
     * 设置拖拽排序
     *
     * @param recyclerView
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
            if (fromPosition < mHeadItemCount || toPosition < mHeadItemCount) {
                return false;
            }
            if (fromPosition >= mData.size() + mHeadItemCount || toPosition >= mData.size() + mHeadItemCount) {
                return false;
            }

            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(mData, i - mHeadItemCount, i - mHeadItemCount + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(mData, i - mHeadItemCount, i - mHeadItemCount - 1);
                }
            }
            notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        }
    });

    public EasyAdapterOnClickListener getEasyAdapterOnClickListener() {
        return easyAdapterOnClickListener;
    }

    public void setEasyAdapterOnClickListener(EasyAdapterOnClickListener easyAdapterOnClickListener) {
        this.easyAdapterOnClickListener = easyAdapterOnClickListener;
    }

    /**
     * 设置合并头布局的跨度，只对GridLayoutManager有效
     *
     * @param position 下标
     * @return 跨行
     */
    protected abstract int setGridLayoutItemRows(int position);

    protected abstract EasyHolder bindHolder(View view, EasyItemType easyItemType);
}
