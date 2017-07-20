package com.jen.easy.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * 数据增删改查
 * Created by Jen on 2017/7/20.
 */

public class EasyDBDao {


    /**
     * @param clazz 要查找的对象
     * @param id
     * @return
     */
    public Object searchById(Class clazz, String id) {
        String tableName = DBReflectMan.getTableName(clazz);
        if (tableName == null) {
            return null;
        }
        Map<String, Object> objectMap = DBReflectMan.getFields(clazz);
        List<String> primaryKey = (List<String>) objectMap.get("primaryKey");
        Map<String, Integer> column = (Map<String, Integer>) objectMap.get("column");
        Map<String, Field> fieldName = (Map<String, Field>) objectMap.get("fieldName");


        int study = 0;
        Cursor cursor = null;
        try {
            SQLiteDatabase db = DBHelper.getInstance().getReadDatabse();

            String sql = "select count(*) from SentenceInfo"
                    + " where CID='" + id + "' and E='N'";
            cursor = db.rawQuery(sql, null);
            if (cursor.getCount() == 0) {
                return 0;
            }
            cursor.moveToFirst();
            study = cursor.getInt(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != cursor)
                cursor.close();
        }
        return study;
    }
}
