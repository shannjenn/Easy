package com.jen.easyui.util;

import android.support.annotation.NonNull;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/*说明：
 G 年代指示符(AD)
 y 年(yy:10 y/yyy/yyyy:2010)
 M 月(M:1 MM:01 MMM:Jan MMMM:January MMMMM:J)
 L 独立月(L:1 LL:01 LLL:Jan LLLL:January LLLLL:J)
 d 一个月中的第几日(只需此一个字符，输出如：10)
 h 时(只需此一个字符，输出如：上/下午 1 ~ 12)
 H 时(只需此一个字符，输出如：0 ~ 23)
 k 一天中的第几个小时(只需此一个字符，输出如：1 ~ 24)
 K 时(上/下午 0 ~ 11)
 m 一小时中的第几分(只需此一个字符，输出如：30)
 s 一分钟中的第几秒(只需此一个字符，输出如：55)
 S 毫秒(只需此一个字符，输出如：978)
 E 星期几(E/EE/EEE:Tue, EEEE:Tuesday, EEEEE:T)
 c 独立星期几(c/cc/ccc:Tue, cccc:Tuesday, ccccc:T)
 D 一年中的第几天(只需此一个字符，输出如：189)
 F 一月中的第几个星期几(只需此一个字符，输出如：2)
 w 一年中的第几个星期(只需此一个字符，输出如：27)
 W 一月中的第几个星期(只需此一个字符，输出如：1)
 a 上/下午标记符(只需此一个字符，输出如：AM/PM)
 Z 时区(RFC822)(Z/ZZ/ZZZ:-0800 ZZZZ:GMT-08:00 ZZZZZ:-08:00)
 z 时区(z/zz/zzz:PST zzzz:Pacific Standard Time)
 要忽略的字符都要用单引号('')括住！
 SimpleDateFormat sdf = new SimpleDateFormat("'日期'yyyy-MM-dd'Time'HH:mm:ss'Z'");*/
public class EasyDateFormatUtil {
    private final String TAG = EasyDateFormatUtil.class.getSimpleName();
    private SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);//默认
    private static EasyDateFormatUtil me;

    public EasyDateFormatUtil() {

    }

    public EasyDateFormatUtil(@NonNull String dateFormat) {
        mFormat = new SimpleDateFormat(dateFormat, Locale.SIMPLIFIED_CHINESE);
    }

    public static EasyDateFormatUtil getIns() {
        if (me == null) {
            synchronized (EasyDateFormatUtil.class) {
                if (me == null) {
                    me = new EasyDateFormatUtil();
                }
            }
        }
        return me;
    }

    /**
     * 设置时间格式
     *
     * @param dateFormat 格式(如:yyyy-MM-dd HH:mm:ss)
     */
    public EasyDateFormatUtil setFormat(@NonNull String dateFormat) {
        mFormat = new SimpleDateFormat(dateFormat, Locale.SIMPLIFIED_CHINESE);
        return this;
    }

    /**
     * @param time 时间(13位时间戳)
     * @return
     */
    public String format(long time) {
        Date date = new Date(time);
        return mFormat.format(date);
    }

    /**
     * 时间转字符串
     *
     * @param timeStamp 时间戳
     * @return 格式化时间
     */
    public String format(String timeStamp) {
        if (timeStamp == null) {
            Log.w(TAG, "时间戳为空");
            return null;
        }
        long time;
        try {
            time = Long.parseLong(timeStamp);
        } catch (NumberFormatException e) {
            Log.w(TAG, "时间戳错误");
            return null;
        }
        Date date = new Date(time);
        return mFormat.format(date);
    }

    /**
     * @param date 日期
     * @return
     */
    public String format(@NonNull Date date) {
        return mFormat.format(date);
    }

    /**
     * @param calendar 日期
     * @return
     */
    public String format(@NonNull Calendar calendar) {
        return mFormat.format(calendar.getTime());
    }


    /**
     * @param formatDate 如(yyyy-MM-dd HH:mm:ss)
     * @return
     */
    public Date parseToDate(String formatDate) {
        Date date = null;
        try {
            date = mFormat.parse(formatDate);
        } catch (ParseException e) {
            Log.w(TAG, "时间格式不正确");
        }
        return date;
    }

    /**
     * @param formatDate 如(yyyy-MM-dd HH:mm:ss)
     * @return
     */
    public long parseToMillisecond(String formatDate) {
        long time = -1;
        Date date = parseToDate(formatDate);
        if (date != null) {
            time = date.getTime();
        }
        return time;
    }

    /**
     * 将北京时间转为当前时区时间
     *
     * @param time 时间
     * @return 格式化时间
     */
    public String beiJingToLocal(long time) {
        Date date = new Date(time);

        TimeZone oldZone = TimeZone.getTimeZone("GMT+8:00");
        TimeZone newZone = TimeZone.getDefault();
        int DST = newZone.getDSTSavings();

        int timeOffset = oldZone.getRawOffset() - (newZone.getRawOffset() + DST);
        Date dateTmp = new Date(date.getTime() - timeOffset);
        return mFormat.format(dateTmp);
    }

    /**
     * 将北京时间转为当前时区时间
     *
     * @param date 时间
     * @return 时间
     */
    public Date beiJingToLocal(Date date) {
        if (date == null)
            return null;

        TimeZone oldZone = TimeZone.getTimeZone("GMT+8:00");
        TimeZone newZone = TimeZone.getDefault();
        int DST = newZone.getDSTSavings();

        int timeOffset = oldZone.getRawOffset() - (newZone.getRawOffset() + DST);
        return new Date(date.getTime() - timeOffset);
    }

    /**
     * 将当前时区时间转为北京时间
     *
     * @param time 时间
     * @return 时间
     */
    public String localToBeiJing(long time) {
        Date date = new Date(time);

        TimeZone bjZone = TimeZone.getTimeZone("GMT+8:00");
        TimeZone loadZone = TimeZone.getDefault();
        int DST = loadZone.getDSTSavings();

        int timeOffset = (loadZone.getRawOffset() + DST) - bjZone.getRawOffset();
        Date dateTmp = new Date(date.getTime() - timeOffset);
        return format(dateTmp);
    }

    /**
     * 将当前时区时间转为北京时间
     *
     * @param date 时间
     * @return 时间
     */
    public Date localToBeiJing(Date date) {
        if (date == null)
            return null;

        TimeZone bjZone = TimeZone.getTimeZone("GMT+8:00");
        TimeZone loadZone = TimeZone.getDefault();
        int DST = loadZone.getDSTSavings();

        int timeOffset = (loadZone.getRawOffset() + DST) - bjZone.getRawOffset();
        return new Date(date.getTime() - timeOffset);
    }

}
