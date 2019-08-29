package easybase;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;


/**
 * 作者：ShannJenn
 * 时间：2017/09/10.
 */

public class EasyToast {

    private static Toast mToast = null;

    /**
     * 避免重复提示
     * @param context
     * @param text
     * @param duration
     */
    public static void showToast(Context context, String text, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, duration);
        } else {
            mToast.setText(text);
            mToast.setDuration(duration);
        }
        mToast.show();
    }

    /**
     * 显示 LENGTH_SHORT
     *
     * @param msg .
     */
    public static void showShort(String msg) {
        Toast.makeText(EasyApplication.getAppContext(), msg, Toast.LENGTH_SHORT).show();
//        CommonUtils.showToast(EasyApplication.getAppContext(), msg);
    }

    /**
     * 中间显示 LENGTH_SHORT
     *
     * @param msg .
     */
    public static void showShortCenter(String msg) {
        Toast.makeText(EasyApplication.getAppContext(), msg, Toast.LENGTH_SHORT).show();
        Toast toast = Toast.makeText(EasyApplication.getAppContext(), msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 显示 LENGTH_LONG
     *
     * @param msg .
     */
    public static void showLong(String msg) {
        Toast.makeText(EasyApplication.getAppContext(), msg, Toast.LENGTH_LONG).show();
//        CommonUtils.showToast(EasyApplication.getAppContext(), msg);
    }

    /**
     * 显示（默认）
     *
     * @param msg .
     */
    public static void show(String msg) {
        Toast.makeText(EasyApplication.getAppContext(), msg, Toast.LENGTH_LONG).show();
//        CommonUtils.showToast(EasyApplication.getAppContext(), msg);
    }

    /**
     * 显示（默认）
     *
     * @param msgId .
     */
    public static void show(int msgId) {
        Toast.makeText(EasyApplication.getAppContext(), msgId, Toast.LENGTH_LONG).show();
//        CommonUtils.showToast(EasyApplication.getAppContext(), msgId);
    }
}
