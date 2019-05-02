package com.suyf.home.page;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.suyf.home.R;
import com.suyf.home.base.BasePage;
import com.suyf.home.presenter.LoginPresenter;
import com.suyf.tiny.page.PageIntent;
import com.suyf.tiny.page.PageResult;

public class LoginPage extends BasePage<LoginPresenter> implements View.OnClickListener {
    private EditText mEtName;
    private EditText mEtPsw;
    private Button mBtnOk;
    private TextView mTvRegister;

    @Override
    protected int setLayout() {
        return R.layout.page_login;
    }

    @Override
    protected void initView() {
        mEtName = (EditText) findViewById(R.id.et_name);
        mEtPsw = (EditText) findViewById(R.id.et_psw);
        mBtnOk = (Button) findViewById(R.id.btn_ok);
        mTvRegister = (TextView) findViewById(R.id.tv_register);
    }

    @Override
    protected void initEvent() {
        mBtnOk.setOnClickListener(this);
        mTvRegister.setOnClickListener(this);
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void onClick(View v) {
        if (v == mBtnOk) {
            startPage(new PageIntent(this, HomePage.class));
            finishPage();
        } else if (v == mTvRegister) {
            PageIntent pageIntent = new PageIntent(this, RegisterPage.class);
            pageIntent.putExtra("register", "register page");
            pageIntent.putExtra("name", mEtName.getText());
            pageIntent.putExtra("psw", mEtPsw.getText());
            startPageForResult(pageIntent);
        }
    }

    @Override
    public void onPageResult(PageResult pageResult) {
        super.onPageResult(pageResult);
        if (pageResult != null) {
            mEtName.setText(pageResult.getString("name"));
            mEtPsw.setText(pageResult.getString("psw"));
        }
    }

}
