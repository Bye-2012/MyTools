package com.moon.mytools.demo;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moon.common.ui.component.MBaseActivity;
import com.moon.mui.refresh.MRefresh;
import com.moon.mui.refresh.MRefreshLayout;
import com.moon.mui.refresh.MTextOverView;
import com.moon.mytools.R;

import java.util.ArrayList;

/**
 * Date: 2021/10/20 10:35 上午
 * Author: Moon
 * Desc:
 */
public class RefreshDemoActivity extends MBaseActivity {

    private MRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);

        mRefreshLayout = findViewById(R.id.mrl);
        mRecyclerView = findViewById(R.id.rv_show);

        mRefreshLayout.setRefreshOverView(new MTextOverView(this));
        mRefreshLayout.setRefreshListener(new MRefresh.MRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.refreshFinished();
                    }
                }, 2000);
            }

            @Override
            public boolean enableRefresh() {
                return true;
            }
        });
        mRefreshLayout.setDisableRefreshScroll(false);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add("这是第" + i + "个元素");
        }
        MyAdapter adapter = new MyAdapter(data);
        mRecyclerView.setAdapter(adapter);
    }

    static class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        private final ArrayList<String> mData;

        public MyAdapter(ArrayList<String> mData) {
            this.mData = mData;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_refresh, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.mTextView.setText(mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mTextView = itemView.findViewById(R.id.tv_title);
        }
    }
}
