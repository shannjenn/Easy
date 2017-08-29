package com.jen.easyui.listview;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jen.easyui.EasyUILog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jen on 2017/8/24.
 */

public class EasyRecyclerAdapter extends RecyclerView.Adapter {
    private List<?> datas;
    private int layout;
    private boolean moreItem;//多种布局
    private Map<Integer, Integer> layouts;
    private AdapterClickEvent adapterClickEvent;
    Map<Integer, String> txtIds = new HashMap<>();
    Map<Integer, Object> imgIds = new HashMap<>();
    List<Integer> onClickIds = new ArrayList<>();
    List<Integer> onLongClickIds = new ArrayList<>();

    public EasyRecyclerAdapter(List<?> datas, int layout) {
        this.datas = datas;
        this.layout = layout;
        if (datas == null || datas.size() == 0) {
            return;
        }
        initItemLayout();
    }

    public EasyRecyclerAdapter(List<?> datas, Map<Integer, Integer> viewType_layouts) {
        this.datas = datas;
        this.layouts = viewType_layouts;
        moreItem = true;
        if (datas == null || layouts == null)
            return;
        initItemLayout();
    }

    private void initItemLayout() {
        txtIds.clear();
        imgIds.clear();
        onClickIds.clear();
        onClickIds.clear();

        Map<String, Object> map = LayoutReflectmanager.bindItemLayout(datas.get(0));
        txtIds.putAll((Map<Integer, String>) map.get(LayoutReflectmanager.TEXT));
        imgIds.putAll((Map<Integer, Object>) map.get(LayoutReflectmanager.IMAGE));
        onClickIds.addAll((List<Integer>) map.get(LayoutReflectmanager.ONCLICK));
        onClickIds.addAll((List<Integer>) map.get(LayoutReflectmanager.ONLONGCLICK));
    }

    class Holder extends RecyclerView.ViewHolder {
        private View itemView;

        public Holder(View itemView) {
            super(null);
            this.itemView = itemView;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (moreItem) {
            for (int i = 0; i < layouts.size(); i++) {
                if (!layouts.containsKey(viewType)) {
                    EasyUILog.e("为找到该值对应item布局：" + viewType);
                    return null;
                }
                int layoutId = layouts.get(viewType);
                View viewLayout = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
                return new Holder(viewLayout);
            }
        } else {
            View viewLayout = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
            return new Holder(viewLayout);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder == null) {
            return;
        }
        View view = ((Holder) holder).itemView;

        for (int key : txtIds.keySet()) {
            TextView tv = view.findViewById(key);
            tv.setText(txtIds.get(key));
        }
        for (int key : imgIds.keySet()) {
            ImageView imageView = view.findViewById(key);
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
            view.findViewById(onClickIds.get(i)).setOnClickListener(adapterClickEvent);
        }
        for (int i = 0; i < onLongClickIds.size(); i++) {
            view.findViewById(onLongClickIds.get(i)).setOnLongClickListener(adapterClickEvent);
        }

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

}
