package com.suyf.suapp;

import android.os.Bundle;
import android.view.ViewGroup;

import com.suyf.another.Module2Activity;
import com.suyf.dialog.page.DialogDemoPage;
import com.suyf.home.base.BaseActivity;
import com.suyf.home.page.LoginPage;
import com.suyf.push.PushActivity;
import com.suyf.third.page.ThirdPartPage;
import com.suyf.tiny.page.PageIntent;
import com.suyf.tiny.page.PageManager;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PageManager.instance().init(this, (ViewGroup) findViewById(android.R.id.content));
        registerClass();
        PageManager.instance().startPage(new PageIntent(LoginPage.class));
    }

    private void registerClass() {
        //模块间跳转需要先注册类名,可以使用ctrl+shift+f或者command+shift+f全局搜索哪里使用了这些classKey
        PageManager.instance().registerClass("Module2Activity", Module2Activity.class.getName());
        PageManager.instance().registerClass("DialogDemoPage", DialogDemoPage.class.getName());
        PageManager.instance().registerClass("ThirdPartPage", ThirdPartPage.class.getName());
        PageManager.instance().registerClass("PushActivity", PushActivity.class.getName());
    }

}
