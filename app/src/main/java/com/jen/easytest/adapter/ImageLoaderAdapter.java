package com.jen.easytest.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jen.easy.imageLoader.ImageLoader;
import com.jen.easytest.R;
import com.jen.easytest.model.ImageLoaderModel;
import com.jen.easyui.recycler.EasyHolder;
import com.jen.easyui.recycler.EasyRecyclerAdapter;
import com.jen.easyui.recycler.EasyRecyclerBaseAdapter;

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
    protected int setGridLayoutItemRows(int position) {
        return 0;
    }

    @Override
    protected EasyHolder bindHolder(View view) {
        return new MyHolder(this,view);
    }

    @Override
    protected int onBindLayout() {
        return R.layout.item_image_loader;
    }


    class MyHolder extends EasyHolder {

        public MyHolder(EasyRecyclerBaseAdapter adapter, View itemView) {
            super(adapter, itemView);
        }

        @Override
        protected void onBindData(View view, int viewType, int position) {
            ImageLoaderModel model = mData.get(position);

            ImageView icon = view.findViewById(R.id.iv_icon);
            TextView tv_name = view.findViewById(R.id.tv_name);

            tv_name.setText(model.getTitle());
            ImageLoader.getInstance().setImage(model.getPic(), icon);
        }

    }
}
