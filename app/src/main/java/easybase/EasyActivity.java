package easybase;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.LayoutRes;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jen.easy.bind.EasyBind;
import com.jen.easyui.dialog.EasyLoading;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */
public abstract class EasyActivity<T> extends AppCompatActivity {
    private final String TAG = AppCompatActivity.class.getSimpleName();
    protected Context mContext;
    protected Handler mHandler = new Handler(Looper.getMainLooper());
    protected EasyBind mBindView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mBindView = new EasyBind();
        setContentView(bindView());
        mBindView.bind(this);
//        setContentView(setLayout());
        checkFilePermission();
        initData(savedInstanceState);
    }

    public abstract int bindView();

    public abstract void initData(Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBindView.unbind(this);
        if (mHandler != null) {
            mHandler.removeMessages(0);
            mHandler = null;
        }
    }


    protected void onBindClick(View view) {
    }

    /**
     * 6.0以上获取读写文件权限
     */
    protected void checkFilePermission() {
        //检查权限（NEED_PERMISSION）是否被授权 PackageManager.PERMISSION_GRANTED表示同意授权
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //用户已经拒绝过一次，再次弹出权限申请对话框需要给用户一个解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
            }
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
//            Toast.makeText(this, "授权成功！", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "checkFilePermission: 已经授权！");
        }
    }
}
