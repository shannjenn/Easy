package com.jen.easyui.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

/**
 * 作者：ShannJenn
 * 时间：2018/11/30.
 * 说明：
 */
public class TextUtil {
    private static TextUtil me;

    private TextUtil() {

    }

    public static TextUtil getIns() {
        if (me == null) {
            synchronized (TextUtil.class) {
                if (me == null) {
                    me = new TextUtil();
                }
            }
        }
        return me;
    }

    /**
     * 设置部分文字颜色
     *
     * @param style .
     * @param color .
     * @return .
     */
    public TextUtil setPartTextColor(SpannableStringBuilder style, int color, int start, int end) {
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(color);
        style.setSpan(foregroundColorSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    /**
     * 设置部分文字加粗
     *
     * @param style .
     * @return .
     */
    public TextUtil setPartTextBold(SpannableStringBuilder style, int start, int end) {
        StyleSpan styleSpan = new StyleSpan(Typeface.BOLD);
        style.setSpan(styleSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    /**
     * 设置提示文本
     */
    /*private void initTips() {
        final SpannableStringBuilder style = new SpannableStringBuilder();
        //设置文字
        style.append(getString(R.string.cashin_waring_2));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                NewsDetailWebViewActivity.start(getActivity(), APIConfig.getWebApiUrl(APIConfig.URL_CUSTOMER_SERVICE_WHAT_BANK_REPORT), true);
//                XGUtils.openWebView(mActivity,APIConfig.getWebApiUrl(APIConfig.URL_CUSTOMER_SERVICE_WHAT_BANK_REPORT),"什么是银行账户凭证");

            }
        };
        style.setSpan(clickableSpan, 86, 96, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tips.setText(style);

        //设置部分文字颜色
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#DDB003"));
        style.setSpan(foregroundColorSpan, 86, 96, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        //配置给TextView
        tips.setMovementMethod(LinkMovementMethod.getInstance());
        tips.setText(style);
    }*/
    /*
     * 复制到粘贴板
     */
    public TextUtil clipboard(Context context, String name, String text) {
        ClipboardManager cbm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(name, text);
        cbm.setPrimaryClip(clipData);
//        CommonUtils.showToast(context, context.getString(R.string.copy_done));
        return this;
    }
}
