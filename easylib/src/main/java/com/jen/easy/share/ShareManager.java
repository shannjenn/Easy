package com.jen.easy.share;

/**
 * 创建人：ShannJenn
 * 时间：2017/8/14.
 */

public class ShareManager {

    /*private final String TAG = ShareManager.class.getSimpleName();
    private static final String xmlFileName = "ucsClientConfig";
    private static ShareManager self = null;
    private static Context context = null;
    private static SharedPreferences config = null;
    private SharedPreferences.Editor editor = null;

    public static ShareManager getInstance(Context ctx) {
        if (self == null) {
            self = new ShareManager();
            context = ctx;
            config = context.getSharedPreferences(xmlFileName, 0);
        }
        return self;
    }

    *//**
     * 获取string
     *
     * @param name
     * @param defaultValue
     * @return
     *//*
    public String getString(String name, String defaultValue) {
        return config.getString(name, defaultValue);
    }

    *//**
     * 保存string
     *
     * @param name
     * @param value
     *//*
    public void setString(String name, String value) {
        editor = config.edit();
        editor.putString(name, value);
        editor.commit();
    }

    *//**
     * 获取boolean
     *
     * @param name
     * @return
     *//*
    public boolean getBoolean(String name) {
        return config.getBoolean(name, false);
    }

    *//**
     * 保存boolean
     *
     * @param name
     * @param value
     *//*
    public void setBoolean(String name, boolean value) {
        editor = config.edit();
        editor.putBoolean(name, value);
        editor.commit();
    }

    *//**
     * 获取int
     *
     * @param name
     * @param defau
     * @return
     *//*
    public int getInt(String name, int defau) {
        return config.getInt(name, defau);
    }

    *//**
     * 保存int
     *
     * @param name
     * @param value
     *//*
    public void setInt(String name, int value) {
        editor = config.edit();
        editor.putInt(name, value);
        editor.commit();
    }

    *//**
     * 保存list
     *
     * @param name
     * @param list
     * @return
     *//*
    public <T> boolean setList(String name, List<T> list) {
        try {
            String value = StringToList.List2String(list);
            setString(name, value);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            LogTools.d(TAG, "setList error =========");
            return false;
        }
    }

    *//**
     * 获取list
     *
     * @param name
     * @return
     *//*
    public <T> List<T> getList(String name) {
        List<T> valueLlist = new ArrayList<T>();
        String value = getString(name, null);
        if (null == value) {
            return valueLlist;
        }
        try {
            List<T> list = StringList.String2List(value);
            if (null != list)
                valueLlist.addAll(list);
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return valueLlist;
    }

    *//**
     * 获取object
     *
     * @param name
     * @return
     *//*
    public Object getObject(String name) {
        String value = getString(name, null);
        if (null == value) {
            return null;
        }
        try {
            Object obj = StringList.String2Object(value);
            return obj;
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    *//**
     * 设置object
     *
     * @param name
     * @param obj
     * @return
     *//*
    public boolean setObject(String name, Object obj) {
        try {
            String value = StringList.object2String(obj);
            if (null == value) {
                return false;
            }
            setString(name, value);
        } catch (IOException e) {
            e.printStackTrace();
            LogTools.d(TAG, "setObject error =========");
            return false;
        }
        return true;
    }

    public void removeValue(String name) {
        editor = config.edit();
        editor.remove(name);
        editor.commit();
    }*/
}
