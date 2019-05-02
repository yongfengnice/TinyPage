package com.suyf.dialog.page;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.suyf.dialog.R;
import com.suyf.tiny.page.PageIntent;
import com.suyf.tiny.page.PageManager;
import com.suyf.tiny.page.PageResult;
import com.suyf.tiny.page.TinyPage;

public class DialogDemoPage extends TinyPage implements View.OnClickListener {
    private TextView mTvText;
    private Button mBtnDialog;
    private Button mBtnPop;
    private Button mBtnDrawer;
    private Button mBtnNotify;
    private PopupWindow mPopupWindow;
    private DrawerLayout mDrawerLayout;
    private LinearLayout mLinearLayout;

    private void assignViews() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mTvText = (TextView) findViewById(R.id.tv_text);
        mBtnDialog = (Button) findViewById(R.id.btn_ok);
        mBtnPop = (Button) findViewById(R.id.btn_ok_2);
        mBtnDrawer = (Button) findViewById(R.id.btn_ok_3);
        mBtnNotify = (Button) findViewById(R.id.btn_ok_4);
        mLinearLayout = findViewById(R.id.ll_drawer);
    }

    @Override
    public void onCreate(PageIntent pageIntent) {
        super.onCreate(pageIntent);
        setContentView(R.layout.page_tab_dialog);
        assignViews();
        mTvText.setText(pageIntent.getString("data"));
        mBtnDialog.setOnClickListener(this);
        mBtnPop.setOnClickListener(this);
        mBtnDrawer.setOnClickListener(this);
        mBtnNotify.setOnClickListener(this);

        mLinearLayout.setOnClickListener(this);
        for (int i = 0; i < mLinearLayout.getChildCount(); i++) {
            mLinearLayout.getChildAt(i).setOnClickListener(this);
        }

        mDrawerLayout.setScrimColor(Color.GRAY);
        mDrawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Toast.makeText(getActivity(), "drawer open", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Toast.makeText(getActivity(), "drawer close", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == mBtnDialog) {
            new AlertDialog.Builder(getActivity())
                    .setTitle("dialog title")
                    .setMessage("dialog message")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getActivity(), "click ok", Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getActivity(), "click no", Toast.LENGTH_SHORT).show();
                }
            })
                    .create()
                    .show();
        } else if (v == mBtnPop) {
            if (mPopupWindow == null) {
                mPopupWindow = new PopupWindow(getActivity());
                mPopupWindow.setWidth(((View) v.getParent()).getWidth());
                mPopupWindow.setHeight(300);
                mPopupWindow.setOutsideTouchable(true);
                TextView textView = new TextView(getActivity());
                textView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));//match parent
                textView.setText("我是PopWindow的内容,点击发送一条广播");
                textView.setGravity(Gravity.CENTER);
                textView.setOnClickListener(this);
                mPopupWindow.setContentView(textView);
                mPopupWindow.setBackgroundDrawable(new ColorDrawable(0xff00ff00));
            }
            mPopupWindow.showAsDropDown(v);
        } else if (mPopupWindow != null && mPopupWindow.getContentView() == v) {
            Toast.makeText(getActivity(), ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
        } else if (v == mBtnDrawer) {
            mDrawerLayout.openDrawer(Gravity.END);
        } else if (v == mBtnNotify) {
            showNotification();
        } else if (v instanceof TextView) {
            Toast.makeText(getActivity(), ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
            PageIntent pageIntent = new PageIntent();
            pageIntent.putExtra("data", "我是广播消息");
            sendBroadcast(pageIntent, "action_name");
        }
    }

    private void showNotification() {
        NotificationManager manager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(getActivity());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "tiny_01";
            NotificationChannel mChannel = new NotificationChannel(channelId, channelId, NotificationManager.IMPORTANCE_LOW);
            manager.createNotificationChannel(mChannel);
            builder.setChannelId(channelId);
        }
        builder.setTicker("你要新的消息");
        builder.setContentTitle("我的标题");
        builder.setContentText("我的内容，点击打开推送页面");
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setAutoCancel(true);
        Intent intent = new Intent();
        try {
            Object activity = PageManager.instance().getClassNameByKey("PushActivity");
            Class<?> aClass = Class.forName(activity.toString());
            intent.setClass(getActivity(), aClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        builder.setContentIntent(PendingIntent.getActivity(getActivity(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT));
        manager.notify(0, builder.build());
    }


    @Override
    public void onBackPressed() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            return;
        }
        PageResult pageResult = new PageResult();
        pageResult.putExtra("data", "data from dialog tab page");
        setPageResult(pageResult);
        super.onBackPressed();
    }

}
