package com.jen.easy.exception;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：网络请求运行状态
 */
public class Throw {

    /**
     * 异常捕获
     * @param type 抛出异常
     * @param msg  抛出类型
     */
    public static void exception(ExceptionType type, String msg) {
        try {
            switch (type) {
                case NullPointerException:
                    throw new NullPointerException(msg);
                case ClassCastException:
                    throw new ClassCastException(msg);
                case NumberFormatException:
                    throw new NumberFormatException(msg);
                case IllegalArgumentException:
                    throw new IllegalArgumentException(msg);
                case RuntimeException:
                    throw new RuntimeException(msg);
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
        }
    }
}
