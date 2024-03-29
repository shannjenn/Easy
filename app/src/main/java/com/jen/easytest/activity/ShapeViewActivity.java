package com.jen.easytest.activity;

import android.os.Bundle;
import android.view.View;

import com.jen.easy.EasyBindClick;
import com.jen.easy.log.EasyLog;
import com.jen.easytest.R;
import com.jen.easytest.model.Book;
import easybase.EasyActivity;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public class ShapeViewActivity extends EasyActivity {

//    @EasyBindId(R.id.shapeTextView)
//    EasyShapeTextView shapeTextView;

//    @EasyBindId(R.id.easy_tv_shape)
//    EasyShapeTextView easy_tv_shape;
//    @EasyBindId(R.id.easy_item_layout)
//    EasyItemLayout easy_item_layout;
    //    @EasyBindId(R.id.rote_view)
//    EasyRotateView rote_view;
//    @EasyBindId(R.id.rote_view2)
//    EasyRotateView rote_view2;

    Book book;

    @Override
    public int bindView() {
        return R.layout.activity_shape_view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        book = new Book();
        book.setName("thread test id");
//        new MyThread(book).start();
    }


    @EasyBindClick({R.id.shapeTextView_check, R.id.shapeTextView_button})
    @Override
    protected void onBindClick(View view) {
        switch (view.getId()) {
            case R.id.shapeTextView_check: {
                EasyLog.d("shapeTextView_check click---------------");
                break;
            }
            case R.id.shapeTextView_button: {
                EasyLog.d("shapeTextView_button click---------------");
                break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EasyLog.d(book.getName() + " onDestroy = " + book);
    }

//    private static class MyThread extends Thread {
//        Book book;
//
//        public MyThread(Book book) {
//            this.book = book;
//        }
//
//        @Override
//        public void run() {
//            super.run();
//            while (true) {
//                try {
//                    Thread.sleep(2000);
//                    EasyLog.d(book.getName() + " " + book);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

}
