package com.suyf.home.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.suyf.home.R;
import com.suyf.home.page.MvpPage;
import com.suyf.tiny.page.PageIntent;
import com.suyf.tiny.page.PageManager;
import com.suyf.tiny.page.PageResult;

public class TabFrag extends Fragment implements View.OnClickListener {
    private TextView mTvText;
    private int mColor = Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));

    public static TabFrag newInstance(String text) {
        Bundle args = new Bundle();
        args.putString("text", text);
        TabFrag fragment = new TabFrag();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setBackgroundColor(mColor);
        mTvText = (TextView) view.findViewById(R.id.tv_text);
        mTvText.setText(getArguments().getString("text"));
        view.findViewById(R.id.btn_ok).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_ok) {
            ViewPager viewPager = (ViewPager) getView().getParent();
            PageIntent pageIntent;
            switch (viewPager.getCurrentItem()) {
                case 0:
                    pageIntent = new PageIntent(MvpPage.class);
                    pageIntent.putExtra("data", "data from home tab");
                    PageManager.instance().startPageForResult(pageIntent);
                    break;
                case 1:
                    PageManager.instance().startActivity(getActivity(), "Module2Activity");
                    break;
                case 2:
                    pageIntent = new PageIntent("DialogDemoPage");
                    pageIntent.putExtra("data", "data from dialog tab");
                    PageManager.instance().startPageForResult(pageIntent);
                    break;
                case 3:
                    pageIntent = new PageIntent("ThirdPartPage");
                    pageIntent.putExtra("data", "data from third part tab");
                    PageManager.instance().startPageForResult(pageIntent);
                    break;
            }
        }
    }

    public void onPageResult(PageResult pageResult) {
        if (pageResult != null) {
            getArguments().putString("text", pageResult.getString("data"));
            mTvText.setText(pageResult.getString("data"));
        }
    }

}
