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
public class TextStyleUtil {
    private TextStyleUtil() {

    }

    /**
     * 左边黑体加粗
     *
     * @param strId   .
     * @param partStr .
     * @return .
     */
    public static SpannableStringBuilder setBoldBlackLeft(Context context, int strId, String partStr) {
        String text = context.getString(strId, partStr);
        return setBoldBlack(text, 0, partStr.length());
    }

    /**
     * 右边黑体加粗
     *
     * @param strId   .
     * @param partStr .
     * @return .
     */
    public static SpannableStringBuilder setBoldBlackRight(Context context, int strId, String partStr) {
        String text = context.getString(strId, partStr);
        int start = text.length() - partStr.length();
        int end = text.length();
        return setBoldBlack(text, start, end);
    }

    /**
     * 设置黑体加粗
     *
     * @param start .
     * @param end   .
     * @param text  .
     * @return .
     */
    public static SpannableStringBuilder setBoldBlack(String text, int start, int end) {
        if (text == null || text.length() == 0) {
            return new SpannableStringBuilder("");
        }
        if (start < 0 || start >= text.length() || end == 0 || end > text.length()) {
            return new SpannableStringBuilder("");
        }
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        setPartTextColor(builder, 0xff000000, start, end);
        setPartTextBold(builder, start, end);
        return builder;
    }

    /**
     * 设置部分文字颜色
     *
     * @param style .
     * @param color .
     */
    public static void setPartTextColor(SpannableStringBuilder style, int color, int start, int end) {
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(color);
        style.setSpan(foregroundColorSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    /**
     * 设置部分文字加粗
     *
     * @param style .
     */
    public static void setPartTextBold(SpannableStringBuilder style, int start, int end) {
        StyleSpan styleSpan = new StyleSpan(Typeface.BOLD);
        style.setSpan(styleSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
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
    public static void clipboard(Context context, String name, String text) {
        ClipboardManager cbm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(name, text);
        cbm.setPrimaryClip(clipData);
//        CommonUtils.showToast(context, context.getString(R.string.copy_done));
    }
}
