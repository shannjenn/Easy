package com.jen.easyui.base;

import android.content.Context;
import android.widget.Toast;


/**
 * 作者：ShannJenn
 * 时间：2017/09/10.
 */

public class EasyToast {

    public static void toast(String txt) {
        Toast.makeText(EasyApplication.getAppContext(), txt, Toast.LENGTH_SHORT).show();
    }

    public static void toast(Context context, String txt) {
        Toast.makeText(context, txt, Toast.LENGTH_SHORT).show();
    }

    public static void snack() {
        /*Snackbar.make(view, "data deleted", Snackbar.LENGTH_LONG)
                .setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                })
                .show();*/
    }
}
