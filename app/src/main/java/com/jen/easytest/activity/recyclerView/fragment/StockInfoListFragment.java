package com.jen.easytest.activity.recyclerView.fragment;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.jen.easy.EasyBindClick;
import com.jen.easy.EasyBindId;
import com.jen.easy.http.HttpBasicRequest;
import com.jen.easytest.R;
import com.jen.easytest.activity.recyclerView.adapter.SortStockTextView;
import com.jen.easytest.activity.recyclerView.adapter.StockInfo;
import com.jen.easytest.activity.recyclerView.adapter.StockInfoListAdapter;
import com.jen.easytest.http.request.StockChooseConditionResultRequest;
import com.jen.easyui.base.EasyFragment;
import com.jen.easyui.recycler.EasyHScrollRecyclerView;
import com.jen.easyui.recycler.EasyHScrollView;
import com.jen.easyui.recycler.EasyItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 股票列表详情
 * Created by Administrator on 2017/10/31.
 */

public class StockInfoListFragment extends EasyFragment {
    //    @EasyBindId(R.id.smart_refresh)
//    private SmartRefreshLayout smart_refresh;
    @EasyBindId(R.id.easyRecycler)
    private EasyHScrollRecyclerView easyRecycler;
    @EasyBindId(R.id.easy_scroll_view)
    private EasyHScrollView easy_scroll_view;

    @EasyBindId(R.id.tv_stock_info_price)
    private SortStockTextView tv_stock_info_price;
    @EasyBindId(R.id.tv_stock_info_changePct)
    private SortStockTextView tv_stock_info_changePct;
    @EasyBindId(R.id.tv_stock_info_change)
    private SortStockTextView tv_stock_info_change;
    @EasyBindId(R.id.tv_stock_info_totalVal)
    private SortStockTextView tv_stock_info_totalVal;
    @EasyBindId(R.id.tv_stock_info_turnOver)
    private SortStockTextView tv_stock_info_turnOver;
    @EasyBindId(R.id.tv_stock_info_totalVolume)
    private SortStockTextView tv_stock_info_totalVolume;
    @EasyBindId(R.id.tv_stock_info_turnRate)
    private SortStockTextView tv_stock_info_turnRate;
    @EasyBindId(R.id.tv_stock_info_pe)
    private SortStockTextView tv_stock_info_pe;
    @EasyBindId(R.id.tv_stock_info_pb)
    private SortStockTextView tv_stock_info_pb;
    @EasyBindId(R.id.tv_stock_info_amplitude)
    private SortStockTextView tv_stock_info_amplitude;
    @EasyBindId(R.id.tv_stock_info_volRate)
    private SortStockTextView tv_stock_info_volRate;
    @EasyBindId(R.id.tv_stock_info_committee)
    private SortStockTextView tv_stock_info_committee;
//    @EasyBindId(R.id.tv_stock_info_sevenDayChgPct)
//    private SortStockTextView tv_stock_info_sevenDayChgPct;
//    @EasyBindId(R.id.tv_stock_info_prevClose)
//    private SortStockTextView tv_stock_info_prevClose;

    @EasyBindId(R.id.iv_stock_info_arrow_left)
    private ImageView iv_stock_info_arrow_left;
    @EasyBindId(R.id.iv_stock_info_arrow_right)
    private ImageView iv_stock_info_arrow_right;

    private StockInfoListAdapter<StockInfo> mAdapter;
    protected List<StockInfo> mData;
    private SortStockTextView mSelectSortView;
    private LoadListener mLoadListener;

    protected boolean mStartRequest;
    protected HttpBasicRequest mRequest;
    final int H_FLAG_RESULT_REFRESH = 100;
    final int H_FLAG_RESULT_LOAD_MORE = 101;

    final int H_FLAG_RESULT_ADD_DEL = 1000;//加入/移除 自选

    protected Type mType = Type.SELECTION_SINGLE;

    @Override
    protected int inflateLayout() {
        return R.layout.fragment_stock_info_list;
    }

