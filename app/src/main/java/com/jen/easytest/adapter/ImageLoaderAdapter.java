package com.jen.easytest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jen.easy.imageLoader.ImageLoader;
import com.jen.easytest.R;
import com.jen.easytest.adapter.decoration.MyItemDecoration;
import com.jen.easytest.model.ImageLoaderModel;
import com.jen.easyui.recycler.EasyHolder;
import com.jen.easyui.recycler.EasyBaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2018/3/2.
 */

public class ImageLoaderAdapter<T extends ImageLoaderModel> extends EasyBaseAdapter<T> {

    public ImageLoaderAdapter(Context context) {
        super(context);
    }

    public ImageLoaderAdapter(Context context, RecyclerView recyclerView) {
        super(context, recyclerView);
    }

    @Override
    protected int gridLayoutItemRows(int position) {
        return 0;
    }

    @Override
    public RecyclerView.ItemDecoration onDecoration() {
        return MyItemDecoration.newInstance(mContext);
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

        public MyHolder(EasyBaseAdapter adapter, View itemView) {
            super(adapter, itemView);
        }

        @Override
        protected void onBindData(View view, int viewType, int position) {
            ImageLoaderModel model = mData.get(position);

            ImageView icon = view.findViewById(R.id.iv_icon_left);
            TextView tv_name = view.findViewById(R.id.tv_name);

            tv_name.setText(model.getTitle());
            ImageLoader.getInstance().setImage(model.getPic(), icon);
        }

    }
}
