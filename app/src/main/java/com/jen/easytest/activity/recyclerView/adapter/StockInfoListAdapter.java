package com.jen.easytest.activity.recyclerView.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jen.easy.EasyBindId;
import com.jen.easy.bind.BindView;
import com.jen.easy.log.EasyLog;
import com.jen.easytest.R;
import com.jen.easytest.activity.recyclerView.fragment.StockInfoListFragment;
import com.jen.easyui.shapeview.EasyShapeTextView;
import com.jen.easyui.recycler.EasyHScrollRecyclerView;
import com.jen.easyui.recycler.EasyHScrollRecyclerViewAdapter;
import com.jen.easyui.recycler.EasyHolder;
import com.jen.easyui.recycler.EasyRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：ShannJenn
 * 时间：2018/10/30.
 */

public class StockInfoListAdapter<T extends StockInfo> extends EasyHScrollRecyclerViewAdapter<T> {
    private final String TAG = StockInfoListAdapter.class.getSimpleName();
    private StockInfoListFragment.Type mType = StockInfoListFragment.Type.SELECTION_SINGLE;
    private final List<Integer> mNeedUpdatePosition = new ArrayList<>();

    /**
     * @param context
     * @param data    数据
     */
    public StockInfoListAdapter(Context context, List<T> data) {
        super(context, data);
    }

    public void update(List<T> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
//        return 20;
    }

    @Override
    protected int onBindLayout() {
        return R.layout.stock_choose_result_list_item;
    }

    @Override
    protected int onBindEasyHScrollViewId() {
        return R.id.easy_scroll_view;
    }

    @Override
    protected EasyHolder bindHolder(View view) {
        return new mEasyHolder(this, view);
    }

    private class mEasyHolder extends EasyHolder {


        public mEasyHolder(EasyRecyclerAdapter adapter, View itemView) {
            super(adapter, itemView);
        }

        @Override
        protected void onBindData(View view, int viewType, int position) {
            addOnClickEvent(R.id.iv_stock_info_icon, position);
            StockInfo item = mData.get(position);
            Temp temp = new Temp(view);
            temp.setData(item);
        }
    }

    class Temp {
        @EasyBindId(R.id.iv_stock_info_icon)
        private ImageView iv_stock_info_icon;
        @EasyBindId(R.id.tv_stock_info_assetId)
        private TextView tv_stock_info_assetId;
        @EasyBindId(R.id.tv_stock_info_name)
        private TextView tv_stock_info_name;
        @EasyBindId(R.id.tv_stock_info_price)
        private TextView tv_stock_info_price;
        @EasyBindId(R.id.tv_stock_info_changePct)
        private EasyShapeTextView tv_stock_info_changePct;
        @EasyBindId(R.id.tv_stock_info_change)
        private EasyShapeTextView tv_stock_info_change;
        @EasyBindId(R.id.tv_stock_info_totalVal)
        private EasyShapeTextView tv_stock_info_totalVal;
        @EasyBindId(R.id.tv_stock_info_turnOver)
        private TextView tv_stock_info_turnOver;
        @EasyBindId(R.id.tv_stock_info_totalVolume)
        private TextView tv_stock_info_totalVolume;
        @EasyBindId(R.id.tv_stock_info_turnRate)
        private TextView tv_stock_info_turnRate;
        @EasyBindId(R.id.tv_stock_info_pe)
        private TextView tv_stock_info_pe;
        @EasyBindId(R.id.tv_stock_info_pb)
        private TextView tv_stock_info_pb;
        @EasyBindId(R.id.tv_stock_info_amplitude)
        private TextView tv_stock_info_amplitude;
        @EasyBindId(R.id.tv_stock_info_volRate)
        private TextView tv_stock_info_volRate;
        @EasyBindId(R.id.tv_stock_info_committee)
        private TextView tv_stock_info_committee;
//        @EasyBindId(R.id.tv_stock_info_sevenDayChgPct)
//        private TextView tv_stock_info_sevenDayChgPct;
//        @EasyBindId(R.id.tv_stock_info_prevClose)
//        private TextView tv_stock_info_prevClose;

//        @EasyBindId(R.id.easy_scroll_view)
//        private EasyHScrollView easy_scroll_view;

        Temp(View view) {
            BindView bindView = new BindView();
            bindView.inject(this, view);
        }