    @Override
    protected void initViews() {
        iv_stock_info_arrow_left.setVisibility(View.INVISIBLE);
        iv_stock_info_arrow_right.setVisibility(View.VISIBLE);

        mAdapter = new StockInfoListAdapter<>(getContext(), null);
        mAdapter.setType(mType);
        mAdapter.addEasyHScrollView(easy_scroll_view);
        mAdapter.setEasyItemClickListener(mEasyAdapterOnClickListener);
        mAdapter.setScrollListener(mScrollListener);
        easyRecycler.setLinearLayoutManager(LinearLayoutManager.VERTICAL);
        easyRecycler.setAdapter(mAdapter);
//        mActivity.mHandler.postDelayed(mLoadAdapterDatas, 50);

        tv_stock_info_price.setCompType(StockInfo.CompType.price);
        tv_stock_info_changePct.setCompType(StockInfo.CompType.changePct);
        tv_stock_info_change.setCompType(StockInfo.CompType.change);
        tv_stock_info_totalVal.setCompType(StockInfo.CompType.totalVal);
        tv_stock_info_turnOver.setCompType(StockInfo.CompType.turnOver);
        tv_stock_info_totalVolume.setCompType(StockInfo.CompType.totalVolume);
        tv_stock_info_turnRate.setCompType(StockInfo.CompType.turnRate);
        tv_stock_info_pe.setCompType(StockInfo.CompType.pe);
        tv_stock_info_pb.setCompType(StockInfo.CompType.pb);
        tv_stock_info_amplitude.setCompType(StockInfo.CompType.amplitude);
        tv_stock_info_volRate.setCompType(StockInfo.CompType.volRate);
        tv_stock_info_committee.setCompType(StockInfo.CompType.committee);
//        tv_stock_info_sevenDayChgPct.setCompType(StockInfo.CompType.sevenDayChgPct);
//        tv_stock_info_prevClose.setCompType(StockInfo.CompType.prevClose);

//        smart_refresh.setEnableLoadmore(true);
//        smart_refresh.setRefreshHeader(new ClassicsHeader(mActivity).setProgressResource(R.drawable.loadanimation).setArrowResource(R.drawable.shiprun_down));
//        smart_refresh.setOnRefreshListener(mOnRefreshListener);
//        smart_refresh.setOnLoadmoreListener(mOnLoadMoreListener);
    }

    @Override
    protected void loadDataAfterView() {
        if (mStartRequest) {
            if (mRequest instanceof StockChooseConditionResultRequest) {
//                mRequest.flagCode = H_FLAG_RESULT_REFRESH;
//                mHttpManager.start(mRequest,H_FLAG_RESULT_REFRESH);
            }
        }
    }

    public enum Type {
        COLLECTION,//加入自选
        SELECTION_SINGLE//单选
    }

    public static StockInfoListFragment attach(FragmentTransaction transaction, int layoutId, List<StockInfo> data,
                                               HttpBasicRequest request, boolean startRequest, Type type) {
        if (request == null) {
            return null;
        }
        StockInfoListFragment fragment = new StockInfoListFragment();
        if (data != null) {
            fragment.mData = data;
        } else {
            fragment.mData = new ArrayList<>();
        }
        fragment.mType = type;
        fragment.mRequest = request;
        fragment.mStartRequest = startRequest;
        transaction.add(layoutId, fragment);
        transaction.commit();
        return fragment;
    }

    /**
     * 点击进行排序
     *
     * @param view
     */
    @EasyBindClick({R.id.tv_stock_info_price, R.id.tv_stock_info_changePct, R.id.tv_stock_info_change, R.id.tv_stock_info_totalVal
            , R.id.tv_stock_info_turnOver, R.id.tv_stock_info_totalVolume, R.id.tv_stock_info_turnRate, R.id.tv_stock_info_pe
            , R.id.tv_stock_info_pb, R.id.tv_stock_info_amplitude, R.id.tv_stock_info_volRate, R.id.tv_stock_info_committee})
    private void onClickSort(View view) {
        if (view instanceof SortStockTextView) {
            if (mSelectSortView != null && mSelectSortView != view) {
                mSelectSortView.resetSort();
            }
            mSelectSortView = (SortStockTextView) view;
            StockInfo.compType = mSelectSortView.getCompType();
            StockInfo.sort = mSelectSortView.getSort();

//            Collections.sort(mData);
//            mAdapter.updateSort(easyRecycler.getLayoutManager());
            if (mRequest instanceof StockChooseConditionResultRequest) {//改为服务器进行排序
                StockChooseConditionResultRequest request = (StockChooseConditionResultRequest) mRequest;
//                request.flagCode = H_FLAG_RESULT_REFRESH;
                request.getParams().setPage(1);
                if (StockInfo.sort == SortStockTextView.Sort.DEFAULT) {
                    request.getParams().setSortField("");
                    request.getParams().setSortDir("");
                } else {
                    request.getParams().setSortField(StockInfo.compType.toString());
                    request.getParams().setSortDir(StockInfo.sort.value() + "");
                }
//                mHttpManager.start(request);
            }
        }
    }

    /**
     * 左右滑动事件
     */
    private EasyHScrollView.ScrollListener mScrollListener = new EasyHScrollView.ScrollListener() {

        @Override
        public void OnScrollChanged(int x, int y) {

        }

        @Override
        public void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
            if (clampedX) {
                if (scrollX == 0) {
                    iv_stock_info_arrow_left.setVisibility(View.INVISIBLE);
                    iv_stock_info_arrow_right.setVisibility(View.VISIBLE);
                } else {
                    iv_stock_info_arrow_left.setVisibility(View.VISIBLE);
                    iv_stock_info_arrow_right.setVisibility(View.INVISIBLE);
                }
            } else {
                iv_stock_info_arrow_left.setVisibility(View.VISIBLE);
                iv_stock_info_arrow_right.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onItemClick(int position) {

        }
    };

    /**
     * item点击事件
     */
    private EasyItemClickListener mEasyAdapterOnClickListener = new EasyItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            StockInfo data = mData.get(position);
//            StockDetailFragmentActivity.start(mActivity, data.getAssetId(), data.getName(), data.getSecType());
        }

