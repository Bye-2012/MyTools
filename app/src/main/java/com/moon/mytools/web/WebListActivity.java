package com.moon.mytools.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.moon.mytools.R;
import com.moon.mytools.utils.AESUtils;
import com.moon.mytools.utils.KeyBordUtil;
import com.moon.mytools.utils.SharedPreferencesUtil;

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
        findViewById(R.id.btn_xvideo).setOnClickListener(this);
        findViewById(R.id.btn_add).setOnClickListener(this);
        findViewById(R.id.btn_bd).setOnClickListener(this);
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
            case R.id.btn_xvideo: {
                String s = SharedPreferencesUtil.getString("MHNvtk9aqhh6mpwf7AOApUP+spFUEYSuvnGO1WDOZQc=");
                if (s != null && s.startsWith("http")) {
                    loadUrl(s);
                } else {
                    inputPwd("MHNvtk9aqhh6mpwf7AOApUP+spFUEYSuvnGO1WDOZQc=");
                }
                break;
            }
            case R.id.btn_bd:
                loadUrl("https://www.bd2020.com/");
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
                findViewById(R.id.btn_xvideo).setVisibility(View.VISIBLE);
                return true;
            case R.id.btn_yinghua:
                findViewById(R.id.btn_xvideo).setVisibility(View.GONE);
                return true;
        }
        return false;
    }

    private void loadUrl(String url) {
        WebViewActivity.open(this, url);
    }

    private AlertDialog inputPwdDialog;

    private void inputPwd(String aesUrl) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_input_pwd, null);
        builder.setView(view);
        AppCompatEditText etUrlPwd = (AppCompatEditText) view.findViewById(R.id.et_url_pwd);
        view.findViewById(R.id.btn_pwd_ensure).setOnClickListener(v -> {
            String pwd = Objects.requireNonNull(etUrlPwd.getText()).toString();
            pwd = pwd + pwd + pwd + pwd;
            String result = AESUtils.decrypt(pwd, aesUrl);
            if (result == null || !result.startsWith("http")) {
                Toast.makeText(WebListActivity.this, "不对啊，兄弟", Toast.LENGTH_SHORT).show();
            } else {
                SharedPreferencesUtil.put(aesUrl, result);
                loadUrl(result);
                inputPwdDialog.dismiss();
            }
        });
        inputPwdDialog = builder.show();
        inputPwdDialog.setCanceledOnTouchOutside(false);
        KeyBordUtil.showSoftInput(this, etUrlPwd);
    }

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
            if (!url.startsWith("http")) {
                Toast.makeText(WebListActivity.this, "必须Http或Https开头", Toast.LENGTH_SHORT).show();
                return;
            }
            WebViewActivity.open(WebListActivity.this, url);
        });
        builder.setView(view);
        AlertDialog dialog = builder.show();
        dialog.setCanceledOnTouchOutside(false);
        KeyBordUtil.showSoftInput(this, etUrl);
    }

    public static void open(Context context) {
        context.startActivity(new Intent(context, WebListActivity.class));
    }
}
