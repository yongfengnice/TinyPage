package com.suyf.home.page;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.suyf.home.R;
import com.suyf.home.base.BasePage;
import com.suyf.home.presenter.MvpPresenter;
import com.suyf.tiny.page.PageIntent;
import com.suyf.tiny.page.PageReceiver;
import com.suyf.tiny.page.PageResult;

public class MvpPage extends BasePage<MvpPresenter> implements View.OnClickListener {
    private TextView mTvText;
    private Button mBtnOk;
    private Button mBtnOk2;
    private Button mBtnOk3;
    private Button mBtnOk4;

    @Override
    protected int setLayout() {
        return R.layout.page_tab_home;
    }

    @Override
    protected void initView() {
        mTvText = (TextView) findViewById(R.id.tv_text);
        mBtnOk = (Button) findViewById(R.id.btn_ok);
        mBtnOk2 = (Button) findViewById(R.id.btn_ok_2);
        mBtnOk3 = (Button) findViewById(R.id.btn_ok_3);
        mBtnOk4 = (Button) findViewById(R.id.btn_ok_4);

    }

    public void showIntentInfo(PageIntent pageIntent) {
        mTvText.setText(pageIntent.getString("data"));
    }

    @Override
    protected void initEvent() {
        mBtnOk.setOnClickListener(this);
        mBtnOk2.setOnClickListener(this);
        mBtnOk3.setOnClickListener(this);
        mBtnOk4.setOnClickListener(this);
    }

    @Override
    protected MvpPresenter createPresenter() {
        return new MvpPresenter();
    }

    @Override
    public void onClick(View v) {
        if (v == mBtnOk) {
            PageResult pageResult = new PageResult();
            pageResult.putExtra("data", "finish current page,return data to pre page");
            setPageResult(pageResult);
            startPage(new PageIntent(this, GoodsListPage.class));
            finishPage();
        } else if (v == mBtnOk2) {
            PageResult pageResult = new PageResult();
            pageResult.putExtra("data", "finish current page,return data to pre page");
            setPageResult(pageResult);
            startPage(new PageIntent(this, GoodsListPage.class));
        } else if (v == mBtnOk3) {
            registerPageReceiver(new PageReceiver() {
                @Override
                public void onBroadcastReceive(PageIntent pageIntent, String action) {
                    mTvText.setText(pageIntent.getString("data"));
                }
            }, "action_name");//action_name同发送者的action_name
        } else if (v == mBtnOk4) {
            unregisterPageReceiver("action_name");
        }
    }
}
