package easybase;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jen.easy.bind.EasyBind;

import java.util.List;
import java.util.Map;

/**
 * EditText
 * 作者：ShannJenn
 * 时间：2017/01/09.
 */

public abstract class EasyFragment/*<Request extends HttpManager>*/ extends Fragment {
    protected View rootView;
    protected EasyBind mBindView;
    private boolean isCreated;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        this.mActivity = (BaseActivity) getActivity();
    }

//    public Request request;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        rootView = super.onCreateView(inflater, container, savedInstanceState);
        mBindView = new EasyBind();
        if (rootView == null) {
            rootView = inflater.inflate(bindLayout(), container, false);
            mBindView = new EasyBind();
            mBindView.inject(this, rootView);
//            mLoading = new EasyLoading(getContext());
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
        initData(savedInstanceState);
        isCreated = true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isCreated = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public boolean isCreated() {
        return isCreated;
    }

    protected abstract int bindLayout();

    protected abstract void initData(@Nullable Bundle savedInstanceState);

    protected void onClick(View view) {
    }

    public void httpSuccess(int flagCode, String flagStr, Object response, Map<String, List<String>> map) {

    }

    public void httpFail(int flagCode, String flagStr, Object response) {

    }

    public void httpProgress(int flagCode, String flagStr, Object response, long currentPoint, long endPoint) {

    }
}