        public void setData(StockInfo item) {
            switch (mType) {
                case COLLECTION: {
                    /*if (XGApplication.getApplication().hasSessionId()) {
                        iv_stock_info_icon.setVisibility(View.VISIBLE);
                    } else {
                        iv_stock_info_icon.setVisibility(View.INVISIBLE);
                    }*/
//                    iv_stock_info_icon.setImageResource(item.getStkStatus() == 0 ? R.drawable.icon_stock_choose_add : R.drawable.icon_stock_choose_delete);
                    break;
                }
                case SELECTION_SINGLE: {
//                    iv_stock_info_icon.setImageResource(item.isCheck() ? R.drawable.icon_checkbox_un_check : R.drawable.icon_checkbox_check);
                    break;
                }
                default:
                    break;
            }

            /*int color = getColor(item.getChangePct());
            tv_stock_info_price.setTextColor(color);
            tv_stock_info_changePct.getShape().setSolidColor(color);
            tv_stock_info_change.getShape().setSolidColor(color);
            tv_stock_info_totalVal.getShape().setSolidColor(color);

            String billion = mContext.getString(R.string.billion);
            String ten_thousand = mContext.getString(R.string.ten_thousand);

            tv_stock_info_assetId.setText(String.valueOf(item.getAssetId()));
            tv_stock_info_name.setText(String.valueOf(item.getName()));
            tv_stock_info_price.setText((MathUtil.formatDecimal(item.getPrice(), 3)));
            tv_stock_info_changePct.setText((MathUtil.formatDecimal(item.getChangePct(), 2) + "%"));
            tv_stock_info_change.setText((MathUtil.formatDecimal(item.getChange(), 3)));

            double totalVal = item.getTotalVal() / 100000000.0;
            String totalValUnit = billion;
            if (totalVal - 10000.0 > 0.0) {
                totalVal = totalVal / 10000.0;
                totalValUnit = ten_thousand + billion;
            }
            tv_stock_info_totalVal.setText((MathUtil.formatDecimal(totalVal, 0) + totalValUnit));

            double turnOver = item.getTurnOver() / 10000.0;
            tv_stock_info_turnOver.setText((MathUtil.formatDecimal(turnOver, 2) + ten_thousand));
            tv_stock_info_totalVolume.setText((MathUtil.formatDecimal(item.getTotalVolume() / 10000.0, 2) + ten_thousand));
            tv_stock_info_turnRate.setText((MathUtil.formatDecimal(item.getTurnRate(), 2) + "%"));
            tv_stock_info_pe.setText((MathUtil.formatDecimal(item.getPe(), 3) + "%"));
            tv_stock_info_pb.setText((MathUtil.formatDecimal(item.getPb(), 3) + "%"));
            tv_stock_info_amplitude.setText((MathUtil.formatDecimal(item.getAmplitude(), 2) + "%"));
            tv_stock_info_volRate.setText((MathUtil.formatDecimal(item.getVolRate(), 1) + "%"));
            tv_stock_info_committee.setText((MathUtil.formatDecimal(item.getCommittee(), 2) + "%"));*/
//            tv_stock_info_sevenDayChgPct.setText(String.valueOf(item.getSevenDayChgPct()));
//            tv_stock_info_prevClose.setText(String.valueOf(item.getPrevClose()));
        }
    }

    public void updateSort(RecyclerView.LayoutManager layoutManager) {
        int firstPos;
        int lastPos;
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager manager = (LinearLayoutManager) layoutManager;
            firstPos = manager.findFirstVisibleItemPosition();
            lastPos = manager.findLastVisibleItemPosition();
        } else {
            return;
        }

        for (int i = firstPos - 2; i < lastPos + 2; i++) {
            if (i < 0 || i >= mData.size()) {
                continue;
            }
            View view = layoutManager.findViewByPosition(i);
            if (view == null) {
                EasyLog.d("view = null --------- position=" + i);
                continue;
            }
            StockInfo item = mData.get(i);
            Temp temp = new Temp(view);
            temp.setData(item);
            EasyLog.d("position = " + i + " price = " + item.getPrice());
        }

        //前后view需要更新数据
        mNeedUpdatePosition.clear();
        if (firstPos - 1 >= 0) {
            mNeedUpdatePosition.add(firstPos - 1);
        }
        if (firstPos - 2 >= 0) {
            mNeedUpdatePosition.add(firstPos - 2);
        }
        if (firstPos - 3 >= 0) {
            mNeedUpdatePosition.add(firstPos - 3);
        }
        if (lastPos + 1 < mData.size()) {
            mNeedUpdatePosition.add(lastPos + 1);
        }
        if (lastPos + 2 < mData.size()) {
            mNeedUpdatePosition.add(lastPos + 2);
        }
        if (lastPos + 3 < mData.size()) {
            mNeedUpdatePosition.add(lastPos + 3);
        }
    }

    @Override
    public void recyclerViewOnScrollChanged(EasyHScrollRecyclerView recyclerView, int l, int t, int oldl, int oldt) {
        super.recyclerViewOnScrollChanged(recyclerView, l, t, oldl, oldt);
        if (mNeedUpdatePosition.size() == 0) {
            return;
        }
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager manager = (LinearLayoutManager) layoutManager;
            int firstPos = manager.findFirstVisibleItemPosition();
            int lastPos = manager.findLastVisibleItemPosition();
            int firstIndex = mNeedUpdatePosition.get(0);
            int lastIndex = mNeedUpdatePosition.get(mNeedUpdatePosition.size() - 1);
            if (firstIndex - firstPos > 5 || lastPos - lastIndex > 5) {//滑过UI缓存则不需要更新
                mNeedUpdatePosition.clear();
                return;
            }
        } else {
            return;
        }
        for (int i = mNeedUpdatePosition.size() - 1; i >= 0; i--) {
            int position = mNeedUpdatePosition.get(i);
            View view = layoutManager.findViewByPosition(position);
            if (view == null) {
                EasyLog.d("recyclerViewOnScrollChanged view = null --------- position=" + i);
                continue;
            }
            StockInfo item = mData.get(position);
            Temp temp = new Temp(view);
            temp.setData(item);
            mNeedUpdatePosition.remove(i);
            EasyLog.d("recyclerViewOnScrollChanged position = " + i + " price = " + item.getPrice());
        }
    }

    public void setType(StockInfoListFragment.Type type) {
        mType = type;
    }

    /*private int getColor(double value) {
        return XGUtils.getTextColor(MFU.dt2p(value), mContext);
    }*/
}
