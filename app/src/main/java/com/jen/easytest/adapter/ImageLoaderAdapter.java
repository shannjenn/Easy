package com.jen.easytest.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jen.easy.imageLoader.ImageLoader;
import com.jen.easytest.R;
import com.jen.easytest.model.ImageLoaderModel;
import com.jen.easyui.recyclerview.EasyRecyclerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2018/3/2.
 */

public class ImageLoaderAdapter<T extends ImageLoaderModel> extends EasyRecyclerAdapter<T> {
    /**
     * @param context
     * @param data    数据
     */
    public ImageLoaderAdapter(Context context, List<T> data) {
        super(context, data);
    }

    @Override
    protected int onBindLayout() {
        return R.layout.item_image_loader;
    }

    @Override
    protected void onBindView(View view, int viewType, T data, int pos) {
        super.onBindView(view, viewType, data, pos);
        ImageLoaderModel model = data;

        ImageView icon = view.findViewById(R.id.iv_icon);
        TextView tv_name = view.findViewById(R.id.tv_name);

        tv_name.setText(model.getTitle());
        ImageLoader.getInstance().setImage(model.getPic(), icon);
    }
}
