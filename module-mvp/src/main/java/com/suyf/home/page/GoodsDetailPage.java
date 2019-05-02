package com.suyf.home.page;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.suyf.home.R;
import com.suyf.home.base.BasePage;
import com.suyf.home.base.BasePresenter;
import com.suyf.home.presenter.GoodsDetailPresenter;
import com.suyf.tiny.page.PageIntent;
import com.suyf.tiny.page.PageManager;

public class GoodsDetailPage extends BasePage {
    private TextView mTvText;
    private Button mBtnOk;
    private Button mBtnOk2;
    private Button mBtnOk3;
    private Button mBtnOk4;

    @Override
    protected int setLayout() {
        return R.layout.page_goods_detail;
    }

    @Override
    protected void initView() {
        mTvText = (TextView) findViewById(R.id.tv_text);
        mBtnOk = (Button) findViewById(R.id.btn_ok);
        mBtnOk2 = (Button) findViewById(R.id.btn_ok_2);
        mBtnOk3 = (Button) findViewById(R.id.btn_ok_3);
        mBtnOk4 = (Button) findViewById(R.id.btn_ok_4);
        mTvText.setText(getPageIntent().getString("data"));
    }

    @Override
    protected void initEvent() {
        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PageIntent intent = new PageIntent(GoodsDetailPage.this, HomePage.class);
                intent.setLaunchMode(PageIntent.MODE_SINGLE_TASK);
                intent.putExtra("data", "from goods detail page to home page");
                startPage(intent);
            }
        });
        mBtnOk2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PageIntent intent = new PageIntent(GoodsDetailPage.this, GoodsDetailPage.class);
                intent.setLaunchMode(PageIntent.MODE_NORMAL);//默认值
                intent.putExtra("data", "PageIntent.MODE_NORMAL");
                startPage(intent);
            }
        });
        mBtnOk3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PageIntent intent = new PageIntent(GoodsDetailPage.this, GoodsDetailPage.class);
                intent.setLaunchMode(PageIntent.MODE_SINGLE_TOP);
                intent.putExtra("data", "PageIntent.MODE_SINGLE_TOP");
                startPage(intent);
            }
        });
        mBtnOk4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PageIntent pageIntent = new PageIntent();
                pageIntent.putExtra("data", "我是来自GoodsDetail页面的广播消息");
                sendBroadcast(pageIntent, "action_name");
            }
        });
        findViewById(R.id.btn_ok_5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PageManager.instance().exitApp();
            }
        });
    }

    @Override
    protected BasePresenter createPresenter() {
        return new GoodsDetailPresenter();
    }

    @Override
    public void onNewPageIntent(PageIntent pageIntent) {
        super.onNewPageIntent(pageIntent);
        Toast.makeText(getActivity(), pageIntent.getString("data"), Toast.LENGTH_SHORT).show();
    }
}
