package com.jen.easyui.recyclerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jen.easy.log.EasyLog;

import java.util.Collections;
import java.util.List;

/**
 * baseAdapter
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */

abstract class EasyRecyclerBaseAdapterManager<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected Context mContext;
    protected List<T> mData;//不包含header、footer
    protected EasyAdapterOnClickListener easyAdapterOnClickListener;

    protected final int VIEW_TYPE_HEAD = -100;
    protected final int VIEW_TYPE_FOOT = -101;
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
            return VIEW_TYPE_HEAD;
        }
        if (isFootPosition(position) && mFootLayout != 0) {
            return VIEW_TYPE_FOOT;
        }
        return super.getItemViewType(position - mHeadItemCount);

    }

    @Override
    public EasyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_HEAD: {
                mHeaderView = LayoutInflater.from(parent.getContext()).inflate(mHeaderLayout, parent, false);
                if (mHeaderView != null) {
                    mHeaderHeight = mHeaderView.getLayoutParams().height;
                    setHeaderVisible(false);
                    return new EasyHolder(mHeaderView);
                }
                break;
            }
            case VIEW_TYPE_FOOT: {
                mFootView = LayoutInflater.from(parent.getContext()).inflate(mFootLayout, parent, false);
                if (mFootView != null) {
                    mFootHeight = mFootView.getLayoutParams().height;
                    setFootVisible(false);
                    return new EasyHolder(mFootView);
                }
                break;
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder == null) {
            return;
        }
        if (position == 0 && mHeaderLayout != 0) {
            onBindHeaderView(holder.itemView);
            return;
        }
        if (isFootPosition(position) && mFootLayout != 0) {
            onBindFooterView(holder.itemView);
            return;
        }
        T t = mData.get(position - mHeadItemCount);
        onBindView(holder.itemView, holder.getItemViewType(), t, position - mHeadItemCount);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        boolean isSet = setSpanSize(0) <= 0;//如果没设置直接返回
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
                    int spanSize = setSpanSize(position);
                    if (spanSize > total || spanSize <= 0) {
                        return total;
                    } else {
                        return spanSize;
                    }
                }
            });
        }
    }

    /**
     * ViewHolder
     */
    class EasyHolder extends RecyclerView.ViewHolder {
        EasyHolder(View itemView) {
            super(itemView);
            initListener();
        }

        private void initListener() {
            if (easyAdapterOnClickListener == null) {
                return;
            }
            int[] clicks = bindClick();
            int[] longClicks = bindLongClick();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() - mHeadItemCount;
                    if (pos < 0)
                        return;
                    easyAdapterOnClickListener.onItemClick(v, pos);
                }
            });

            if (clicks != null) {
                for (int id : clicks) {
                    View view = itemView.findViewById(id);
                    if (view == null) {
                        EasyLog.w("点击设置事件失败，请检查ID是否正确id=" + id);
                        continue;
                    }
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int pos = getAdapterPosition() - mHeadItemCount;
                            if (pos < 0)
                                return;
                            easyAdapterOnClickListener.onClick(v, pos);
                        }
                    });
                }
            }

            if (longClicks != null) {
                for (int id : longClicks) {
                    View view = itemView.findViewById(id);
                    if (view == null) {
                        EasyLog.w("点击设置事件失败，请检查ID是否正确id=" + id);
                        continue;
                    }
                    view.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            int pos = getAdapterPosition() - mHeadItemCount;
                            if (pos < 0)
                                return false;
                            return easyAdapterOnClickListener.onLongClick(v, pos);
                        }
                    });
                }
            }
        }
    }

    protected abstract int[] bindClick();

    protected abstract int[] bindLongClick();

    /**
     * 设置合并头布局的跨度，只对GridLayoutManager有效
     *
     * @param position 下标
     * @return 跨行
     */
    protected abstract int setSpanSize(int position);

    /**
     * 初始化item数据
     *
     * @return
     */
    protected abstract void onBindView(View view, int viewType, T data, int pos);

    /**
     * 头部view
     *
     * @param view
     */
    protected abstract void onBindHeaderView(View view);

    /**
     * 底部view
     *
     * @param view
     */
    protected abstract void onBindFooterView(View view);

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
                        mFootView.setVisibility(View.VISIBLE);
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

}
