package com.jen.easytest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.jen.easy.EasyMain;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 创建人：ShannJenn
 * 时间：2017/8/19.
 */

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        EasyMain.LOG.start();

        //assertEquals("com.jen.easy", appContext.getPackageName());
    }
}
