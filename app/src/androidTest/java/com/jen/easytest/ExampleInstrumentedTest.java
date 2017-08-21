package com.jen.easytest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.jen.easy.EasyMain;
import com.jen.easytest.demo.Student;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

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

        assertEquals("com.jen.easytest", appContext.getPackageName());
        testHttp();
    }


    private void testHttp(){
        Student student = new Student();
        student.httpParam.url = "http://47.92.134.67:83/product-type";
        EasyMain.HTTP.start(student);
    }
}
