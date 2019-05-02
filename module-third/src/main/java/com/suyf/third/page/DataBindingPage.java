package com.suyf.third.page;

import android.view.View;

import com.suyf.third.R;
import com.suyf.third.bean.UserBean;
import com.suyf.third.databinding.PageDataBindingBinding;
import com.suyf.tiny.page.PageIntent;
import com.suyf.tiny.page.TinyPage;

public class DataBindingPage extends TinyPage {

    private PageDataBindingBinding mBinding;

    @Override
    public void onCreate(PageIntent pageIntent) {
        super.onCreate(pageIntent);
        setContentView(R.layout.page_data_binding);
        mBinding = PageDataBindingBinding.bind(getContentView());

        UserBean userBean = new UserBean();
        userBean.setUserName("marry");
        userBean.setAge(18);
        mBinding.setUserBean(userBean);
        mBinding.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == mBinding.btnOk) {
                    UserBean userBean = mBinding.getUserBean();
                    userBean.setAge(userBean.getAge() + 1);
                    mBinding.setUserBean(userBean);
                }
            }
        });
    }
}
