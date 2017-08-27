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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jen on 2017/8/24.
 */

public class EasyRecyclerAdapter extends RecyclerView.Adapter {
    private List<Object> datas;
    private int layout;
    Map<Integer, String> layoutText = new HashMap<>();
    Map<Integer, Object> layoutImg = new HashMap<>();
    List<Integer> layoutclicks = new ArrayList<>();

    private Field field;
    private int[] values;
    private int[] layouts;
    Map<Integer, String> layoutsText = new HashMap<>();
    Map<Integer, Object> layoutsImg = new HashMap<>();
    List<Integer> layoutsclicks = new ArrayList<>();

    private boolean moreItem;//多种布局
    private AdapterClickEvent adapterClickEvent;

    public EasyRecyclerAdapter(List<Object> datas, int layout) {
        this.datas = datas;
        this.layout = layout;
    }

    public EasyRecyclerAdapter(List<Object> datas, boolean moreItem) {
        this.datas = datas;
        this.moreItem = moreItem;
        if (datas == null)
            return;
        for (int i = 0; i < datas.size(); i++) {
            if (datas.get(i) == null)
                continue;
            Map<String, Object> map = LayoutReflectmanager.getLaout(datas.get(0).getClass());
            if (map.size() == 0) {
                continue;
            }
            field = (Field) map.get(LayoutReflectmanager.FIELD);
            values = (int[]) map.get(LayoutReflectmanager.VIEWTYPES);
            layouts = (int[]) map.get(LayoutReflectmanager.LAYOUTS);
            break;
        }

    }

    class Holder extends RecyclerView.ViewHolder {
        private View itemView;

        public Holder(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (moreItem) {
            for (int i = 0; i < values.length; i++) {
                int layoutId = layouts[i];
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
        View view = ((Holder) holder).itemView;
        if (moreItem) {
            for (int id : layoutsText.keySet()) {
                TextView textView = view.findViewById(id);
                if (textView == null) {
                    EasyUILog.e("找不到该控件textView");
                    continue;
                }
                textView.setText(layoutsText.get(id));
            }
            for (int id : layoutsImg.keySet()) {
                ImageView imageView = view.findViewById(id);
                if (imageView == null) {
                    EasyUILog.e("找不到该控件imageView");
                    continue;
                }
                Object img = layoutsImg.get(id);
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
        } else {
            for (int id : layoutText.keySet()) {
                TextView textView = view.findViewById(id);
                if (textView == null) {
                    EasyUILog.e("找不到该控件textView");
                    continue;
                }
                textView.setText(layoutText.get(id));
            }
            for (int id : layoutImg.keySet()) {
                ImageView imageView = view.findViewById(id);
                if (imageView == null) {
                    EasyUILog.e("找不到该控件imageView");
                    continue;
                }
                Object img = layoutImg.get(id);
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
            for (int key : layoutclicks) {
                View viewClick = view.findViewById(key);
                if (viewClick != null) {
                    viewClick.setOnClickListener(adapterClickEvent);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        if (datas == null || datas.size() == 0) {
            return 0;
        }
        if (moreItem && field == null) {
            EasyUILog.e("请在demo中备注@Itemlayout");
            return 0;
        }
        return datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (moreItem) {
            try {
                Object obj = field.get(datas);
                if (obj != null && obj instanceof Integer) {
                    return (int) obj;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return -1;
        } else {
            return super.getItemViewType(position);
        }
    }

    public void setAdapterClickEvent(AdapterClickEvent adapterClickEvent) {
        this.adapterClickEvent = adapterClickEvent;
    }

}
