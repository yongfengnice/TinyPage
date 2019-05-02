package com.suyf.home.page;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.suyf.home.R;
import com.suyf.home.base.BasePage;
import com.suyf.home.fragment.TabFrag;
import com.suyf.home.presenter.HomePresenter;
import com.suyf.tiny.page.PageIntent;
import com.suyf.tiny.page.PageResult;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends BasePage<HomePresenter> implements View.OnClickListener {
    private ViewPager mViewPager;
    private LinearLayout mLinearLayout;
    private List<TabFrag> mTabFragList;

    @Override
    protected int setLayout() {
        return R.layout.page_home;
    }

    @Override
    protected void initView() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mLinearLayout = (LinearLayout) findViewById(R.id.ll_bottom);

        for (int i = 0; i < mLinearLayout.getChildCount(); i++) {
            mLinearLayout.getChildAt(i).setTag(i);
            mLinearLayout.getChildAt(i).setOnClickListener(this);
        }

        mTabFragList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            TabFrag tabFrag = TabFrag.newInstance("text:" + (i + 1));
            mTabFragList.add(i, tabFrag);
        }

        FragmentActivity activity = (FragmentActivity) getActivity();
        mViewPager.setAdapter(new FragmentPagerAdapter(activity.getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mTabFragList.get(i);
            }

            @Override
            public int getCount() {
                return mTabFragList.size();
            }
        });

    }

    @Override
    protected void initEvent() {
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                for (int i = 0; i < mLinearLayout.getChildCount(); i++) {
                    mLinearLayout.getChildAt(i).setBackgroundColor(0xffffffff);
                }
                mLinearLayout.getChildAt(position).setBackgroundColor(0xff00ff00);
            }
        });
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    public void onPageResult(PageResult pageResult) {
        super.onPageResult(pageResult);
        mTabFragList.get(mViewPager.getCurrentItem()).onPageResult(pageResult);
    }

    @Override
    public void onNewPageIntent(PageIntent pageIntent) {
        super.onNewPageIntent(pageIntent);
        Toast.makeText(getActivity(), pageIntent.getString("data"), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v.getTag() instanceof Integer) {
            mViewPager.setCurrentItem(Integer.parseInt(v.getTag().toString()));
        }
    }

}
