package com.toqa.githubrepos.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.toqa.githubrepos.R;

import butterknife.ButterKnife;

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView {

    private T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getViewResourceID());

        ButterKnife.bind(this);
        initView();
    }

    protected abstract int getViewResourceID();

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void initView() {
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showErrorMsg(String error) {

    }
}
