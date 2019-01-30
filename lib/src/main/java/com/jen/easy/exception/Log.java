package com.jen.easy.exception;

import com.jen.easy.log.EasyLog;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：网络请求运行状态
 */
abstract class Log {
    protected static String tag;

    public static void d(String msg) {
        EasyLog.d(tag, msg);
    }

    public static void i(String msg) {
        EasyLog.i(tag, msg);
    }

    public static void w(String msg) {
        EasyLog.w(tag, msg);
    }

    public static void e(String msg) {
        EasyLog.e(tag, msg);
    }

    /**
     * 异常捕获
     *
     * @param type 抛出异常
     * @param msg  抛出类型
     */
    public static void exception(@ExceptionType int type, String msg) {
        try {
            switch (type) {
                case ExceptionType.NullPointerException:
                    throw new NullPointerException(msg);
                case ExceptionType.ClassCastException:
                    throw new ClassCastException(msg);
                case ExceptionType.NumberFormatException:
                    throw new NumberFormatException(msg);
                case ExceptionType.IllegalArgumentException:
                    throw new IllegalArgumentException(msg);
                case ExceptionType.RuntimeException:
                    throw new RuntimeException(msg);
                case ExceptionType.InstantiationException:
                    throw new InstantiationException(msg);
                case ExceptionType.IllegalAccessException:
                    throw new IllegalAccessException(msg);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
