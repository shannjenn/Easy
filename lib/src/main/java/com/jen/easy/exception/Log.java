package com.jen.easy.exception;

import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteException;

import com.jen.easy.log.EasyLog;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：网络请求运行状态
 */
abstract class Log {

    protected static void d(String tag, String msg) {
        EasyLog.d(tag, msg);
    }

    protected static void i(String tag, String msg) {
        EasyLog.i(tag, msg);
    }

    protected static void w(String tag, String msg) {
        EasyLog.w(tag, msg);
    }

    protected static void e(String tag, String msg) {
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
                case ExceptionType.IllegalStateException:
                    throw new IllegalStateException(msg);
                case ExceptionType.SQLiteCantOpenDatabaseException:
                    throw new SQLiteCantOpenDatabaseException(msg);
                case ExceptionType.SQLiteException:
                    throw new SQLiteException(msg);
                case ExceptionType.JSONException:
                    throw new JSONException(msg);
                case ExceptionType.InvocationTargetException:
                    throw new RuntimeException(msg);
                case ExceptionType.IOException:
                    throw new IOException(msg);
                case ExceptionType.FileNotFoundException:
                    throw new FileNotFoundException(msg);
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
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
