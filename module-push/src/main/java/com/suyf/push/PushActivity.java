package com.suyf.push;

import android.app.Activity;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.suyf.tiny.page.PageManager;

public class PushActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webView = new WebView(this);
        setContentView(webView);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });
        webView.loadUrl("https://www.baidu.com");
    }

    @Override
    protected void onDestroy() {
        //不支持开启多进程，因为多进程下getPageContext()一直为空
        if (PageManager.instance().getPageContext() == null) {
            Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
            startActivity(intent);
        }
        super.onDestroy();
    }
}
