package com.suyf.home.page;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.suyf.home.R;
import com.suyf.home.base.BasePage;
import com.suyf.home.presenter.RegisterPresenter;
import com.suyf.tiny.page.PageIntent;
import com.suyf.tiny.page.PageResult;

public class RegisterPage extends BasePage<RegisterPresenter> implements View.OnClickListener {

    private EditText mEtName;
    private EditText mEtPsw;
    private Button mBtnOk;

    @Override
    protected int setLayout() {
        return R.layout.page_register;
    }

    @Override
    protected void initView() {
        mEtName = (EditText) findViewById(R.id.et_name);
        mEtPsw = (EditText) findViewById(R.id.et_psw);
        mBtnOk = (Button) findViewById(R.id.btn_ok);
    }

    @Override
    protected void initEvent() {
        mBtnOk.setOnClickListener(this);
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }

    public void showIntentText(PageIntent pageIntent) {
        mEtName.setText(pageIntent.getString("name"));
        mEtPsw.setText(pageIntent.getString("psw"));
    }

    @Override
    public void onClick(View v) {
        if (v == mBtnOk) {
            String name = mEtName.getText().toString();
            String psw = mEtPsw.getText().toString();
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(psw)) {
                Toast.makeText(getActivity(), "请输入用户名或者密码", Toast.LENGTH_SHORT).show();
                return;
            }
            PageResult pageResult = new PageResult();
            pageResult.putExtra("name", name);
            pageResult.putExtra("psw", psw);
            setPageResult(pageResult);
            finishPage();
        }
    }
}
