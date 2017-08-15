package com.jen.easy;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.jen.easy.aop.factory.DynamicProxyFactory;
import com.jen.easy.log.Logcat;
import com.jen.easy.sqlite.DBConstant;
import com.jen.easy.sqlite.DBDaoManager;
import com.jen.easy.sqlite.DBHelperManager;
import com.jen.easy.sqlite.imp.DBDaoImp;
import com.jen.easy.sqlite.imp.DBHelperImp;
import com.jen.easy.util.FileDecryptManager;

import org.junit.Test;
import org.junit.runner.RunWith;

import static com.jen.easy.aop.factory.DynamicProxyFactory.getProx;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

//        assertEquals("com.jen.easy", appContext.getPackageName());
        DBHelperImp DBtemp = new DBHelperManager(appContext);
        DBDaoImp DBDtemp = new DBDaoManager(appContext);

        DynamicProxyFactory proxyDB = getProx();
        DBHelperImp DB = (DBHelperImp) proxyDB.bind(DBtemp);
        String path = appContext.getDatabasePath(DB.getName()).getPath();
        DBConstant.PASSWORD = "abcd";
        proxyDB.setBeforeMethod(FileDecryptManager.class, new Object[]{path, DBConstant.PASSWORD});
        proxyDB.setAfterMethod(FileDecryptManager.class, new Object[]{path, DBConstant.PASSWORD});

        DB.create();
        Logcat.d("DB=" + DB);

    }
}
