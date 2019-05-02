package com.suyf.home.page;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.suyf.home.R;
import com.suyf.home.base.BasePage;
import com.suyf.home.presenter.GoodsListPresenter;
import com.suyf.tiny.page.PageIntent;

public class GoodsListPage extends BasePage<GoodsListPresenter> {
    private RecyclerView mRecyclerView;


    @Override
    protected int setLayout() {
        return R.layout.page_goods_list;
    }

    @Override
    protected void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }

    @Override
    protected void initEvent() {
        mRecyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new Holder(LayoutInflater.from(getActivity()).inflate(R.layout.item_goods, viewGroup, false));
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
                Holder holder = (Holder) viewHolder;
                holder.mTextView.setText("goods item :" + i);
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PageIntent intent = new PageIntent(GoodsListPage.this, GoodsDetailPage.class);
                        intent.putExtra("data", "goods list:" + i);
                        startPage(intent);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return 100;
            }
        });
    }

    @Override
    protected GoodsListPresenter createPresenter() {
        return new GoodsListPresenter();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView mTextView;

        public Holder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.tv_text);
        }
    }
}
