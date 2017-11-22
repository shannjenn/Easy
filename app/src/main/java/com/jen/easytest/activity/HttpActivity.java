package com.jen.easytest.activity;

import android.os.Bundle;
import android.view.View;

import com.jen.easy.http.HttpBaseRequest;
import com.jen.easy.http.imp.HttpBaseListener;
import com.jen.easytest.R;
import com.jen.easyui.activity.EasyBaseActivity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import static junit.framework.Assert.fail;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public class HttpActivity extends EasyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);
    }

    @Override
    protected void intDataBeforeView() {
        HttpBaseRequest baseRequest = new HttpBaseRequest();
        baseRequest.setBseListener(httpBaseListener);

//        Class clazz = httpBaseListener.getClass().getDeclaringClass();
        Type type = httpBaseListener.getClass().getGenericSuperclass();//获取超类T类型
        if (!(type instanceof ParameterizedType)) {
//            return;
        }
        Type classType = ((ParameterizedType) type).getActualTypeArguments()[0];//获取T值实体类型
        if (!(classType instanceof Class)) {
            fail(classType + " 不是Class类型");
//            return;
        }
    }

    @Override
    protected void initViews() {
    }

    @Override
    protected void loadDataAfterView() {

    }

    @Override
    protected void onBindClick(View view) {

    }

    private HttpBaseListener<HttpBaseResponse> httpBaseListener = new HttpBaseListener<HttpBaseResponse>() {
        @Override
        public void success(int flagCode, String flag, HttpBaseResponse response) {

        }

        @Override
        public void fail(int flagCode, String flag, String msg) {

        }
    };

    class HttpBaseResponse {
        String id;


        Class objClass;
    }

}
