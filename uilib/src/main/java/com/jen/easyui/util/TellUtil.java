package com.jen.easyui.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * 作者：ShannJenn
 * 时间：2018/12/05.
 * 说明：
 */
public class TellUtil {

    /**
     * 调用拨号界面
     *
     * @param phone 电话号码
     */
    public static void gotoCallTell(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
