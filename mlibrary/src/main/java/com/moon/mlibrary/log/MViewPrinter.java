package com.moon.mlibrary.log;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moon.mlibrary.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 9/16/21 11:23 AM
 * Author: Moon
 * Desc: 界面日志打印器
 */
public class MViewPrinter implements MLogPrinter {

    private final RecyclerView recyclerView;
    private final LogAdapter adapter;
    private final MViewPrinterProvider viewProvider;

    public MViewPrinter(Activity activity) {
        FrameLayout rootView = activity.findViewById(android.R.id.content);
        recyclerView = new RecyclerView(activity);
        adapter = new LogAdapter(LayoutInflater.from(recyclerView.getContext()));
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        viewProvider = new MViewPrinterProvider(rootView, recyclerView);
    }

    @Override
    public void print(@NotNull MLogConfig config, int level, String tag, @NotNull String printString) {
        // 将log展示添加到recycleView
        adapter.addItem(new MLogMo(System.currentTimeMillis(), level, tag, printString));
        // 滚动到对应的位置
        recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
    }

    public MViewPrinterProvider getViewProvider() {
        return viewProvider;
    }

    private static class LogAdapter extends RecyclerView.Adapter<LogViewHolder> {

        private final LayoutInflater inflater;
        private final List<MLogMo> logList = new ArrayList<>();

        public LogAdapter(LayoutInflater inflater) {
            this.inflater = inflater;
        }

        public void addItem(MLogMo item) {
            logList.add(item);
            notifyItemInserted(logList.size() - 1);
        }

        @NonNull
        @Override
        public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = inflater.inflate(R.layout.mlog_item, parent, false);
            return new LogViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
            MLogMo item = logList.get(position);
            int txtColor = getHighLightColor(item.level);
            holder.tvTag.setTextColor(txtColor);
            holder.tvMsg.setTextColor(txtColor);
            holder.tvTag.setText(item.getFlattened());
            holder.tvMsg.setText(item.log);
        }

        /**
         * 根据Level等级获取日志文本颜色
         *
         * @param logLevel 日志等级
         * @return 颜色值
         */
        private int getHighLightColor(int logLevel) {
            int highlight;
            switch (logLevel) {
                case MLogType.V:
                    highlight = 0xffbbbbbb;
                    break;
                case MLogType.D:
                    highlight = 0xffffffff;
                    break;
                case MLogType.I:
                    highlight = 0xff6a8759;
                    break;
                case MLogType.W:
                    highlight = 0xffbbb529;
                    break;
                case MLogType.E:
                    highlight = 0xffff6b68;
                    break;
                default:
                    highlight = 0xffffff00;
                    break;
            }
            return highlight;
        }

        @Override
        public int getItemCount() {
            return logList.size();
        }
    }

    private static class LogViewHolder extends RecyclerView.ViewHolder {

        TextView tvTag;
        TextView tvMsg;

        public LogViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTag = itemView.findViewById(R.id.tag);
            tvMsg = itemView.findViewById(R.id.message);
        }
    }
}
