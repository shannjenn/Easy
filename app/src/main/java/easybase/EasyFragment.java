package easybase;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jen.easy.bind.EasyBind;
import com.jen.easyui.dialog.EasyLoading;

/**
 * EditText
 * 作者：ShannJenn
 * 时间：2017/01/09.
 */

public abstract class EasyFragment<T> extends Fragment {
    protected View rootView;
    protected Context mContext;
    protected Handler mHandler = new Handler(Looper.getMainLooper());
    protected EasyBind mBindView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        if (rootView == null) {
            rootView = inflater.inflate(inflateLayout(), container, false);
            mBindView = new EasyBind();
            mBindView.inject(this, rootView);
//            httpListener.setListenerImp(this);
        }
        // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        loadDataAfterView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeMessages(0);
            mHandler = null;
        }
    }

    protected abstract int inflateLayout();

    protected abstract void initViews();

    protected abstract void loadDataAfterView();

}
