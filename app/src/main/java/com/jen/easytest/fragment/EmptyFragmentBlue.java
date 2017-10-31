package com.jen.easytest.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jen.easytest.R;

/**
 * Created by Administrator on 2017/10/31.
 */

public class EmptyFragmentBlue extends Fragment{


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_empty_blue, null);

    }
}
