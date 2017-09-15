package com.jen.easyui.listview;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jen.easyui.EasyUILog;

import java.util.List;
import java.util.Map;

/**
 * Created by Jen on 2017/8/24.
 */

public class EasyRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String TAG = EasyRecyclerAdapter.class.getSimpleName() + " ";
    private List<T> datas;
    private int layout;
    private boolean moreItem;//多种布局
    private Map<Integer, Integer> layouts;
    private AdapterClickEvent adapterClickEvent;
    private ClickEvent clickEvent = new ClickEvent();

    public EasyRecyclerAdapter(List<T> datas, int layout) {
        this.datas = datas;
        this.layout = layout;
        if (datas == null || datas.size() == 0) {
            return;
        }
    }

    /**
     * @param datas            数据
     * @param viewType_layouts key: @ItemSource（isViewType） value：布局
     */
    public EasyRecyclerAdapter(List<T> datas, Map<Integer, Integer> viewType_layouts) {
        this.datas = datas;
        this.layouts = viewType_layouts;
        moreItem = true;
        if (datas == null || layouts == null)
            return;
    }

    class Holder extends RecyclerView.ViewHolder {
        private View itemView;

        public Holder(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (moreItem) {
            for (int i = 0; i < layouts.size(); i++) {
                if (!layouts.containsKey(viewType)) {
                    EasyUILog.e("为找到该值对应item布局：" + viewType);
                    return null;
                }
                int layoutId = layouts.get(viewType);
                View viewLayout = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
                Holder holder = new Holder(viewLayout);
                return holder;
            }
        } else {
            View viewLayout = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
            Holder holder = new Holder(viewLayout);
            return holder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder == null) {
            return;
        }
        View view = holder.itemView;

        Map<String, Object> map = LayoutReflectmanager.bindItemLayout(datas.get(position));
        Map<Integer, String> txtIds = (Map<Integer, String>) map.get(LayoutReflectmanager.TEXT);
        Map<Integer, Object> imgIds = (Map<Integer, Object>) map.get(LayoutReflectmanager.IMAGE);
        List<Integer> onClickIds = (List<Integer>) map.get(LayoutReflectmanager.ONCLICK);
        List<Integer> onLongClickIds = (List<Integer>) map.get(LayoutReflectmanager.ONLONGCLICK);

        for (int key : txtIds.keySet()) {
            TextView tv = (TextView) view.findViewById(key);
            if (tv == null) {
//                EasyUILog.w(TAG + "找不到对应：txtId=" + key + " position=" + position);
                continue;
            }
            tv.setText(txtIds.get(key));
        }
        for (int key : imgIds.keySet()) {
            ImageView imageView = (ImageView) view.findViewById(key);
            if (imageView == null) {
//                EasyUILog.w(TAG + "找不到对应：imgId=" + key + " position=" + position);
            }
            Object img = imgIds.get(key);
            if (img instanceof Integer) {
                imageView.setImageResource((Integer) img);
            } else if (img instanceof Drawable) {
                imageView.setImageDrawable((Drawable) img);
            } else if (img instanceof Bitmap) {
                imageView.setImageBitmap((Bitmap) img);
            } else if (img instanceof Uri) {
                imageView.setImageURI((Uri) img);
            }
        }
        for (int i = 0; i < onClickIds.size(); i++) {
            int viewId = onClickIds.get(i);
            View onClickView = view.findViewById(viewId);
            if (onClickView == null) {
//                EasyUILog.w(TAG + "找不到对应：onClickView viewId=" + viewId + " position=" + position);
                continue;
            }
            onClickView.setOnClickListener(clickEvent);
        }
        for (int i = 0; i < onLongClickIds.size(); i++) {
            int viewId = onClickIds.get(i);
            View onLongClickView = view.findViewById(viewId);
            if (onLongClickView == null) {
                EasyUILog.w(TAG + "找不到对应：onLongClickView viewId=" + viewId + " position=" + position);
                continue;
            }
            onLongClickView.setOnLongClickListener(clickEvent);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterClickEvent.onItemClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (datas == null || datas.size() == 0) {
            return 0;
        }
        if (moreItem && (layouts == null || layouts.size() == 0)) {
            return 0;
        }
        return datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (moreItem) {
            return LayoutReflectmanager.getViewType(datas.get(position));
        } else {
            return super.getItemViewType(position);
        }
    }

    public void setAdapterClickEvent(AdapterClickEvent adapterClickEvent) {
        this.adapterClickEvent = adapterClickEvent;
    }

    public class ClickEvent implements View.OnClickListener, View.OnLongClickListener, View.OnTouchListener {

        @Override
        public void onClick(View v) {
            adapterClickEvent.onClick(v);
        }

        @Override
        public boolean onLongClick(View v) {
            return adapterClickEvent.onLongClick(v);
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return adapterClickEvent.onTouch(v, event);
        }
    }

}
