package com.moon.mytools.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.moon.mytools.R;

import java.util.Objects;

/**
 * Date: 2021/10/18 11:15 上午
 * Author: Moon
 * Desc: 视频列表
 */
@SuppressLint("NonConstantResourceId")
public class WebListActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weblist);

        findViewById(R.id.btn_nunu).setOnClickListener(this);
        findViewById(R.id.btn_nunu).setOnLongClickListener(this);
        findViewById(R.id.btn_yinghua).setOnClickListener(this);
        findViewById(R.id.btn_yinghua).setOnLongClickListener(this);
        findViewById(R.id.btn_91).setOnClickListener(this);
        findViewById(R.id.btn_xvideo).setOnClickListener(this);
        findViewById(R.id.btn_add).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                AddUrl();
                break;
            case R.id.btn_nunu:
                loadUrl("https://www.nunuyy.cc/");
                break;
            case R.id.btn_yinghua:
                loadUrl("http://m.yhdm.io/");
                break;
            case R.id.btn_91:
                loadUrl("https://91porn.com/");
                break;
            case R.id.btn_xvideo:
                loadUrl("https://www.xvideos.com/");
                break;
            default:
                loadUrl("https://www.baidu.com");
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.btn_nunu:
                findViewById(R.id.btn_91).setVisibility(View.VISIBLE);
                findViewById(R.id.btn_xvideo).setVisibility(View.VISIBLE);
                return true;
            case R.id.btn_yinghua:
                findViewById(R.id.btn_91).setVisibility(View.GONE);
                findViewById(R.id.btn_xvideo).setVisibility(View.GONE);
                return true;
        }
        return false;
    }

    private void loadUrl(String url) {
        WebViewActivity.open(this, url);
    }

    @SuppressLint("SetTextI18n")
    private void AddUrl() {
        final boolean[] https = {false};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_url, null);
        AppCompatEditText etUrl = (AppCompatEditText) view.findViewById(R.id.et_url);
        view.findViewById(R.id.btn_http).setOnClickListener(v -> {
            String text = https[0] ? "https://" : "http://";
            etUrl.setText(text);
            etUrl.setSelection(text.length());
            https[0] = !https[0];
        });
        view.findViewById(R.id.btn_url).setOnClickListener(v -> {
            String url = Objects.requireNonNull(etUrl.getText()).toString();
            WebViewActivity.open(WebListActivity.this, url);
        });
        builder.setView(view);
        AlertDialog dialog = builder.show();

        dialog.setCanceledOnTouchOutside(false);
    }

    public static void open(Context context) {
        context.startActivity(new Intent(context, WebListActivity.class));
    }
}