        @Override
        public void onViewClick(View view, int position) {
            switch (view.getId()) {
                case R.id.iv_stock_info_icon: {
                    /*switch (mType) {
                        case COLLECTION: {
                            if (XGApplication.getApplication().hasSessionId()) {
                                StockInfo info = mData.get(position);
                                StockChooseAddDelRequest request = new StockChooseAddDelRequest();
                                request.flagCode = H_FLAG_RESULT_ADD_DEL + position;
                                List<String> assetIds = new ArrayList<>();
                                assetIds.add(info.getAssetId());
                                request.param.setAssetIds(assetIds);
                                request.param.setIsTotalUpdate("0");
                                request.param.setHandleType(String.valueOf(info.getStkStatus() == 0 ? 1 : 0));
                                request.setSign();
//                            mAddDelView.put(request.flagCode, (ImageView) view);
                                mHttpManager.start(request);
                            } else {
                                ToastUtil.show(mActivity, getString(R.string.ask_stock_success_add_to_optional_failed), 1);
                                startActivity(new Intent(mActivity, UserOutLoginActivity.class));
//                                finish();
                            }
                            break;
                        }
                        default:
                            break;
                    }*/
                    break;
                }
            }
        }

        @Override
        public boolean onViewLongClick(View view, int position) {
            return false;
        }
    };

    /**
     * 下拉刷新
     */
    /*private OnRefreshListener mOnRefreshListener = new OnRefreshListener() {
        @Override
        public void onRefresh(RefreshLayout refreshLayout) {
            EasyLog.d("onRefresh -------");
            mRequest.flagCode = H_FLAG_RESULT_REFRESH;
            int page = 1;
            if (mRequest instanceof StockChooseConditionResultRequest) {
                ((StockChooseConditionResultRequest) mRequest).getParams().setPage(page);
            }
            mHttpManager.setShowWaitDialog(false);
            mHttpManager.start(mRequest);
        }
    };*/

    /**
     * 上拉加载更多
     */
    /*private OnLoadmoreListener mOnLoadMoreListener = new OnLoadmoreListener() {
        @Override
        public void onLoadmore(RefreshLayout refreshLayout) {
            EasyLog.d("onLoadmore -------");
            mRequest.flagCode = H_FLAG_RESULT_LOAD_MORE;
            if (mRequest instanceof StockChooseConditionResultRequest) {
                int page = ((StockChooseConditionResultRequest) mRequest).getParams().getPage() + 1;
                ((StockChooseConditionResultRequest) mRequest).getParams().setPage(page);
            }
            mHttpManager.setShowWaitDialog(false);
            mHttpManager.start(mRequest);
        }
    };

    @Override
    protected void httpPostSuccess(int flagCode, String flagStr, BaseResponse response) {
        super.httpPostSuccess(flagCode, flagStr, response);
        if (response instanceof StockChooseConditionResultResponse) {
            switch (flagCode) {
                case H_FLAG_RESULT_REFRESH: {
                    List<StockInfo> mData = ((StockChooseConditionResultResponse) response).getResult().getData();
                    if (mData.size() > 0) {
                        mData.clear();
                        mData.addAll(mData);
//                        Collections.sort(mData);
                        mAdapter.updateSort(easyRecycler.getLayoutManager());
                    }
                    if (mLoadListener != null) {
                        mLoadListener.finishRefresh();
                    }
                    smart_refresh.finishRefresh(true);
                    break;
                }
                case H_FLAG_RESULT_LOAD_MORE: {
                    List<StockInfo> mData = ((StockChooseConditionResultResponse) response).getResult().getData();
                    if (mData.size() > 0) {
                        mData.addAll(mData);
//                        Collections.sort(mData);
                        mAdapter.updateSort(easyRecycler.getLayoutManager());
                    }
                    if (mLoadListener != null) {
                        mLoadListener.finishLoadMore();
                    }
                    smart_refresh.finishLoadmore(true);
                    break;
                }
            }
        } else if (response instanceof StockChooseAddDelResponse) {
            int index = flagCode - H_FLAG_RESULT_ADD_DEL;
            StockInfo info = mData.get(index);
            if (info != null) {
                int status = info.getStkStatus();
                info.setStkStatus(status == 0 ? 1 : 0);
                mAdapter.updateSort(easyRecycler.getLayoutManager());
                ToastUtil.show(mActivity, getString(status == 0 ? R.string.toast_add_stock_success : R.string.toast_del_stock_success), 0);
            }
        }
    }

    @Override
    public void httpPostFail(int flagCode, String flagStr, String msg) {
        super.httpPostFail(flagCode, flagStr, msg);
        smart_refresh.finishRefresh(true);
        smart_refresh.finishLoadmore(true);
    }*/

    /*public void updateSort() {
        Collections.sort(mData);
        mAdapter.updateSort(easyRecycler.getLayoutManager());
    }*/

    public interface LoadListener {
        void finishRefresh();

        void finishLoadMore();
    }

    public void setLoadListener(LoadListener loadListener) {
        mLoadListener = loadListener;
    }
}
